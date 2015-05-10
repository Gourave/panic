var express = require('express');
var app = module.exports = express();
var mongoose = require('mongoose');
var PanicPackage = require('../panicpackages/panicpackage.model.js');

//1 lat/long point ~= 100km
// 5km = 5/110.574 = 0.04521858664 latitude
// longitude = 111.320*cos(latitude) = 111320*cos(0.04521858664) = 111.206210244 longitude
app.get('/:longitude/:latitude', function(req, res) {
	var latitude = req.params.latitude;
	var longitude = req.params.longitude;
	var searchradius = 1/100;

	var numPanicsInArea = 0;
	PanicPackage.find({},function(err, results){
		for (var i = 0; i < results.length; i++) {
			var result = results[i];
			if(!result.longitude || !result.latitude){
				break;
			}
			var dx = result.latitude - latitude;
			var dy = result.longitude - longitude;

			console.log((dx^2) + (dy^2)); 
			if( ((dx^2) + (dy^2)) < (searchradius^2) ){

				numPanicsInArea++;
			}
		}
		res.send({dangerlevel : numPanicsInArea});
	});
});

module.exports = app;