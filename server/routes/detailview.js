var express = require('express');
var mysql = require('mysql');
var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var db_config = require('../config/db_config.json');
var router = express.Router();

aws.config.loadFromPath('./config/aws_config.json');

var s3 = new aws.S3({signatureVersion : "v4"});

//var s3 = new aws.S3();

var upload = multer({
	storage: multerS3({
		s3: s3,
		bucket: 'milklogo',
		acl: 'public-read',
		key: function (req, file, cb) {
			cb(null, Date.now() + '.' + file.originalname.split('.').pop());
		}
	})
});

var pool = mysql.createPool({
	host : db_config.host,
	port : db_config.port,
	user : db_config.user,
	password : db_config.password,
	database : db_config.database,
	connectionLimit : db_config.connectionLimit
});


//분유 상세보기 완료==================================================================================================================================
//분유 상세보기
router.get('/:milk_name', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var milk_info, ingredient_info=[], review_info;

			//분유 정보 가져오기
			connection.query('select drymilk.milk_image, drymilk.milk_name, drymilk.milk_company, drymilk.milk_grade from drymilk where drymilk.milk_name=?', [req.params.milk_name], function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("분유 없음");
						connection.release();
						
						milk_info={};
						ingredient_info=[];
						review_info=[{}];
						res.send({result :{milk:milk_info, ingredient:ingredient_info, review: review_info}});
						//여기서 500을 날려야하는지 잘 모르겠음
					}else{
						milk_info = {
								"milk_image":rows[0].milk_image,
								"milk_name":rows[0].milk_name,
								"milk_company": rows[0].milk_company,
								"milk_grade": rows[0].milk_grade
						};

						//성분이름정보 가져오기
						connection.query('select dryingredient.ingre_name from dryingredient where dryingredient.milk_name = ? and content order by content desc ',[req.params.milk_name], function(error, rows){
							if(error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								if(rows[0] == null){
									console.log("성분이 없음");
									connection.release();
									ingredient_info=[];
									review_info=[{}];
									res.send({result :{milk:milk_info, ingredient:ingredient_info, review: review_info}});
									//여기서 500을 날려야 하는지 모르겠음
								}
								else{
									for (var i = 0; i < 2 ; i++ ){
										ingredient_info.push(rows[i].ingre_name+"");
									}

									//특정 분유에 대한 조회수 증가
									connection.query('update drymilk set milk_hit = milk_hit + 1 where milk_name = ?',[req.params.milk_name], function(error, rows){
										if(error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else{
											console.log(req.params.milk_name + "milk_hit upate complete");
											//리뷰 가져오기
											connection.query('select review.review_id,review.title, review.review_writer, review.review_grade from drymilk, review where drymilk.milk_id=review.milk_id and drymilk.milk_name= ? order by review_hit desc ',[req.params.milk_name], function(error, rows){
												if(error){
													console.log("Connection Error" + error);
													res.sendStatus(500);
													connection.release();
												}
												else{
													if(rows[0] == null){
														console.log("리뷰가 없음");
														review_info= [{}];
														res.status(200).send({result : {milk:milk_info, ingredient:ingredient_info, review: review_info}});
														connection.release();
														//여기서 500을 날려야 하는지 모르겠음
													}
													else{
														review_info = rows;
														res.status(200).send({result :{milk:milk_info, ingredient:ingredient_info, review: review_info}});
														connection.release();
													}
												}
											});
										}
									});
								}
							}
						});
					}
				}
			});
		}
	});
});

//분유의 모든 성분 보여주기
router.get('/ingredient/:milk_name', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var ingredient_info=[];
			//성분이름정보 가져오기
			connection.query('select dryingredient.ingre_name, dryingredient.content, dryingredient.content_unit from dryingredient where dryingredient.milk_name = ? order by ingre_name asc',[req.params.milk_name], function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("성분이 없음");
						connection.release();
						ingredient_info=[{}];
						res.send({result:{ingredient:ingredient_info}});
					}
					else{
						for (var i in rows){
							ingredient_info.push({
								"ingre_name": rows[i].ingre_name,
								"ingre_content": rows[i].content,
								"content_unit": rows[i].content_unit
							});
						}
						res.status(200).send({result :{ingredient:ingredient_info}});
						connection.release();
					}
				}
			});
		}
	});
});

//즐겨찾기 추가
router.post('/bookmark', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			connection.query('INSERT INTO bookmark(p_nickname, milk_name) values(?,?)', [req.body.p_nickname, req.body.milk_name],
					function(error, rows) {
				if (error){
					console.log("이미 즐겨찾기를 하였음.");
					console.log("Connection Error" + error);
					res.status(408).send({result:false});
				}
				else {
					res.status(201).send({result : true});
					/*http status*/
				}
				connection.release();
			});
		}
	});
});

module.exports = router;
