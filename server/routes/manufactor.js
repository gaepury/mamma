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

router.get('/:manufactor_name', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			var sql;
			var manufactors;
			var drymilks = [];
			sql='SELECT m.*, d.milk_id, d.milk_image, d.milk_name, d.milk_grade, dryingredient.stage from manufactor m, drymilk d, dryingredient where m.manu_name = d.milk_company and d.milk_name = dryingredient.milk_name and m.manu_name = ? group by milk_name';
			connection.query(sql,[req.params.manufactor_name], function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
				}
				else {
					if(rows[0] == null){
						console.log("제조사 없음");
						connection.release();
						manufactors={};
						drymilks=[{}];
						res.send({result : {manufactor : manufactors, drymilk : drymilks}});
						//여기서 500을 날려야 하는지 모르겠음
					}else{
						manufactors = {
								manu_image : rows[0].manu_image,
								manu_name : rows[0].manu_name,
						};
						for (var i in rows){
							drymilks.push({
								milk_image : rows[i].milk_image,
								milk_name : rows[i].milk_name,
								milk_stage : rows[i].stage,
								milk_grade : rows[i].milk_grade
							});
						}
						res.status(200).send({result : {manufactor : manufactors, drymilk : drymilks}});
						connection.release();
					}
				}
			});
		}
	});
});

module.exports = router;
