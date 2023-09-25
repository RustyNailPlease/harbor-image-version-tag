package com.dengqn.harborimageversiontag.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dengqn
 * @date 2023/9/24 19:47
 */
public class ArtifactsTag implements Serializable {
	private static final long serialVersionUID = -8867899323303620529L;

	private String name;

	private Date pushTime;

	public ArtifactsTag() {
	}

	public ArtifactsTag(String name, Date pushTime) {
		this.name = name;
		this.pushTime = pushTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
}
