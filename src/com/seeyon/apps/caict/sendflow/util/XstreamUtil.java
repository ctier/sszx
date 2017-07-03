package com.seeyon.apps.caict.sendflow.util;

import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
public class XstreamUtil {
	
	public static final String xmlTop = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	@SuppressWarnings("unchecked")
	public static <T> T xmlToBeanFromFile(String fileName, Class<T> cls)
			throws Exception {
		InputStream ins = null;
		try {
			Resource resource = resourceLoader.getResource(fileName);
			ins = resource.getInputStream();
			XStream xstream = new XStream(new DomDriver("UTF-8"));
			xstream.processAnnotations(cls);
			T obj = (T) xstream.fromXML(ins);
			return obj;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ins != null)
				ins.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlToBeanFromXml(String xml, Class<T> cls)
			throws Exception {
		try {
			XStream xstream = new XStream(new DomDriver("UTF-8"));
			xstream.processAnnotations(cls);
			T obj = (T) xstream.fromXML(xml);
			return obj;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String beanToXml(Object bean, boolean isXmlTop)
	throws Exception {
		try {
			XStream xstream = new XStream(new DomDriver("UTF-8"));
			xstream.processAnnotations(bean.getClass());
			if(isXmlTop){
				return xmlTop + xstream.toXML(bean);
			}else{
				return xstream.toXML(bean);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}