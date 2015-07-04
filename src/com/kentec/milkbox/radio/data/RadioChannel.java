package com.kentec.milkbox.radio.data;

public class RadioChannel {
	
	private int id;
	private String type;
	private String name;
	private String url;
	private String image;
	private String information;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage() {
		return image;
	}	
	public void setInformation(String information) {
		this.information = information;
	}
	public String getInformation() {
		return information;
	}
}
