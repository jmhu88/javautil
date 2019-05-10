/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-backend
 * PackName: com.house365.finance.controller
 * FileName: FileBean.java
 * Author: 邱刘军
 * Date: 2014年10月1日 上午10:29:18
 */

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author 邱刘军
 * @date 2014年10月1日
 * @version 1.0
 */
public class FileBean implements Serializable {

    private static final long serialVersionUID = 3533427850551510820L;
    
    private String originalFilename;
    private String filename;
    private long fileSize;
    private String fileContentType;
    private String serverFilename;
    private String fullFilename;
    
    /**
     * @return the originalFilename
     */
    public String getOriginalFilename() {
        return originalFilename;
    }
    /**
     * @param originalFilename the originalFilename to set
     */
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }
    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }
    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    /**
     * @return the fileContentType
     */
    public String getFileContentType() {
        return fileContentType;
    }
    /**
     * @param fileContentType the fileContentType to set
     */
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    /**
     * @return the serverFilename
     */
    public String getServerFilename() {
        return serverFilename;
    }
    /**
     * @param serverFilename the serverFilename to set
     */
    public void setServerFilename(String serverFilename) {
        this.serverFilename = serverFilename;
    }
    /**
     * @return the fullFilename
     */
    public String getFullFilename() {
        return fullFilename;
    }
    /**
     * @param fullFilename the fullFilename to set
     */
    public void setFullFilename(String fullFilename) {
        this.fullFilename = fullFilename;
    }
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
