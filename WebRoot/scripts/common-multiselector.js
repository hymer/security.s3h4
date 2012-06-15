var MultiSelector = function(divId, options) {
	var instance = this;
	instance.div = $('#' + divId);
	var _default = {
		// some default options.
		url : '',
		param : '',
		allData : 'all',
		selectedData : 'selected',
		valueFiled : "id",
		displayField : "name",
		selectorWidth : '150px',
		selectorHeight : '120px',
		autoLoad : true
	};
	instance.options = jQuery.extend(true, _default, options);
	// 深度继承

	var loadData = function() {
		$.ajax({
			dataType : "json",
			type : "get",
			timeout : 30000,
			url : instance.options.url,
			data : instance.options.param,
			success : function(data, textStatus, XMLHttpRequest) {
				initMultiSelector(data.data[instance.options.allData], data.data[instance.options.selectedData]);
			}
		});
	}
	var createSelects = function() {
		var allSelector = $("<select name='" + instance.options.allData + "' multiple='multiple' />");
		var operators = $("<font>-></font>");
		operators.css('padding', '2px 10px');
		var selectedSelector = $("<select name='" + instance.options.selectedData + "' multiple='multiple' />");
		
		allSelector.css('width', instance.options.selectorWidth);
		allSelector.css('height', instance.options.selectorHeight);
		selectedSelector.css('width', instance.options.selectorWidth);
		selectedSelector.css('height', instance.options.selectorHeight);
		
		instance.div.append(allSelector);
		instance.div.append(operators);
		instance.div.append(selectedSelector);
		instance.allSelector = allSelector;
		instance.selectedSelector = selectedSelector;
	}
	var initMultiSelector = function(allObjects, selectedObjects) {
		for(var i = 0; i < allObjects.length; i++) {
			var option = $("<option></option>");
			var obj = allObjects[i];
			option.val(obj[instance.options.valueFiled]);
			option.html(obj[instance.options.displayField]);
			instance.allSelector.append(option);
		}
		for(var i = 0; i < selectedObjects.length; i++) {
			var option = $("<option></option>");
			var obj = selectedObjects[i];
			option.val(obj[instance.options.valueFiled]);
			option.html(obj[instance.options.displayField]);
			instance.selectedSelector.append(option);
		}
		addListener();
	}
	var addListener = function() {
		instance.div.find("option").dblclick(function() {
			var option = $(this);
			var optionClone = $(this).clone(true);
			if (option.parent().attr("name") == instance.options.allData) {
				instance.selectedSelector.append(optionClone);
				option.remove();
			} else {
				instance.allSelector.append(optionClone);
				option.remove();
			}
		});
	}

	this.val = function() {
		var selecteds = instance.selectedSelector.find('option');
		var ids = '';
		selecteds.each(function() {
			ids += $(this).val() + ",";			
		});
		return ids;
	}
	this.getSelected = function() {
		return this.val();
	}
	// /////////////////////////////////////////////
	// Executes
	// /////////////////////////////////////////////
	createSelects();
	if(instance.options.autoLoad) {
		loadData();
	}
}