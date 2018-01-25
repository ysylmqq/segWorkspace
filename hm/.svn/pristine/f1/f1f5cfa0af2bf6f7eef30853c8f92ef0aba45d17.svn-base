package com.gboss.util;

import org.csource.common.*;
import org.csource.fastdfs.*;

/**
 * @Package:com.gboss.util
 * @ClassName:FdfsClient
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-19 下午2:44:54
 */
public class FdfsClient {

	private FdfsClient() {

	}

	public static void upload(String files[]) {
		String conf_filename = Config.getConfigPath()+"classes/fdfs_client.conf";
		//String local_filename = files[1];
		try {
			ClientGlobal.init(conf_filename);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);

			for(int i=0;i<files.length;i++){
				NameValuePair[] metaList = new NameValuePair[1];
		        metaList[0] = new NameValuePair("fileName", files[i]);
		        String fileId = client.upload_file1(files[i], null, metaList);
		        System.out.println("upload success. file id is: " + fileId);
			}
			/*
			int i = 0;
			while (i++ < 10) {
				byte[] result = client.download_file1(fileId);
				System.out
						.println(i + ", download result is: " + result.length);
			}*/
			trackerServer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String[] strs = new String[3];
		strs[0] = "H:/pictures/7.jpg";
		upload(strs);
	}

}
