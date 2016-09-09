package com.huateng.cdts.analysislog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

import com.huateng.cdts.tools.GetTimer;
import com.huateng.cdts.tools.HDFSTools;
import com.huateng.cdts.tools.ReadConfigureFile;

public class AnalysisLogSendMsg {
	private final static String SEND_MSG_LOG = ReadConfigureFile.getInstance().getValue("SEND_MSG_LOG");
	private static FileSystem fs = HDFSTools.getFs();
	private static FSDataInputStream fsDataInputStream = null;
	private static HDFSTools hdfsTools = new HDFSTools();
	private static Text line = new Text();
	private static LineReader reader = null;
	private static StringBuffer tmp = new StringBuffer("");
	private static GetTimer time = new GetTimer();
	private static String last = null;
	private static Map<String, Object> sendmsgMap = new HashMap<String, Object>();
	/**
	 * ½âÎöSend Msg ÎÄ¼þ
	 * @param srcFilePath
	 */
	public static void analysisSendMsg(Path srcFilePath){
		try {
			fsDataInputStream = fs.open(srcFilePath);
			reader = new LineReader(fsDataInputStream);
			while(reader.readLine(line) > 0){
				String content = line.toString();
				if(content.indexOf("send msg:")>=0){
				last = content.substring(content.indexOf("send msg:")+9);
				String t = time.run(content);
				sendmsgMap.put(t + "@" + "send msg", last);
				tmp.append(StringUtils.strip(sendmsgMap.toString(), "{}")+"\n");
				hdfsTools.PutTxt(tmp, fs,SEND_MSG_LOG);
				sendmsgMap.clear();
				tmp = new StringBuffer("");
				
				}
				
				
				
			//	System.out.println(content);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		Path path = new Path("hdfs://192.168.75.129:9000/user/PreAnalysis/sendmsg.txt");
		analysisSendMsg(path);
	}
}
