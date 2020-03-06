var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'HMSMapKit', 'coolMethod', [arg0]);
};
/*
{"markers":[
	{
		"lat":"12.231233",
		"lng":"12.223123",
		"snippet":"snippet here",
		"title":"title here"
	}
]}

*/
exports.loadMapWithMarkers = function (markersJson,success,error) {
    exec(success, error,'HMSMapKit', 'loadMapWithMarkers', [markersJson]);
    //exec(function(res){}, function(err){}, 'HMSMapKit', 'loadMapWithMarkers', [markersJson]);
};
exports.isHMSAvailable = function (success,error) {
    exec(success, error,'HMSMapKit', 'isHMSAvailable', []);
};
exports.isGMSAvailable = function (success,error) {
    exec(success, error,'HMSMapKit', 'isGMSAvailable', []);
};