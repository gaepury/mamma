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


//홈 뷰 완성==================================================================================================================================
//홈 뷰
router.get('/', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var searchRank = [], reviewRank = [];
			//분유 조회수순으로 3개만
			connection.query('select drymilk.milk_image, drymilk.milk_company, drymilk.milk_name from drymilk order by milk_hit desc', function(error, rows){
				if(error){
					console.log('a');
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("많이 찾은 분유가 없음");
						connection.release();
						searchRank=[{}];
						reviewRank=[{}];
						dayingre_info={};
						res.send({result:{searchrank: searchRank, reviewRank: reviewRank , dayingredient : dayingre_info}});
						//여기서 500을 날려야 하는지 모르겠음
					}
					else{
						console.log('b');
						for(var i = 0 ; i < 3 ; i++){
							searchRank.push({
								milk_image : rows[i].milk_image,
								milk_company : rows[i].milk_company,
								milk_name : rows[i].milk_name
							});
						}
						//리뷰가 많은순으로 3개만(by review_num)
						connection.query('select drymilk.milk_image, drymilk.milk_name from drymilk, review where drymilk.milk_id = review.milk_id group by milk_name order by review_num desc', function(error, rows){
							if(error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								if(rows[0] == null){
									console.log("리뷰가 많은 분유가 없음");
									connection.release();
									reviewRank=[{}];
									dayingre_info={};
									res.send({result:{searchrank: searchRank, reviewRank: reviewRank , dayingredient : dayingre_info}});
									//여기서 500을 날려야 하는지 모르겠음
								}
								else{
									if(rows.length>=3){
										for(var i = 0 ; i < 3 ; i++){
											reviewRank.push({
												milk_image : rows[i].milk_image,
												milk_name : rows[i].milk_name
											});
										}
									}else{
										for(var i = 0 ; i < rows.length ; i++){
											reviewRank.push({
												milk_image : rows[i].milk_image,
												milk_name : rows[i].milk_name
											});
										}
									}	
								}
								connection.query('select ingredient.ingre_name,ingredient.overview from ingredient where ingredient.overview != "준비중"', function(error, rows){
									if(error){
										console.log("Connection Error" + error);
										res.sendStatus(500);
										connection.release();
									}else{
										if(rows[0]==null){
											console.log("오늘의 성분 분유가 없음");
											connection.release();
											dayingre_info={};
											res.send({result:{searchrank: searchRank, reviewRank: reviewRank , dayingredient : dayingre_info}});
										}else{
											console.log('d');
											console.log(rows.length);
											var rand = Math.floor(Math.random()*rows.length);
											var dayingre_info = rows[rand];
											console.log(rand);
											connection.release();
											res.status(200).send({result:{searchrank: searchRank, reviewRank: reviewRank , dayingredient : dayingre_info}});
										}
									}
								});
							}
						});
					}
				}
			});
		}
	});
});


module.exports = router;
