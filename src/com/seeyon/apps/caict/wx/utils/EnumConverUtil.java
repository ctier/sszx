package com.seeyon.apps.caict.wx.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.v3x.util.Strings;

public class EnumConverUtil {
	
	private static final Logger LOGGER = Logger.getLogger(EnumConverUtil.class);
	private static EnumManager enumManagerNew = (EnumManager) AppContext.getBean("enumManagerNew");
	
	/**
	 * 所属地区枚举转换
	 * @param value
	 * @return
	 * @throws BusinessException
	 */
	public static Long locEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String enumId = AppContext.getSystemProperty("appealAccept.formfield.province");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(enumId));
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 申诉来源枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long sslyEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String sslyId = AppContext.getSystemProperty("appealAccept.formfield.ssly");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(sslyId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终申诉来源ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 被投诉企业枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long qyEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String qyId = AppContext.getSystemProperty("appealAccept.formfield.btsqy");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(qyId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终被投诉企业ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 申诉类型枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long appealTypeEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String lxId = AppContext.getSystemProperty("appealAccept.formfield.appealTypeId");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(lxId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终申诉类型ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 申诉方式枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long appealModeEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String lxId = AppContext.getSystemProperty("appealAccept.formfield.appealModeId");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(lxId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终申诉类型ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 处理方式枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long processModeEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String lxId = AppContext.getSystemProperty("appealAccept.formfield.processModeId");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(lxId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终申诉类型ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 业务码枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long ywmEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String lxId = AppContext.getSystemProperty("appealAccept.formfield.businessId");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(lxId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终业务码ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 分类码枚举转换
	 * @param label
	 * @return
	 * @throws BusinessException
	 */
	public static Long flEnumConver(String label) throws BusinessException{
		Long enId = null ;
		if(Strings.isNotBlank(label)){
			String lxId = AppContext.getSystemProperty("appealAccept.formfield.classifyId");
			List<CtpEnumItem> xmlxEnumList = enumManagerNew.getEmumItemByEmumId(Long.valueOf(lxId));
			
			for(CtpEnumItem en:xmlxEnumList){
				String itemVal = en.getShowvalue();
				if(itemVal.equals(label)){
					enId = en.getId();
					LOGGER.info("获取到的最终分类码业务ID为："+enId);
				}
			}
		}
		return enId;
	}
	/**
	 * 获取各小组认定员id
	 * @param value
	 * @return
	 * @throws BusinessException
	 */
//	public static String getGroupId(Long id,String type) throws BusinessException{
//		String groupId = null ;
//		if(id != null && !"".equals(id)){
//			CtpEnumItem ctpEnumItem = enumManagerNew.getCacheEnumItem(id);
//			String showvalue = ctpEnumItem.getShowvalue();
//			if("中国移动".equals(showvalue) || "中移铁通".equals(showvalue)){
//				if(Constants.RDGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ydrd");
//				}else if(Constants.SHGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ydsh");
//				}else if(Constants.BLGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ydbl");
//				}else if(Constants.TJGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ydtj");
//				}
//			}else if("中国联通".equals(showvalue)){
//				if(Constants.RDGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ltrd");
//				}else if(Constants.SHGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ltsh");
//				}else if(Constants.BLGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.ltbl");
//				}else if(Constants.TJGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.lttj");
//				}
//			}else if("中国电信".equals(showvalue)){
//				if(Constants.RDGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.dxrd");
//				}else if(Constants.SHGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.dxsh");
//				}else if(Constants.BLGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.dxbl");
//				}else if(Constants.TJGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.dxtj");
//				}
//			}else if("虚拟运营商".equals(showvalue)){
//				if(Constants.RDGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.xnrd");
//				}else if(Constants.SHGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.xnsh");
//				}else if(Constants.BLGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.xnbl");
//				}else if(Constants.TJGROUP.equals(type)){
//					groupId = AppContext.getSystemProperty("appealAccept.groupId.xntj");
//				}
//			}
//		}
//		return groupId;
//	}
	/**
	 * 获取当天的开始时间
	 * @return
	 */
	public static Date getStartTime() {  
	        Calendar todayStart = Calendar.getInstance();  
	        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
	        todayStart.set(Calendar.MINUTE, 0);  
	        todayStart.set(Calendar.SECOND, 0);  
	        todayStart.set(Calendar.MILLISECOND, 0);  
	        return todayStart.getTime();  
	 }  
	/**
	 * 获取当天的结束时间  
	 * @return
	 */
	public static Date getEndTime() {  
	        Calendar todayEnd = Calendar.getInstance();  
	        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
	        todayEnd.set(Calendar.MINUTE, 59);  
	        todayEnd.set(Calendar.SECOND, 59);  
	        todayEnd.set(Calendar.MILLISECOND, 999);  
	        return todayEnd.getTime();  
	}
	/**
	 * 获取前一天的开始时间
	 * @return
	 */
	public static Date getBeforeStartTime() {  
	        Calendar todayStart = Calendar.getInstance();  
	        todayStart.add(Calendar.DATE, -1);
	        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
	        todayStart.set(Calendar.MINUTE, 0);  
	        todayStart.set(Calendar.SECOND, 0);  
	        todayStart.set(Calendar.MILLISECOND, 0);  
	        return todayStart.getTime();  
	 }
	/**
	 * 获取前一天的结束时间
	 * @return
	 */
	public static Date getBeforeEndTime() {  
	        Calendar todayEnd = Calendar.getInstance();  
	        todayEnd.add(Calendar.DATE, -1);
	        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
	        todayEnd.set(Calendar.MINUTE, 59);  
	        todayEnd.set(Calendar.SECOND, 59);  
	        todayEnd.set(Calendar.MILLISECOND, 999);  
	        return todayEnd.getTime();  
	 }
	public static void main(String[] args) {
		String path = "中国移动,中国联通,其他";
		System.out.println(path.contains("中国移动"));
	}
}