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

//리뷰 싫어요버튼 누르기
router.post('/:p_email/:review_id', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			var Result;
			connection.query('SELECT like_id FROM review_like WHERE p_email = ? and review_id = ?', [req.params.p_email, req.params.review_id],
					function (error, rows) {
				if (error) {
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				//좋아요를 누르지 않은 경우
				else {
					if (rows[0] == null) { // 좋아요테이블에 값이 없는 경우에만
						connection.query('SELECT bad_id FROM review_bad WHERE p_email = ? and review_id = ?',
								[req.params.p_email, req.params.review_id],
								function(error, rows){
							if (error) {
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else {
								//싫어요를 누르지 않은 경우
								if (rows[0] == null) { // 싫어요 실행
									connection.query('INSERT INTO review_bad(p_email, review_id) values(?,?)', [req.params.p_email, req.params.review_id],
											function(error, rows) {
										if (error){
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else {
											connection.query('UPDATE review SET badnum = badnum+1 WHERE review_id = ?', [req.params.review_id],
													function(error, rows) {
												if(error) {
													console.log("Connection Error" + error);
													res.sendStatus(500);
													connection.release();
												}
												else {
													connection.query('SELECT goodnum,badnum FROM review WHERE review_id = ?', [req.params.review_id],
															function(error, rows) {
														if(error) {
															console.log("Connection Error" + error);
															res.sendStatus(500);
															connection.release();
														}
														else {
															if(rows)
															Result={
																	goodnum : rows[0].goodnum,
																	badnum : rows[0].badnum
															};
															res.status(201).send({result : {goodbad : Result}});
															connection.release();
														}
													});
												}
											});
										}
									});
								}
								//싫어요를 눌렀던 경우
								else { // 이미 싫어요를 누른 경우 -> 싫어요취소 시켜야 한다.
									connection.query('DELETE FROM review_bad WHERE p_email = ? and review_id = ?', [req.params.p_email, req.params.review_id],
											function(error, rows) {
										if(error) {
											console.log("Connection Error" + error);
											res.sendStatus(500);
											connection.release();
										}
										else {
											connection.query('UPDATE review SET badnum = badnum -1 WHERE review_id = ?', [req.params.review_id],
													function(error, rows) {
												if(error) {
													console.log("Connection Error" + error);
													res.sendStatus(500);
													connection.release();
												}
												else {
													connection.query('SELECT goodnum, badnum FROM review WHERE review_id = ?', [req.params.review_id],
															function (error, rows) {
														if(error) {
															console.log("Connection Error" + error);
															res.sendStatus(500);
															connection.release();
														}
														else {
															Result={
																	goodnum : rows[0].goodnum,
																	badnum : rows[0].badnum
															};
															res.status(201).send({result : {goodbad : Result}});
															connection.release();
														}
													});
												}
											});
										}
									});
								}
							}
						});
					}
					//좋아요를 눌렀던 경우
					else {
						connection.query('DELETE FROM review_like WHERE p_email = ? and review_id = ?', [req.params.p_email, req.params.review_id],
								function(error, rows) {
							if(error) {
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else {
								connection.query('INSERT INTO review_bad(p_email, review_id) values(?,?)', [req.params.p_email, req.params.review_id],
										function(error, rows) {
									if (error){
										console.log("Connection Error" + error);
										res.sendStatus(500);
										connection.release();
									}else {
										connection.query('UPDATE review SET badnum = badnum +1, goodnum = goodnum-1 WHERE review_id = ?', [req.params.review_id],
												function(error, rows) {
											if(error) {
												console.log("Connection Error" + error);
												res.sendStatus(500);
												connection.release();
											}
											else {
												connection.query('SELECT goodnum,badnum FROM review WHERE review_id = ?', [req.params.review_id],
														function (error, rows) {
													if(error) {
														console.log("Connection Error" + error);
														res.sendStatus(500);
														connection.release();
													}
													else {
														Result={
																goodnum : rows[0].goodnum,
																badnum : rows[0].badnum
														};
														res.status(201).send({result : {goodbad : Result}});
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
				}
			});
		}
	});
});

module.exports = router;