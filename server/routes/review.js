var express = require('express');
var mysql = require('mysql');
var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var db_config = require('../config/db_config.json');
var router = express.Router();

aws.config.loadFromPath('./config/aws_config.json');

var s3 = new aws.S3({signatureVersion : "v4"});

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

//리뷰 작성
router.post('/post/:milk_name/:review_writer', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			var tmp_milk_id;

			connection.query('select milk_id from drymilk where milk_name =?', [req.params.milk_name],function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					tmp_milk_id = rows[0]; // jsonObject 형태 . milk_id 를 뽑내야됨.

					connection.query('update drymilk set review_num=review_num+1 where milk_id= ? ', [tmp_milk_id.milk_id], function(error, rows){
						if (error){
							console.log("Connection Error" + error);
							res.sendStatus(500);
							connection.release();
						}
						else {
							connection.query('insert into review(good,bad,tip,title,review_grade,milk_id,review_writer) values(?,?,?,?,?,?,?)', [req.body.good, req.body.bad, req.body.tip, req.body.title, req.body.review_grade, tmp_milk_id.milk_id, req.params.review_writer],
									function(error, rows){
								if (error){
									console.log("Connection Error" + error);
									res.status(500);
									connection.release();
								}else {
									var tmp_milk_id;
									connection.query('select AVG(review_grade) as avg_review_grade from review, drymilk where review.milk_id=drymilk.milk_id AND drymilk.milk_name = ? group by milk_name',[req.params.milk_name], function(error, rows){
										if (error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else {
											console.log(rows);
											var avg_adjust;
											var avg = rows[0].avg_review_grade;


											var flo= Math.floor(avg);
											var result = avg-flo;
											if(result+0.25>1){
												avg_adjust=flo+1;
											}else if(result+0.25>0.5){
												avg_adjust=flo+0.5;
											}else{
												avg_adjust=flo;
											}
											console.log(avg_adjust);
											connection.query('update drymilk set milk_grade = ? where drymilk.milk_name= ? ',[avg_adjust,req.params.milk_name], function(error, rows){
												if (error){
													console.log("Connection Error" + error);
													res.sendStatus(500);
													connection.release();
												}
												else {
													res.status(201).send({result : true});
													connection.release();
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});

		}
	});
});

//리뷰 작성시 필요한 사진이랑 이름
router.get('/post/:milk_name/:review_writer', function(req, res,next){
	pool.getConnection(function(error, connection){
		if(error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var drymilk_Info = {};
			var Worry = [];

			//분유에 대한 정보 가져오기
			connection.query('select drymilk.milk_image, drymilk.milk_company from drymilk where drymilk.milk_name = ?',[req.params.milk_name], function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("분유에 대한 정보가 없습니다.");
						res.status(200).send({result:{drymilk_info : drymilk_Info, worry: Worry}});
						connection.release();
					}
					else{
						drymilk_Info = {
								"milk_image" : rows[0].milk_image,
								"milk_name" : rows[0].milk_name,
								"milk_company" : rows[0].milk_company
						};

						//아이의 고민거리 가져오기
						connection.query('select baby.worry1, baby.worry2, baby.worry3 from baby, parent where baby.p_email = parent.p_email and parent.p_nickname = ?',[req.params.review_writer],function(error, rows){
							if(error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								if(rows[0] == null){
									console.log("아이에 대한 고민거리를 가져오지 못했습니다.");
									res.status(200).send({result:{drymilk_info: drymilk_Info , worry: Worry}});
								}
								else{
									Worry=[
									       rows[0].worry1,
									       rows[0].worry2,
									       rows[0].worry3
									       ];

									res.status(200).send({result: {drymilk_info: drymilk_Info, worry: Worry}});
									connection.release();
								}
							}
						});
					}
				}
			});
		}
	});
});



//리뷰 글 상세보기 (분유이름, 리뷰아이디)
router.get('/:milk_name/:review_id/:p_nickname', function(req, res, next) {
	pool.getConnection(function(error, connection){
		var datas = [];
		var good_check;
		var bad_check;
		var milk_name, milk_company, title, goodnum, badnum, review_grade, worry1,worry2,worry3, good,bad,tip,good_check,bad_check,review_id;

		var a='c';
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {

			connection.query('select drymilk.milk_image,drymilk.milk_company,review.title, goodnum, badnum, review_grade, worry1, worry2, worry3, good, bad, tip from review, parent, baby,drymilk where review.milk_id = (select drymilk.milk_id from drymilk where milk_name = ? ) and review_id=? and review.review_writer = parent.p_nickname and parent.p_email= baby.p_email and review.milk_id=drymilk.milk_id ',
					[req.params.milk_name, req.params.review_id],
					function(error, rows){
				if (error) {
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if (rows[0] != null){
						
						milk_image=rows[0].milk_image,
						milk_company=rows[0].milk_company,
						title=rows[0].title,
						goodnum = rows[0].goodnum,
						badnum = rows[0].badnum,
						review_grade = rows[0].review_grade,
						worry1 = rows[0].worry1,
						worry2 = rows[0].worry2,
						worry3 = rows[0].worry3,
						good = rows[0].good,
						bad = rows[0].bad,
						tip = rows[0].tip,

						connection.query('select * from review_like where review_id = ? AND p_email = (select p_email from parent where p_nickname= ?)',
								[req.params.review_id,req.params.p_nickname], function(error, rows){
							if (error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else {
								//좋아요를 누른 적이 없음.
								if(rows[0]==null){
									connection.query('select * from review_bad where review_id = ? AND p_email = (select p_email from parent where p_nickname= ?)',
											[req.params.review_id,req.params.p_nickname], function(error, rows){
										if (error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else {
											//좋아요,싫어요 둘다 누른적이 없음
											if(rows[0]==null){
												console.log('a');
												good_check=false;
												bad_check=false;
												datas={
														"milk_image":milk_image,
														"milk_company":milk_company,
														"title":title,
														"goodnum" : goodnum,
														"badnum" : badnum,
														"review_grade" : review_grade,
														"worry1" : worry1,
														"worry2" : worry2,
														"worry3" : worry3,
														"good" : good,
														"bad" : bad,
														"tip" : tip,
														"good_check":good_check,
														"bad_check":bad_check
												};
												connection.query('UPDATE review SET review_hit = review_hit+1 WHERE review_id=?',
														[req.params.review_id], function(error, rows){
													if (error){
														console.log("Connection Error" + error);
														res.sendStatus(500);
														connection.release();
													}
													else {
														console.log('test');
														if (rows!=null){
															console.log("review_hit update success");
														}
														res.status(200).send({result:{review : datas}});
														connection.release();
													}
												});

											}
											//싫어요를 누름.
											else{
												console.log('b');
												good_check=false;
												bad_check=true;
												datas={
														"milk_image":milk_image,
														"milk_company":milk_company,
														"title":title,
														"goodnum" : goodnum,
														"badnum" : badnum,
														"review_grade" : review_grade,
														"worry1" : worry1,
														"worry2" : worry2,
														"worry3" : worry3,
														"good" : good,
														"bad" : bad,
														"tip" : tip,
														"good_check":good_check,
														"bad_check":bad_check
												};
												connection.query('UPDATE review SET review_hit = review_hit+1 WHERE review_id=?',
														[req.params.review_id], function(error, rows){
													if (error){
														console.log("Connection Error" + error);
														res.sendStatus(500);
														connection.release();
													}
													else {
														console.log('test');
														if (rows!=null){
															console.log("review_hit update success");
														}
														res.status(200).send({result:{review : datas}});
														connection.release();
													}
												});
											}
										}
									});
								}
								//좋아요를 누름.
								else{
									console.log('c');
									good_check=true;
									bad_check=false;
									datas={
											"milk_image":milk_image,
											"milk_company":milk_company,
											"title":title,
											"goodnum" : goodnum,
											"badnum" : badnum,
											"review_grade" : review_grade,
											"worry1" : worry1,
											"worry2" : worry2,
											"worry3" : worry3,
											"good" : good,
											"bad" : bad,
											"tip" : tip,
											"good_check":good_check,
											"bad_check":bad_check
									};
									connection.query('UPDATE review SET review_hit = review_hit+1 WHERE review_id=?',
											[req.params.review_id], function(error, rows){
										if (error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else {
											console.log('test');
											if (rows!=null){
												console.log("review_hit update success");
											}
											res.status(200).send({result:{review : datas}});
											connection.release();
										}
									});
								}
							}
						});

						// 리뷰 상세 보기 누르면 조회수 증가하기

					}

					else{
						datas={};
						res.status(200).send({result : {review : datas}});
						connection.release();
					}
				}
			});
		}
	});
});

router.delete('/delete/:review_id',function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			console.log('a');
			connection.query('select review.review_grade, drymilk.review_num, review.milk_id, drymilk.milk_grade from review, drymilk where drymilk.milk_id = review.milk_id and review.review_id = ?', [req.params.review_id], function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log('b');
						res.status(203).send({result: false});
						connection.release();
					}
					else{
						console.log('c');
						var tmp_milk_grade;
						var ori_milk_grade = rows[0].milk_grade;
						console.log(ori_milk_grade);
						var tmp_review_num = rows[0].review_num;
						console.log(tmp_review_num);
						console.log(tmp_review_grade);
						var tmp_milk_id = rows[0].milk_id;
						var tmp_review_grade = rows[0].review_grade;
						tmp_milk_grade = ((ori_milk_grade * tmp_review_num)-tmp_review_grade)/tmp_review_num-1;
						console.log(tmp_milk_id);
						console.log(tmp_milk_grade);
						//분유 총 평점 바꾸기, 리뷰 개수 -1
						connection.query('update drymilk set milk_grade = ?, review_num = review_num-1 where milk_id = ?',[tmp_milk_grade, tmp_milk_id],function(error, rows){
							if(error){
								console.log(error);
								console.log(rows);
								console.log('f');
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								console.log('d');
								//review_id에 해당하는 리뷰, 리뷰의 좋아요, 리뷰의 싫어요 삭제
								connection.query('delete from review where review.review_id=?',[req.params.review_id], function(error, rows){
									if(error){
										console.log("Connection Error" + error);
										res.sendStatus(500);
										connection.release();
									}
									else{
										console.log('e');
										res.status(203).send({result: true});
										connection.release();
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
