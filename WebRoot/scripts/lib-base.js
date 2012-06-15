/*
 * ! import js files and css files.
 */
function importJs(jsFilePath) {
	document.write('<script language="javascript" src="' + jsFilePath + '"></script>');
}

function importCss(cssFilePath) {
	document.write('<link rel="stylesheet" type="text/css" href="' + cssFilePath + '" media="all" />');
}

var js = ["scripts/jquery-1.7.2.min.js", "scripts/common-simpletable.js", "scripts/jquery.form.js"];

var css = ["css/jquery-ui-1.8.20.custom.css", "css/main.css", "css/common-simpletable.css"];

for(var i = 0; i < js.length; i++) {
	importJs(js[i]);
}
for(var i = 0; i < css.length; i++) {
	importCss(css[i]);
}