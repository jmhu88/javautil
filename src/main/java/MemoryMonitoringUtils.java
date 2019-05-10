import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 内存监控工具类
 * 
 * @author pengpeng
 * @date 2013-10-14 下午9:47:16
 * @version 1.0
 */
public class MemoryMonitoringUtils {

	private static final Log logger = LogFactory.getLog(MemoryMonitoringUtils.class);
	
	public static void logMemoryInfo(){
		Runtime runtime = Runtime.getRuntime();
		logger.info(">>> maxMemory : " + runtime.maxMemory() / (1024 * 1024) + ", totalMemory : " + runtime.totalMemory() / (1024 * 1024) + ", freeMemory : " + runtime.freeMemory() / (1024 * 1024));
	}
	
}
