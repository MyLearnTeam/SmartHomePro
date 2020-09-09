
function setFileIDs(file_ids, jsp_file_id) {
    var new_file_id_list=file_ids.split(',');
    var old_file_id_list=[];
    var file_ids=$("#"+jsp_file_id+"").val();
    if(file_ids!=''){
        old_file_id_list=file_ids.split(',');
    }
    var newest_file_id_list=old_file_id_list;
    if(old_file_id_list.length>0){
        for(var n=0;n<new_file_id_list.length;n++){
            var has_id=false;
            var new_id=new_file_id_list[n];
            for(var o=0;o<old_file_id_list;o++){
                if(new_id==old_file_id_list[o]){
                    has_id=true;
                    break;
                }
            }
            if(!has_id){
                newest_file_id_list.push(new_id);
            }
        }
    }else{
        newest_file_id_list=new_file_id_list;
    }
    var new_ids="";
    for(var n=0;n<newest_file_id_list.length;n++){
        new_ids+=newest_file_id_list[n];

        if(n<(newest_file_id_list.length-1)){
            new_ids+=",";
        }
    }
    $("#"+jsp_file_id+"").val(new_ids);
}

function deleteFile(e,id, jsp_file_id) {
    var ids = "";
    if(id<1) {
        $('input:checkbox[name=delete]:checked').each(function(k){
            if(k == 0){
                number = $(this).val();
            }else{
                number += ','+$(this).val();
            }
        })
    }else{
        ids=id;
    }
    if(id == ""){
    	$(e.currentTarget).parent().parent().remove();
    	return false;
    }
    var file_ids=$("#"+jsp_file_id+"").val();
    var sim_id=id+",";
    if(file_ids.indexOf(sim_id)>-1){
        file_ids= file_ids.replace(id+",","");
    }else if(file_ids.indexOf(","+id)>-1){
        file_ids= file_ids.replace(","+id,"");
    }else if(file_ids.indexOf(id)>-1){
        file_ids= file_ids.replace(id,"");
    }
    $("#"+jsp_file_id+"").val(file_ids);

    var params = {
        "ids": ids
    };
    httpRequest.ajax({
        url: "delFile",//请求URL
        data: params,//请求参数
        loading: true,//是否显示loading加载框
        success: function (data, status) {
            if(data==null || data.statusCode==null){
                layer.msg("提交失败，请重试");
            }
            if(data.statusCode == "200"){
                if(typeof(e)!='undefined' && e!=null){
                	if(e.currentTarget){                		
                		$(e.currentTarget).parent().parent().remove();
                	}else{
                		e.parent().parent().remove();
                	}
                }
                return true;
            }
            layer.msg(data.msg);
        }
    });
}
function deleteImgs(id,node){
	
}
function initJqFileUploaderAttachs(pickDivId,jsp_file_id) {
    'use strict';
    $('#'+pickDivId).fileupload({
        url: 'multiUploadFiles',
        downloadTemplateId: 'template-download-attachs',
        add: function (e, data) {
            data.submit();            
        },
        uploaded:function (e,data) {
        	console.log(data);
        	var result = data.jqXHR.responseJSON
            if(typeof(result.statusCode)!='undefined' && result.statusCode == "200"){
                setFileIDs(result.file_ids, jsp_file_id);
            }
        },
        deleteFile:function(e,id){
            deleteFile(e,id, jsp_file_id);
        }
    });
}
function initJqFileUploaderAttach(pickDivId,jsp_file_id) {
	'use strict';
	$('#'+pickDivId).fileupload({
		url: 'multiUploadFiles',
		downloadTemplateId: 'template-download-imgs',
		upTemplateId: 'template-upload-imgs',
		add: function (e, data) {
			$('#'+pickDivId).find("section .up-section").remove();
			data.submit();            
		},
		uploaded:function (e,data) {
			console.log(data);
			var result = data.jqXHR.responseJSON
			if(typeof(result.statusCode)!='undefined' && result.statusCode == "200"){
				$("#"+jsp_file_id).val(result.file_ids);
				if(result.files.length>1){
					$("#"+jsp_file_id).val(result.files[0].file_id);
				}
				$('#'+pickDivId).find("section .z_file").hide();
			}
		},
		deleteFile:function(e,id){
			deleteFile(e,id, jsp_file_id);
			$('#'+pickDivId).find("section .z_file").show();
		}
	});
}
function initJqFileUploaderImgs(pickDivId,jsp_file_id) {
	'use strict';
	$('#'+pickDivId).fileupload({
		url: 'multiUploadFiles',
		downloadTemplateId: 'template-download-imgs',
		upTemplateId: 'template-upload-imgs',
		add: function (e, data) {
			var acceptFileTypes = /^image\/(gif|jpe?g|png|x-icon|bmp)$/i;
			//文件类型判断
			for(var i=0;i<data.originalFiles.length;i++){				
				if(data.originalFiles[i]['type'].length && !acceptFileTypes.test(data.originalFiles[i]['type'])) {
				   alert("不支持上传该文件类型("+data.originalFiles[i]['type']+")!");
				   return false;
				}
			}
			data.submit();            
		},
		uploaded:function (e,data) {
			console.log(data);
			var result = data.jqXHR.responseJSON
			if(typeof(result.statusCode)!='undefined' && result.statusCode == "200"){
				setFileIDs(result.file_ids, jsp_file_id);
			}
		},
		deleteFile:function(e,id){
			deleteFile(e,id, jsp_file_id);
		}
	});
}
function initJqFileUploaderImg(pickDivId,jsp_file_id) {
	'use strict';
	$('#'+pickDivId).fileupload({
		url: 'multiUploadFiles',
		downloadTemplateId: 'template-download-imgs',
		upTemplateId: 'template-upload-imgs',
		add: function (e, data) {
			var acceptFileTypes = /^image\/(gif|jpe?g|png|x-icon|bmp)$/i;
			//文件类型判断
			if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
			  	alert("不支持上传该文件类型("+data.originalFiles[0]['type']+")!");
			   return false;
			}
			$('#'+pickDivId).find("section .up-section").remove();
			data.submit();            
		},
		uploaded:function (e,data) {
			console.log(data);
			var result = data.jqXHR.responseJSON
			if(typeof(result.statusCode)!='undefined' && result.statusCode == "200"){
				$("#"+jsp_file_id).val(result.file_ids);
				if(result.files.length>1){
					$("#"+jsp_file_id).val(result.files[0].file_id);
				}
				$('#'+pickDivId).find("section .z_file").hide();
			}
		},
		deleteFile:function(e,id){
			deleteFile(e,id, jsp_file_id);
			$('#'+pickDivId).find("section .z_file").show();
		}
	});
}

