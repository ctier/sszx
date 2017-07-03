function Dsycom(){
	this.Items = {};
}
Dsycom.prototype.add = function(id,iArray){
	this.Items[id] = iArray;
};
Dsycom.prototype.Exists = function(id){
	if(typeof(this.Items[id]) == "undefined") return false;
	return true;
};

function change_com(v){
	var str="0";
	for(var i=0;i<v;i++){
		str+=("_"+(document.getElementById(scom[i]).selectedIndex-1));
	};
	var ss=document.getElementById(scom[v]);
	with(ss){
		length = 0;
		options[0]=new Option(opt0com[v],opt0com[v]);
		if(v && document.getElementById(scom[v-1]).selectedIndex>0 || !v){
			if(dsycom.Exists(str)){
				ar = dsycom.Items[str];
				for(var i=0;i<ar.length;i++){
					options[length]=new Option(ar[i],ar[i]);
				}//end for
				if(v){ options[0].selected = true; }
			}
		}//end if v
		if(++v<scom.length){change_com(v);}
	}//End with
}

var dsycom = new Dsycom();

dsycom.add("0",["中国电信","中国移动","中国联通","中移铁通","虚拟运营商","其他","三网融合试点企业","互联网企业"]);
dsycom.add("0_4",["阿里巴巴通信技术（北京）有限公司","苏州蜗牛数字科技股份有限公司","北京国美电器有限公司","海南海航信息技术有限公司",
                "深圳爱施德股份有限公司","苏宁云商集团股份有限公司苏宁互联公司","北京迪信通通信服务有限公司","北京分享在线网络技术有限公司",
                "北京京东叁佰陆拾度电子商务有限公司","鹏博士电信传媒集团股份有限公司","天音通信有限公司","巴士在线控股有限公司","远特（北京）通信技术有限公司",
                "红豆电信有限公司","深圳市中兴视通科技有限公司","广东恒大和通信科技股份有限公司","银盛通信有限公司","中邮世纪（北京）通信技术有限公司",
                "小米科技有限责任公司","话机世界数码连锁集团股份有限公司","贵阳朗玛信息技术股份有限公司","长江时代通信股份有限公司","北京乐语通信科技有限公司",
                "北京联想调频科技有限公司","合一信息技术（北京）有限公司","深圳星美圣典文化传媒集团有限公司","凤凰资产管理有限公司","青岛日日顺网络科技有限公司",
                "北京世纪互联宽带数据中心有限公司","青岛丰信通信有限公司","北京华翔联信科技有限公司","北京青牛科技有限公司","深圳平安通信科技有限公司","二六三网络通信股份有限公司",
                "浙江连连科技有限公司","民生电子商务有限责任公司","中期集团有限公司","郑州市迅捷贸易有限公司","用友移动通信技术服务有限公司","北京北纬通信科技股份有限公司",
                "厦门三五互联科技股份有限公司","广州博元讯息服务有限公司"]);

var scom=["firstCompany","secondCompany"];//三个select的name
var opt0com = ["请选择","请选择"];//初始值
function _init_company(){  //初始化函数
	for(var i=0;i<scom.length-1;i++){
	  document.getElementById(scom[i]).onchange=new Function("change_com("+(i+1)+")");
	}
	change_com(0);
}
