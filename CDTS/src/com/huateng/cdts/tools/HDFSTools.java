package com.huateng.cdts.tools;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileSystem.Statistics;
import org.apache.hadoop.fs.Path;

public class HDFSTools {
	private static FileSystem fs;
	private FSDataOutputStream fsDataOutputStream = null;
	private Path destpath = null;
	private static String PATH_TO_CORE = HDFSTools.class.getClassLoader()
			.getResource("core-site.xml").getPath();
	private static String PATH_TO_HDFS = HDFSTools.class.getClassLoader()
			.getResource("hdfs-site.xml").getPath();
	private static String PATH_TO_YARN = HDFSTools.class.getClassLoader()
			.getResource("yarn-site.xml").getPath();
	public HDFSTools(Path destpath) {
		this.destpath = destpath;
	}
	

	public HDFSTools() {
		super();
		getConfiguration();
	}
	/**
	 * 加载配置文件，连接集群
	 * @return
	 */
	public static Configuration getConfiguration(){
		Configuration conf = new Configuration();
		conf.addResource(new Path(PATH_TO_CORE));
		conf.addResource(new Path(PATH_TO_HDFS));
		conf.addResource(new Path(PATH_TO_YARN));
		conf.set("dfs.support.append", "true");
		try {		
			fs = FileSystem.get(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
		
	}
	
	public boolean PutTxt(StringBuffer tmp, FileSystem fs) {
		boolean flag = false;
		if (tmp == null) {
			return flag;
		}
		try {
			if (fs.exists(destpath)) {
				fsDataOutputStream = new FSDataOutputStream(
						fs.append(destpath), new Statistics(""));
				fsDataOutputStream.writeBytes(tmp.toString());
				flag = true;
			}else{
			fsDataOutputStream = new FSDataOutputStream(fs.create(destpath,
					true, 3), new Statistics(""));
			fsDataOutputStream.writeBytes(tmp.toString());
			flag = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fsDataOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	//为tmp加换行
	public void PutTxt(StringBuffer tmp, FileSystem fs,String receiveMessagePath) {
		destpath=(new Path(receiveMessagePath));
		PutTxt(tmp,fs);
	}
	
	
	public Path getDestpath() {
		return destpath;
	}

	public void setDestpath(Path destpath) {
		this.destpath = destpath;
	}


	public static FileSystem getFs() {
		return fs;
	}


	public static void setFs(FileSystem fs) {
		HDFSTools.fs = fs;
	}


	



	
}
