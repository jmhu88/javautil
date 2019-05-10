/*
 * Copyright (C), 2002-2016, 365金融研发中心
 * FileName: //文件名
 * Author:   Administrator
 * Date:     2016年3月18日 下午6:25:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP操作工具类
 * 
 * @author 张益武
 */
public class FtpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FtpUtils.class);
    
    /**
     * Windows系统正向斜杠/
     */
    private static final String PATH_SLASH = "/";

    /**
     * 
     * 连接FTPClient
     * 
     * @param ftpIP
     * @param port
     * @param userName
     * @param userPassword
     * @return
     */
    public static FTPClient getConnect(String ftpIP, int port, String userName, String userPassword) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpIP, port);
            ftpClient.login(userName, userPassword);
            // 设置文件类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置FTP 工作模式
            ftpClient.enterLocalPassiveMode();
        } catch (SocketException e) {
            LOG.error("FtpUtils.getConnect SocketException!", e);
        } catch (IOException e) {
            LOG.error("FtpUtils.getConnect IOException!", e);
        }
        return ftpClient;
    }
    
    /**
     * 
     * 关闭FTP连接
     * 
     * @param ftpClient
     */
    public static void disConnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                LOG.error("FtpUtils.disConnect IOException!", e);
            }
        }
    }
    
    /**
     * 
     * 创建Linux目录
     * 
     * @param directory，例如：/test/
     * @param ftpClient
     * @return
     */
    public static boolean createDirecroty(String directory, FTPClient ftpClient) {
        boolean isSuccess = true;
        try {
            if (!directory.equalsIgnoreCase(PATH_SLASH) && !ftpClient.changeWorkingDirectory(directory)) {
                int start = 0;
                int end = 0;
                if (directory.startsWith(PATH_SLASH)) {
                    start = 1;
                } else {
                    start = 0;
                }
                end = directory.indexOf(PATH_SLASH, start);
                while (true) {
                    String subDirectory = directory.substring(start, end);
                    if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                        if (ftpClient.makeDirectory(subDirectory)) {
                            ftpClient.changeWorkingDirectory(subDirectory);
                        } else {
                            isSuccess = false;
                            break;
                        }
                    }
                    start = end + 1;
                    end = directory.indexOf(PATH_SLASH, start);
                    if (end <= start) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("FtpUtils.createDirecroty IOException!", e);
        }
        return isSuccess;
    }
    
    /**
     * 
     * 上传文件到指定FTP目录,如果目录不存在则创建目录
     * 〈true=上传成功 ，false=失败〉
     * 
     * @param ftpClient
     * @param savePath，例如：/upload/
     * @param fileName
     * @param inputStream
     * @return
     */
    public static boolean uploadFileByInputStream(FTPClient ftpClient, String savePath, String fileName, InputStream inputStream) {
        boolean isUploaded = false;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                // 创建多级目录
                FtpUtils.createDirecroty(savePath, ftpClient);
                ftpClient.changeWorkingDirectory(savePath);
                // 保存文件
                ftpClient.storeFile(fileName, inputStream);
                isUploaded = true;
            } catch (SocketException e) {
                LOG.error("FtpUtils.uploadFileByInputStream SocketException!", e);
            } catch (IOException e) {
                LOG.error("FtpUtils.uploadFileByInputStream IOException!", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return isUploaded;
    }
    
    /**
     * 
     * 上传文件到指定FTP目录,如果目录不存在则创建目录 〈true=上传成功 ，false=失败〉
     * 
     * @param fileContent 要保存的文件内容
     * @param ftpClient
     * @param savePath，例如：/test/
     * @param fileName
     * @return
     */
    public static boolean uploadFileApaceByteArray(StringBuilder fileContent, FTPClient ftpClient, String savePath, String fileName) {
        InputStream inputStream = new ByteArrayInputStream(fileContent.toString().getBytes());
        boolean result = FtpUtils.uploadFileByInputStream(ftpClient, savePath, fileName, inputStream);
        return result;
    }
    
    /**
     * 
     * 从指定FTP读取文本文件内容
     * 〈以StringBuffer的形式返回文件内容〉
     * 
     * @param ftpClient
     * @param ftpPath，例如：/test/
     * @param fileName
     * @return
     */
    public static StringBuilder readTxtFile(FTPClient ftpClient, String ftpPath, String fileName) {
        StringBuilder fileContent = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                String[] listNames = ftpClient.listNames(ftpPath + PATH_SLASH + fileName);
                if (listNames.length <= 0) {
                    return null;
                }
                fileContent = new StringBuilder();
                ftpClient.changeWorkingDirectory(ftpPath);
                is = ftpClient.retrieveFileStream(fileName);
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String str = "";
                while ((str = br.readLine()) != null) {
                    fileContent.append(str);
                }
                br.close();
                is.close();
            } catch (IOException e) {
                LOG.error("FtpUtils.readTxtFile IOException!", e);
                return null;
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(isr);
                IOUtils.closeQuietly(br);
            }

        }
        return fileContent;
    }
    
    /**
     * 
     * 检查指定FTP目录下是否有相同文件名的文件存在 〈true=存在，false=未知或不存在〉
     * 
     * @param ftpClient
     * @param savePath，例如：/test/
     * @param fileName
     * @return
     */
    public static boolean isFileExisting(FTPClient ftpClient, String savePath, String fileName) {
        boolean existingFlag = false;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                String[] aFileNames = ftpClient.listNames(savePath + PATH_SLASH + fileName);
                if (aFileNames.length > 0) {
                    existingFlag = true;
                }
            } catch (SocketException e) {
                LOG.error("FtpUtils.isFileExisting SocketException!", e);
            } catch (IOException e) {
                LOG.error("FtpUtils.isFileExisting IOException!", e);
            }
        }
        return existingFlag;
    }
    
    /**
     * 
     * 删除指定FTP上一个文件
     * 〈删除成功返回true，失败返回false〉
     * 
     * @param ftpClient
     * @param filePath，例如：/test/
     * @param fileName
     * @return
     */
    public static boolean deleteFile(FTPClient ftpClient, String filePath, String fileName) {
        boolean flag = false;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                ftpClient.changeWorkingDirectory(filePath);
                flag = ftpClient.deleteFile(fileName);
            } catch (IOException e) {
                flag = false;
                LOG.error("FtpUtils.deleteFile IOException!", e);
            }
        }
        return flag;
    }
    
    /**
     * 
     * 删除FTP服务器上的一个空目录 
     * 〈注意是空目录才能删除〉
     * 
     * @param ftpClient
     * @param path，例如：/test/
     * @return
     */
    public static boolean removeDirectory(FTPClient ftpClient, String path) {
        boolean flag = false;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                flag = ftpClient.removeDirectory(path);
            } catch (IOException e) {
                flag = false;
                LOG.error("FtpUtils.removeDirectory IOException!", e);
            } finally {
                disConnect(ftpClient);
            }
        }
        return flag;
    }
    
    /**
     * 
     * 递归删除FTP服务器上一个目录 
     * 〈目录可以不为空〉
     * 
     * @param ftpClient
     * @param path，例如：/test/
     * @param isAll 是否删除所有内容
     * @return
     * @throws IOException
     */
    public static boolean removeDirectory(FTPClient ftpClient, String path, boolean isAll) throws IOException {

        if (!isAll) {
            return ftpClient.removeDirectory(path);
        }
        // 遍历子目录和文件
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return ftpClient.removeDirectory(path);
        }

        for (int i = 0; i < ftpFileArr.length; i++) {
            FTPFile ftpFile = ftpFileArr[i];
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                removeDirectory(ftpClient, path + PATH_SLASH + name, true);
            } else if (ftpFile.isFile()) {
                ftpClient.deleteFile(path + PATH_SLASH + name);
            } else if (ftpFile.isSymbolicLink()) {
                LOG.error("FtpUtils.removeDirectory Unknown Exception,caused by FTPFile isSymbolicLink");
            } else if (ftpFile.isUnknown()) {
                LOG.error("FtpUtils.removeDirectory Unknown Exception,caused by FTPFile isUnknown");
            }
        }
        return ftpClient.removeDirectory(path);
    }
    
    /**
     * 
     * FTP文件重命名
     * 
     * @param ftpClient
     * @param filePath，例如：/test/
     * @param oldFileName
     * @param newFileName
     * @return
     */
    public static boolean renameFile(FTPClient ftpClient, String filePath, String oldFileName, String newFileName) {
        boolean flag = false;
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(oldFileName)
                || StringUtils.isEmpty(newFileName)) {
            return flag;
        }
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                ftpClient.changeWorkingDirectory(filePath);
                flag = ftpClient.rename(oldFileName, newFileName);
            } catch (IOException e) {
                LOG.error("FtpUtils.renameFile failed.", e);
            } finally {
                disConnect(ftpClient);
            }
        }
        return flag;
    }
    
    /**
     * 获取ftpclient指定的文件流
     * 
     * @param ftpClient
     * @param fileName
     * @param filePath，例如：/test/
     * @return
     */
    public static InputStream getFile(FTPClient ftpClient, String fileName, String filePath) {
        InputStream is = null;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                ftpClient.changeWorkingDirectory(filePath);
                is = ftpClient.retrieveFileStream(fileName);

            } catch (IOException e) {
                LOG.error("FtpUtils.get File inputstream failed.", e);
            }
        }
        return is;
    }

    /**
     * 
     * 根据目录 获取分页引擎
     * 
     * @param ftpClient
     * @param filePath，例如：/test/
     * @return
     */
    public static FTPListParseEngine getEngine(FTPClient ftpClient, String filePath) {
        FTPListParseEngine engine = null;
        if (!ObjectUtils.isEmpty(ftpClient)) {
            try {
                ftpClient.changeWorkingDirectory(filePath);
                engine = ftpClient.initiateListParsing();
            } catch (IOException e) {
                LOG.error("FtpUtils.get FTPListParseEngine failed.", e);
            } finally {
                disConnect(ftpClient);
            }
        }
        return engine;
    }
    
}
