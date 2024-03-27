package cn.stum.pojo;

public class Result {
	String flag;
	Object data;

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(String flag, Object data) {
		super();
		this.flag = flag;
		this.data = data;

	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
