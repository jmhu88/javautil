import com.house365.finance.framework.persistence.support.Pager;
import java.util.List;

/**
 * 分页查询结果对象
 * 
 * @param <T>
 * @author pengpeng
 * @date 2013-10-14 下午10:30:22
 * @version 1.0
 */
public class PageList<T> {

	private List<T> dataList;
	
	private Pager pager;

	public PageList() {
		super();
	}

	public PageList(List<T> dataList, Pager pager) {
		super();
		this.dataList = dataList;
		this.pager = pager;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String toString() {
		return "PageList [dataList=" + dataList + ", pager=" + pager + "]";
	}

}
