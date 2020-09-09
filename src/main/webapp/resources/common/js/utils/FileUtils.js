
var Head = document.getElementsByTagName('head')[0],style = document.createElement('style');
var Body=document.getElementsByTagName('body');

//文件全部加载完成显示DOM
function loadScriptDOMLoaded(parm){

    style.innerHTML = 'body{display:none}';//动态加载文件造成样式表渲染变慢，为了防止DOM结构在样式表渲染完成前显示造成抖动，先隐藏body，样式表读完再显示

    Head.insertBefore(style,Head.firstChild)

    var linkScript, linckScriptCount = parm.length, currentIndex = 0;

    for ( var i = 0 ; i < parm.length; i++ ){

        if(/\.css[^\.]*$/.test(parm[i])) {

            linkScript = document.createElement("link");

            linkScript.type = "text/" + ("css");

            linkScript.rel = "stylesheet";

            linkScript.href = parm[i];

        } else {

            linkScript = document.createElement("script");

            linkScript.type = "text/" + ("javascript");

            linkScript.src = parm[i];

        }

        var content=Body[0];
        content.insertBefore(linkScript, content.lastChild)

        linkScript.onload = linkScript.onerror = function(){

            currentIndex++;

            if(linckScriptCount == currentIndex){

                style.innerHTML = 'body{display:block}';

                Head.insertBefore(style,Head.lastChild)

            }

        }

    }

}

//异步加载css,js文件

function loadScript(parm, fn) {

    var linkScript;

    if(/\.css[^\.]*$/.test(parm)) {

        linkScript = document.createElement("link");

        linkScript.type = "text/" + ("css");

        linkScript.rel = "stylesheet";

        linkScript.href = parm;

    } else {

        linkScript = document.createElement("script");

        linkScript.type = "text/" + ("javascript");

        linkScript.src = parm;

    }

    Head.insertBefore(linkScript, Head.lastChild)

    linkScript.onload = linkScript.onerror = function() {

        if(fn) fn()

    }

}



function formatFileSize (bytes) {
    if (typeof bytes !== 'number') {
        return '';
    }
    if (bytes >= 1000000000) {
        return (bytes / 1000000000).toFixed(2) + ' GB';
    }
    if (bytes >= 1000000) {
        return (bytes / 1000000).toFixed(2) + ' MB';
    }
    return (bytes / 1000).toFixed(2) + ' KB';
}

function showIcon(img,ext_name) {

    if(ext_name.indexOf('.')>-1){
        ext_name=ext_name.replace('.','');
    }

    var icon_file_name=iconMap.get(ext_name);

    if(icon_file_name==null || icon_file_name=='null'){
        icon_file_name='unknown';
    }

    icon_file_name+=".png";

    var icon_path = "/resources/images/icon/"+icon_file_name;

    $(img).attr('src',icon_path);

    $(img).onerror=null;

}


// examples:
// loadScriptDOMLoaded([
//
//     "/content/bootstrap/assets/css/style.css",
//
//     "/content/bootstrap/assets/css/bootstrap.css",
//
//     "/content/bootstrap/assets/js/footable/css/footable.standalone.css"
//
// ])
//
// loadScript("/content/bootstrap/assets/css/entypo-icon.css")
//
// loadScript("/content/bootstrap/assets/css/font-awesome.css")