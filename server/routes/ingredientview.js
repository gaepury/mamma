var express = require('express');
var mysql = require('mysql');
var db_config = require('../config/db_config.json');
var router = express.Router();

var pool = mysql.createPool({
	host : db_config.host,
	port : db_config.port,
	user : db_config.user,
	password : db_config.password,
	database : db_config.database,
	connectionLimit : db_config.connectionLimit
});



//성분정보 요청(ingre_name)
router.get('/:ingre_name', function(req, res, next) {
	pool.getConnection(function(error, connection){
		if (error){
			console.log("getConnection Error" + error);
			res.sendStatus(500);
		}
		else {
			var ingredient_info, rank = [];
			connection.query('SELECT ingre_name, overview, uses, side '+ 'FROM ingredient WHERE ingre_name = ? ;', [req.params.ingre_name],function(error, rows){
				if (error){
					console.log("Connection Error" + error);
					res.sendStatus(500);
					connection.release();
				}
				else {
					if(rows.length >= 1) {
						ingredient_info = {
								"ingre_name":rows[0].ingre_name,
								"overview":rows[0].overview,
								"uses":rows[0].uses,
								"side":rows[0].side
						};
					}else{
						ingredient_info={};
					}
					connection.query('SELECT milk_image, milk_company, dryingredient.milk_name '+
							'FROM drymilk, dryingredient WHERE dryingredient.ingre_name = ? AND dryingredient.milk_name = drymilk.milk_name AND dryingredient.stage = 1 ORDER BY dryingredient.content DESC LIMIT 4;', [req.params.ingre_name],
							function(error, rows) {
						if(error) {
							console.log("Connection Error" + error);
							res.sendStatus(500);
							connection.release();
						}
						else {
							if (rows.length == 0) {
								console.log("성분 내용 없음");
								connection.release();
								res.send({result:{ingredient_info:ingredient_info, rank:[{}]}});
							}
							else {
								for(var i in rows) {
									rank.push({
										"milk_image":rows[i].milk_image,
										"milk_company":rows[i].milk_company,
										"milk_name":rows[i].milk_name
									});
								}
								res.status(200).send({result:{ingredient_info:ingredient_info, rank:rank}});
								connection.release();
							}
						}
					});
				}
			});
		}
	});
});

module.exports = router;
