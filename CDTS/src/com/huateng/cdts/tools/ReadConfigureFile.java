package com.huateng.cdts.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ReadConfigureFile {
	static Logger log = Logger.getLogger(ReadConfigureFile.class);
	private Properties prop;
	private static ReadConfigureFile instance;
	ReadConfigureFile(){
		init();
	}
	public void init(){
		prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/config.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static ReadConfigureFile getInstance(){
		if(instance == null){
			return new ReadConfigureFile();
		}
		return instance;
	}
	public String createFileDir(String key){
		String path = prop.getProperty(key);
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path; 
	}
	public String getValue(String key){
		return prop.getProperty(key);
	}
	public Properties getValues(){
		return prop;
	}
}
