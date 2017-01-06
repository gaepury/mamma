/**
 * http://usejsdoc.org/
 */
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


//리뷰 완성==================================================================================================================================
//리뷰 뷰(pathparamer로 뭐를 보내줄까)
router.get('/:p_nickname', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{		
			console.time('a');
			var today=new Date();
			var reviewRank = [] ;
			var similar_info;
			var today=new Date();
			//분유 조회수순으로 3개만
			//분유 조회수
			connection.query('select drymilk.milk_image, drymilk.milk_company, drymilk.milk_name from drymilk, review where drymilk.milk_id = review.milk_id group by milk_name order by review_num desc', function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("리뷰가 많은 분유가 없음."); //있을수 없다.
						reviewRank=[{}];
						similar_info={};

						res.send({result:{reviewrank : reviewRank,similar:similar_info}});
						connection.release();

						//여기서 500을 날려야 하는지 모르겠음
					}
					else{
						if(rows.length>=3){
							for(var i = 0 ; i < 3 ; i++){
								reviewRank.push({
									milk_image : rows[i].milk_image,
									milk_name : rows[i].milk_name,
									milk_company : rows[i].milk_company
								});
							}
						}else{
							for(var i = 0 ; i < rows.length ; i++){
								reviewRank.push({
									milk_image : rows[i].milk_image,
									milk_name : rows[i].milk_name,
									milk_company : rows[i].milk_company
								});
							}
						}	
						connection.query('select baby.b_birth ,worry1,worry2,worry3 from baby ,parent where baby.p_email=parent.p_email AND parent.p_nickname = ?',[req.params.p_nickname], function(error, rows){
							if(error){
								console.log("query error");
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								if(rows[0] == null){
									console.log("아기가 없음");
									connection.release();
									similar_info={};
									res.send({result:{reviewrank : reviewRank,similar:similar_info}});
									//여기서 500을 날려야 하는지 모르겠음
								}
								else{
									console.log("내아기");
									var worry1=rows[0].worry1;
									worry1 = worry1 =="" ? "-":worry1; 
									console.log(worry1);
									var worry2=rows[0].worry2;
									worry2 = worry2 =="" ? "-":worry2;
									console.log(worry2);
									var worry3=rows[0].worry3;
									worry3 = worry3 =="" ? "-":worry3;
									console.log(worry3);
									var m_birth=new Date(rows[0].b_birth);
									var m_age=((today.getFullYear()-m_birth.getFullYear())*12 + Math.abs(today.getMonth()-m_birth.getMonth()));
									connection.query('select b_birth,b_id from parent, baby where parent.p_email=baby.p_email AND p_nickname != ?',[req.params.p_nickname], function(error, rows){
										if(error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}else{
											console.log("내아기X");
											if(rows.length == 0){
												console.log("아이가 없음");
												connection.release();
												res.send({result:{reviewrank : reviewRank,similar:similar_info}});
												//여기서 500을 날려야 하는지 모르겠음
											}else{
												var s=[];
												for(var i=0;i<rows.length;i++){
													var o_birth=new Date(rows[i].b_birth);
													var o_age=((today.getFullYear()-o_birth.getFullYear())*12 + Math.abs(today.getMonth()-o_birth.getMonth()));
													if(Math.abs(m_age-o_age)<=2){
														console.log(Math.abs(m_age-o_age));
														s.push(rows[i].b_id);
														console.log(s);
													}
												}
												var start="(";
												for(var i=0;i<s.length;i++){
													if(i==s.length-1){
														start+=s[i];
													}
													else{
														start+=s[i]+",";
													}
												}
												start+=")";
												if(start == "()"){
													start = "(NULL)";
												}
												////////////
												connection.query('select distinct b_id from baby,parent,review where baby.p_email= parent.p_email AND review.review_writer in (select parent.p_nickname from baby ,parent where baby.p_email=parent.p_email AND parent.p_nickname!=?) AND (worry1 in (?,?,?) OR worry2 in (?,?,?) OR worry3 in (?,?,?)) AND baby.b_id in '+start, [req.params.p_nickname,worry1,worry2,worry3,worry1,worry2,worry3,worry1,worry2,worry3], function(error, rows){
													if (error){
														console.log("Connection Error" + error);
														res.sendStatus(500);
														connection.release();
													}else {
														if (rows.length =0){
															console.log("비슷한 아이가 없음");
															connection.query('select review.title,review.review_grade,review.review_id from review,baby,parent where review.review_writer =parent.p_nickname AND baby.p_email= parent.p_email order by review_hit desc LIMIT 1',
																	function(error, rows) {
																if(error) {
																	console.log("Connection Error" + error);
																	res.sendStatus(500);
																	connection.release();
																}
																else {
																	//조회수가 가장 높은 분유의 대한 정보를 뿌려줌
																	var title=rows[0].title;
																	var review_grade=rows[0].review_grade;
																	var review_id=rows[0].review_id;
																	connection.query('select drymilk.milk_image,drymilk.milk_name from drymilk where drymilk.milk_id= (select review.milk_id from review,baby,parent where review.review_writer =parent.p_nickname AND baby.p_email= parent.p_email order by review_hit desc LIMIT 1)',
																			function (error, rows) {
																		if(error) {
																			console.log("Connection Error" + error);
																			res.sendStatus(500);
																			connection.release();
																		}
																		else {
																			var milk_image=rows[0].milk_image;
																			var milk_name=rows[0].milk_name;
																			similar_info = {
																					"nickname":null,
																					"age":null,
																					"worry":null,
																					"milk_image":milk_image,
																					"milk_name":milk_name,
																					"review_grade":review_grade,
																					"review_id":review_id,
																					"title":title

																			};
																			console.timeEnd('a');
																			console.log(similar_info);
																			res.status(200).send({result:{reviewrank : reviewRank,similar:similar_info}});
																			connection.release();
																		}
																	});
																}
															});
														}
														else{
															connection.query('select worry1,worry2,worry3,parent.p_nickname,baby.b_birth,b_id, review.review_id, review.title,review.good, review.bad, review.tip, review.review_hit, review.review_grade from review, baby, parent where review.review_writer = parent.p_nickname and baby.p_email= parent.p_email and b_id in(select distinct b_id from baby,parent,review where baby.p_email= parent.p_email AND review.review_writer in (select parent.p_nickname from baby ,parent where baby.p_email=parent.p_email AND parent.p_nickname!=?) AND (worry1 in (?,?,?) OR worry2 in (?,?,?) OR worry3 in (?,?,?)) AND baby.b_id in (select b_id from baby where b_id in '+start+')) order by review_hit desc limit 1',[req.params.p_nickname,worry1,worry2,worry3,worry1,worry2,worry3,worry1,worry2,worry3], function(error, rows){
																if(error){
																	console.log("Connection Error" + error);
																	res.sendStatus(500);
																	connection.release();
																}else{
																	if(rows[0] == null){
																		console.log("2)비슷한 아이는 있으나 비슷한 아이의 엄마가 남긴 리뷰가 없음");
																		//단순히 review_hit가 많은걸 뽑아냄
																		connection.query('select review.title,review.review_grade from review,baby,parent where review.review_writer =parent.p_nickname AND baby.p_email= parent.p_email order by review_hit desc LIMIT 1',
																				function(error, rows) {
																			if(error) {
																				console.log("Connection Error" + error);
																				res.sendStatus(500);
																				connection.release();
																			}
																			else {
																				//조회수가 가장 높은 분유의 대한 정보를 뿌려줌
																				var title=rows[0].title;
																				var review_grade=rows[0].review_grade;
																				connection.query('select drymilk.milk_image,drymilk.milk_name from drymilk where drymilk.milk_id= (select review.milk_id from review,baby,parent where review.review_writer =parent.p_nickname AND baby.p_email= parent.p_email order by review_hit desc LIMIT 1)',
																						function (error, rows) {
																					if(error) {
																						console.log("Connection Error" + error);
																						res.sendStatus(500);
																						connection.release();
																					}
																					else {
																						var milk_image=rows[0].milk_image;
																						var milk_name=rows[0].milk_name;

																						similar_info = {};
																						console.timeEnd('a');
																						console.log(similar_info);
																						res.status(200).send({result:{reviewrank : reviewRank,similar:similar_info}});
																						connection.release();
																					}
																				});
																			}
																		});
																		//여기서 500을 날려야 하는지 모르겠음
																	}else{ //비슷한 아이의 정보
																		var title=rows[0].title;
																		var review_id=rows[0].review_id;
																		var review_grade=rows[0].review_grade;
																		var nickname= rows[0].p_nickname;
																		console.log(nickname);
																		var b_birth=rows[0].b_birth;
																		var tmp=new Date(b_birth);
																		var age=((today.getFullYear()-tmp.getFullYear())*12+Math.abs(today.getMonth()-tmp.getMonth()));
																		console.log(age+'ab');
																		var other_baby_worry1=rows[0].worry1;
																		var other_baby_worry2=rows[0].worry2;
																		var other_baby_worry3=rows[0].worry3;
																		connection.query('select drymilk.milk_company,drymilk.milk_image, drymilk.milk_name, review.title,review.good, review.bad, review.tip, review.review_hit, review.review_grade from review, baby, parent, drymilk where drymilk.milk_id = review.milk_id and review.review_writer = parent.p_nickname and baby.p_email= parent.p_email and b_id in(select distinct b_id from baby,parent,review where baby.p_email= parent.p_email AND review.review_writer in (select parent.p_nickname from baby ,parent where baby.p_email=parent.p_email AND parent.p_nickname!=?) AND (worry1 in (?,?,?) OR worry2 in (?,?,?) OR worry3 in (?,?,?)) AND baby.b_id in (select b_id from baby where b_id in '+start+')) order by review_hit desc limit 1',[req.params.p_nickname,worry1,worry2,worry3,worry1,worry2,worry3,worry1,worry2,worry3], function(error, rows){
																			if(error){
																				console.log("Connection Error" + error);
																				res.sendStatus(500);
																				connection.release();
																			}else{
																				console.log(rows);
																				if(rows[0] == null){
																					console.log("분유 없음");//있을수 없음
																					similar_info={};
																					res.status(200).send({result:{reviewrank : reviewRank,similar:similar_info}});
																					connection.release();
																					//여기서 500을 날려야 하는지 모르겠음
																				}else{
																					var milk_image=rows[0].milk_image;
																					var milk_name=rows[0].milk_name;
																					var milk_company=rows[0].milk_company;
																				}
																			}
																			similar_info = {
																					"nickname":nickname,
																					"age":age,
																					"worry":[other_baby_worry1,other_baby_worry2,other_baby_worry3],
																					"milk_image":milk_image,
																					"milk_name":milk_name,
																					"review_grade":review_grade,
																					"review_id":review_id,
																					"title":title
																			};
																			console.timeEnd('a');
																			res.status(200).send({result:{reviewrank : reviewRank,similar:similar_info}});
																			connection.release();
																		});
																	}
																}
															});

														}
													}
												});
												/////////////////
											}
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


module.exports = router;
