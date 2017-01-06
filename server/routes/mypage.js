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


//마이페이지 뷰 요청
router.get('/:p_nickname', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			var tmp_b_birth, age, tmp_year="", tmp_month="";
			var Parent, Baby, myReview=[],bookMark=[];

			connection.query('SELECT p.p_nickname as p_nickname, p.p_name as p_name, b.b_name as b_name, b.b_birth as b_birth, b.worry1 as worry1, b.worry2 as worry2, b.worry3 as worry3 , b.sex from parent p join baby b on p.p_email = b.p_email where p.p_nickname = ?',[req.params.p_nickname], function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if(rows[0] != null){
						var today = new Date();
						var tmp_birth=new Date(rows[0].b_birth);
						var age=((today.getFullYear()-tmp_birth.getFullYear())*12 + Math.abs(today.getMonth()-tmp_birth.getMonth()));
						Parent = {
								p_nickname:rows[0].p_nickname,
								p_name:rows[0].p_name
						};
						Baby = {
								b_name:rows[0].b_name,
								sex:rows[0].sex,
								b_month:age,
								worry1:rows[0].worry1,
								worry2:rows[0].worry2,
								worry3:rows[0].worry3
						};

						connection.query('select r.review_id,d.milk_image, d.milk_name from drymilk d, review r, parent p where d.milk_id = r.milk_id and r.review_writer = p.p_nickname and p.p_nickname= ? order by review_id desc',[req.params.p_nickname],function(error, rows){
							if(error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else {
								if(rows[0] == null){
									console.log('a');
									myReview=[{}];
								}
								else{
									for(var i in rows){
										myReview.push({
											milk_image : rows[i].milk_image,
											milk_name : rows[i].milk_name,
											review_id : rows[i].review_id
										});
									}

								}
								connection.query('select drymilk.milk_image,drymilk.milk_name from drymilk, bookmark where bookmark.p_nickname=? AND bookmark.milk_name = drymilk.milk_name order by bookmark_id desc',[req.params.p_nickname],function(error, rows){
									if(error){
										console.log("Connection Error" + error);
										res.sendStatus(500);
										connection.release();
									}
									else {
										if(rows[0] == null){
											console.log('b');
											bookMark=[{}];
											res.status(200).send({result : {parent:Parent, baby:Baby, myreview: myReview, bookmark: bookMark}});
											connection.release();
										}
										else{
											for(var i in rows){
												bookMark.push({
													milk_image:rows[i].milk_image,
													milk_name:rows[i].milk_name

												});
											}
											res.status(200).send({result : {parent:Parent, baby:Baby, myreview: myReview, bookmark: bookMark}});
											connection.release();
										}
									}
								});
							}
						});
					}
					else{
						connection.release();
						Parent={};
						Baby={};
						myReview=[{}];
						bookMark=[{}];
						res.send({result:{parent:Parent, baby:Baby, myreview: myReview, bookmark: bookMark}});
					}
				}
			});
		}
	});
});



//마이페이지 수정 완료==================================================================================================================================
//마이페이지 수정
router.post('/update/:nickname', function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var sql, inserts;
			
			connection.query('select p_nickname from parent where p_nickname=?',[req.params.nickname],function(error, rows){
				if(error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						res.status(201).send({result : false});
						connection.release();
					}
					else{
						var b_birth = req.body.b_birth;
						var b_year= b_birth.substring(0,4);
						var b_month=b_birth.substring(4,6);
						var b_date=b_birth.substring(6,8);
						var new_bbirth = b_year+'-'+b_month+'-'+b_date;
						
						sql = 'update baby baby inner join parent parent on baby.p_email=parent.p_email set baby.b_birth = ?, baby.sex = ?, baby.worry1 = ?, baby.worry2 = ?, baby.worry3 = ? ';
						sql += 'where parent.p_nickname = ? ';
						inserts = [new_bbirth, req.body.sex, req.body.worry1, req.body.worry2, req.body.worry3, req.params.nickname];
						//아기 정보 수정 DB업데이트
						connection.query(sql, inserts, function(error, rows){
							if(error){
								console.log("Connection Error" + error);
								res.sendStatus(500);
								connection.release();
							}
							else{
								console.log("update of baby's information success");
								res.status(201).send({result : true});
								connection.release();
							}
						});
					}
				}
			});
		}
	});
});


module.exports = router;