package com.udesk.entity;

public class ResponseResult<T> {
	//用于表示响应结果，当控制器中的方法返回这个类的对象时，加上Jackson框架的处理，会返回这个类对应的JSON字符串！

	private Integer state = 200;
	private String message;
	private T data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Integer state, Exception ex) {
		super();
		this.state = state;
		this.message = ex.getMessage();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


}
