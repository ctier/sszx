function Dsyc(){
	this.Items = {};
}
Dsyc.prototype.add = function(id,iArray){
	this.Items[id] = iArray;
};
Dsyc.prototype.Exists = function(id){
	if(typeof(this.Items[id]) == "undefined") return false;
	return true;
};

function change_c(v){
	var str="0";
	for(var i=0;i<v;i++){
		str+=("_"+(document.getElementById(sc[i]).selectedIndex-1));
	};
	var ss=document.getElementById(sc[v]);
	with(ss){
		length = 0;
		options[0]=new Option(opt0c[v],opt0c[v]);
		if(v && document.getElementById(sc[v-1]).selectedIndex>0 || !v){
			if(dsyc.Exists(str)){
				ar = dsyc.Items[str];
				for(var i=0;i<ar.length;i++){
					options[length]=new Option(ar[i],ar[i]);
				}//end for
				if(v){ options[0].selected = true; }
			}
		}//end if v
		if(++v<sc.length){change_c(v);}
	}//End with
}

var dsyc = new Dsyc();

dsyc.add("0",["资费","营销","网络","收费","服务","安全","其他"]);
dsyc.add("0_0_0",["资费水平","价格其他"]);
dsyc.add("0_0_1",["用户提前解约","限制更改套餐","超套餐费用","擅自改变套餐收费","变更套餐争议","套餐到期后争议","套餐不合理","最低消费","套餐类其他"]);
dsyc.add("0_0_2",["资费其他争议"]);
dsyc.add("0_0",["资费价格","资费套餐","资费其他"]);
dsyc.add("0_1_0",["不明码标价收费不透明","营销活动","夸大优惠隐瞒条件","未兑现优惠或承诺","其他活动宣传争议"]);
dsyc.add("0_1_1",["电话短信营销","上网弹出广告","DNS劫持","上门营销","其他扰民行为"]);
dsyc.add("0_1_2",["校园营销","小区限制进入","其他竞争争议"]);
dsyc.add("0_1_3",["无服务协议","霸王条款","协议不清","其他协议争议"]);
dsyc.add("0_1_4",["捆绑销售","强制变更服务","其他强制服务"]);
dsyc.add("0_1_5",["营销其他争议"]);
dsyc.add("0_1",["虚假宣传","营销扰民","不正当竞争","协议争议","强制服务","营销其他"]);
dsyc.add("0_2_0",["无线信号覆盖","申请光纤升级","无端口装移","其他覆盖"]);
dsyc.add("0_2_1",["通话质量差","业务功能被封","其他通信质量"]);
dsyc.add("0_2_2",["无法上网","上网质量差","网速不达标","限制上行网速","其他上网争议"]);
dsyc.add("0_2_3",["移动网络故障","固定网络故障","其他网络故障"]);
dsyc.add("0_2_4",["网间电话故障","网间短信故障","网间互联网故障","其他网间故障"]);
dsyc.add("0_2_5",["网络其他争议"]);
dsyc.add("0_2",["网络覆盖","通信不畅","上网不畅","网络故障","网间故障","网络其他"]);
dsyc.add("0_3_0",["计时争议","计量争议","系统差错","计量其他"]);
dsyc.add("0_3_1",["账单条目争议","不提供账单"]);
dsyc.add("0_3_2",["错收费用"]);
dsyc.add("0_3_3",["不明增值业务","手机吸费"]);
dsyc.add("0_3_4",["充值未到账","充错号码","余额不退"]);
dsyc.add("0_3_5",["其他收费争议"]);
dsyc.add("0_3",["计量差错","账单争议","收费差错","不明扣费","充值争议","收费其他"]);
dsyc.add("0_4_0",["装移修超时","配送超时","提醒不及时","其他时限"]);
dsyc.add("0_4_1",["擅自停机","无法停机或销号","业务无法退订","号码被回收","其他退停"]);
dsyc.add("0_4_2",["违反服务协议","违反三包规定","擅自变更服务协议","其他违约"]);
dsyc.add("0_4_3",["客服电话打不通","客服态度","营业窗口","投诉后不处理","工作差错","其他客服问题"]);
dsyc.add("0_4_4",["限制使用方式","限制终端或路由器","限制办理业务","账号被封","其他限制"]);
dsyc.add("0_4_5",["携出方阻挠","携入后无法正常使用","携转其他"]);
dsyc.add("0_4_6",["其他服务争议"]);
dsyc.add("0_4",["服务时限","服务退停","服务违约","服务差错和态度","服务限制","携号转网","服务其他"]);
dsyc.add("0_5_0",["被他人补卡","用户信息被盗用","通信骚扰","通信诈骗","电话非法改号","伪基站","泄露用户信息","其他信息安全"]);
dsyc.add("0_5_1",["电子交易诈骗","密码泄露","钓鱼网站","网络病毒","其他网络安全"]);
dsyc.add("0_5_2",["拒绝办理实名","号码被他人实名","用户提供虚假信息","未实名被停机","实名制其他"]);
dsyc.add("0_5_3",["其他安全争议"]);
dsyc.add("0_5",["信息安全","网络安全","实名制","安全其他"]);
dsyc.add("0_6",["撤诉信","表扬信","建议信","非电信服务","其他无法归类"]);

var sc=["classify_first","classify_second","classify_third"];//三个select的name
var opt0c = ["请选择","请选择","请选择"];//初始值
function _init_classify(){  //初始化函数
	for(var i=0;i<sc.length-1;i++){
	  document.getElementById(sc[i]).onchange=new Function("change_c("+(i+1)+")");
	}
	change_c(0);
}
