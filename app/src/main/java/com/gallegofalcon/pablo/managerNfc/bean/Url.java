package com.gallegofalcon.pablo.managerNfc.bean;

public class Url {
	private Long id;
	private String code;
	private String name;
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String toString() {
		return name + "\n" + url;
	}
}
