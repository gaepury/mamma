var express = require('express');
var mysql = require('mysql');
var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var crypto = require('crypto');
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

var secret_key = 'eunjunjaemin';

//회원가입 완료==================================================================================================================
//회원가입
router.post('/up', function(req, res, next){
	console.log(req.body);
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var sql, inserts;
			var email_Check = false;
			var nickname_Check = false;

			
			// 이메일 확인
			connection.query('select parent.p_email from parent where parent.p_email= ?',[req.body.p_email], function(error, rows){
				if (error){
					
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if(rows[0] == null){
						
						email_Check=true;
						// 닉네임 확인
						connection.query('select parent.p_nickname from parent where parent.p_nickname= ?', [req.body.p_nickname], function(error, rows){
							if (error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else {
								if(rows[0] == null){
										console.log("asd");
										var tmp_password = req.body.p_password;
										var encryption = crypto.createCipher('seed', secret_key);
										encryption.update(tmp_password,'utf8','base64');
										var encryptionData = encryption.final('base64');
										console.log("encryption : " + encryptionData);
										// 부모정보 삽입
										var p_birth = req.body.p_birth;
										var p_year= p_birth.substring(0,4);
										var p_month=p_birth.substring(4,6);
										var p_date=p_birth.substring(6,8);
										var new_pbirth = p_year+'-'+p_month+'-'+p_date;
										
										sql='insert into parent(p_email, p_name, p_password, p_birth, p_nickname) values(?,?,?,?,?)';
										inserts=[req.body.p_email, req.body.p_name, encryptionData, new_pbirth, req.body.p_nickname];
										connection.query(sql, inserts, function(error, rows){
											if (error){
												console.log("Connection Error" + error);
												res.sendStatus(500);
												connection.release();
											}
											else {
												console.log("insertion of parent's information");
												// 아기정보 삽입
												var b_birth = req.body.b_birth;
												var b_year= b_birth.substring(0,4);
												var b_month=b_birth.substring(4,6);
												var b_date=b_birth.substring(6,8);
												var new_bbirth = b_year+'-'+b_month+'-'+b_date;
												
												sql='insert into baby(b_name, b_birth, sex, worry1, worry2, worry3, p_email) values(?,?,?,?,?,?,?)';
												inserts=[req.body.b_name, new_bbirth, req.body.sex, req.body.worry1, req.body.worry2, req.body.worry3, req.body.p_email];
												connection.query(sql, inserts, function(error, rows){
													if (error){
														console.log("Connection Error" + error);
														res.sendStatus(500);
														connection.release();
													}
													else {
														console.log("insertion of baby's information");
														res.status(201).send({result : true});
														connection.release();
													}
												});
											}
										});
								}else{
									console.log('닉네임 중복');
									res.send({result : false});
								}
							}
						});
					}else{
						console.log('이메일 중복');
						res.send({result : false});
					}
				}
			});
		}
	});
});
//회원가입에서 post성공 200에서 201로 바꿈


//아이디 중복검사 완료===================================================================================================================
//아이디 중복검사
router.get('/duplication/email/:email', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			connection.query('select parent.p_email from parent where parent.p_email= ?', [req.params.email], function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if(rows[0] == null){
						res.status(200).send({result : true});
					}
					else{
						res.status(200).send({result : false});
					}
					connection.release();
				}
			});
		}
	});
});


//닉네임 중복검사 완료===========================================================================================================
//닉네임 중복검사
router.get('/duplication/nickname/:nickname', function(req, res, next){
	pool.getConnection(function(error, connection){
		console.log('a');
		console.log(req.params.nickname);
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{

			connection.query('select parent.p_nickname from parent where p_nickname= ?', [req.params.nickname], function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if(rows[0] == null){
						res.status(200).send({result : true});
					}
					else{
						res.status(200).send({result : false});
					}
					connection.release();
				}
			});
		}
	});
});


//로그인 요청 완료=============================================================================================================
//로그인 요청
router.post('/in', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			//이메일 확인
			var signin;
			var p_Nickname;
			connection.query('select p_email, p_password, p_nickname from parent where p_email= ?', [req.body.p_email], function(error, rows){
				if (error){
					console.log("getConnection Error" + error);
					res.sendStatus(500);
				}
				else{
					console.log(rows);
					if(rows[0] == null){
						console.log("이메일 오류");
						signin=false;
						p_Nickname=null;
						res.send({result : {signresult :{signin:signin , p_nickname:p_Nickname}}});
						connection.release();
					}
					else{
							console.log("이메일 확인 완료");
							//비밀번호 확인(비밀번호가 없을때)
							if(rows[0].p_password == null){
								console.log("비밀번호 오류");
								signin=false;
								p_Nickname=null;
								res.send({result : {signresult :{signin:signin , p_nickname:p_Nickname}}});
								connection.release();
							}
							else{
								var tmp_password = rows[0].p_password;
								var decryption=crypto.createDecipher('seed', secret_key);
								decryption.update(tmp_password, 'base64', 'utf8');
								var decryptionData=decryption.final('utf8');

								if(decryptionData == req.body.p_password){
									console.log("비밀번호 확인 완료");
									signin=true;
									p_Nickname=rows[0].p_nickname;
									res.status(201).send({result : {signresult :{signin:signin , p_nickname:p_Nickname}}});
									connection.release();
								}
								else{
									console.log("비밀번호 오류");
									signin=false;
									p_Nickname=null;
									res.status(201).send({result : {signresult :{signin:signin , p_nickname:p_Nickname}}});
									connection.release();
								}
							}
						}	
				}
			});
		}
	});
});



module.exports = router;