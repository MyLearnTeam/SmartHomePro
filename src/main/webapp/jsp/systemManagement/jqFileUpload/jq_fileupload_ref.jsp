<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!-- The template to display files available for upload -->
	<script id="template-upload-imgs" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        	<section class="up-section fl">
				<div><img class="close-upimg delete" data-fileId="{%=file.file_id%}" data-url="{%=file.deleteUrl%}" src="/resources/images/fileUpload/a7.png"></div>
				<img class="up-img" onerror="showIcon(this,'{%=file.extname%}')" onclick="viewImg('{%=file.filename%}','{%=file.filePath%}')" src="{%=file.filePath%}">
				<p class="img-name-p">Processing...</p>
				<input id="taglocation" name="taglocation" value="" type="hidden">
				<input id="tags" name="tags" value="" type="hidden">
				<p>{%=file.filename%}</p>
			</section>
        {% } %}
    </script>
	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-upload fade">
            <td>
                <span class="preview"></span>
            </td>
            <td>
                <p class="name">{%=file.name%}</p>
                <strong class="error"></strong>
            </td>
            <td>
                <p class="size">Processing...</p>
            </td>
            <td>
                {% if (!i && !o.options.autoUpload) { %}
                <button class="start btn btn-primary btn-xs" disabled>上传22</button>
                {% } %}
                {% if (!i) { %}
                <button class="cancel btn btn-warning btn-xs">取消</button>
                {% } %}
            </td>
        </tr>
        {% } %}
    </script>
<!-- The template to display files available for download -->
<script id="template-download-attachs" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            <td>
                <span class="preview">
                    {% if (file.filePath) { %}
                    <a href="{%=file.filePath%}" title="{%=file.filename%}" target="_blank" download="{%=file.filename%}" data-gallery>
                        <img width="32px" onerror="showIcon(this,'{%=file.extname%}')" src="{%=file.filePath%}" onerror="showIcon(this,'{%=file.extName%}')">
                    </a>
                    {% } %}

                </span>
            </td>
            <td>
                <%--<p class="name">--%>
                    <a href="{%=file.filePath%}" title="{%=file.filename%}"  target="_blank" download="{%=file.filename%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.filename%}</a>
                <%--</p>--%>
                {% if (file.error) { %}
                <div><span class="error">Error</span> {%=file.error%}</div>
                {% } %}
            </td>
            <td>
                <span class="size">{%=file.fileSize%} kb</span>
            </td>
            <td>
                <button class="delete btn btn-danger btn-xs" data-type="{%=file.deleteType%}" data-fileId="{%=file.file_id%}" data-url="{%=file.deleteUrl%}" {% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}' {% } %}>删除</button>
                <%--全选删除有bug，暂时屏蔽掉--%>
                <%--<input type="checkbox" name="delete" value="1" class="toggle">--%>
            </td>
        </tr>
        {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download-imgs" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        	<section class="up-section fl">
				
				<div><img class="close-upimg delete" data-fileId="{%=file.file_id%}" data-url="{%=file.deleteUrl%}" src="/resources/images/fileUpload/a7.png"></div>
				<img class="up-img"  onerror="showIcon(this,'{%=file.extname%}')" onclick="viewImg('{%=file.filename%}','{%=file.filePath%}')" src="{%=file.filePath%}">
				<p class="img-name-p">{%=file.filename%}</p>
				<input id="taglocation" name="taglocation" value="" type="hidden">
				<input id="tags" name="tags" value="" type="hidden">
				<p><a href="{%=file.filePath%}" title="{%=file.filename%}" target="_blank" download="{%=file.filename%}">{%=file.filename%}</a></p>
			</section>
        {% } %}
    </script>


<!-- The Templates plugin is included to render the upload/download listings -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/css/jquery.fileupload.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/css/jquery.fileupload-ui.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/css/imgUpload.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/dependJs/tmpl.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/dependJs/load-image.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/dependJs/jquery.blueimp-gallery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jq_FileUpload/jquery.fileupload-ui.js"></script>
<!--上传文件相关-->
<script src="${pageContext.request.contextPath}/jsp/systemManagement/jqFileUpload/fileUploaderUtil.js"></script>
