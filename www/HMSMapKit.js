var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'HMSMapKit', 'coolMethod', [arg0]);
};
exports.addTwoNumbers = function (arg0,arg1, success, error) {
    exec(success, error, 'HMSMapKit', 'addTwoNumbers', [arg0,arg1]);
};