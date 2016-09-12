package com.huateng.cdts.analysislog;



import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.huateng.cdts.centreanalysislog.AnalysisLogRecMsg;
import com.huateng.cdts.centreanalysislog.AnalysisLogRequestXML;
import com.huateng.cdts.centreanalysislog.AnalysisLogResponseXML;
import com.huateng.cdts.centreanalysislog.AnalysisLogSendMsg;
import com.huateng.cdts.preanalysislog.PreAnalysisLog;
import com.huateng.cdts.tools.HDFSTools;
import com.huateng.cdts.tools.ReadConfigureFile;

public class AnalysisLog {
	private final static String XML_REQUEST_PATH = ReadConfigureFile.getInstance().getValue("XML_REQUEST_PATH");
	private final static String XML_RESPONSE_PATH = ReadConfigureFile.getInstance().getValue("XML_RESPONSE_PATH");
	private final static String SEND_MSG_PATH = ReadConfigureFile.getInstance().getValue("SEND_MSG_PATH");
	private final static String RECEIVE_MESSAGE_PATH = ReadConfigureFile.getInstance().getValue("RECEIVE_MESSAGE_PATH");
	private final static String DATA_PATH = ReadConfigureFile.getInstance().getValue("DATA_PATH");
	private final static String XML_REQUEST_LOG = ReadConfigureFile.getInstance().getValue("XML_REQUEST_LOG");
	private final static String XML_RESPONSE_LOG = ReadConfigureFile.getInstance().getValue("XML_RESPONSE_LOG");
	private final static String SEND_MSG_LOG = ReadConfigureFile.getInstance().getValue("SEND_MSG_LOG");
	private final static String RECEIVE_MESSAGE_LOG = ReadConfigureFile.getInstance().getValue("RECEIVE_MESSAGE_LOG");
	private final static String PRE_DATA_PATH = ReadConfigureFile.getInstance().getValue("PRE_DATA_PATH");
	private final static String XML_REQUEST_LOG_CTC = ReadConfigureFile.getInstance().getValue("XML_REQUEST_LOG_CTC");
	private final static String XML_RESPONSE_LOG_CTC = ReadConfigureFile.getInstance().getValue("XML_RESPONSE_LOG_CTC");
	private final static String RECEIVE_MESSAGE = ReadConfigureFile.getInstance().getValue("RECEIVE_MESSAGE");
	private final static String SEND_MSG = ReadConfigureFile.getInstance().getValue("SEND_MSG");
	private static FileSystem fs;
	static {
		HDFSTools.getConfiguration();
		fs =HDFSTools.getFs();
	}
	public static void main(String[] args) {
		try {
			//欲处理
			if(fs.exists(new Path(PRE_DATA_PATH))){
				fs.delete(new Path(PRE_DATA_PATH), true);
				PreAnalysisLog.getStringByLog(new Path(DATA_PATH));
			}else{
				PreAnalysisLog.getStringByLog(new Path(DATA_PATH));
			}
			//key-value的形式
			if(fs.exists(new Path(XML_REQUEST_LOG))){
				fs.delete(new Path(XML_REQUEST_LOG), true);
				AnalysisLogRequestXML.analysisXMLRequest(new Path(XML_REQUEST_PATH));//
			}else{
				AnalysisLogRequestXML.analysisXMLRequest(new Path(XML_REQUEST_PATH));//
			}
			
			
			
			if(fs.exists(new Path(XML_RESPONSE_LOG))){
				fs.delete(new Path(XML_RESPONSE_LOG), true);
				AnalysisLogResponseXML.analysisXMLResponse(new Path(XML_RESPONSE_PATH));
			}else{
				AnalysisLogResponseXML.analysisXMLResponse(new Path(XML_RESPONSE_PATH));
			}
			
			
			if(fs.exists(new Path(SEND_MSG_LOG))){
				fs.delete(new Path(SEND_MSG_LOG), true);
				AnalysisLogSendMsg.analysisSendMsg(new Path(SEND_MSG_PATH));
			}else{
				AnalysisLogSendMsg.analysisSendMsg(new Path(SEND_MSG_PATH));
			}
			
			
			
			if(fs.exists(new Path(RECEIVE_MESSAGE_LOG))){
				fs.delete(new Path(RECEIVE_MESSAGE_LOG), true);
				AnalysisLogRecMsg.analysisRecMsg(new Path(RECEIVE_MESSAGE_PATH));
			}else{
				AnalysisLogRecMsg.analysisRecMsg(new Path(RECEIVE_MESSAGE_PATH));
			}
			
			
			//生成最终结果
			if(fs.exists(new Path(XML_REQUEST_LOG_CTC))){
				fs.delete(new Path(XML_REQUEST_LOG_CTC), true);
				AnalysisLogRequestXML.RequestKeyAndValue(new Path(XML_REQUEST_PATH));//
			}else{
				AnalysisLogRequestXML.RequestKeyAndValue(new Path(XML_REQUEST_PATH));//
			}
			
			
			
			if(fs.exists(new Path(XML_RESPONSE_LOG_CTC))){
				fs.delete(new Path(XML_RESPONSE_LOG_CTC), true);
				AnalysisLogResponseXML.ResponseKeyAndValue(new Path(XML_RESPONSE_PATH));
			}else{
				AnalysisLogResponseXML.ResponseKeyAndValue(new Path(XML_RESPONSE_PATH));
			}
			
			
			if(fs.exists(new Path(SEND_MSG))){
				fs.delete(new Path(SEND_MSG), true);
				AnalysisLogSendMsg.SendMsgValue(new Path(SEND_MSG_PATH));
			}else{
				AnalysisLogSendMsg.SendMsgValue(new Path(SEND_MSG_PATH));
			}
			
			
			
			if(fs.exists(new Path(RECEIVE_MESSAGE))){
				fs.delete(new Path(RECEIVE_MESSAGE), true);
				AnalysisLogRecMsg.RecMsgvalue(new Path(RECEIVE_MESSAGE_PATH));
			}else{
				AnalysisLogRecMsg.RecMsgvalue(new Path(RECEIVE_MESSAGE_PATH));
			}
			
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
