package com.dengqn.harborimageversiontag.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author dengqn
 * @date 2023/9/24 19:46
 */
public class HarborArtifactsVO implements Serializable {
	private static final long serialVersionUID = -7061428415859904552L;

	private Long size;

	private List<ArtifactsTag> tags;

	public HarborArtifactsVO() {
	}

	public HarborArtifactsVO(Long size, List<ArtifactsTag> tags) {
		this.size = size;
		this.tags = tags;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public List<ArtifactsTag> getTags() {
		return tags;
	}

	public void setTags(List<ArtifactsTag> tags) {
		this.tags = tags;
	}
}
