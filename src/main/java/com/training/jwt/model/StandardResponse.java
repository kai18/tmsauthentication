package com.training.jwt.model;

import java.io.Serializable;

public class StandardResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5858388419604766502L;
	int code;
	String status;
	String message;
	T element;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	@Override
	public String toString() {
		return "StandardResponse [code=" + code + ", status=" + status + ", message=" + message + ", element=" + element
				+ "]";
	}
}