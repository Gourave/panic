var express = require('express');
var app = module.exports = express();

var panicpackages = require('./panicpackages/');
app.use('/panicpackages', panicpackages);

var danger = require('./danger/');
app.use('/danger', danger);


app.get('/', function(req, res) {
	res.send('index');
});



module.exports = app;