package com.seeyon.apps.caict.wx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.caict.wx.po.BusinessCodePo;
import com.seeyon.apps.caict.wx.po.FilePo;
import com.seeyon.apps.caict.wx.po.HistoryPo;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.apps.caict.wx.utils.EnumConverUtil;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.JDBCAgent;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.UUIDLong;

public class AppealAcceptDaoImpl implements AppealAcceptDao {
	
	private static Logger LOGGER = Logger.getLogger(AppealAcceptDaoImpl.class);
	
	@Override
	public List<AppealAcceptPo> getAppealData(AppealAcceptEnum appealAcceptEnum) throws BusinessException {
		LOGGER.info("****进入获取微信端用户申诉信息数据方法****");
		if (Strings.isBlank(appealAcceptEnum.getValue())) {
			LOGGER.info("获取用户申诉信息数据，入参状态为空！");
			return new ArrayList<AppealAcceptPo>();
		}
		
		List<AppealAcceptPo> appealList = new ArrayList<AppealAcceptPo>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.ID,A.USER_NAME,A.USER_CAR_ID,A.USER_PHONE,A.USER_TEL,");
		sb.append("A.COMP_NUM,A.USER_EMAIL,A.USER_ADDRESS,A.POSTAL_CODE,A.USER_WORK_ADDRESS,A.USER_APPEAL_DATE,");
		sb.append("A.APPEAL_SOURCE,A.DISPUTE_NUM,A.EP_LOCAL_AREA,A.RESP_ENTERPRISE,A.RESP_ENTERPRISE_XN,");
		sb.append("A.USER_APPEAL_CONTENT,A.OA_INSTANCE_NUM_SS,A.PROVINCE_SHORT FROM WX_APPEAL A WHERE A.STATE = '");
		sb.append(appealAcceptEnum.getValue()).append("' ");
		sb.append("UNION ALL ").append("SELECT A.ID,A.USER_NAME,A.USER_CAR_ID,A.USER_PHONE,A.USER_TEL,");
		sb.append("A.COMP_NUM,A.USER_EMAIL,A.USER_ADDRESS,A.POSTAL_CODE,A.USER_WORK_ADDRESS,A.USER_APPEAL_DATE,");
		sb.append("A.APPEAL_SOURCE,A.DISPUTE_NUM,A.EP_LOCAL_AREA,A.RESP_ENTERPRISE,A.RESP_ENTERPRISE_XN,");
		sb.append("A.USER_APPEAL_CONTENT,A.OA_INSTANCE_NUM_SS,A.PROVINCE_SHORT FROM WEB_APPEAL A WHERE A.STATE = '");
		sb.append(appealAcceptEnum.getValue()).append("'");
		LOGGER.info("查询用户申诉记录的sql=======" + sb);
		JDBCAgent jdbc = new JDBCAgent(false); 
		try {
			jdbc.execute(sb.toString());
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(dataList==null || dataList.size()==0){
				LOGGER.info("查询数据结果为空，不存在");
			}else{
				for(Map<String, Object> map : dataList){
					AppealAcceptPo po = new AppealAcceptPo();
					if(appealAcceptEnum.NEW.getValue().equals(appealAcceptEnum.getValue())){
						po.setState(Constants.SS);
					}else{
						po.setState(Constants.CS);
					}
					//ID
					if(map.get("ID") != null){
						LOGGER.info("id:"+map.get("ID"));
						String  id= map.get("ID").toString();
						po.setId(id);
					}
					//用户姓名
					if(map.get("USER_NAME") != null){
						String userName = map.get("USER_NAME").toString();
						po.setUserName(userName);
					}
					//身份证号
					if(map.get("USER_CAR_ID") != null){
						String carId = map.get("USER_CAR_ID").toString();
						po.setCarId(carId);
					}
					//联系方式
					if(map.get("USER_PHONE") != null){
						String phone = (String)map.get("USER_PHONE");
						po.setPhone(phone);
					}
					//固定电话
					if(map.get("USER_TEL") != null){
						String tel = map.get("USER_TEL").toString();
						po.setTel(tel);
					}
					//企业投诉编码
					if(map.get("COMP_NUM") != null){
						String compNum = (String)map.get("COMP_NUM");
						po.setCompNum(compNum);
					}
					//电子邮件
					if(map.get("USER_EMAIL") != null){
						String email = (String)map.get("USER_EMAIL");
						po.setEmail(email);
					}
					//通信地址
					if(map.get("USER_ADDRESS") != null){
						String userAddress = (String)map.get("USER_ADDRESS");
						po.setUserAddress(userAddress);
					}
					//邮政编码
					if(map.get("POSTAL_CODE") != null){
						String postalCode = (String)map.get("POSTAL_CODE");
						po.setPostalCode(postalCode);
					}
					//工作单位
					if(map.get("USER_WORK_ADDRESS") != null){
						String workAddress = (String)map.get("USER_WORK_ADDRESS");
						po.setWorkAddress(workAddress);
					}
					//申诉日期
					if(map.get("USER_APPEAL_DATE") != null){
						Date appealDate = (Date)map.get("USER_APPEAL_DATE");
						po.setAppealDate(appealDate);
					}
					//申诉来源
					if(map.get("APPEAL_SOURCE") != null){
						String appealSource = (String)map.get("APPEAL_SOURCE");
						po.setAppealSource(appealSource);
					}
					//申诉涉及号码
					if(map.get("DISPUTE_NUM") != null){
						String disputeNum = (String)map.get("DISPUTE_NUM");
						po.setDisputeNum(disputeNum);
					}
					//所属地区
					if(map.get("EP_LOCAL_AREA") != null){
						String locararea =(String)map.get("EP_LOCAL_AREA");
						po.setLocalAreaFir(locararea);
					}
					//被投诉企业一级
					if(map.get("RESP_ENTERPRISE") !=null){
						String respEnterprise=(String)map.get("RESP_ENTERPRISE");
						po.setRespEnterprise(respEnterprise);
					}
					//被投诉企业二级(虚拟运营商)
					if(map.get("RESP_ENTERPRISE_XN") !=null){
						String XnCompany=(String)map.get("RESP_ENTERPRISE_XN");
						po.setXnCompany(XnCompany);
					}
					//申诉内容
					Object field = map.get("USER_APPEAL_CONTENT");
					LOGGER.info("申诉内容**********"+field);
					if(field !=null){
						po.setAppealContent(field.toString());
					}
					if(map.get("OA_INSTANCE_NUM_SS") != null){
						po.setOaInstanceNum(map.get("OA_INSTANCE_NUM_SS").toString());
					}
					if(map.get("PROVINCE_SHORT") != null){
						po.setProvinceShort(map.get("PROVINCE_SHORT").toString());
					}
					appealList.add(po);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****获取用户申诉数据量：" + appealList.size());
		return appealList;
	}

	@Override
	public List<FilePo> getAttachement4Web(String Id) throws BusinessException {
		List<FilePo> fileList = new ArrayList<FilePo>();
		String sql= "SELECT A.FILEID,A.FILENAME,A.FILEURL FROM UPLOADFILE A WHERE A.USERID ='"+Id+"'";
		LOGGER.info("查询用户申诉记录对应的sql=======" + sql);
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sql.toString());
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(Strings.isNotEmpty(dataList)){
				for(Map<String, Object> map : dataList){
					FilePo fp = new FilePo();
					Object file;
					//ID
					file = map.get("FILEID");
					if(file != null && !"".equals(file)){
						fp.setFileId(file.toString());
					}
					// 附件名称
					file = map.get("FILENAME");
					if(file != null && !"".equals(file)){
						fp.setFileName(file.toString());
					}
					//附件url
					file = map.get("FILEURL");
					if(file != null && !"".equals(file)){
						fp.setFilePath(file.toString());
					}
					fileList.add(fp);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);			
			e.printStackTrace();
			throw new BusinessException(e);
		}finally {
			jdbc.close();
		}
		return fileList;
	}

	@Override
	public Long getSlColSummary(String userName,String disputeNum) throws BusinessException {
		LOGGER.info("*****根据争议号码 查询受理流程Colsummary数据****");

		if (Strings.isBlank(disputeNum)) {
			LOGGER.info("根据争议号码 查询Colsummary数据 方法getColSummary入参为空！");
			return null;
		}
		JDBCAgent jdbc = new JDBCAgent(false);
		//受理流程sql
		StringBuilder aSql = new StringBuilder();
		aSql.append("select s.id as ID from col_summary s,").append(AppContext.getSystemProperty("appealAccept.sformfield.formmain")).append(" f")
			.append(" where s.form_recordid=f.id and f.").append(AppContext.getSystemProperty("appealAccept.sformfield.zyhm"))
			.append(" = '").append(disputeNum).append("' ").append("and s.state = '0' and f.state= '1'")
			.append(" and f.").append(AppContext.getSystemProperty("appealAccept.sformfield.userName")).append(" = '").append(userName).append("'");
		LOGGER.info("根据争议号码 查询Colsummary数据方法的sql=======" + aSql);
		Long summaryId = null;
		try {
			jdbc.execute(aSql.toString());
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(Strings.isEmpty(dataList)){
				return null;
			}
			if(dataList.size() > 1){
				LOGGER.error("根据争议号码  查询Colsummary数据方法返回的summary对象数据为" + dataList.size()+",数据错误！");
				return null;
			}
			Map<String, Object> map = dataList.get(0);
			summaryId =Long.parseLong(map.get("ID").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****根据争议号码  查询受理流程Colsummary数据结束****");
		return summaryId;
	}

	@Override
	public Long getXxColSummary(String userName,String disputeNum) throws BusinessException {
		LOGGER.info("*****根据争议号码 查询信息单流程Colsummary数据****");

		if (Strings.isBlank(disputeNum)) {
			LOGGER.info("根据争议号码 查询Colsummary数据 方法getColSummary入参为空！");
			return null;
		}
		JDBCAgent jdbc = new JDBCAgent(false);
		//受理流程sql
		StringBuilder aSql = new StringBuilder();
		aSql.append("select s.id as ID from col_summary s,").append(AppContext.getSystemProperty("appealAccept.formfield.formmain")).append(" f")
			.append(" where s.form_recordid=f.id and f.").append(AppContext.getSystemProperty("appealAccept.formfield.disputeNum"))
			.append(" = '").append(disputeNum).append("' ").append("and s.state = '0' and f.state= '1'")
			.append(" and f.").append(AppContext.getSystemProperty("appealAccept.formfield.userName")).append(" = '").append(userName).append("'");
	
		LOGGER.info("根据争议号码 查询Colsummary数据方法的sql=======" + aSql);
		Long summaryId = null;
		try {
			jdbc.execute(aSql.toString());
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(Strings.isEmpty(dataList)){
				return null;
			}
			if(dataList.size() > 1){
				LOGGER.error("根据争议号码  查询Colsummary数据方法返回的summary对象数据为" + dataList.size()+",数据错误！");
				return null;
			}
			Map<String, Object> map = dataList.get(0);
			summaryId =Long.parseLong(map.get("ID").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****根据争议号码  查询信息单流程Colsummary数据结束****");
		return summaryId;
	}
	@Override
	public Long getBlColSummary(String userName,String disputeNum) throws BusinessException {
		LOGGER.info("*****根据争议号码 查询办理流程Colsummary数据****");

		if (Strings.isBlank(disputeNum)) {
			LOGGER.info("根据争议号码 查询Colsummary数据 方法getColSummary入参为空！");
			return null;
		}
		JDBCAgent jdbc = new JDBCAgent(false);
		//办理流程sql
		StringBuilder bSql = new StringBuilder();
		bSql.append("select s.id as ID from col_summary s,").append(AppContext.getSystemProperty("appealAccept.bformField.formmain")).append(" f")
			.append(" where s.form_recordid=f.id and f.").append(AppContext.getSystemProperty("appealAccept.bformField.zyhm"))
			.append(" = '").append(disputeNum).append("' ").append("and s.state = '0' and f.state= '1'")
			.append(" and f.").append(AppContext.getSystemProperty("appealAccept.bformField.userName")).append(" = '").append(userName).append("'");
		LOGGER.info("根据争议号码 查询办理流程的Colsummary数据方法的sql=======" + bSql);
		Long summaryId = null;
		try {
			jdbc.execute(bSql.toString());
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(Strings.isEmpty(dataList)){
				return null;
			}
			if(dataList.size() > 1){
				LOGGER.error("根据争议号码 查询Colsummary数据方法返回的summary对象数据为" + dataList.size()+",数据错误！");
				return null;
			}
			Map<String, Object> map = dataList.get(0);
			summaryId =Long.parseLong(map.get("ID").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****根据争议号码 查询办理流程Colsummary数据结束****");
		return summaryId;
	}
	@Override
	public void updateProcessFlag(String state, String oaInstanceId,String tabelName) throws BusinessException {
		StringBuffer sb = new StringBuffer();
		sb.append("update ").append(tabelName).append(" a");
		sb.append(" set a.state = ?").append(", a.process_date = ?");
		sb.append(", a.process_result = ?");
		if(tabelName.equals("wx_appeal") || tabelName.equals("web_appeal")){
			sb.append(" where a.oa_instance_num_ss = ?");
		}else{
			sb.append(" where a.oa_instance_num = ?");
		}
		LOGGER.info("执行更新的sql is :"+ sb);
		List<Object> params = new ArrayList<Object>();
		params.add(state);
		params.add(new Date());
		if(state.equals(AppealAcceptEnum.STOP.getValue())){
			params.add(Constants.SHWTG);
		}else if(state.equals(AppealAcceptEnum.TRANSACT.getValue())){
			params.add(Constants.BLZ);
		}else if(state.equals(AppealAcceptEnum.FINISH.getValue())){
			params.add(Constants.YWJ);
		}else if(state.equals(AppealAcceptEnum.ACCEPT.getValue())){
			params.add(Constants.YSL);
		}else if(state.equals(AppealAcceptEnum.FINISH_CANCEL.getValue())){
			params.add(Constants.YCS);
		}
		params.add(oaInstanceId);
		LOGGER.info("手动终止流程修改表单参数params:"+Strings.join(params, "-"));
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString(), params);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
	}

	@Override
	public void updateProcessFlag4Start(String state, AppealAcceptPo appeal,String serialNumber,boolean b,String type) throws BusinessException {
		LOGGER.info("=========更新流程表数据状态方法开始=========");
		//申诉sql
		String wxaSql = "update wx_appeal set state=?,oa_instance_num_ss=?,process_date=?,process_result=? where id=?";
		String webaSql = "update web_appeal set state=?,oa_instance_num_ss=?,process_date=?,process_result=? where id=?";
		//调解sql
		String wxtSql = "update wx_mediate set state=?,oa_instance_num=?,process_date=?,process_result=? where id=?";
		String webtSql = "update web_mediate set state=?,oa_instance_num=?,process_date=?,process_result=? where id=?";
		//撤诉sql
		String wxcSql = "update wx_appeal set state=?,oa_instance_num_ss=?,oa_instance_num_cs=?,process_date=?,process_result=? where id=?";
		String webcSql = "update web_appeal set state=?,oa_instance_num_ss=?,oa_instance_num_cs=?,process_date=?,process_result=? where id=?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(state);
		params.add(serialNumber);
		if(Constants.CS.equals(type)){
			params.add(appeal.getOaInstanceNum());
		}
		params.add(new Date());
		params.add(Constants.SHZ);	
		params.add(appeal.getId());
		LOGGER.info("更新表数据的参数========="+Strings.join(params, "-"));
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			if(Constants.SS.equals(type)){
				if(b){
					jdbc.execute(wxaSql, params);
				}else{
					jdbc.execute(webaSql, params);
				}
			}else if(Constants.TJ.equals(type)){
				if(b){
					jdbc.execute(wxtSql, params);
				}else{
					jdbc.execute(webtSql, params);
				}
			}else{
				if(b){
					jdbc.execute(wxcSql, params);
				}else{
					jdbc.execute(webcSql, params);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			throw  new BusinessException(e);
		} finally {
			jdbc.close();
		}
	}

	@Override
	public String getTabelName(String oaInstanceId,String type) throws BusinessException {
		LOGGER.info("============查询OA实例号存在于那张表中========");
		String formName = "";
		//申诉&撤诉
		String asql = "SELECT * from wx_appeal where OA_INSTANCE_NUM_SS='"+ oaInstanceId +"'";
		LOGGER.info("查询用户申诉记录的sql=======" + asql);
		//撤诉
//		String csql = "SELECT * from wx_appeal where OA_INSTANCE_NUM_CS='"+ oaInstanceId +"'";
		//调解
		String msql = "SELECT * from wx_mediate where OA_INSTANCE_NUM='"+ oaInstanceId +"'";
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			if(Constants.TJ.equals(type)){
				jdbc.execute(msql.toString());
				List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
				if(dataList !=null || dataList.size()>0){
					formName = "web_mediate";
				}else{
					formName = "wx_mediate" ;
				}
//			}else if(Constants.CS.equals(type)){
//				jdbc.execute(csql.toString());
//				List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
//				if(dataList != null || dataList.size() > 0){
//					formName = "web_appeal";
//				}else{
//					formName = "wx_appeal" ;
//				}
			}else{
				jdbc.execute(asql.toString());
				List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
				if(dataList == null || dataList.size() == 0){
					formName = "web_appeal";
				}else{
					formName = "wx_appeal" ;
				}
			}
		}catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("OA实例号存在的表名："+formName);
		return formName ;
	}
	
	@Override
	public boolean isExistTable(String id,String type) throws BusinessException {
		LOGGER.info("---------------判断数据来源哪张表------------------");
		boolean b = false;
		String wxaSql = "select * from wx_appeal where id = '"+id+"'";
		String wxmSql = "select * from wx_mediate where id = '"+id+"'";
		
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			if(Constants.SS.equals(type) || Constants.CS.equals(type)){
				jdbc.execute(wxaSql);
				List<Map<String,Object>> dataList = jdbc.resultSetToList();
				if(dataList.size()>0){
					b = true;
				}
			}else{
				jdbc.execute(wxmSql);
				List<Map<String,Object>> dataList = jdbc.resultSetToList();
				if(dataList.size()>0){
					b = true;
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return b;
	}

	@Override
	public void saveAppealData(AppealAcceptPo aap) throws BusinessException {
		LOGGER.info("开始保存手动发起流程的数据saveAppealData=================");
		StringBuffer sb = new StringBuffer();
		sb.append("insert into wx_appeal (").append("id,state,user_name,user_car_id,user_phone,user_tel,user_address,");
		sb.append("user_email,user_work_address,postal_code,dispute_num,resp_enterprise,");
		sb.append("resp_enterprise_xn,ep_local_area,comp_num,appeal_source,user_appeal_date,user_appeal_content,");
		sb.append("process_result,process_date").append(")values(");
		sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?").append(")");
		LOGGER.info("保存数据的sql=========="+sb);
		List<Object> params = new ArrayList<Object>();
		params.add(aap.getId());
		params.add(AppealAcceptEnum.READ.getValue());
		params.add(aap.getUserName());
		params.add(aap.getCarId());
		params.add(aap.getPhone());
		params.add(aap.getTel());
		params.add(aap.getUserAddress());
		params.add(aap.getEmail());
		params.add(aap.getWorkAddress());
		params.add(aap.getPostalCode());
		params.add(aap.getDisputeNum());
		params.add(aap.getRespEnterprise());
		params.add(aap.getXnCompany());
		params.add(aap.getLocalAreaFir());
		params.add(aap.getCompNum());
		params.add(aap.getAppealSource());
		params.add(aap.getAppealDate());
		params.add(aap.getAppealContent());
		params.add(Constants.SHZ);
		params.add(new Date());
		LOGGER.info("保存手动发起受理流程的参数params:"+Strings.join(params, "-"));
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString(), params);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
	}

	@Override
	public List<AppealAcceptPo> getMediateData(AppealAcceptEnum appealAcceptEnum)
			throws BusinessException {
		LOGGER.info("****进入获取微信&网站申请调解数据方法****");
		
		List<AppealAcceptPo> appealList = new ArrayList<AppealAcceptPo>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.ID,A.USER_NAME,A.USER_CAR_ID,A.USER_PHONE,"); 
		sb.append("A.USER_TEL,A.COMP_NUM,A.USER_EMAIL,A.USER_ADDRESS,");
		sb.append("A.POSTAL_CODE,A.USER_WORK_ADDRESS,A.USER_APPEAL_DATE,");
		sb.append("A.MEDIATE_SOURCE,A.DISPUTE_NUM,A.EP_LOCAL_AREA,A.RESP_ENTERPRISE,");
		sb.append("A.RESP_ENTERPRISE_XN,A.MEDIATE_CONTENT,A.OA_INSTANCE_NUM,A.PROVINCE_SHORT");
		sb.append(" FROM WX_MEDIATE A WHERE A.STATE = '");
		sb.append(appealAcceptEnum.getValue()).append("' ");
		sb.append("UNION ALL ").append("SELECT A.ID,A.USER_NAME,A.USER_CAR_ID,A.USER_PHONE,");
		sb.append("A.USER_TEL,A.COMP_NUM,A.USER_EMAIL,A.USER_ADDRESS,");
		sb.append("A.POSTAL_CODE,A.USER_WORK_ADDRESS,A.USER_APPEAL_DATE,");
		sb.append("A.MEDIATE_SOURCE,A.DISPUTE_NUM,A.EP_LOCAL_AREA,A.RESP_ENTERPRISE,");
		sb.append("A.RESP_ENTERPRISE_XN,A.MEDIATE_CONTENT,A.OA_INSTANCE_NUM,A.PROVINCE_SHORT");
		sb.append(" FROM WEB_MEDIATE A WHERE A.STATE ='");
		sb.append(appealAcceptEnum.getValue()).append("'");
		LOGGER.info("查询申请调解数据的sql=======" + sb);
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString());
			
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(dataList==null || dataList.size()==0){
				LOGGER.info("查询数据不能为空！");
			}else{
				for(Map<String, Object> map : dataList){
					AppealAcceptPo po = new AppealAcceptPo();
					po.setState(Constants.TJ);
					//ID
					if(map.get("ID") != null){
						String  id= map.get("ID").toString();
						po.setId(id);
					}
					//用户姓名
					if(map.get("USER_NAME") != null){
						String userName = map.get("USER_NAME").toString();
						po.setUserName(userName);
					}
					//身份证号
					if(map.get("USER_CAR_ID") != null){
						String carId = map.get("USER_CAR_ID").toString();
						po.setCarId(carId);
					}
					//联系方式
					if(map.get("USER_PHONE") != null){
						String phone = (String)map.get("USER_PHONE");
						po.setPhone(phone);
					}
					//固定电话
					if(map.get("USER_TEL") != null){
						String tel = map.get("USER_TEL").toString();
						po.setTel(tel);
					}
					//企业投诉编码
					if(map.get("COMP_NUM") != null){
						String compNum = (String)map.get("COMP_NUM");
						po.setCompNum(compNum);
					}
					//电子邮件
					if(map.get("USER_EMAIL") != null){
						String email = (String)map.get("USER_EMAIL");
						po.setEmail(email);
					}
					//通信地址
					if(map.get("USER_ADDRESS") != null){
						String userAddress = (String)map.get("USER_ADDRESS");
						po.setUserAddress(userAddress);
					}
					//邮政编码
					if(map.get("POSTAL_CODE") != null){
						String postalCode = (String)map.get("POSTAL_CODE");
						po.setPostalCode(postalCode);
					}
					//工作单位
					if(map.get("WORK_ADDRESS") != null){
						String workAddress = (String)map.get("USER_WORK_ADDRESS");
						po.setWorkAddress(workAddress);
					}
					//申诉日期
					if(map.get("USER_APPEAL_DATE") != null){
						Date appealDate = (Date)map.get("USER_APPEAL_DATE");
						po.setAppealDate(appealDate);
					}
					//申诉来源
					if(map.get("MEDIATE_SOURCE") != null){
						String appealSource = (String)map.get("MEDIATE_SOURCE");
						po.setAppealSource(appealSource);
					}
					//申诉涉及号码
					if(map.get("DISPUTE_NUM") != null){
						String disputeNum = (String)map.get("DISPUTE_NUM");
						po.setDisputeNum(disputeNum);
					}
					//所属地区
					if(map.get("EP_LOCAL_AREA") != null){
						String locararea =(String)map.get("EP_LOCAL_AREA");
						po.setLocalAreaFir(locararea);
					}
					//被投诉企业一级
					if(map.get("RESP_ENTERPRISE") !=null){
						String respEnterprise=(String)map.get("RESP_ENTERPRISE");
						po.setRespEnterprise(respEnterprise);
					}
					//被投诉企业二级(虚拟运营商)
					if(map.get("RESP_ENTERPRISE_XN") !=null){
						String XnCompany=(String)map.get("RESP_ENTERPRISE_XN");
						po.setXnCompany(XnCompany);
					}
					//申诉内容
					Object field = map.get("MEDIATE_CONTENT");
					LOGGER.info("申诉内容**********"+field);
					if(field !=null){
						po.setAppealContent(field.toString());
					}
					if(map.get("OA_INSTANCE_NUM") != null){
						po.setOaInstanceNum(map.get("OA_INSTANCE_NUM").toString());
					}
					if(map.get("PROVINCE_SHORT") != null){
						po.setProvinceShort(map.get("PROVINCE_SHORT").toString());
					}
					appealList.add(po);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****获取微信端&网站端调解信息数据量：" + appealList.size());
		return appealList;
	}

	@Override
	public long updateFormAttachmentColumn(Long id,String tableName,String tableFiel) throws BusinessException {
		LOGGER.info("更新流程表附件ID字段....");
		long uuid = UUIDLong.longUUID();
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableName);
		sql.append(" set ").append(tableFiel).append("='");
		sql.append(uuid).append("'").append(" where id = '").append(id).append("'");
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return uuid;
	}

	@Override
	public AppealAcceptPo getAppealDate(String key, Long formId) throws BusinessException {
		LOGGER.info("进入获取相应流程表单中申诉日期方法getAppealDate");
		//申诉信息单申请日期
		String xsd = AppContext.getSystemProperty("appealAccept.formfield.appealDate");
		String xsTable = AppContext.getSystemProperty("appealAccept.formfield.formmain");
		String xslh = AppContext.getSystemProperty("appealAccept.formfield.oaInstanceId");
		//受理信息单申请日期
		String acd = AppContext.getSystemProperty("appealAccept.sformfield.appealDate");
		String acTable = AppContext.getSystemProperty("appealAccept.sformfield.formmain");
		String aslh = AppContext.getSystemProperty("appealAccept.sformfield.xxdlsh");
		//办理信息单申请日期
		String trd = AppContext.getSystemProperty("appealAccept.bformField.appealDate");
		String trTable = AppContext.getSystemProperty("appealAccept.bformField.formmain");
		String tslh = AppContext.getSystemProperty("appealAccept.bformField.aInstanceId");
		//调解信息单申请日期
		String md = AppContext.getSystemProperty("appealAccept.tformField.mediateDate");
		String mTable = AppContext.getSystemProperty("appealAccept.tformField.formmain");
		String mslh = AppContext.getSystemProperty("appealAccept.tformField.serialNumber");
		
		StringBuilder xsql = new StringBuilder();
		xsql.append("select ").append(xsd).append(",").append(xslh).append(" from ").append(xsTable);
		xsql.append(" where id = '").append(formId).append("'");
		LOGGER.info("查询信息单申诉时间和流水号的sql:"+xsql.toString());
		
		StringBuilder asql = new StringBuilder();
		asql.append("select ").append(acd).append(",").append(aslh).append(" from ").append(acTable);
		asql.append(" where id = '").append(formId).append("'");
		
		StringBuilder tsql = new StringBuilder();
		tsql.append("select ").append(trd).append(",").append(tslh).append(" from ").append(trTable);
		tsql.append(" where id = '").append(formId).append("'");
		
		StringBuilder msql = new StringBuilder();
		msql.append("select ").append(md).append(",").append(mslh).append(" from ").append(mTable);
		msql.append(" where id = '").append(formId).append("'");
		
		JDBCAgent jdbc = new JDBCAgent(false);
		Date d = null;
		String instanceNum ="";
		AppealAcceptPo aap = null;
		try {
			if("xx".equals(key)){
				jdbc.execute(xsql.toString());
				List<Map<String,Object>> list = jdbc.resultSetToList(true);
				if(list != null || list.size()>0){
					Map<String,Object> map = list.get(0);
					d = (Date)map.get(xsd);
					instanceNum = map.get(xslh).toString();
					aap = new AppealAcceptPo();
					aap.setAppealDate(d);
					aap.setOaInstanceNum(instanceNum);
				}
			}else if("sl".equals(key)){
				jdbc.execute(asql.toString());
				List<Map<String,Object>> list = jdbc.resultSetToList(true);
				if(list != null || list.size()>0){
					Map<String,Object> map = list.get(0);
					d = (Date)map.get(acd);
					instanceNum = map.get(aslh).toString();
					aap = new AppealAcceptPo();
					aap.setAppealDate(d);
					aap.setOaInstanceNum(instanceNum);
				}
			}else if("bl".equals(key)){
				jdbc.execute(tsql.toString());
				List<Map<String,Object>> list = jdbc.resultSetToList(true);
				if(list != null || list.size()>0){
					Map<String,Object> map = list.get(0);
					d = (Date)map.get(trd);
					instanceNum = map.get(tslh).toString();
					aap = new AppealAcceptPo();
					aap.setAppealDate(d);
					aap.setOaInstanceNum(instanceNum);
				}
			}else if("tj".equals(key)){
				jdbc.execute(msql.toString());
				List<Map<String,Object>> list = jdbc.resultSetToList(true);
				if(list != null || list.size()>0){
					Map<String,Object> map = list.get(0);
					d = (Date)map.get(md);
					instanceNum = map.get(mslh).toString();
					aap = new AppealAcceptPo();
					aap.setAppealDate(d);
					aap.setOaInstanceNum(instanceNum);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return aap;
	}

	@Override
	public Long saveAppealData2DB(AppealAcceptPo appeal) throws BusinessException {
		LOGGER.info("定时任务保存数据到底表saveAppealData2DB方法中...");
		String table = AppContext.getSystemProperty("appealAccept.dbFormField.formmain");
		String userName = AppContext.getSystemProperty("appealAccept.dbFormField.userName");
		String workAdd = AppContext.getSystemProperty("appealAccept.dbFormField.workAdd");
		String disputeNum = AppContext.getSystemProperty("appealAccept.dbFormField.disputeNum");
		String poscode = AppContext.getSystemProperty("appealAccept.dbFormField.poscode");
		String userAdd = AppContext.getSystemProperty("appealAccept.dbFormField.userAdd");
		String usercarId = AppContext.getSystemProperty("appealAccept.dbFormField.usercarId");
		String appealSource = AppContext.getSystemProperty("appealAccept.dbFormField.appealSource");
		String appealDate = AppContext.getSystemProperty("appealAccept.dbFormField.appealDate");
		String userPhone = AppContext.getSystemProperty("appealAccept.dbFormField.userPhone");
		String email = AppContext.getSystemProperty("appealAccept.dbFormField.email");
		String bssqy = AppContext.getSystemProperty("appealAccept.dbFormField.bssqy");
		String ejssqy = AppContext.getSystemProperty("appealAccept.dbFormField.ejssqy");
		String qytsbm = AppContext.getSystemProperty("appealAccept.dbFormField.qytsbm");
		String dqyj = AppContext.getSystemProperty("appealAccept.dbFormField.dqyj");
		String dqej = AppContext.getSystemProperty("appealAccept.dbFormField.dqej");
		String appealContext = AppContext.getSystemProperty("appealAccept.dbFormField.appealContext");
		String sslx = AppContext.getSystemProperty("appealAccept.dbFormField.sslx");
		String jc = AppContext.getSystemProperty("appealAccept.dbFormField.jc");
		String flowState = AppContext.getSystemProperty("appealAccept.dbFormField.flowState");
		String dataId = AppContext.getSystemProperty("appealAccept.dbFormField.dataId");
		String cslsh = AppContext.getSystemProperty("appealAccept.dbFormField.cslsh");
		
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(table).append("(");
		sb.append("id,").append(userName).append(",").append(workAdd).append(",");
		sb.append(disputeNum).append(",").append(poscode).append(",").append(userAdd).append(",");
		sb.append(usercarId).append(",").append(appealSource).append(",").append(appealDate).append(",");
		sb.append(userPhone).append(",").append(email).append(",").append(bssqy).append(",");
		sb.append(ejssqy).append(",").append(qytsbm).append(",").append(dqyj).append(",");
		sb.append(dqej).append(",").append(appealContext).append(",");
		sb.append(sslx).append(",").append(jc).append(",").append(flowState).append(",").append(dataId).append(",").append(cslsh)
		.append(")values(").append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?").append(")");
		LOGGER.info("保存数据底表的sql============="+sb.toString());
		
		List<Object> params = new ArrayList<Object>();
		long uuid = UUIDLong.longUUID();
		params.add(uuid);
		params.add(appeal.getUserName());
		params.add(appeal.getWorkAddress());
		params.add(appeal.getDisputeNum());
		params.add(appeal.getPostalCode());
		params.add(appeal.getUserAddress());
		params.add(appeal.getCarId());
		Long lyId = EnumConverUtil.sslyEnumConver(appeal.getAppealSource());
		params.add(lyId);
		params.add(appeal.getAppealDate());
		params.add(appeal.getPhone());
		params.add(appeal.getEmail());
		Long firstQyId = EnumConverUtil.qyEnumConver(appeal.getRespEnterprise());
        Long secQyId = EnumConverUtil.qyEnumConver(appeal.getXnCompany());
		params.add(firstQyId);
		params.add(secQyId);
		params.add(appeal.getCompNum());
		String[] a = appeal.getLocalAreaFir().split("-");
        Long provinceId = EnumConverUtil.locEnumConver(a[0]);
        Long cityId = EnumConverUtil.locEnumConver(a[1]);
		params.add(provinceId);
		params.add(cityId);
		params.add(appeal.getAppealContent());
		Long tId = EnumConverUtil.appealTypeEnumConver(appeal.getState());
		params.add(tId);
		params.add(appeal.getProvinceShort());
		params.add(appeal.getFlowState());
		params.add(appeal.getId());
		params.add(appeal.getOaInstanceNum());
		LOGGER.info("插入到信息底表中的参数："+Strings.join(params, ","));
		
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString(), params);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return uuid;
	}

	@Override
	public Long getTjColSummary(String userName,String disputeNum) throws BusinessException {
		LOGGER.info("*****根据争议号码 查询受理流程Colsummary数据****");

		if (Strings.isBlank(disputeNum)) {
			LOGGER.info("根据争议号码 查询Colsummary数据 方法getColSummary入参为空！");
			return null;
		}
		JDBCAgent jdbc = new JDBCAgent(false);
		//受理流程sql
		StringBuilder aSql = new StringBuilder();
		aSql.append("select s.id as ID from col_summary s,").append(AppContext.getSystemProperty("appealAccept.tformField.formmain")).append(" f")
			.append(" where s.form_recordid=f.id and f.").append(AppContext.getSystemProperty("appealAccept.tformField.zyhm"))
			.append(" = '").append(disputeNum).append("' ").append("and s.state = '0' and f.state= '1'")
			.append(" and f.").append(AppContext.getSystemProperty("appealAccept.tformField.userName")).append(" = '").append(userName).append("'");
		LOGGER.info("根据争议号码 查询Colsummary数据方法的sql=======" + aSql);
		Long summaryId = null;
		try {
			jdbc.execute(aSql.toString());
			List<Map<String, Object>> dataList = jdbc.resultSetToList(false);
			if(Strings.isEmpty(dataList)){
				return null;
			}
			if(dataList.size() > 1){
				LOGGER.error("根据争议号码  查询Colsummary数据方法返回的summary对象数据为" + dataList.size()+",数据错误！");
				return null;
			}
			Map<String, Object> map = dataList.get(0);
			summaryId =Long.parseLong(map.get("ID").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		LOGGER.info("****根据争议号码  查询受理流程Colsummary数据结束****");
		return summaryId;
	}
	@Override
	public List<String> getAakForLeaveId(String groupId) throws BusinessException {
	    LOGGER.info("enter into method getAskForleaveId() ");
	    String field0005 = AppContext.getSystemProperty("appealAccept.aflFormField.userid");
	    String field0009 = AppContext.getSystemProperty("appealAccept.aflFormField.starttime");
	    String field0010 = AppContext.getSystemProperty("appealAccept.aflFormField.endtime");
		//获取时间
		Date  createDate = new Date(); 
		String time = DateUtil.format(createDate,DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.");
		sql.append(field0005).append(" as FIELD0005 from ");
		sql.append(AppContext.getSystemProperty("appealAccept.aflFormField.formmain")).append(" a ").append("  ");
		sql.append("where (a." ).append(field0009).append(" > ");
		sql.append("to_date('").append(time).append("','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" or a.").append(field0010).append(" < ");
		sql.append("to_date('").append(time).append("','yyyy-MM-dd hh24:mi:ss') or (a.").append(field0009);
		sql.append(" is null and a.").append(field0009).append(" is null))");
		sql.append(" and a.").append(AppContext.getSystemProperty("appealAccept.aflFormField.groupid")).append(" = '");
		sql.append(groupId).append("' ");	
		
		LOGGER.info("查询请休假人员状态底表中人员id的sql="+sql);
		
		JDBCAgent jdbc=new JDBCAgent(false);
		List<String> listId = new ArrayList<String>();
		try {
			jdbc.execute(sql.toString());
			List<Map<String, Object>> datalist = jdbc.resultSetToList(false);
			if(datalist == null || datalist.size() == 0){
				LOGGER.info("retrieve  user id failed.");
			}else{
				for(Map<String,Object> map: datalist){
					String userId = (String)map.get(field0005.toUpperCase());
					listId.add(userId);
				}
				LOGGER.info("retrieve  group id success. The user id :"+listId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return listId;
	}

	@Override
	public List<Long> getDbData4AssigningTask(Long companyId,FlipInfo fpi)
			throws BusinessException {
		LOGGER.info("====获取运营商未分配任务的ID====");
		StringBuilder sb = new StringBuilder();
		sb.append("select ID from ").append(AppContext.getSystemProperty("appealAccept.dbFormField.formmain"));
		sb.append(" where ").append(AppContext.getSystemProperty("appealAccept.dbFormField.bssqy"));
		sb.append(" = '").append(companyId).append("' and ");
		sb.append(AppContext.getSystemProperty("appealAccept.dbFormField.flowState")).append(" = '");
		sb.append(AppContext.getSystemProperty("appealAccept.flowStateEnum.wfp")).append("'");
		LOGGER.info("查询运营商未分配任务的sql====="+sb);
		JDBCAgent jdbc = new JDBCAgent(false);
		List<Long> ids = new ArrayList<Long>();
		try {
			fpi = jdbc.findByPaging(sb.toString(), fpi);
			List<Map<String,Object>> dataList = fpi.getData();
			if(dataList != null && dataList.size() > 0){
				for(Map<String,Object> map: dataList){
					Long dbId = Long.valueOf(map.get("id").toString());
					ids.add(dbId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return ids;
	}

	@Override
	public List<Long> getParentEnumId() throws BusinessException {
		LOGGER.info("====进入获取运营商ID方法========");
		String id = AppContext.getSystemProperty("appealAccept.formfield.btsqy");
		String sql = "select ID from ctp_enum_item e where e.ref_enumid = '"+id+"' and e.parent_id = '0'";
		JDBCAgent jdbc = new JDBCAgent(false);
		List<Long> ids = new ArrayList<Long>();
		try {
			jdbc.execute(sql.toString());
			List<Map<String,Object>> dataList = jdbc.resultSetToList(false);
			if(dataList != null && dataList.size() > 0){
				for(Map<String,Object> map: dataList){
					Long dbId = Long.valueOf(map.get("ID").toString());
					ids.add(dbId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return ids;
	}

	@Override
	public List<AppealAcceptPo> getCountDbData4AssigningTask() throws BusinessException {
		List<AppealAcceptPo> aapList = new ArrayList<AppealAcceptPo>();
		String appealType = AppContext.getSystemProperty("appealAccept.dbFormField.sslx");
		String userName = AppContext.getSystemProperty("appealAccept.dbFormField.userName");
		String userPhone = AppContext.getSystemProperty("appealAccept.dbFormField.userPhone");
		String disputeNum = AppContext.getSystemProperty("appealAccept.dbFormField.disputeNum");
		String company = AppContext.getSystemProperty("appealAccept.dbFormField.bssqy");
		String area = AppContext.getSystemProperty("appealAccept.dbFormField.dqyj");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select ID,").append(appealType).append(",").append(userName).append(",").append(userPhone);
		sql.append(",").append(disputeNum).append(",").append(company).append(",").append(area).append(" from ");
		sql.append(AppContext.getSystemProperty("appealAccept.dbFormField.formmain"));
		sql.append(" where ").append(AppContext.getSystemProperty("appealAccept.dbFormField.flowState")).append(" = '");
		sql.append(AppContext.getSystemProperty("appealAccept.flowStateEnum.wfp")).append("'");
		LOGGER.info("获取未分配的任务sql---------"+sql);
		JDBCAgent jdbc = new JDBCAgent(false);
        try {
        	jdbc.execute(sql.toString());
        	List<Map<String,Object>> dataList = jdbc.resultSetToList(false);
        	if(dataList != null && dataList.size() > 0){
				for(Map<String,Object> map: dataList){
					AppealAcceptPo aap = new AppealAcceptPo();
					String dbId = map.get("ID").toString();
					aap.setId(dbId);
					//申诉类型
					if(map.get(appealType) != null){
						String lx = map.get(appealType).toString();
						aap.setState(lx);
					}
					//用户姓名
					if(map.get(userName) != null){
						String name = map.get(userName).toString();
						aap.setUserName(name);
					}
					//联系电话
					if(map.get(userPhone) != null){
						String phone = map.get(userPhone).toString();
						aap.setPhone(phone);
					}
					//涉及号码
					if(map.get(disputeNum) != null){
						String num = map.get(disputeNum).toString();
						aap.setDisputeNum(num);
					}
					//被投诉企业
					if(map.get(company) != null){
						String cp = map.get(company).toString();
						aap.setRespEnterprise(cp);
					}
					//所属地区
					if(map.get(area) != null){
						String dq = map.get(area).toString();
						aap.setLocalAreaFir(dq);
					}
					aapList.add(aap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
        return aapList;
	}

	@Override
	public void updateDbPersonId(Long taskId, Long personId,String code)
			throws BusinessException {
		LOGGER.info("进入更新信息底表人员ID的方法....");
		if(personId == null){
			LOGGER.error("认定员ID参数为空，请确认！");
			return;
		}
		String sql = "update "+AppContext.getSystemProperty("appealAccept.dbFormField.formmain")+" d " +
				"set d."+AppContext.getSystemProperty("appealAccept.dbFormField.personId")+" ='"+personId+"',d. " +
				AppContext.getSystemProperty("appealAccept.dbFormField.personNum")+" = '"+code+ "' where id = '"+taskId+"'" ;
		LOGGER.info("更新信息底表人员ID的sql====="+sql);
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sql);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
	}

	@Override
	public Long getFormCompanyId(String formRecordId,String tableName,String companyField) throws BusinessException {
		LOGGER.info("进入获取表单中运营商企业的ID方法getFormCompanyId...");
		String sql = "select a."+companyField+" as FIELD0015 from "+tableName+" a where a.id ='"+formRecordId+"'";
		JDBCAgent jdbc = new JDBCAgent(false);
		Long companyId = null;
		try {
			jdbc.execute(sql);
			List<Map<String,Object>> dataList = jdbc.resultSetToList(false);
			if(dataList != null && dataList.size() > 0){
				Map<String,Object> map = dataList.get(0);
				companyId = Long.valueOf(map.get("FIELD0015").toString());
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return companyId;
	}

	@Override
	public int getHandleFormCount(String memberId, String templeteId)
			throws BusinessException {
		
		Date startTime = EnumConverUtil.getStartTime();
		Date endTime = EnumConverUtil.getEndTime();
		String st = DateUtil.format(startTime, DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
		String et = DateUtil.format(endTime, DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as rowCount ");
		sql.append(" from ctp_affair a");
		sql.append(" where a.member_id = '").append(memberId).append("'");
		sql.append(" and a.templete_id = '").append(templeteId).append("'");
		sql.append(" and a.create_date between to_date('").append(st).append("','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" and to_date('").append(et).append("','yyyy-MM-dd hh24:mi:ss')");
		LOGGER.info("获取当天处理表单事项的数量sql==========="+sql);
		JDBCAgent jdbc = new JDBCAgent(false);
        try {
        	jdbc.execute(sql.toString());
    		ResultSet rs = jdbc.getQueryResult();
        	rs.next();
			return rs.getInt("rowCount");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return 0;
		} finally {
			jdbc.close();
		}
	}

	@Override
	public void updateLeaveInfo2DB(Long memberId, String code, Date startTime,
			Date endTime) throws BusinessException {
		LOGGER.info("更新人员请休假信息底表请假时间开始......");
		String table = AppContext.getSystemProperty("appealAccept.aflFormField.formmain");
		String st = AppContext.getSystemProperty("appealAccept.aflFormField.starttime");
		String et = AppContext.getSystemProperty("appealAccept.aflFormField.endtime");
		String uid = AppContext.getSystemProperty("appealAccept.aflFormField.userid");
		String userCode = AppContext.getSystemProperty("appealAccept.aflFormField.userCode");
				
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(table).append(" l ").append("set l.").append(st).append(" = '");
		sb.append(startTime).append("',l.").append(et).append(" = '").append(endTime).append("' ");
		sb.append("where l.").append(uid).append("= '").append(memberId).append("' and l.");
		sb.append(userCode).append(" = '").append(code).append("'");
		LOGGER.info("更新人员信息底表请休假时间的sql========="+sb);
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
	}

	@Override
	public void updateDbState(String dataId) throws BusinessException {
		LOGGER.info("开始更新底表数据状态方法....");
		String sql = "update "+AppContext.getSystemProperty("appealAccept.dbFormField.formmain")+" " +
					"d set d."+AppContext.getSystemProperty("appealAccept.dbFormField.flowState")+" = " +
					"'"+AppContext.getSystemProperty("appealAccept.flowStateEnum.yfp")+"' " +
					"where d."+AppContext.getSystemProperty("appealAccept.dbFormField.dataId")+" ='"+dataId+"'";
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sql);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
	}

	@Override
	public List<AppealAcceptPo> getTask4DB() throws BusinessException {
		LOGGER.info("进入获取底表数据任务的方法....");
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ").append(AppContext.getSystemProperty("appealAccept.dbFormField.formmain"))
		  .append(" where ").append(AppContext.getSystemProperty("appealAccept.dbFormField.personId")).append(" is not null and ")
		  .append(AppContext.getSystemProperty("appealAccept.dbFormField.flowState")).append(" = '")
		  .append(AppContext.getSystemProperty("appealAccept.flowStateEnum.wfp")).append("'");
		LOGGER.info("底表中查询满足条件数据自动发起的sql===="+sb);
		List<AppealAcceptPo> list = new ArrayList<AppealAcceptPo>();
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString());
			List<Map<String,Object>> dataList = jdbc.resultSetToList(true);
			if(dataList==null || dataList.size()==0){
				LOGGER.info("查询数据结果为空，不存在");
			}else{
				for(Map<String, Object> map : dataList){
					AppealAcceptPo po = new AppealAcceptPo();
					//申诉类型
					if(map.get("field0084") != null){
						po.setState(map.get("field0084").toString());
					}
					//数据ID
					if(map.get("field0008") != null){
						LOGGER.info("id:"+map.get("field0008"));
						String  id= map.get("field0008").toString();
						po.setId(id);
					}
					//用户姓名
					if(map.get("field0001") != null){
						String userName = map.get("field0001").toString();
						po.setUserName(userName);
					}
					//工作单位
					if(map.get("field0002") != null){
						String workAddress = (String)map.get("field0002");
						po.setWorkAddress(workAddress);
					}
					//申诉日期
					if(map.get("field0066") != null){
						Date appealDate = (Date)map.get("field0066");
						po.setAppealDate(appealDate);
					}
					//联系方式
					if(map.get("field0069") != null){
						Long phone = ((java.math.BigDecimal)map.get("field0069")).longValue();
						po.setPhone(String.valueOf(phone));
					}
					//身份证号
					if(map.get("field0043") != null){
						String carId = map.get("field0043").toString();
						po.setCarId(carId);
					}
					//固定电话
					if(map.get("field0095") != null){
						String tel = map.get("field0095").toString();
						po.setTel(tel);
					}
					//企业投诉编码
					if(map.get("field0085") != null){
						String compNum = (String)map.get("field0085");
						po.setCompNum(compNum);
					}
					//电子邮件
					if(map.get("field0080") != null){
						String email = (String)map.get("field0080");
						po.setEmail(email);
					}
					//通信地址
					if(map.get("field0005") != null){
						String userAddress = (String)map.get("field0005");
						po.setUserAddress(userAddress);
					}
					//邮政编码
					if(map.get("field0004") != null){
						String postalCode = (String)map.get("field0004");
						po.setPostalCode(postalCode);
					}
					//申诉来源
					if(map.get("field0063") != null){
						String appealSource = (String)map.get("field0063");
						po.setAppealSource(appealSource);
					}
					//申诉涉及号码
					if(map.get("field0003") != null){
						String disputeNum = (String)map.get("field0003");
						po.setDisputeNum(disputeNum);
					}
					//所属地区一级
					if(map.get("field0067") != null){
						String locararea =(String)map.get("field0067");
						po.setLocalAreaFir(locararea);
					}
					//所属地区二级
					if(map.get("field0072") != null){
						String locararea =(String)map.get("field0072");
						po.setLocalAreaSec(locararea);
					}
					//被投诉企业一级
					if(map.get("field0062") != null){
						String respEnterprise=(String)map.get("field0062");
						po.setRespEnterprise(respEnterprise);
					}
					//被投诉企业二级(虚拟运营商)
					if(map.get("field0076") != null){
						String XnCompany = (String)map.get("field0076");
						po.setXnCompany(XnCompany);
					}
					//省份简称
					if(map.get("field0010") != null){
						String jc = (String)map.get("field0010");
						po.setProvinceShort(jc);
					}
					//申诉附件
					if(map.get("field0055") != null){
						Long fjId = Long.valueOf(map.get("field0055").toString());
						po.setFjId(fjId);
					}
					//申诉内容
					Object field = map.get("field0057");
					LOGGER.info("申诉内容**********"+field);
					if(field !=null){
						po.setAppealContent(field.toString());
					}
					//认定员
					if(map.get("field0083") != null){
						Long personId = Long.valueOf(map.get("field0083").toString());
						po.setPersonId(personId);
					}
					//认定员人员编号
					if(map.get("field0081") != null){
						String code = map.get("field0081").toString();
						po.setCode(code);
					}
					//撤诉时使用流水号
					if(map.get("field0086") != null){
						po.setOaInstanceNum(map.get("field0086").toString());
					}
					list.add(po);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return list;
	}

	@Override
	public List<FilePo> getAllFileUrlByReference(Long subReferenceid)
			throws BusinessException {
		LOGGER.info("根据subReferenceid获取表单中的所有附件ID");
		String sql = "select a.file_Url from ctp_attachment a where a.sub_reference ='"+subReferenceid+"'";
		List<FilePo> fpList = new ArrayList<FilePo>();
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sql);
			List<Map<String,Object>> dataList = jdbc.resultSetToList(true);
			if(dataList==null || dataList.size()==0){
				LOGGER.info("查询数据结果为空，不存在");
			}else{
				for(Map<String, Object> map : dataList){
					FilePo po = new FilePo();
					//附件url
					if(map.get("file_url") != null){
						po.setFileId(map.get("file_url").toString());
						fpList.add(po);
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return fpList;
	}

	@Override
	public Long getXnqyPersonId(Long qyId,Long xnId,boolean b) throws BusinessException {
		LOGGER.info("enter the method getXnqyPersonId....");
		if(qyId == null){
			return null;
		}
		String table = AppContext.getSystemProperty("appealAccept.xnCompany.formmain");
		String qys = AppContext.getSystemProperty("appealAccept.xnCompany.ssqys");
		String qyf = AppContext.getSystemProperty("appealAccept.xnCompany.ssqyf");
		String pNum = AppContext.getSystemProperty("appealAccept.xnCompany.personNum");
		String area = AppContext.getSystemProperty("appealAccept.xnCompany.area");
		//虚拟运营商执行此条sql
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(pNum).append(" from ").append(table);
		sb.append(" where ").append(qyf).append(" ='").append(qyId).append("'");
		if(xnId != null){
			sb.append(" and ").append(qys).append(" ='").append(xnId).append("'");
		}
		LOGGER.info("查询企业回复人员的sql===="+sb);
		//省中心执行此条sql
		StringBuffer sSql = new StringBuffer();
		sSql.append("select ").append(pNum).append(" from ").append(table);
		sSql.append(" where ").append(qyf).append(" ='").append(qyId).append("'");
		sSql.append(" and ").append(area).append(" ='").append(xnId).append("'");
		Long pId = null;
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			List<Map<String,Object>> dataList = null;
			if(b){
				jdbc.execute(sSql.toString());
			}else{
				jdbc.execute(sb.toString());
			}
			dataList = jdbc.resultSetToList(true);
			if(dataList != null && dataList.size()>0){
				Map<String, Object> map = dataList.get(0);
				pId = Long.valueOf(map.get(pNum).toString());
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		LOGGER.info("企业回复人员ID*****"+pId);
		return pId;
	}

	@Override
	public Long getProcessorId(String userName, String userPhone,
			String disputeNum) throws BusinessException {
		LOGGER.info("enter the method getProcessorId....");
		String rdy = AppContext.getSystemProperty("appealAccept.formfield.rdy");
		String infoTable = AppContext.getSystemProperty("appealAccept.formfield.formmain");
		String name = AppContext.getSystemProperty("appealAccept.formfield.userName");
		String phone = AppContext.getSystemProperty("appealAccept.formfield.userPhone");
		String num = AppContext.getSystemProperty("appealAccept.formfield.disputeNum");
		StringBuilder sb = new StringBuilder();
		sb.append("select f.").append(rdy).append(" from col_summary s,").append(infoTable).append(" f where ");
		sb.append(" s.form_recordid = f.id and s.templete_id =? and f.").append(name).append(" =? and f.");
		sb.append(phone).append(" =? and f.").append(num).append(" =? order by s.create_date desc");
		LOGGER.info("查询申诉信息单处理人ID的sql========="+sb);
		List<Object> params = new ArrayList<Object>();
		params.add(AppContext.getSystemProperty("appealAccept.templateId.xx"));
		params.add(userName);
		params.add(userPhone);
		params.add(disputeNum);
		LOGGER.info("查询申诉信息单处理人ID-sql的参数==="+Strings.join(params, ","));
		Long pId = null;
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString(), params);
			List<Map<String,Object>> dataList = jdbc.resultSetToList(true);
			if(dataList != null && dataList.size()>0){
				Map<String, Object> map = dataList.get(0);
				pId = Long.valueOf(map.get(rdy).toString());
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		LOGGER.info("查询申诉信息单处理人ID====="+pId);
		return pId;
	}

	@Override
	public Map<String,Long> getMemberId4CaseSplit(String tableName, String formId)
			throws BusinessException {
		LOGGER.info("进入获取案件拆分企业回复人员方法getMemberId4CaseSplit====");
		String sql = "";
		if(Constants.SLFORMMAIN.equals(tableName)){
			//受理--企业回复人员
			sql = "select  field0032 as ZGYD,field0033 as ZGLT,field0034 as ZGDX," +
					"field0070 as ZYTT,field0072 as XNZ," +
					"field0069 as XNHFRY,field0014 as AREA from "+tableName+" where id = '"+formId+"'";
		}else if(Constants.BLFORMMAIN.equals(tableName)){
			//办理--企业回复人员
			sql = "select  field0012 as ZGYD,field0078 as XNHFRY,field0014 as AREA from "+tableName+" where id = '"+formId+"'";
		}else if(Constants.TJFORMMAIN.equals(tableName)){
			//调解--企业回复人员
			sql = "select  field0012 as ZGYD,field0098 as XNHFRY,field0014 as AREA from "+tableName+" where id = '"+formId+"'";
		}
		LOGGER.info("获取企业回复人员的sql------"+sql);
		JDBCAgent jdbc = new JDBCAgent(false);
		Map<String,Long> reMap = new HashMap<String, Long>();
		try {
			jdbc.execute(sql);
			List<Map<String,Object>> dataList = jdbc.resultSetToList(false);
			LOGGER.info("*******查询企业回复人员的结果为*******"+dataList.size());
			if(dataList != null && dataList.size()>0){
				Map<String, Object> map = dataList.get(0);
				Object o;
				o = map.get("ZGYD");
				if(o != null){
					long rs = Long.valueOf(o.toString());
					if(rs != 0 && rs != 1){
						reMap.put("ZGYD", rs);
					}else if(rs != 0){
						reMap.put("ZGYD", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.yd")));
					}
				}
				o = map.get("ZGLT");
				if(o != null){
					Integer ltResult = Integer.valueOf(o.toString());
					if(ltResult != 0){
						reMap.put("ZGLT", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.lt")));
					}
				}
				o = map.get("ZGDX");
				if(o != null){
					Integer dxResult = Integer.valueOf(o.toString());
					if(dxResult != 0){
						reMap.put("ZGDX", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.dx")));
					}
				}
//				Integer hlwResult = Integer.valueOf(map.get("field0076").toString());
//				if(hlwResult != 0){
//					reMap.put("HLWQY", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.hlw")));
//				}
				o = map.get("ZYTT");
				if(o != null){
					Integer ttResult = Integer.valueOf(o.toString());
					if(ttResult != 0){
						reMap.put("ZYTT", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.tt")));
					}
				}
//				fieldVal = map.get("field0071");
//				if(fieldVal != null){
//					Long swResult = Long.valueOf(fieldVal.toString());
//					reMap.put("SWY", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.sw")));
//					reMap.put("SWE", swResult);
//				}
				o = map.get("XNZ");
				if(o != null){
					Long xnResult = Long.valueOf(o.toString());
					reMap.put("XNY", Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.xn")));
					reMap.put("XNE", xnResult);
				}
				o = map.get("XNHFRY");
				if(o != null){
					Long xnPersonId = Long.valueOf(o.toString());
					reMap.put("XNPERSONID", xnPersonId);
				}
				o = map.get("AREA");
				if(o != null){
					Long area = Long.valueOf(o.toString());
					reMap.put("AREA", area);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		LOGGER.info("企业回复人员返回值Map:"+reMap.size());
		return reMap;
	}

	@Override
	public Long getRdPersonId4Province(String areaId) throws BusinessException {
		LOGGER.info("enter the method getRdPersonId4Province");
		String personField = AppContext.getSystemProperty("appealAccept.sfrdydb.rdy");
		String areaField = AppContext.getSystemProperty("appealAccept.sfrdydb.sf");
		String tableName = AppContext.getSystemProperty("appealAccept.sfrdydb.formmain");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(personField).append(" from ").append(tableName);
		sb.append(" where ").append(areaField).append(" = '").append(areaId).append("'");
		LOGGER.info("查询省份认定员的sql============"+sb);
		Long personId = null;
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString());
			List<Map<String,Object>> list = jdbc.resultSetToList(false);
			if(Strings.isNotEmpty(list)){
				Map<String,Object> map = list.get(0);
				personId = Long.valueOf(map.get(personField).toString());
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return personId;
	}

	@Override
	public BusinessCodePo getBusinessCode(String disputeNum, String userName)
			throws BusinessException {
		LOGGER.info("========enter the method getBusinessCode=============");
		StringBuffer sb = new StringBuffer();
		String bCodeOne = AppContext.getSystemProperty("appealAccept.formfield.bCodeOne");
		String bCodeTwo = AppContext.getSystemProperty("appealAccept.formfield.bCodeTwo");
		String bCodeThree = AppContext.getSystemProperty("appealAccept.formfield.bCodeThree");
		String bCodeFour = AppContext.getSystemProperty("appealAccept.formfield.bCodeFour");
		String classifyCodeOne = AppContext.getSystemProperty("appealAccept.formfield.classifyCodeOne");
		String classifyCodeTwo = AppContext.getSystemProperty("appealAccept.formfield.classifyCodeTwo");
		String classifyCodeThree = AppContext.getSystemProperty("appealAccept.formfield.classifyCodeThree");
		String tableName = AppContext.getSystemProperty("appealAccept.formfield.formmain");
		String uName = AppContext.getSystemProperty("appealAccept.formfield.userName");
		String disNum = AppContext.getSystemProperty("appealAccept.formfield.disputeNum");
		String date = AppContext.getSystemProperty("appealAccept.formfield.appealDate");
		
		sb.append("select ").append(bCodeOne).append(",").append(bCodeTwo).append(",").append(bCodeThree).append(",")
			.append(bCodeFour).append(",").append(classifyCodeOne).append(",").append(classifyCodeTwo).append(",")
			.append(classifyCodeThree).append(" from ").append(tableName).append(" where ").append(uName).append(" = '")
			.append(userName).append("' and ").append(disNum).append(" = '").append(disputeNum).append("' order by ").append(date)
			.append(" desc");
		LOGGER.info("查询最新一条申诉类型为申诉时的业务码与分类码的sql========== "+sb);
		BusinessCodePo bcp = new BusinessCodePo();
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.execute(sb.toString());
			List<Map<String,Object>> list = jdbc.resultSetToList(true);
			if(Strings.isNotEmpty(list)){
				Map<String, Object> map = list.get(0);
				Object field;
				field = map.get(bCodeOne);
				//业务码一
				if(field != null){
					bcp.setBusinessCodeOne(Long.valueOf(field.toString()));
				}
				field = map.get(bCodeTwo);
				//业务码二
				if(field != null){
					bcp.setBusinessCodeTwo(Long.valueOf(field.toString()));
				}
				field = map.get(bCodeThree);
				//业务码三
				if(field != null){
					bcp.setBusinessCodeThree(Long.valueOf(field.toString()));
				}
				field = map.get(bCodeFour);
				//业务码四
				if(field != null){
					bcp.setBusinessCodeFour(Long.valueOf(field.toString()));
				}
				field = map.get(classifyCodeOne);
				//分类码一
				if(field != null){
					bcp.setClassificationCodeOne(Long.valueOf(field.toString()));
				}
				field = map.get(classifyCodeTwo);
				//分类码二
				if(field != null){
					bcp.setClassificationCodeTwo(Long.valueOf(field.toString()));
				}
				field = map.get(classifyCodeThree);
				//分类码三
				if(field != null){
					bcp.setClassificationCodeThree(Long.valueOf(field.toString()));
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return bcp;
	}

	@Override
	public List<HistoryPo> getHistoryData(String disputeNum, String userName)
			throws BusinessException {
		LOGGER.info("进入查询历史记录方法getHistoryData...");
		StringBuffer sb = new StringBuffer();
		sb.append("select field0065,field0001,field0069,field0066,field0057,field0063,field0097,field0062,field0067 ");
		sb.append("from formmain_0025 where field0001 = '").append(userName).append("' and field0003 = '");
		sb.append(disputeNum).append("' and field0065 is not null");
		JDBCAgent jdbc = new JDBCAgent(false);
		List<HistoryPo> dataList = new ArrayList<HistoryPo>();
		try {
			jdbc.execute(sb.toString());
			List<Map<String,Object>> list = jdbc.resultSetToList(true);
			if(Strings.isNotEmpty(list)){
				for(Map<String,Object> map : list){
					HistoryPo hp = new HistoryPo();
					Object field;
					//申诉信息流水号
					field = map.get("field0065");
					if(field != null){
						hp.setLsh(field.toString());
					}
					//用户姓名
					field = map.get("field0001");
					if(field != null){
						hp.setUserName(field.toString());
					}
					//联系电话
					field = map.get("field0069");
					if(field != null){
						hp.setPhone(field.toString());
					}
					//申诉时间
					field = map.get("field0066");
					if(field != null){
						hp.setAppealDate((Date)field);
					}
					//申诉内容
					field = map.get("field0057");
					if(field != null){
						hp.setAppealContent(field.toString());
					}
					//申诉来源
					field = map.get("field0063");
					if(field != null){
						hp.setAppealSource(Long.valueOf(field.toString()));
					}
					//处理状态
					field = map.get("field0097");
					if(field != null){
						hp.setClzt(Long.valueOf(field.toString()));
					}
					//被申诉企业
					field = map.get("field0062");
					if(field != null){
						hp.setBssqy(Long.valueOf(field.toString()));
					}
					//所属地区
					field = map.get("field0067");
					if(field != null){
						hp.setArea(Long.valueOf(field.toString()));
					}
					dataList.add(hp);
				}
				LOGGER.info("查询到的相应历史记录条数为："+dataList.size());
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return dataList;
	}
}

