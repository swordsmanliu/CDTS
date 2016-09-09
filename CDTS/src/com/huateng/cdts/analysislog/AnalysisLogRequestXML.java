package com.huateng.cdts.analysislog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;
import org.jdom2.JDOMException;

import com.huateng.cdts.tools.HDFSTools;
import com.huateng.cdts.tools.ParseXMLUtils;
import com.huateng.cdts.tools.ReadConfigureFile;

public class AnalysisLogRequestXML {
	private final static String XML_REQUEST_LOG = ReadConfigureFile.getInstance().getValue("XML_REQUEST_LOG");
	private final static String XML_REQUEST_LOG_DICT = ReadConfigureFile.getInstance().getValue("XML_REQUEST_LOG_DICT");
	private final static String HEAD = "Head";
	private final static String BODY = "Body";
	private static FileSystem fs;
	private static FSDataInputStream fsDataInputStream = null;
	private static HDFSTools hdfsTools = new HDFSTools();
	private static Text line = new Text();
	private static LineReader reader = null; // 一行一行的读
	private static StringBuffer tmp = new StringBuffer("");
	private static Map<String,Object> requestMap = null;
	private static List<String> listvalue = new ArrayList<String>();
	private static Set<String> setkey  = new LinkedHashSet<String>();
	static{
		HDFSTools.getConfiguration();
		fs = HDFSTools.getFs();
	}
	/**
	 * 解析RequestXML文件
	 * @param srcFilePath
	 */
	public static void analysisXMLRequest(Path srcFilePath){
		try {
			fsDataInputStream = fs.open(srcFilePath);
			reader = new LineReader(fsDataInputStream);
			while(reader.readLine(line) > 0){
				String content = line.toString();
				tmp.append(content);
				if(tmp.indexOf("<?xml")>=0){
					if(tmp.indexOf("<Head")>=0){
						requestMap = ParseXMLUtils.parseXML(tmp.toString(),HEAD);
						for(Map.Entry<String, Object> entry: requestMap.entrySet()){
							listvalue.add(StringUtils.strip(entry.getValue().toString(), "{}"));
							setkey.add(StringUtils.strip(entry.getKey().toString(), "{}"));
						}
						
					}
					if(tmp.indexOf("<Body")>=0){
						requestMap = ParseXMLUtils.parseXML(tmp.toString(),BODY);
						for(Map.Entry<String,Object> entry:requestMap.entrySet()){
							listvalue.add(StringUtils.strip(entry.getValue().toString(), "{}"));
							setkey.add(StringUtils.strip(entry.getKey().toString(), "{}"));
						}
					}
					hdfsTools.PutTxt(new StringBuffer(StringUtils.strip(listvalue.toString(), "[]")+"\n"), fs,XML_REQUEST_LOG);
					listvalue.clear();
					tmp = new StringBuffer("");
				}
			}
			hdfsTools.PutTxt(new StringBuffer(StringUtils.strip(setkey.toString(), "[]")+"\n"), fs,XML_REQUEST_LOG_DICT);
			
		} catch (IOException | JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fsDataInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Path path = new Path("hdfs://192.168.75.129:9000/user/PreAnalysis/xmlrequest.txt");
		HDFSTools.getConfiguration();
		analysisXMLRequest(path);
	}
}
