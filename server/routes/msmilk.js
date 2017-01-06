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


var upload = multer({
  storage: multerS3({
    s3: s3,
    bucket: 'pury',
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


//완료========================================================================================================================================

//다른엄마들은 어떤분유를 검색했을까?
router.get('/',function(req, res, next){
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else{
			var searchRank;
			connection.query('select drymilk.milk_image, drymilk.milk_name ,dryingredient.stage,drymilk.milk_grade from drymilk, dryingredient where drymilk.milk_name=dryingredient.milk_name group by milk_name order by milk_hit desc', function(error, rows){
				if(error){
					console.log("query error");
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else{
					if(rows[0] == null){
						console.log("리뷰가 없음");
						connection.release();
						searchRank=[{}];
						res.send({result:{searchrank: searchRank}});
						//여기서 500을 날려야 하는지 모르겠음
					}
					else{
						searchRank = rows;
						res.status(200).send({result:{searchrank: searchRank}});
						connection.release();
					}
				}
			});
		}
	});
});

module.exports = router;
