import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 有关文件操作的工具类
 *  
 * @author pengpeng
 * @date 2013-10-16 上午9:44:12
 * @version 1.0
 */
public class FileUtils {

	/**
	 * 默认的标准推荐使用的文件路径分隔符
	 */
	public static final String DEFAULT_STANDARD_FILE_DELIMITER = "/";
	
	/**
	 * 默认的不推荐使用的文件路径分隔符
	 */
	public static final String DEFAULT_AGAINST_FILE_DELIMITER = "\\";
	
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
    //保存文件的文件夹,用mount从应用服务器的硬盘映射到图片服务器的硬盘 TODO
    private static final String IMAGE_FOLDER = "/var/file/";
    //图片服务器的域名 TODO
    private static final String IMAGE_DOMAIN = "http://";
    //本地临时文件缓存目录 TODO
    private static final String TMP_IMAGE_FOLDER = "/var/tmp/file/";
    
    /**
     * 直接上传文件(可上传一个，也可上传多个)
     * 
     * @param request
     * @param response
     * @return 文件保存之后在图片服务器的url列表
     * @throws IllegalStateException
     * @throws IOException
     */
    public static List<String> uploadMultipart(HttpServletRequest request,
            HttpServletResponse response) {
        // 文件保存之后在图片服务器的url列表
        List<String> imageList = new ArrayList<String>();
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        logger.info("上传的文件： " + myFileName);
                        // 重命名上传后的文件名
                        String fileName = UUIDKeyGenerate.getUUIDKey();
                        // 定义上传路径
                        String path = IMAGE_FOLDER + fileName;
                        File localFile = new File(path);
                        try {
                            file.transferTo(localFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        imageList.add(IMAGE_DOMAIN + fileName);
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                logger.info("本次文件上传共耗时： " + (finaltime - pre));
            }
        }
        return imageList;
    }

    /**
     * 上传文件之后生成并保存缩放图(可上传一个，也可上传多个)
     * 
     * @param request
     * @param response
     * @param destImgSize[] 要生成缩小图的size列表
     * @return 文件保存之后在图片服务器的url列表
     * @throws IllegalStateException
     * @throws IOException
     */
    public static List<String> uploadMultipartAndResize(HttpServletRequest request,
            HttpServletResponse response, int[] destImgSize) {
        // 文件保存之后在图片服务器的url列表
        List<String> imageList = new ArrayList<String>();
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        logger.info("上传的文件： " + myFileName);
                        
                        // 定义上传路径
                        String path = TMP_IMAGE_FOLDER + myFileName;
                        //暂时保存到临时目录
                        File localFile = new File(path);
                        try {
                            file.transferTo(localFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        // 重命名上传后的文件名
                        for(int nsize : destImgSize){
                            String path2 = TMP_IMAGE_FOLDER + UUIDKeyGenerate.getUUIDKey();
                            File localFile2 = new File(path2);
                            FileUtils.createPreviewImage(localFile, localFile2, nsize);
                            String aimFileName = UUIDKeyGenerate.getUUIDKey();
                            String path3 = IMAGE_FOLDER + aimFileName;
                            File localFile3 = new File(path3);
                            try {
                                FileUtils.copyFile(localFile2, localFile3);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                continue;
                            }
                            imageList.add(IMAGE_DOMAIN + aimFileName);
                        }
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                logger.info("本次文件上传共耗时： " + (finaltime - pre));
            }
        }
        return imageList;
    }
    
	/**
	 * 获取文件格式
	 * @param imageFileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		Assert.notEmpty(fileName, "can not get a image format with a null or empty file name, fileName = " + fileName);
		return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
	}
	
	/**
	 * 获取文件大小
	 * @param fileFullPath
	 * @return
	 * @throws IOException
	 */
	public static int getFileSize(String fileFullPath) throws IOException {
		Assert.notEmpty(fileFullPath, "can not get a image format with a null or empty file full path, fileFullPath = " + fileFullPath);
		int size = 0;
		fileFullPath = formatFilePath(fileFullPath);
		File file = new File(fileFullPath);
		if(file.exists() && !isDirectory(fileFullPath)){
			FileInputStream fis = new FileInputStream(file);
			size = fis.available();
			if(fis != null){
				fis.close();
			}
		}
		return size;
	}
	
	/**
	 * 纠正不标准的文件路径分隔符
	 * 如：\,\\,\\\,// -> /
	 * @param path
	 * @return
	 */
	public static String formatFilePath(String path) {
		if(!StringUtils.isEmpty(path)){
			return path.replace(DEFAULT_AGAINST_FILE_DELIMITER + DEFAULT_AGAINST_FILE_DELIMITER, DEFAULT_AGAINST_FILE_DELIMITER).replace(DEFAULT_AGAINST_FILE_DELIMITER, DEFAULT_STANDARD_FILE_DELIMITER).replace(DEFAULT_STANDARD_FILE_DELIMITER + DEFAULT_STANDARD_FILE_DELIMITER, DEFAULT_STANDARD_FILE_DELIMITER);
		}
		return path;
	}
	
	/**
	 * 根据文件路径获取File实例
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static File getFile(String filePath) {
		Assert.notEmpty(filePath, "can not get a image format with a null or empty file full path, filePath = " + filePath);
		filePath = formatFilePath(filePath);
		return new File(filePath);
	}
	
	/**
	 * 创建文件目录
	 * @param filePath
	 * @return	true-创建了新目录;false-没有创建新目录
	 * @throws Exception
	 */
	public static boolean mkDirIfNecessary(String filePath) throws Exception {
		filePath = formatFilePath(filePath);
		File dirFile = new File(getFileDir(filePath));
		if(!dirFile.exists()){
			dirFile.mkdirs();
			return true;
		}
		return false;
	}
	
	/**
	 * 根据文件路径判断该路径表示的是文件还是目录
	 * @param filePath
	 * @return
	 */
	public static boolean isDirectory(String filePath){
		if(!StringUtils.isEmpty(filePath)){
			int index1 = filePath.lastIndexOf('.');
			if(index1 == -1){
				return true;
			}else{
				int index2 = filePath.lastIndexOf('/') == -1 ? filePath.lastIndexOf('\\') : filePath.lastIndexOf('/');
				if(index2 != -1){
					if(index1 > index2){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据文件路径获取其目录
	 * @param filePath
	 * @return
	 */
	public static String getFileDir(String filePath){
		if(!StringUtils.isEmpty(filePath)){
			filePath = formatFilePath(filePath);
			if(isDirectory(filePath)){
				return filePath;
			}else{
				return filePath.substring(0, filePath.lastIndexOf(DEFAULT_STANDARD_FILE_DELIMITER));
			}
		}
		System.out.println(filePath);
		return filePath;
	}
	
	/**
	 * 重命名文件名
	 * @param originalName	- 原文件名
	 * @param renameAll		- true-舍弃原文件名完全做随机重新命名;false-在原文件名后面做随机重命名
	 * @param appendStr		- 加在文件名后的追加后缀,e.g. ${originalName}_${appendStr}.jpg
	 * @return
	 * @throws Exception
	 */
	public static String renameFile(String originalName, boolean renameAll, String appendStr) {
		Assert.notEmpty(originalName, "can not rename filename with null or empty originalName!");
		String suffix = originalName.substring(originalName.lastIndexOf('.') + 1);
		String fileName = originalName.substring(0, originalName.lastIndexOf('.'));
		String randomName = UUID.randomUUID().toString().replace("-", "");
		if(!StringUtils.isEmpty(appendStr)){
			return String.format("%s_%s.%s", renameAll ? randomName : fileName + "_" + randomName.substring(0, 8), appendStr, suffix);
		}else{
			return String.format("%s.%s", renameAll ? randomName : fileName + "_" + randomName.substring(0, 8), suffix);
		}
	}
	
	/**
	 * 文件复制
	 * @param srcFullFileName	- 源文件名
	 * @param destFullFileName	- 目标文件名
	 * @throws Exception
	 */
	public static void copyFile(String srcFullFileName, String destFullFileName) throws Exception {
		Assert.notEmpty(srcFullFileName, "源文件全路径名不能为空!");
		Assert.notEmpty(destFullFileName, "目标文件全路径名不能为空!");
		
		//获取源文件和目标文件的输入输出字节流
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//获取输入输出通道
		FileChannel fci = null;
		FileChannel fco = null;
		try {
			fis = new FileInputStream(srcFullFileName);
			fos = new FileOutputStream(destFullFileName);
			fci = fis.getChannel();
			fco = fos.getChannel();
			//创建字节缓冲区
			ByteBuffer bbuffer =  ByteBuffer.allocate(1024);
			while(true){
				//clear缓冲区以接受新的数据
				bbuffer.clear();
				//从输入通道中将数据读出来,读到流末尾返回-1
				int r = fci.read(bbuffer);
				if(r == -1){
					break;
				}
				//反转此缓冲区,让缓冲区可以将新读入的数据写入输出通道
				bbuffer.flip();
				//从输出通道中将数据写入缓冲区
				fco.write(bbuffer);
			}
		} finally {
			if(fci != null){
				fci.close();
			}
			if(fis != null){
				fis.close();
			}
			if(fco != null){
				fco.close();
			}
			if(fos != null){
				fos.close();
			}
		}
	}
	
	/**
	 * 文件复制
	 * @param srcFile		- 源文件
	 * @param destFile		- 目标文件
	 * @throws Exception
	 */
	public static void copyFile(File srcFile, File destFile) throws Exception {
		Assert.notNull(srcFile, "源文件不能为空!");
		Assert.notNull(destFile, "目标文件不能为空!");
		
		//获取源文件和目标文件的输入输出字节流
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//获取输入输出通道
		FileChannel fci = null;
		FileChannel fco = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			fci = fis.getChannel();
			fco = fos.getChannel();
			//创建字节缓冲区
			ByteBuffer bbuffer =  ByteBuffer.allocate(1024);
			while(true){
				//clear缓冲区以接受新的数据
				bbuffer.clear();
				//从输入通道中将数据读出来,读到流末尾返回-1
				int r = fci.read(bbuffer);
				if(r == -1){
					break;
				}
				//反转此缓冲区,让缓冲区可以将新读入的数据写入输出通道
				bbuffer.flip();
				//从输出通道中将数据写入缓冲区
				fco.write(bbuffer);
			}
		} finally {
			if(fci != null){
				fci.close();
			}
			if(fis != null){
				fis.close();
			}
			if(fco != null){
				fco.close();
			}
			if(fos != null){
				fos.close();
			}
		}
	}
	
	/**
	 * 获取文件大小的简单形式
	 * @param fileSize		- 单位byte
	 * @return
	 */
	public static String resolveFileSizeDisplayValue(long fileSize){
		DecimalFormat df = new DecimalFormat("#.00");
		if(fileSize < 0){
			return "不限";
		}else if(fileSize < 1024){
			return fileSize + "Byte";
		}else if(fileSize < 1024 * 1024){
			return df.format(fileSize/ (1024 * 1.0)) + "KB";
		}else if(fileSize < 1024 * 1024 * 1024){
			return df.format(fileSize / (1024 * 1024 * 1.0)) + "MB";
		}else{
			return df.format(fileSize / (1024 * 1024 * 1024 * 1.0)) + "GB";
		}
	}

	/**
	 * 删除文件
	 * @param fullPath
	 * @return
	 */
	public static boolean deleteFile(String fullPath) {
		File file = getFile(fullPath);
		return file.delete();
	}
	
	/**
	 * 生成缩小图
	 * 
	 * @param srcFile
	 *            原文件
	 * @param destFile
	 *            目标文件
	 * @param destImgSize
	 *            希望缩小到多大， 比如 缩小到120*120，就传入120
	 */
	public static void createPreviewImage(File fi, File fo,
			int destImgSize) {
		try {
			BufferedImage bis = ImageIO.read(fi);
			// 原图片的长高和精度
			int w = bis.getWidth();
			int h = bis.getHeight();
			double scale = (double) w / h;
			int nw = destImgSize;
			int nh = (nw * h) / w;
			if (nh > destImgSize) {
				nh = destImgSize;
				nw = (nh * w) / h;
			}
			double sx = (double) nw / w;
			double sy = (double) nh / h;
			final AffineTransform transform = new AffineTransform();
			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, " jpeg ", fo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					" Failed in create preview image. Error:  "
							+ e.getMessage());
		} finally {

		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println(deleteFile("d:\\small-iphone4_120x120.jpg"));
	}
}
