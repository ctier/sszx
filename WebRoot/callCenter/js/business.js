function Dsyb(){
	this.Items = {};
}
Dsyb.prototype.add = function(id,iArray){
	this.Items[id] = iArray;
};
Dsyb.prototype.Exists = function(id){
	if(typeof(this.Items[id]) == "undefined") return false;
	return true;
};

function change_b(v){
	var str="0";
	for(var i=0;i<v;i++){
		str+=("_"+(document.getElementById(sb[i]).selectedIndex-1));
	};
	var ss=document.getElementById(sb[v]);
	with(ss){
		length = 0;
		options[0]=new Option(opt0b[v],opt0b[v]);
		if(v && document.getElementById(sb[v-1]).selectedIndex>0 || !v){
			if(dsyb.Exists(str)){
				ar = dsyb.Items[str];
				for(var i=0;i<ar.length;i++){
					options[length]=new Option(ar[i],ar[i]);
				}//end for
				if(v){ options[0].selected = true; }
			}
		}//end if v
		if(++v<sb.length){change_b(v);}
	}//End with
}

var dsyb = new Dsyb();

dsyb.add("0",["固定通信","移动通信","互联网","其他"]);
dsyb.add("0_0_0",["语音","增值"]);
dsyb.add("0_0_0_0",["长途","市话","国际","其他"]);
dsyb.add("0_0_0_1",["来电显示","声讯台","彩铃彩印","其他"]);
dsyb.add("0_0_1",["网络质量","网络设备","网络资源","IPTV","网络其他"]);
dsyb.add("0_0_2",["网络质量","网络设备","网络资源","IPTV","网络其他"]);
dsyb.add("0_0_3",["网络质量","网络设备","网络资源","IPTV","网络其他"]);
dsyb.add("0_0_4",["网络质量","网络设备","网络资源","网络其他"]);
dsyb.add("0_0",["固话","光纤宽带","DSL宽带","小区宽带","局域网","固定通信其他"]);
dsyb.add("0_1_0",["语音","流量","短信","增值","号码","终端","基础业务"]);
dsyb.add("0_1_1_0",["长途","市话","国际","漫游","其他"]);
dsyb.add("0_1_1_1",["流量","加油包","定向包","优惠包","其他"]);
dsyb.add("0_1_1_2",["短信","垃圾短信","其他"]);
dsyb.add("0_1_1_3",["短信订制","网页订制","APP","声讯台","其他"]);
dsyb.add("0_1_1_4",["码号","靓号"]);
dsyb.add("0_1_1_5",["手机","物联卡","其他"]);
dsyb.add("0_1_1_6",["充值缴费","发票","积分","手机支付","其他"]);
dsyb.add("0_1_2_0",["流量","加油包","定向包","优惠包","其他"]);
dsyb.add("0_1_1",["语音","流量","短信","增值","号码","终端","基础业务"]);
dsyb.add("0_1_2",["流量"]);
dsyb.add("0_1",["2/3G","4G","上网卡"]);
dsyb.add("0_2_0",["QQ","微信","其他"]);
dsyb.add("0_2_1",["微商","网络购物","其他"]);
dsyb.add("0_2_2",["网站接入","网站域名","网站其他"]);
dsyb.add("0_2_3",["朋友圈","空间","微博","其他"]);
dsyb.add("0_2_4",["视频","音频","其他"]);
dsyb.add("0_2_5",["游戏"]);
dsyb.add("0_2_6",["微信支付","财付通","其他"]);
dsyb.add("0_2_7",["定位服务"]);
dsyb.add("0_2_8",["互联网其他"]);
dsyb.add("0_2",["即时通讯","电子商务","网站","网络内容","网络视频","网络游戏","网络支付","定位","互联网其他"]);
dsyb.add("0_3_0",["其他"]);
dsyb.add("0_3",["其他"]);

var sb=["business_first","business_second","business_third","business_fourth"];//三个select的name
var opt0b = ["请选择","请选择","请选择","请选择"];//初始值
function _init_business(){  //初始化函数
	for(var i=0;i<sb.length-1;i++){
	  document.getElementById(sb[i]).onchange=new Function("change_b("+(i+1)+")");
	}
	change_b(0);
}
