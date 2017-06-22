$.ajaxSetup({
    contentType : "application/x-www-form-urlencoded;charset=UTF-8",
    complete : function(XMLHttpRequest, textStatus) {
    	f_noPopeDomHandle(XMLHttpRequest, textStatus);
    }
});
var f_noPopeDomHandle = function (XMLHttpRequest, textStatus){
    var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
    var uri = XMLHttpRequest.getResponseHeader("nourl");
    if (sessionstatus == "noPopedom") {
    	console.log("未经授权: "+uri);
    	alert("未授权,请联系管理员!");
    } else if (sessionstatus == "timeout") {        	
    	// 如果超时就处理 ，指定要跳转的页面
    	window.location.replace("/");
    } else {
    	
    }
};

var f_createModelObj = function (row, modelName) {
	var newRow = {};
	for ( var p in row) {
		newRow[modelName+"." + p] = row[p];
	}
	return newRow;
}

var f_cloneObj = function (obj) {
	var newObj = {};
	if(obj instanceof Array) {
		newObj =[];
	}
	for(var key in obj) {
		var val = obj[key];
		//newObj[Key] = typeof val === 'object' ? arguments.callee(val) :val ;//arguments.callee在哪个函数中允许,他就代表那个函数,一般用在匿名函数中
		newObj[key] = typeof val === 'object' ? cloneObj(val):val;
	}
	return newObj;
}


/**
 * 默认加载，控制权限按钮的显示
 */
//$.post('/permission/permissionButtonList',function(data){
//	$.each(data, function(i, p){
//		$(".permission-button").each(function(){
//			var per = $(this).data("perid");
//			if(per == p.ID){
//				$(this).removeClass("permission-button");
//			}
//		});
//	});
//},'json');

//var f_formatForm = function (formTarget) {
//	var serializeObj = {};
//	$.each(formTarget.serializeArray(), function (i, s){
//		if(s.value != null && s.value != 0) {
//			serializeObj[s.name] = s.value;
//		}
//	});
//	return serializeObj;
//}
//	

/**********自定义****************/
var xs_check_status = {
		0 : {msg:'待上报',color:'#359102'},
		1 : {msg:'科室审核中...',color:'#359102'},
		2 : {msg:'科室审核驳回',color:'red'},
		3 : {msg:'科室批准待上报',color:'#359102'},
		4 : {msg:'单位审核中...',color:'#359102'},
		5 : {msg:'单位审核驳回',color:'red'},
		6 : {msg:'单位批准待上报',color:'#359102'},
		7 : {msg:'路局审核中...',color:'#359102'},
		8 : {msg:'路局审核驳回',color:'red'},
		9 : {msg:'审核通过',color:'#359102'}
	}