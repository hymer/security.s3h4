$(function() {
	$("select.ajaxSelector").each(function(i) {
		var autoLoad = $(this).attr('autoLoad');
		if(autoLoad == 'false') {
			$(this).click(function() {
				loadSelector($(this));
			});
		} else {
			loadSelector($(this));
		}
	});
});
function loadSelector(select) {
	var url = select.attr('url');
	var param = select.attr('param');
	var dataRoot = select.attr('dataRoot');
	var displayField = select.attr('displayField');
	var valueField = select.attr('valueField');
	var allOptions = select.find("option");

	var loadData = function(selector) {
		$.ajax({
			dataType : "json",
			type : "get",
			timeout : 30000,
			url : url,
			data : param,
			success : function(data, textStatus, XMLHttpRequest) {
				var datas = data.data[dataRoot];
				for(var i = 0; i < datas.length; i++) {
					var obj = datas[i];
					var option = $("<option></option>");
					option.val(obj[valueField]);
					option.html(obj[displayField]);
					selector.append(option);
				}
			}
		});
	}
	if(allOptions.length == 0) {
		loadData(select);
	} else if(allOptions.length == 1) {
		if($(allOptions[0]).val() == '') {
			loadData(select);
		}
	}
}