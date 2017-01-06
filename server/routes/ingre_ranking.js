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

//name:성분네임 (ingre_name)
router.get('/:ingre_name/:stage', function(req, res, next) {
  pool.getConnection(function(error, connection){
      if (error){
        console.log("getConnection Error" + error);
        res.sendStatus(500);
      }else {
        var milk_Info=[];
        connection.query('select ingre_name, drymilk.milk_name, milk_image, milk_grade, dryingredient.stage from dryingredient, drymilk where drymilk.milk_name=dryingredient.milk_name and dryingredient.ingre_name=? and dryingredient.stage = ? order by content desc', [req.params.ingre_name, req.params.stage], function(error, rows){
          if (error){
            console.log("Connection Error" + error);
            res.sendStatus(500);
          }
          else {
            if (rows[0] != null){
              for (var i in rows){
                milk_Info.push({
                  milk_image : rows[i].milk_image,
                  milk_name : rows[i].milk_name,
                  milk_grade : rows[i].milk_grade,
                  stage : rows[i].stage
                });
                }
                res.status(200).send({result : {ingre_ranking:milk_Info}});
                connection.release();
              }
              else{
                console.log("성분이 포함된 분유가 없음." +
                		"" +
                		"");
            	res.status(200).send({result : {ingre_ranking:[{}]}});
                connection.release();
              }
            }
        });
      }
    });
});

module.exports = router;
