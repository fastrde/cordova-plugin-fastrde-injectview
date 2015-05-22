var cordova = require('cordova'),
    exec = require('cordova/exec'),
		pluginloader = require('cordova/pluginloader'),
		modulemapper = require('cordova/modulemapper');

var inject = {
	javascriptFile: function(script, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "Inject", "javascriptFile", [script]);
	},
	javascriptString:  function(script, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "Inject", "javascriptString", [script]);
	}
}

module.exports = inject;
