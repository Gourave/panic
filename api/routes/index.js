var express = require('express');
var app = module.exports = express();

var panicpackages = require('./panicpackages/');
app.use('/panicpackages', panicpackages);

/* GET list of api routes */
app.get('/', function(req, res) {
	res.send('index');
});

module.exports = app;