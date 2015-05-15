var express = require('express');
var app = module.exports = express();
var mongoose = require('mongoose');
var PanicPackage = require('./panicpackage.model.js');

app.get('/', function(req, res) {
	PanicPackage.find({},function(err, results){
		res.json(results);
	});
});

app.get('/markers', function(req, res) {
	PanicPackage.find({},function(err, results){
		var markersList = [];
		for (var i = 0; i < results.length; i++) {
			var result = results[i];
			var newMarker = {
				id : result._id ,latitude: result.latitude, longitude : result.longitude, title : result.name
			};
			markersList.push(newMarker);
		}
		res.json(markersList);
	});
});


app.post('/', function(req, res) {
	var name = req.body.name;
	var longitude = req.body.longitude;
	var latitude = req.body.latitude;
	var heartbeat = req.body.heartbeat;
	var temperature = req.body.temperature;
	var filename = req.body.filename;

	var newPanicPackage = new PanicPackage(
		{
			name: name,
			longitude : longitude,
			latitude : latitude,
			heartbeat : heartbeat,
			temperature : temperature,
			filename : filename
		}
	);	
	newPanicPackage.save(function (err, result) {
		if (err){
			console.log(err);
		} else {
			res.send('success');
		}
	});
});

app.post('/upload', function(req, res) {
  filename = req.files.filedata.name;
  res.send('success');
});

module.exports = app;