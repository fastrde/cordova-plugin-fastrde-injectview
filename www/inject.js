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
	},
	getCookies: function(cookieDomain, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "Inject", "getCookies", [cookieDomain]);
	},
	getCookie: function(cookieDomain, cookieName, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "Inject", "getCookie", [cookieDomain, cookieName]);
	}
}

module.exports = inject;
