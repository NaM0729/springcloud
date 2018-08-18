package com.demo.device_server.util;

public class DataResult {

	// 状态码
	private Integer code;
	// 状态描述
	private String message;
	// 返回数据
	private Object datas;

	/**
	 * 成功
	 * 
	 * @return
	 */
	public static DataResult success() {
		DataResult result = new DataResult();
		result.setCode(200);
		result.setMessage("success");
		return result;
	}
	public static DataResult success(Object data) {
		DataResult result = new DataResult();
		result.setCode(200);
		result.setMessage("success");
		result.setData(data);
		return result;
	}
	public static DataResult success(String message) {
		DataResult result = new DataResult();
		result.setCode(200);
		result.setMessage(message);
		return result;
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public static DataResult fail() {
		DataResult result = new DataResult();
		result.setCode(400);
		result.setMessage("error");
		return result;
	}
	public static DataResult fail(String errorMessage) {
		DataResult result = new DataResult();
		result.setCode(400);
		result.setMessage(errorMessage);
		return result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return datas;
	}

	public void setData(Object datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "DataResult{" +
				"code=" + code +
				", message='" + message + '\'' +
				", data=" + datas +
				'}';
	}
}
