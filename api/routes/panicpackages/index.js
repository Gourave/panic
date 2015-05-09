var express = require('express');
var app = module.exports = express();

/* GET list of api routes */
app.get('/', function(req, res) {
	res.send('panic pacakges');
});

module.exports = app;