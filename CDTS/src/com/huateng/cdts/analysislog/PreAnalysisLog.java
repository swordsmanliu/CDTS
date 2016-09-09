package com.huateng.cdts.analysislog;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

import com.huateng.cdts.tools.GetTimer;
import com.huateng.cdts.tools.HDFSTools;
import com.huateng.cdts.tools.ReadConfigureFile;

public class PreAnalysisLog {
	private final static String XML_REQUEST_PATH = ReadConfigureFile.getInstance().getValue("XML_REQUEST_PATH");
	private final static String XML_RESPONSE_PATH = ReadConfigureFile.getInstance().getValue("XML_RESPONSE_PATH");
	private final static String SEND_MSG_PATH = ReadConfigureFile.getInstance().getValue("SEND_MSG_PATH");
	private final static String RECEIVE_MESSAGE_PATH = ReadConfigureFile.getInstance().getValue("RECEIVE_MESSAGE_PATH");
	private static FSDataInputStream fsDataInputStream = null;
	private static FileSystem fs = HDFSTools.getFs();
	private static HDFSTools hdfsTools = new HDFSTools();
	private static Text line = new Text();
	private static LineReader reader = null; // һ��һ�еĶ�
	private static StringBuffer tmp=new StringBuffer("");//�洢�ı�����
	private static GetTimer runner = new GetTimer();//��ȡʱ��
	private static boolean flag1 = false;//�Ƿ�Ϊ����ʱ��ı��
	private static boolean flag2 = false;//�Ƿ�Ϊ��Ϣ<xml����[��ͷ�ı��
	/**
	 * ��HDFS�ж�ȡ��־�ļ������ҽ���־�ļ��ֳ�4���ļ�
	 * @param srcFilePath
	 */
	public static void getStringByLog(Path srcFilePath) {
		try {
			fsDataInputStream = fs.open(srcFilePath);
			reader = new LineReader(fsDataInputStream);
			while (reader.readLine(line) > 0) {
				//��ȡÿһ�в���ȥ��xml�ļ�ע��
				String content = line.toString().replaceAll("(?s)<\\!\\-\\-.+?\\->", "");
				//�ж�tmp��Ϊ��
				if(tmp.length()>0){
					//����ʱ��
					if(flag1 == true){
						//������ʱ����<xml����[��ͷ��
						if(flag2 == true){
							tmp.append(content);
							if(tmp.indexOf("send msg:")>=0){
								if(content.indexOf("</VMX_ROOT>")>=0||content.indexOf("]") >= 0){
									conmmonUpdate(SEND_MSG_PATH);
								}
							}else if(tmp.indexOf("Receive message is:")>=0){
								if(content.indexOf("]") >= 0){
									conmmonUpdate(RECEIVE_MESSAGE_PATH);
								}
							}
						}else{	//if(flag2 = true)
							if(tmp.indexOf("send msg:")>=0){
								if(content.indexOf("<?xml") >= 0||content.indexOf("[")>=0){
									tmp.append(content);
									flag2 = true;
								}
								if(content.indexOf("</VMX_ROOT>")>=0||content.indexOf("]") >= 0){
									conmmonUpdate(SEND_MSG_PATH);
								}
							}else if(tmp.indexOf("Receive message is:")>=0){
								if(content.indexOf("[")>=0){
									tmp.append(content);
									flag2 = true;
								}
								if(content.indexOf("]") >= 0){
									conmmonUpdate(RECEIVE_MESSAGE_PATH);
								}
							}
						}
					}else{
						tmp.append(content);
						if(content.indexOf("</Request>") >= 0){
							conmmonUpdate(XML_REQUEST_PATH);
						}else if(content.indexOf("</Response>") >= 0){
							conmmonUpdate(XML_RESPONSE_PATH);
							}
						}
				}else{
					//tmpΪ����ʱ�䲻Ϊ��
					String s = runner.run(content);//��ȡʱ��
					if(s != null){
						tmp.append(content);
						flag1=true;
						if(content.indexOf("send msg:")>=0){
							if(content.indexOf("<?xml") >= 0||content.indexOf("[")>=0){
								flag2 = true;
							}
							if(content.indexOf("</VMX_ROOT>")>=0||content.indexOf("]") >= 0){
								conmmonUpdate(SEND_MSG_PATH);
							}
						}else if(content.indexOf("Receive message is:")>=0){
							if(content.indexOf("[")>=0){
								flag2 = true;
							}
							if(content.indexOf("]") >= 0){
								conmmonUpdate(RECEIVE_MESSAGE_PATH);
							}
						}
					}else{
						//����ʱ������xml��ͷ
						if(content.indexOf("<?xml") >= 0){
							tmp.append(content);
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				fsDataInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private static void conmmonUpdate(String s) {
		hdfsTools.PutTxt(tmp.append("\n"), fs,s);
		tmp =new StringBuffer("");
		flag1 = false;
		flag2 = false;
	}


	public static void main(String[] args) throws Exception {
		Path path = new Path("hdfs://192.168.75.129:9000/user/data/CDTS.log");
		HDFSTools.getConfiguration();
		getStringByLog(path);
	}
}
