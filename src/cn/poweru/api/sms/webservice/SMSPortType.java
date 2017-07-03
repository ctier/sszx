
package cn.poweru.api.sms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SMSPortType", targetNamespace = "http://webservice.sms.api.poweru.cn")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SMSPortType {


    /**
     * 
     * @param in1
     * @param in0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetRecvSMSList")
    @WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
    @RequestWrapper(localName = "GetRecvSMSList", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetRecvSMSList")
    @ResponseWrapper(localName = "GetRecvSMSListResponse", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetRecvSMSListResponse")
    public String getRecvSMSList(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in1);

    /**
     * 
     * @param in1
     * @param in0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "AddSMSList")
    @WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
    @RequestWrapper(localName = "AddSMSList", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.AddSMSList")
    @ResponseWrapper(localName = "AddSMSListResponse", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.AddSMSListResponse")
    public String addSMSList(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in1);

    /**
     * 
     * @param in1
     * @param in0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetReportSMSList")
    @WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
    @RequestWrapper(localName = "GetReportSMSList", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetReportSMSList")
    @ResponseWrapper(localName = "GetReportSMSListResponse", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetReportSMSListResponse")
    public String getReportSMSList(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in1);

    /**
     * 
     * @param in1
     * @param in0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetSMSUEID")
    @WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
    @RequestWrapper(localName = "GetSMSUEID", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetSMSUEID")
    @ResponseWrapper(localName = "GetSMSUEIDResponse", targetNamespace = "http://webservice.sms.api.poweru.cn", className = "cn.poweru.api.sms.webservice.GetSMSUEIDResponse")
    public String getSMSUEID(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in0,
        @WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn")
        String in1);

}
