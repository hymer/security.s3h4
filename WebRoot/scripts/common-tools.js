/****************************************************************
 *
 * this script file contains some common useful function.
 *
 * **************************************************************
 */

/**
 * do post AJAX, by default
 */
function doAjax(url, param, callback) {
	return doPostAjax(url, param, callback);
}

/**
 * jquery post ajax method.
 */
function doPostAjax(url, param, callback) {
	$.ajax({
		dataType : "json",
		type : "post",
		timeout : 30000,
		url : url,
		data : param,
		success : function(data, textStatus, XMLHttpRequest) {
			if (typeof callback == 'function') {
				callback(data, textStatus, XMLHttpRequest);
			}
		}
	});
}
/**
 * jquery get ajax method.
 */
function doGetAjax(url, param, callback) {
	$.ajax({
		dataType : "json",
		type : "get",
		timeout : 30000,
		url : url,
		data : param,
		success : function(data, textStatus, XMLHttpRequest) {
			if (typeof callback == 'function') {
				callback(data, textStatus, XMLHttpRequest);
			}
		}
	});
}

/**
 * submit a form AJAX.
 */
function ajaxSubmitForm(formId, callback) {
	$("#" + formId).ajaxSubmit({
		//target : "msgDiv", //配置此项，则会用返回的数据更新此div，并且不会招行success callback！。
		beforeSubmit : function(formData, jqForm, options) {

		},
		success : function(resp, statusText, xhr, $form) {
			if(resp.result == true) {
				$("#" + formId + "_div").dialog("close");
				showMsg(resp);
			} else {
				showMsg(resp);
			}
			if( typeof callback == 'function') {
				callback();
			}
		}
	});
}

/**
 * fill out a form with a object.
 */
function fillForm(formId, obj) {
	var form = $("#" + formId);
	for(var attr in obj) {
		if( typeof (obj[attr]) == 'function') {
			continue;
		}
		var $input = $("input[name='" + attr + "']", form);
		var type = $input.attr("type");
		if($input.length == 0) {
			//说明不是text/password/radio/checkbox
			var select = $("select[name='" + attr + "']", form);
			if(select.length == 0) {
				var textarea = $("textarea[name='" + attr + "']", form);
				if(textarea.length != 0) {
					//textarea
					textarea.val(obj[attr]);
				}
			} else {
				// select
				select.find("option[value='" + obj[attr] + "']").attr('selected', 'selected');
			}
		} else {
			if(type == "checkbox" || type == "radio") {
				var avalues;
				if(obj[attr].toString().indexOf(',') >= 0) {
					avalues = obj[attr].toString().split(",");
				} else {
					avalues = [obj[attr]];
				}
				for(var v = 0; v < avalues.length; v++) {
					$input.each(function(i, n) {
						if($(this).val() == avalues[v].toString()) {
							$(this).attr("checked", "checked");
						}
					});
				}
			} else {
				$input.val(obj[attr]);
			}
		}
	}
}

/**
 * show message.
 * message object should contain 'result' and 'msg' field.
 */
function showMsg(resp) {
	var div = $("<div></div>");
	if( typeof resp == 'object') {
		if(resp.result) {
			div.html("<font>" + resp.msg + "</font>");
		} else {
			div.html("<font color='red'>" + resp.msg + "</font>");
		}
	} else if( typeof resp == 'string') {
		div.html(resp);
	} else {
		return false;
	}
	div.dialog({
		draggable : false,
		title : '系统提示',
		width : 420,
		modal : true,
		// position:'center', 'left', 'right', 'top', 'bottom'.  or [350,100]
		resizable : false,
		// draggable : true,
		// autoOpen : false,
		// show:'slide',
		height : 150,
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			}
		}
	});
}

/**
 * confirm dialog.
 */
function showConfirm(text, yesCallback, noCallback) {
	var div = $("<div></div>");
	if( typeof text == 'string') {
		div.html(text);
	} else {
		return false;
	}
	div.dialog({
		draggable : false,
		title : '确认框',
		width : 420,
		modal : true,
		resizable : false,
		draggable : true,
		height : 150,
		buttons : {
			"Yes" : function() {
				$(this).dialog("close");
				if( typeof yesCallback == 'function') {
					yesCallback();
				}
			},
			"No" : function() {
				$(this).dialog("close");
				if( typeof noCallback == 'function') {
					noCallback();
				}
			}
		}
	});
}