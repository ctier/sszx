function formatNumber(num)
{
	return num < 10 ? "0" + num : num;
}

function longToTime(eventTime)
{
	d = new Date(eventTime);
	var s = "";
	s += d.getFullYear() + "-";
	s += formatNumber(d.getMonth() + 1) + "-";
	s += formatNumber(d.getDate()) + " ";
	s += formatNumber(d.getHours()) + ":";
	s += formatNumber(d.getMinutes()) + ":";
	s += formatNumber(d.getSeconds());
	return s; 
}

/***********************************************************
[功能]解析通过QueryCallDataEx接口获取的呼叫数据当呼叫数据是
一个结构体数据时，通过该函数可以将每个结构体成员作为字符串解
析出来。
[输入]QueryCallDataEx接口的返回值
[返回]数组，数组中每个元素对应一个结构体成员变量的字符串值。
[修改]
<2008-11-27>
解决将形如“%00%00%00%00”的空数据解析成多个空字段
的问题。
***********************************************************/
function decodeCallData(calldata) {
	var arrBuf = new Array();
	var temp = '';
	var ch, i = 0;
	var cint = 0;
	var isFieldEnd = true;//是否解析一个字段完成

	//将QueryCallDataEx接口的返回值转成unicode字符串
	var s = escape(calldata);	
	
	//呼叫数据为空，直接返回
	var len = s.length;
	if (0 == len) {
		return arrBuf;
	}
	while (i < len) {
		ch = s.charCodeAt(i);
		if ((33 <= ch && ch < 37) || (37 < ch && ch <= 128)) {	//除百分号%以外的所有字符
				temp = temp.concat(String.fromCharCode(ch));						
				i += 1;
				isFieldEnd = false;		//正在解析一个新的字段
		} else if (ch == 37) {								//find %
			cint = 0;
				i += 1;
			if ('u' != s.substr(i,1)) {// %XX : map to ascii(XX)
				cint = (cint << 4) | parseInt(s.substr(i,1), 16);
				cint = (cint << 4) | parseInt(s.substr(i+1,1), 16);
				i += 2;
			} else {// %uXXXX : map to unicode(XXXX)
					i += 1;
					cint = (cint << 4) | parseInt(s.substr(i,1), 16);
					cint = (cint << 4) | parseInt(s.substr(i+1,1), 16);
					cint = (cint << 4) | parseInt(s.substr(i+2,1), 16);
					cint = (cint << 4) | parseInt(s.substr(i+3,1), 16);
					i += 4;
			}
		    if (cint != 0) {
		    	temp = temp.concat(String.fromCharCode(cint));
				isFieldEnd = false;		//正在解析一个新的字段
			} else {//遇到一个字段的结束符"%00"
				if (!isFieldEnd) {
					arrBuf.push(temp);
					temp = '';
					isFieldEnd = true;		//一个字段解析结束
				}
			}
		}// end of else if (ch == 37)
	}// end of while

	//如果最后一个字段后没有跟结束符"%00"	，则需要将其保存
	if (!isFieldEnd)
		arrBuf.push(temp);
		
	return arrBuf;
}