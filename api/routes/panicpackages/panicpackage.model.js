var mongoose = require('mongoose');

module.exports = mongoose.model('PanicPackage',{
	name:  String,
	longitude: Number,
	latitude:   Number,
	date: { type: Date, default: Date.now },
	heartbeat: Number,
	temperature: Number
});