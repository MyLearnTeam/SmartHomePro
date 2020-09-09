//定义用户资源跟路径，固定写法不得修改
//alert(document.location);
var basePath = "http://" + window.location.host + "/resource/xiaowei";
// 全局ajax回调处理
var index;
$.ajaxSetup({
	beforeSend : function(){
		index = layer.load(2);
	},
	error : function(XMLHttpRequest, textStatus){
		if(XMLHttpRequest.responseText){
			var response = JSON.parse(XMLHttpRequest.responseText);
			console.log(response);
			layer.msg(response.error+", 操作失败", {icon : 2});
		}else{
			layer.msg("操作失败", {icon : 2});			
		}
	},
	complete : function(XMLHttpRequest, textStatus) {
		layer.close(index);
	}
});
$(document).ready(function() {
	//设置table可拖动列宽
	$("table").resizableColumns();
	//设置该样式从右边缓缓加载动画
	//$(".animate").animate({left:'0','opacity':'1'},300, 'easeOutExpo'); 
	bindChildOper();
	$(".body-height").height($(window).height()-getTopHeight()-20);
});

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh) {
	top.$.fn.jerichoTab.addTab({
		tabFirer : $this,
		title : title,
		closeable : closeable == undefined,
		data : {
			dataType : 'iframe',
			dataLink : url
		}
	}).loadData(refresh != undefined);
}
// 根据src获取父页面的iframe
function getParentWindow(src) {
	var result;
	parent.$('iframe').each(function() {
		if ($(this).attr('src')&& $(this).attr('src').indexOf(src) > -1) {
			result = $(this)[0].contentWindow;
			return false;
		}
	});
	return result;
}
// 打开对话框
function openDialog(title, url, width, height) {
	parent.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url,
	});

}
// 打开对话框(修改)
function openDialogEdit(title, url, width, height, $table, id, idName) {
	if(null==id || ""==id){
		var rows = $table.bootstrapTable('getSelections', null);
		if (null != rows && rows.length != 1) {
			layer.msg("请选择一条进行修改", {
				icon : 7,
				time : 1000
			});
			return false;
		}else{
			for (var item in rows[0]) {
			     if(item == idName){
			    	 id = rows[0][item];
			     }
			}
		}
	}
	parent.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url+"?id="+id,
	});

}
// 列表页面删除数据
function deleteRecords(url, $table_, ids, idName) {
	if (null == ids || "" == ids) {
		var rows = $table_.bootstrapTable('getSelections', null);
		if (null == rows || rows.length == 0) {
			layer.msg("请至少选择一条删除", {
				icon : 7,
				time : 1000
			});
			return false;
		} else {
			var id = [];
			$.each(rows, function(i) {
				for (var item in rows[i]) {
				     if(item == idName){
				    	 id.push(rows[i][item]);
				     }
				}
			});
			ids = id.join(",");
		}
	}
	layer.confirm("确定删除吗，是/否?", function(index) {
		var params = {
			"ids" : ids
		};
		$.ajax({
			type : "POST",
			url : url,
			data : JSON.stringify(params),
			// dataType: "json",
			contentType : "application/json",
			success : function(data, status) {
				if (data.statusCode == "200") {
					layer.msg("删除成功", {
						icon : 1,
						time : 1000
					});
					$table_.bootstrapTable('selectPage',1);
				} else {
					layer.msg("删除失败", {
						icon : 2
					});
				}
			}
		});
	});
}
// 打开对话框(查看)
function openDialogView(title, url, width, height) {
	top.layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : true, // 开启最大化最小化按钮
		content : url,
		btn : [ '关闭' ],
		cancel : function(index) {
		}
	});
}
// 关闭当前layer弹窗
function closeCurrent() {
	var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
	parent.layer.close(index);
}
//下拉树关闭打开
function selectTree(id){
	if($("#"+id).css("display") == "none") {　　　　　　　　　　
		$("#"+id).show();　　　　　　　　
    } else {　　　　　　　　　　
    	$("#"+id).hide();　　　　　　　　
    }　　　　　　　
}

/**
 * 表单转Object Json
 */
$.fn.formToObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
$.fn.formToList = function() {
	var o = {};
	var a = this.serializeArray();
	var list = new Array();
	var count=0;
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
		if($.isArray(o[this.name])){
			if(count < o[this.name].length){
				count = o[this.name].length;
			}
		}
	});
	if(count == 0){
		list.push(o);
		return list;
	}
	for(var i = 0; i < count; i++){
		var detail = {};
		for(var p in o){
			if($.isArray(o[p])){			
				detail[p] = o[p][i];
			}else{
				if(i==0){					
					detail[p] = o[p];
				}
			}
		}
		list.push(detail);
	}
	return list;
}
var imgIndex;
function viewImg(name,src){
	var extStart = src.lastIndexOf('.'), ext = src.substring(extStart, src.length).toUpperCase();
	if (ext !== '.PNG' && ext !== '.JPG' && ext !== '.JPEG' && ext !== '.GIF' && ext != ".ICO" && ext != ".BMP") {
		location.href = "../../../downloadFile?url="+src+"&name="+name;
	} else {
		var theImage = new Image();
		theImage.src = src;
		var imageWidth = theImage.width;
		var imageHeight = theImage.height;
		var winHeight = window.screen.height-150;
		var winWidth = window.screen.width-150;
		if(imageWidth > winWidth){
			imageWidth = winWidth;
		}
		if(imageHeight > winHeight){
			imageHeight = winHeight;
		}
		var imgIndex = top.layer.open({
			type : 1,
			title : false,
			closeBtn : 1,
			skin : 'layui-layer-nobg', // 没有背景色
			shadeClose : true,
			area: [imageWidth + 'px', imageHeight + 'px'], //宽高
			content : "<img src='" + src + "' />"
		});
	}
}

// Public.ajaxPost方法
var Public = Public || {};

Public.ajaxPost = function(url, params, callback, beforeSend, errCallback,
		progress) {
	// console.log(JSON.stringify(params));
	$.ajax({
		type : "POST",
		url : url,
		async : true,
		cache : false,
		data : JSON.stringify(params),
		dataType : "json",
		headers : {
			'User-ID' : user_id
		},
		// contentType:"application/json",
		success : function(data, status) {
			// alert(JSON.stringify(data));
			callback(data);
		},
		beforeSend : function() {
			if (beforeSend != null)
				beforeSend;
		},
		error : function(err) {
			errCallback && errCallback(err);
		},
		xhr : function() { // 在jquery函数中直接使用ajax的XMLHttpRequest对象
			var xhr = new XMLHttpRequest();
			xhr.upload.addEventListener("progress", function(evt) {
				if (evt.lengthComputable) {
					var percentComplete = Math.round(evt.loaded * 100
							/ evt.total);
					if (progress != null)
						progress(percentComplete);
					console.log("正在提交." + percentComplete.toString() + '%'); // 在控制台打印上传进度
				}
			}, false);

			return xhr;
		}
	});
};
/**
 * 父子表增删方法
 * @returns
 */
function bindChildOper(){
	$("#table1").resizableColumns();
	//绑定回车键按下事件
	$(".child-table").find("td").keydown(function(e){
		if (e.keyCode == 13) {
			unBindChildOper();
			var currTr = $(this).parent("tr");
			var tr = currTr.siblings("tr.template-tr").clone();//.siblings("tr:last-child");
			tr.removeClass("template-tr");
			currTr.after(tr);
			bindChildOper();
		}
	});
	//增加按钮
	$(".child-table").find(".add-row").click(function() {
		unBindChildOper();
		var currTr = $(this).parent("td").parent("tr");
		var tr = currTr.siblings("tr.template-tr").clone();//("tr:last-child");
		tr.removeClass("template-tr");
		currTr.after(tr);
		bindChildOper();
	});
	//删除按钮
	$(".child-table").find(".delete-row").click(function() {
		var tr = $(this).parent("td").parent("tr");
		tr.remove();
	});
}
//解除绑定事件
function unBindChildOper() {
	$(".child-table").find(".add-row").unbind("click");
	$(".child-table").find(".delete-row").unbind("click");
	$(".child-table").find("td").unbind("keydown");
}
//添加一行
function addColumn(tableId) {
	unBindChildOper();
	//console.log($("#" + tableId + " tr.template-tr"));
	var tr = $("#" + tableId + " tr.template-tr").clone();
	tr.removeClass("template-tr");
	$("#" + tableId).append(tr);
	bindChildOper();
}
//父子表增删方法----end

function getTopHeight(){
	if(parent.$("#topic1").is(":hidden")){
		return 0;
	}else{		
		return parent.$("#topic1").height();
	}
}
