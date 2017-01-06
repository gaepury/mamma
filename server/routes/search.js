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

 

router.get('/:keyword', function(req, res, next) {
	console.log("키워드는 " + req.params.keyword);
	pool.getConnection(function(error, connection){
		var datas = {};
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			connection.query('SELECT manufactor.manu_image, manufactor.manu_name '+
					'FROM manufactor WHERE manufactor.manu_name LIKE "%"?"%";', [req.params.keyword], function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if (rows.length >= 1)
						datas.manufactor = rows;
					else
						datas.manufactor = [{}];
					

					connection.query('SELECT drymilk.milk_image, drymilk.milk_name, drymilk.milk_grade '+
							'FROM drymilk WHERE drymilk.milk_name LIKE "%"?"%";', [req.params.keyword], function(error, rows){
						if (error){
							console.log("Connection Error" + error);
							res.sendStatus(500);
							connection.release();
						}else {
							if (rows.length >= 1)
								datas.drymilk = rows;
							else
								datas.drymilk=[{}];
								
							connection.query('SELECT ingredient.ingre_name '+
									'FROM ingredient WHERE ingredient.ingre_name LIKE "%"?"%";', [req.params.keyword], function(error, rows){
								if (error){
									console.log("Connection Error" + error);
									res.sendStatus(500);
									connection.release();
								}
								else {
									if (rows.length >= 1)
										datas.ingredient = rows;
									else
										datas.ingredient = [{}];
									if(datas.manufactor ==null && datas.drymilk==null && datas.ingredient==null){
										res.send({result : false});
										connection.release();
									}else{
										res.status(200).send({result : datas});
										connection.release();
									}
								}
							});
						}
					});
				}
			});
		}
	});
});

module.exports = router;
