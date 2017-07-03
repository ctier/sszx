package com.seeyon.apps.caict.wx.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.jdbc.CallCenterOracleFactory;
import com.seeyon.apps.caict.wx.po.EfaxPo;
import com.seeyon.apps.caict.wx.po.TelAcceptPo;
import com.seeyon.apps.caict.wx.utils.EnumConverUtil;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.JDBCAgent;
import com.seeyon.v3x.dee.common.base.util.UuidUtil;
import com.seeyon.v3x.util.Strings;

public class CallCenterDaoImpl implements CallCenterDao {

	private static Logger LOGGER = Logger.getLogger(CallCenterDaoImpl.class);
	
	@Override
	public List<EfaxPo> getEfax() throws BusinessException {
		LOGGER.info("enter the method for getEfax.....");
		Date beforeStartTime = EnumConverUtil.getBeforeStartTime();
		Date beforeEndTime = EnumConverUtil.getBeforeEndTime();
		Connection conn = CallCenterOracleFactory.getConnection();
		LOGGER.info("数据库连接conn========="+conn);
		StringBuffer sb = new StringBuffer();
		sb.append("select tif.id as ID,tif.callerno as CALLERNO,tif.calleeno as CALLEENO,");
		sb.append("tif.time as TIME,tif.faxfile as FAXFILE,tif.path as PATH from t_ivr_fax tif ");
		sb.append("where tif.time between ").append(beforeStartTime).append(" and ").append(beforeEndTime);
		LOGGER.info("查询前一天所有的电子传真信息的sql===="+sb);
		
		ResultSet rs = CallCenterOracleFactory.executeQuery(conn, sb.toString());
		List<EfaxPo> dataList = new ArrayList<EfaxPo>();
		try {
			while(rs.next()){
				EfaxPo ep = new EfaxPo();
				String id = rs.getString("ID");
				LOGGER.info("电子传真ID==="+id);
				ep.setId(id);
				String callerno = rs.getString("CALLERNO");
				LOGGER.info("电子传真callerno==="+callerno);
				ep.setCallerno(callerno);
				String calleeno = rs.getString("CALLEENO");
				LOGGER.info("电子传真calleeno==="+calleeno);
				ep.setCalleeno(calleeno);
				Date time = rs.getDate("TIME");
				LOGGER.info("电子传真TIME==="+time);
				ep.setTime(time);
				String faxFile = rs.getString("FAXFILE");
				LOGGER.info("电子传真FAXFILE==="+faxFile);
				ep.setFaxFile(faxFile);
				String path = rs.getString("PATH");
				LOGGER.info("电子传真path==="+path);
				//TODO
				path.indexOf("r:'\'");
				ep.setPath(path);
				dataList.add(ep);
			}
			LOGGER.info("电子传真数量:"+dataList.size());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			CallCenterOracleFactory.closeConnection(conn);
		}
		return dataList;
	}
	@Override
	public void saveTelData(TelAcceptPo tap) throws BusinessException {
		LOGGER.info("进入DAO保存电话受理数据方法saveTelData(TelAcceptPo tap)");
		StringBuffer sb = new StringBuffer();
		sb.append("insert into formmain_0361(id,field0001,field0002,field0003,field0004,field0005,")
		  .append("field0006,field0007,field0008,field0009,field0010,field0011,field0012,")
		  .append("field0014,field0015,field0016,field0017,field0018,field0019,field0020,field0021,")
		  .append("field0022,field0023,field0024,field0025,field0026,field0027,field0028,field0029,")
		  .append("field0030,field0031,field0032,field0033)values(")
		  .append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?").append(")");
		List<Object> params = new ArrayList<Object>();
		params.add(UuidUtil.uuid());
		params.add(tap.getUserName());
		params.add(tap.getWorkAdd());
		params.add(tap.getDisputeNum());
		params.add(tap.getPostalCode());
		params.add(tap.getUserAdd());
		params.add(tap.getClassifyFirst());
		params.add(tap.getClassifySecond());
		params.add(tap.getClassifyThird());
		params.add(tap.getBusinessFirst());
		params.add(tap.getBusinessSecond());
		params.add(tap.getBusinessThird());
		params.add(tap.getBusinessFourth());
		params.add(tap.getAppealSource());
		params.add(tap.getPhone());
		params.add(tap.getAppealDate());
		params.add(tap.getFirstCompany());
		params.add(tap.getSecondCompany());
		params.add(tap.getProvince());
		params.add(tap.getCity());
		params.add(tap.getAppealContent());
		params.add(tap.getAppealMemberId());
		params.add(tap.getAppealMode());
		params.add(tap.getProcessMode());
		params.add(tap.getSjqy().contains("中国移动")?1:0);
		params.add(tap.getRemark());
		params.add(tap.getSjqy().contains("中国电信")?1:0);
		params.add(tap.getSjqy().contains("中国联通")?1:0);
		params.add(tap.getSjqy().contains("中国铁通")?1:0);
		params.add(tap.getSjqy().contains("虚拟运营商")?1:0);
		params.add(tap.getSjqy().contains("互联网企业")?1:0);
		params.add(tap.getSjqy().contains("三网融合试点企业")?1:0);
		params.add(tap.getSjqy().contains("其他")?1:0);
		LOGGER.info("保存电话受理数据的params===="+Strings.join(params, ","));
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			int row = jdbc.execute(sb.toString(), params);
			LOGGER.info("===============成功保存记录条数============="+row);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
	}
	@Override
	public FlipInfo getVoiceData(FlipInfo flipInfo, Map<String, String> query)
			throws BusinessException {
		LOGGER.info("进入getVoiceData方法，获取语音留言信息");
		String sql = "select v.id as LYBH,v.callerno as LYHM,v.time as JSSJ,v.recordfile as STLY,v.path as LYDZ from t_ivr_record_oa v where v.state = '0'";
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			LOGGER.info("****查询语音留言数据表sql=" + sql);
//			flipInfo.setSize(5);
			LOGGER.info("当前页========"+flipInfo.getPage()+"每页展示数量========="+flipInfo.getSize());
			jdbc.findByPaging(sql, flipInfo);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		} finally {
			jdbc.close();
		}
		return flipInfo;
	}
	@Override
	public List<EfaxPo> getVoiceData() throws BusinessException {
		LOGGER.info("enter the method for getVoiceData.....");
		Date startTime = EnumConverUtil.getStartTime();
		Date endTime = EnumConverUtil.getEndTime();
		Connection conn = CallCenterOracleFactory.getConnection();
		LOGGER.info("数据库连接conn========="+conn);
		StringBuffer sb = new StringBuffer();
		sb.append("select tif.id as ID,tif.callerno as CALLERNO,tif.calleeno as CALLEENO,");
		sb.append("tif.time as TIME,tif.recordfile as RECORDFILE,tif.path as PATH from t_ivr_record tif ");
		sb.append("where tif.time between ").append(startTime).append(" and ").append(endTime);
		LOGGER.info("查询语音留言信息的sql===="+sb);
		
		ResultSet rs = CallCenterOracleFactory.executeQuery(conn, sb.toString());
		List<EfaxPo> dataList = new ArrayList<EfaxPo>();
		try {
			while(rs.next()){
				EfaxPo ep = new EfaxPo();
				String id = rs.getString("ID");
				LOGGER.info("语音留言ID==="+id);
				ep.setId(id);
				String callerno = rs.getString("CALLERNO");
				LOGGER.info("语音留言callerno==="+callerno);
				ep.setCallerno(callerno);
				String calleeno = rs.getString("CALLEENO");
				LOGGER.info("语音留言calleeno==="+calleeno);
				ep.setCalleeno(calleeno);
				Date time = rs.getDate("TIME");
				LOGGER.info("语音留言TIME==="+time);
				ep.setTime(time);
				String faxFile = rs.getString("RECORDFILE");
				LOGGER.info("语音留言FAXFILE==="+faxFile);
				ep.setFaxFile(faxFile);
				String path = rs.getString("PATH");
				LOGGER.info("语音留言path==="+path);
				//TODO
				path.indexOf("r:'\'");
				ep.setPath(path);
				dataList.add(ep);
			}
			LOGGER.info("语音留言数量:"+dataList.size());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			CallCenterOracleFactory.closeConnection(conn);
		}
		return dataList;
	}
	@Override
	public List<String> getVoiceIdData() throws BusinessException {
		LOGGER.info("进入获取OA表中语音留言数据ID方法getVoiceIdData----");
		Date startTime = EnumConverUtil.getStartTime();
		Date endTime = EnumConverUtil.getEndTime();
		String sql = "select tir.id from t_ivr_record_oa tir where tir.time between "+startTime+" and "+endTime;
		LOGGER.info("查询OA数据库语音留言数据的sql==========="+sql);
		JDBCAgent jdbc = new JDBCAgent(false);
		List<String> list = new ArrayList<String>();
		try {
			jdbc.execute(sql);
			List<Map<String,Object>> dataList = jdbc.resultSetToList(false);
			if(dataList != null && dataList.size()>0){
				for(Map<String,Object> map :dataList){
					String id = map.get("ID").toString();
					list.add(id);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return list;
	}
	@Override
	public void saveVoiceData(List<EfaxPo> dataList) throws BusinessException {
		LOGGER.info("进入保存语音留言数据方法saveVoiceData===========");
		String sql = "insert into t_ivr_record_oa values(?,?,?,?,?,?,?)";
		JDBCAgent jdbc = new JDBCAgent(false);
		try {
			jdbc.batch1Prepare(sql);
			for(EfaxPo ep : dataList){
				List<Object> params = new ArrayList<Object>();
				params.add(ep.getId());
				params.add(ep.getCallerno());
				params.add(ep.getCalleeno());
				params.add(ep.getTime());
				params.add(ep.getFaxFile());
				params.add(ep.getPath());
				params.add(0);
				jdbc.batch2Add(params);
			}
			int size = jdbc.batch3Execute();
			LOGGER.info("成功保存到OA数据表条数："+size);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		
	}
	@Override
	public int updateVoiceState(String voiceId) throws BusinessException {
		LOGGER.info("更新语音留言数据状态的方法updateVoiceState=========");
		String sql = "update t_ivr_record_oa set state = '1' where id = '"+voiceId+"'";
		JDBCAgent jdbc = new JDBCAgent(false);
		int row = 0;
		try {
			row = jdbc.execute(sql);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			jdbc.close();
		}
		return row;
	}
}
