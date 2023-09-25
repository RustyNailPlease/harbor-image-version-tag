package com.dengqn.harborimageversiontag;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.dengqn.harborimageversiontag.vo.HarborArtifactsVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dengqn
 * @date 2023/9/24 19:16
 */
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

	@GetMapping(value = "/project/{project}/repo/{repo}/size/{size}", produces = {MediaType.IMAGE_JPEG_VALUE})
	public Mono<byte[]> tagImage(@PathVariable("project") String project,
	                                     @PathVariable("repo") String repo,
	                                     @PathVariable("size") Integer size) throws IOException {
		if (size == null || size == 0 || size > 10) {
			size = 1;
		}


		String url = StrUtil.format("{}/projects/{}/repositories/{}/artifacts?page_size={}",
				System.getenv("harbor_host"),
				project, repo, size);

		HttpRequest request = HttpUtil.createRequest(Method.GET, url);
		request.basicAuth(System.getenv("harbor_username"), System.getenv("harbor_password"));

		HttpResponse response = request.execute();

		List<HarborArtifactsVO> list = JSONUtil.toList(JSONUtil.parseArray(response.body()), HarborArtifactsVO.class);
		List<String> versions = list.stream()
				.filter(a -> a.getTags() != null)
				.map(a -> a.getTags().stream()
						.filter(Objects::nonNull)
						.map(t -> t.getName() + "(" + formatTime(t.getPushTime()) + ")" + " - " + a.getSize() / 1024 / 1024 + "Mb")
						.collect(Collectors.toList()) ).flatMap(Collection::stream)
				.toList();

		int height = 16 * (versions.size() + 1);

		Font font = new Font("Default", Font.PLAIN, 16);

		BufferedImage bi = new BufferedImage(500, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setFont(font);


		for (int i = 0; i < versions.size(); i++) {
			int x = 12;
			int y = (32 + (i * 32)) / 2;
			g2.drawString(versions.get(i), x, y + 8);
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		boolean jpg = ImageIO.write(bi, "jpg", byteArrayOutputStream);

		return Mono.just(byteArrayOutputStream.toByteArray());
	}

	private String formatTime(Date time) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
	}


}
