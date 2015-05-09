var express = require('express');
var app = module.exports = express();
var mongoose = require('mongoose');
var PanicPackage = require('./panicpackage.model.js');

/* GET list of api routes */
app.get('/', function(req, res) {
	PanicPackage.find({},function(err, results){
		res.json(results);
	});
});

var filename;
app.post('/', function(req, res) {
	var name = req.body.name;
	var longitude = req.body.longitude;
	var latitude = req.body.latitude;
	var heartbeat = req.body.heartbeat;
	var temperature = req.body.temperature;

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