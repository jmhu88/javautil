/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: FastDFS_demo
 * PackName: com.demo
 * FileName: FastDFSUtil.java
 * Author: 邱刘军
 * Date: 2014年10月4日 下午2:38:46
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFSUtil工具类
 * 
 * @author 邱刘军
 * @date 2014年10月4日
 * @version 1.0
 */
public class FastDFSUtil {
    
    private static Logger logger = LoggerFactory.getLogger(FastDFSUtil.class);
    
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;
    
    static {
        try {
            String classPath = new File(FastDFSUtil.class.getResource("/").getFile()).getCanonicalPath();
            String configFilePath = classPath +"/fdfs_client.conf";
            logger.debug("配置文件:" + configFilePath);
            ClientGlobal.init(configFilePath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 上传文件
     * 
     * @param localFilename
     * @return
     */
    public static String uploadFile(String localFilename){
        File file = new File(localFilename);
        if(!file.exists() || !file.isFile()) {
            logger.debug("当前文件不存在或不是文件，上传失败");
            return null;
        }
        String remoteFilename = null;
        try {
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("width", "120");
            metaList[1] = new NameValuePair("heigth", "120");
            metaList[2] = new NameValuePair("author", "admin");
            String fileExtname = getFileSuffix(localFilename);
            String[] results = storageClient.upload_file(localFilename, fileExtname, metaList);
            remoteFilename = results[0] + File.separator + results[1];
            logger.debug("groupName："+ results[0]);
            logger.debug("remoteFilename："+ results[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return remoteFilename;
    }
    
    /**
     *  以流的方式上传文件
     * @param inStream
     * @param uploadFileName
     * @return
     * @throws IOException
     * @author 张益武
     */
    public static String uploadFileByStream(InputStream inStream, String uploadFileName) throws IOException {
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = getFileSuffix(uploadFileName);
        } else {
            logger.warn("Fail to upload file, because the format of filename is illegal.");
            return null;
        }
        byte[] fileBuffer = new byte[inStream.available()];
        inStream.read(fileBuffer);
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(inStream.available()));
        String remoteFilename = null;
        try {
            // results[0]: groupName, results[1]: remoteFilename.
            String[] results = storageClient
                    .upload_file(fileBuffer, fileExtName, metaList);
            remoteFilename = results[0] + "/" + results[1];
        } catch (MyException e) {
            logger.warn("Upload file " + uploadFileName + " fails");
        }

        return remoteFilename;
    }
    
    /**
     * 文件上传
     * 
     * @param localFile
     * @return
     */
    public static String uploadFile(File localFile){
        if(!localFile.exists() || !localFile.isFile()) {
            logger.debug("当前文件不存在或不是文件，上传失败");
            return null;
        }
        String remoteFilename = null;
        InputStream is = null;
        try {
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("width", "120");
            metaList[1] = new NameValuePair("heigth", "120");
            metaList[2] = new NameValuePair("author", "admin");
            //--------读取本地文件成byte[]数组，用于文件上传--------
            is = new FileInputStream(localFile);
            byte[] fileBuffer = new byte[is.available()];
            is.read(fileBuffer);
            //-----------------------------------------
            String fileName = localFile.getName();
            String fileExtname = getFileSuffix(fileName);
            String[] results = storageClient.upload_file(fileBuffer, fileExtname, metaList);
            remoteFilename = results[0] +"/"+ results[1];
            logger.debug("groupName："+ results[0]);
            logger.debug("remoteFilename："+ results[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return remoteFilename;
    }
    
    /**
     * 下载文件
     * 
     * @param groupName
     * @param remoteFilename
     * @param localFilename
     * @return
     */
    public static byte[] downloadFile(String groupName, String remoteFilename){
        byte[] fileBuffer = null;
        try {
            fileBuffer = storageClient.download_file(groupName, remoteFilename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileBuffer;
    }
    
    /**
     * 下载文件
     * 
     * @param groupName
     * @param remoteFilename
     * @param localFilename
     * @return
     */
    public static File downloadFile(String groupName, String remoteFilename, String localFilename){
        File file = new File(localFilename);
        try {
            byte[] fileBuffer = storageClient.download_file(groupName, remoteFilename);
            FileUtils.writeByteArrayToFile(file, fileBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return file.exists()?file:null;
    }
    
    /**
     * 获得文件
     * 
     * @param groupName
     * @param remoteFilename
     * @return
     */
    public static FileInfo getRemoteFile(String groupName, String remoteFilename){
        FileInfo fileInfo = null;
        try {
            fileInfo = storageClient.get_file_info(groupName, remoteFilename);
            //logger.debug("文件IP：" + fileInfo.getSourceIpAddr());
            //logger.debug("文件大小：" + fileInfo.getFileSize());
            //logger.debug("创建时间：" + fileInfo.getCreateTimestamp());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }
    
    /**
     * 获得文件的元数据
     * 
     * @param groupName
     * @param remoteFilename
     * @return
     */
    public static NameValuePair[] getRemoteMetadata(String groupName, String remoteFilename){
        NameValuePair[] metaList = null;
        try {
            metaList = storageClient.get_metadata(groupName, remoteFilename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return metaList;
    }
    
    /**
     * 删除文件
     * 
     * @param groupName
     * @param remoteFilename
     */
    public static void deleteRemoteFile(String groupName, String remoteFilename){
        try {
            storageClient.delete_file(groupName, remoteFilename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 〈获取文件后缀名，不含.〉
     * 〈功能详细描述〉
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        String suffix = null;
        if (!StringUtils.isBlank(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return suffix;
    } 
    
    /**
     * 〈校验上传的图片格式是否符合要求〉
     * 〈功能详细描述〉
     * @param fileName
     * @return
     */
    public static boolean checkFile(String fileName, MultipartFile myfile){
        boolean extFlag = false;
        String imageExt = CustomPropertyConfigurer.getMessage("system.default.imageExt");
        long maxSize = Long.valueOf(CustomPropertyConfigurer.getMessage("system.default.imageMax"));
        String[] extName = null;
        if(imageExt.contains(",")){
            extName = imageExt.split(",");
        } else {
            extName = new String[]{imageExt};
        }
        // 校验上传的图片格式是否符合要求
        for(String fileExt : extName){
            String fileSuffix = getFileSuffix(fileName);
            if(fileSuffix.toLowerCase().equals(fileExt.toLowerCase())){
                extFlag = true;
                break;
            }
        }
        // 校验上传的图片大小是否符合要求
        if(myfile.getSize() > maxSize){
            extFlag = false;
        }
        return extFlag;
    }


    /**
     * 校验图片上传失败原因
     *
     * @param fileName
     * @param myfile
     * @return
     */
    public static String checkFileFailMsg(String fileName, MultipartFile myfile) {
        String extFlag = "";
        String imageExt = CustomPropertyConfigurer.getMessage("system.default.imageExt");
        long maxSize = Long.valueOf(CustomPropertyConfigurer.getMessage("system.default.imageMax"));
        String[] extName = null;
        if (imageExt.contains(",")) {
            extName = imageExt.split(",");
        } else {
            extName = new String[]{imageExt};
        }
        // 校验上传的图片格式是否符合要求
        for (String fileExt : extName) {
            String fileSuffix = getFileSuffix(fileName);
            if (!fileSuffix.toLowerCase().equals(fileExt.toLowerCase())) {
                extFlag = "上传的图片格式不符合要求";
                break;
            }
        }
        // 校验上传的图片大小是否符合要求
        if (myfile.getSize() > maxSize) {
            extFlag = "上传的图片大小不符合要求";
        }
        return extFlag;
    }
}
