//图片判断
function checkImage(fileId, imageId) {
	var file = document.getElementById(fileId);
	if (file.files[0].type.indexOf("image") != 0) {
		layer.msg("选择的文件不是图片格式");
		return false;
	}
	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.value)) {
		layer.msg("只支持gif/jpg/jpeg/png/GIF/JPG/PNG");
		return false;
	}
	if (file.files[0].size / (1024 * 1024) > 5) {
		layer.msg("图片大小不能大于5M");
		return false;
	}
	return true;
}
// 图片预览
function previewImage(fileId, imageId) {
	var url;
	var file = document.getElementById(fileId);
	var agent = navigator.userAgent;
	if (agent.indexOf("MSIE") >= 1) {
		url = file.value;
	} else if (agent.indexOf("Firefox") > 0) {
		url = window.URL.createObjectURL(file.files.item(0));
	} else if (agent.indexOf("Chrome") > 0) {
		url = window.URL.createObjectURL(file.files.item(0));
	}
	var imgPre = document.getElementById(imageId);
	imgPre.src = url;
}
// 验证组织机构合法性方法
function orgcodeValidate(value) {
	if (value != "") {
		var values = value.split("-");
		var ws = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
		var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		var reg = /^([0-9A-Z]){8}$/;
		if (!reg.test(values[0])) {
			return true
		}
		var sum = 0;
		for (var i = 0; i < 8; i++) {
			sum += str.indexOf(values[0].charAt(i)) * ws[i];
		}
		var C9 = 11 - (sum % 11);
		var YC9 = values[1] + '';
		if (C9 == 11) {
			C9 = '0';
		} else if (C9 == 10) {
			C9 = 'X';
		} else {
			C9 = C9 + '';
		}
		return YC9 != C9;
	}
}
/**
 * 统一信用社会代码验证合法性
 * 
 * @param Code
 * @returns
 */
function CheckSocialCreditCode(Code) {
	var patrn = /^[0-9A-Z]+$/;
	// 18位校验及大写校验
	if ((Code.length != 18) || (patrn.test(Code) == false)) {
		// alert("不是有效的统一社会信用编码！");
		return false;
	} else {
		var Ancode;// 统一社会信用代码的每一个值
		var Ancodevalue;// 统一社会信用代码每一个值的权重
		var total = 0;
		var weightedfactors = [ 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8,
				24, 10, 30, 28 ];// 加权因子
		var str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
		// 不用I、O、S、V、Z
		for (var i = 0; i < Code.length - 1; i++) {
			Ancode = Code.substring(i, i + 1);
			Ancodevalue = str.indexOf(Ancode);
			total = total + Ancodevalue * weightedfactors[i];// 权重与加权因子相乘之和
		}
		var logiccheckcode = 31 - total % 31;
		if (logiccheckcode == 31) {
			logiccheckcode = 0;
		}
		var Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
		var Array_Str = Str.split(',');
		logiccheckcode = Array_Str[logiccheckcode];
		var checkcode = Code.substring(17, 18);
		if (logiccheckcode != checkcode) {
			// alert("不是有效的统一社会信用编码！");
			return false;
		}
		return true;
	}
}

/**
 * 验证组织机构代码是否合法：组织机构代码为8位数字或者拉丁字母+“-”+1位校验码。 验证最后那位校验码是否与根据公式计算的结果相符。 编码规则请参看
 * http://wenku.baidu.com/view/d615800216fc700abb68fc35.html
 */

function isValidOrgCode(orgCode) {
	// return ""==orgCode || orgCode.length==10;
	// return true;
	var ret = false;
	var codeVal = [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" ];
	var intVal = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
			34, 35 ];
	var crcs = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
	if (!("" == orgCode) && orgCode.length == 10) {
		var sum = 0;
		for (var i = 0; i < 8; i++) {
			var codeI = orgCode.substring(i, i + 1);
			var valI = -1;
			for (var j = 0; j < codeVal.length; j++) {
				if (codeI == codeVal[j]) {
					valI = intVal[j];
					break;
				}
			}
			sum += valI * crcs[i];
		}
		var crc = 11 - (sum % 11);
		switch (crc) {
		case 10: {
			crc = "X";
			break;
		}
		default: {
			break;
		}
		}
		if (crc == orgCode.substring(9)) {
			ret = true;
		}
	} else if ("" == orgCode) {
		ret = true;
	}
	return ret;
}

/**
 * 验证营业执照是否合法：营业执照长度须为15位数字，前14位为顺序码， 最后一位为根据GB/T 17710 1999(ISO
 * 7064:1993)的混合系统校验位生成算法 计算得出。此方法即是根据此算法来验证最后一位校验位是否政正确。如果
 * 最后一位校验位不正确，则认为此营业执照号不正确(不符合编码规则)。 以下说明来自于网络:
 * 我国现行的营业执照上的注册号都是15位的，不存在13位的，从07年开始国 家进行了全面的注册号升级就全部都是15位的了，如果你看见的是13位的注
 * 册号那肯定是假的。 15位数字的含义，代码结构工商注册号由14位数字本体码和1位数字校验码
 * 组成，其中本体码从左至右依次为：6位首次登记机关码、8位顺序码。 一、前六位代表的是工商行政管理机关的代码，国家工商行政管理总局用
 * “100000”表示，省级、地市级、区县级登记机关代码分别使用6位行 政区划代码表示。设立在经济技术开发区、高新技术开发区和保税区
 * 的工商行政管理机关（县级或县级以上）或者各类专业分局应由批准 设立的上级机关统一赋予工商行政管理机关代码，并报国家工商行政 管理总局信息化管理部门备案。
 * 二、顺序码是7-14位，顺序码指工商行政管理机关在其管辖范围内按照先 后次序为申请登记注册的市场主体所分配的顺序号。为了便于管理和
 * 赋码，8位顺序码中的第1位（自左至右）采用以下分配规则： 1）内资各类企业使用“0”、“1”、“2”、“3”； 2）外资企业使用“4”、“5”；
 * 3）个体工商户使用“6”、“7”、“8”、“9”。 顺序码是系统根据企业性质情况自动生成的。 三、校验码是最后一位，校验码用于检验本体码的正确性
 */
function isValidBusCode(busCode) {
	// return true;
	var ret = false;
	if (busCode.length == 15) {
		var sum = 0;
		var s = [];
		var p = [];
		var a = [];
		var m = 10;
		p[0] = m;
		for (var i = 0; i < busCode.length; i++) {
			a[i] = parseInt(busCode.substring(i, i + 1), m);
			s[i] = (p[i] % (m + 1)) + a[i];
			if (0 == s[i] % m) {
				p[i + 1] = 10 * 2;
			} else {
				p[i + 1] = (s[i] % m) * 2;
			}
		}
		if (1 == (s[14] % m)) {
			// 营业执照编号正确!
			// alert("营业执照编号正确!");
			ret = true;
		} else {
			// 营业执照编号错误!
			ret = false;
			// alert("营业执照编号错误!");
		}
	} else if ("" == busCode) {
		ret = true;
	}
	return ret;
}