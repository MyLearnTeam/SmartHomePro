/**
 * 判断是否为整数
 * 
 * @param obj
 * @returns {boolean}
 */
function isInteger(str) {
    var r = /^\+?[0-9][0-9]*$/;　　// 正整数
    var flag= r.test(str);

    return flag;
}

/**
 * 保留两位小数正数
 * 
 * @param str
 * @returns {boolean}
 */
function isAmount(str){
    if( ! /^\d*(?:\.\d{0,2})?$/.test(str)){
    	return false;
    }
    return true;
}


// 比较日前大小
function compareDate(checkStartDate, checkEndDate) {
    if(checkStartDate.indexOf(" ") != -1){
        checkStartDate=checkStartDate.substring(0,checkStartDate.indexOf(" "));
    }

    if(checkEndDate.indexOf(" ") != -1){
        checkEndDate=checkEndDate.substring(0,checkEndDate.indexOf(" "));
    }

    var arys1= new Array();
    var arys2= new Array();
    if(checkStartDate != null && checkEndDate != null) {
        arys1=checkStartDate.split('-');
        var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);
        arys2=checkEndDate.split('-');
        var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);
        if(sdate > edate) {
            // alert("日期开始时间大于结束时间");
            return false;
        }  else {
            // alert("通过");
            return true;
        }
    }
}

// 判断日期，时间大小
function compareTime(startDate, endDate) {
    if (startDate.length > 0 && endDate.length > 0) {
        var startDateTemp = startDate.split(" ");
        var endDateTemp = endDate.split(" ");

        var arrStartDate = startDateTemp[0].split("-");
        var arrEndDate = endDateTemp[0].split("-");

        var arrStartTime = startDateTemp[1].split(":");
        var arrEndTime = endDateTemp[1].split(":");

        var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
        var allEndDate = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);

        if (allStartDate.getTime() >= allEndDate.getTime()) {
            // alert("startTime不能大于endTime，不能通过");
            return false;
        } else {
            // alert("startTime小于endTime，所以通过了");
            return true;
        }
    } else {
        // alert("时间不能为空");
        return false;
    }
}

// 比较日期，时间大小
function compareDatetime(startDate, endDate) {
    if (startDate.indexOf(" ") != -1 && endDate.indexOf(" ") != -1 ) {
        // 包含时间，日期
       return compareTime(startDate, endDate);
    } else {
        // 不包含时间，只包含日期
      return  compareDate(startDate, endDate);
    }
}


function changeMoneyToChinese(money){
    var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); // 汉字的数字
    var cnIntRadice = new Array("","拾","佰","仟"); // 基本单位
    var cnIntUnits = new Array("","万","亿","兆"); // 对应整数部分扩展单位
    var cnDecUnits = new Array("角","分","毫","厘"); // 对应小数部分单位
    // var cnInteger = "整"; //整数金额时后面跟的字符
    var cnIntLast = "圆"; // 整型完以后的单位
    var maxNum = 999999999999999.9999; // 最大处理的数字

    var IntegerNum; // 金额整数部分
    var DecimalNum; // 金额小数部分
    var ChineseStr=""; // 输出的中文金额字符串
    var parts; // 分离金额后用的数组，预定义
    if( money == "" ){
        return "";
    }
    money = parseFloat(money);
    if( money >= maxNum ){
        $.alert('超出最大处理数字');
        return "";
    }
    if( money == 0 ){
        // ChineseStr = cnNums[0]+cnIntLast+cnInteger;
        ChineseStr = cnNums[0]+cnIntLast
        // document.getElementById("show").value=ChineseStr;
        return ChineseStr;
    }
    money = money.toString(); // 转换为字符串
    if( money.indexOf(".") == -1 ){
        IntegerNum = money;
        DecimalNum = '';
    }else{
        parts = money.split(".");
        IntegerNum = parts[0];
        DecimalNum = parts[1].substr(0,4);
    }
    if( parseInt(IntegerNum,10) > 0 ){// 获取整型部分转换
        zeroCount = 0;
        IntLen = IntegerNum.length;
        for( i=0;i<IntLen;i++ ){
            n = IntegerNum.substr(i,1);
            p = IntLen - i - 1;
            q = p / 4;
            m = p % 4;
            if( n == "0" ){
                zeroCount++;
            }else{
                if( zeroCount > 0 ){
                    ChineseStr += cnNums[0];
                }
                zeroCount = 0; // 归零
                ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
            }
            if( m==0 && zeroCount<4 ){
                ChineseStr += cnIntUnits[q];
            }
        }
        ChineseStr += cnIntLast;
        // 整型部分处理完毕
    }
    if( DecimalNum!= '' ){// 小数部分
        decLen = DecimalNum.length;
        for( i=0; i<decLen; i++ ){
            n = DecimalNum.substr(i,1);
            if( n != '0' ){
                ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
            }
        }
    }
    if( ChineseStr == '' ){
        // ChineseStr += cnNums[0]+cnIntLast+cnInteger;
        ChineseStr += cnNums[0]+cnIntLast;
    }/*
		 * else if( DecimalNum == '' ){ ChineseStr += cnInteger; ChineseStr +=
		 * cnInteger; }
		 */
    return ChineseStr;
}