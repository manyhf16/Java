package zpark.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.octo.captcha.engine.image.gimpy.DefaultGimpyEngine;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

import zpark.entity.City;

public class TestAction {
	private List<City> list;
	private Map<String, City> map;
	
	public List<City> getList() {
		return list;
	}

	public void setList(List<City> list) {
		this.list = list;
	}

	public Map<String, City> getMap() {
		return map;
	}

	public void setMap(Map<String, City> map) {
		this.map = map;
	}

	public String execute() throws IOException {
		DefaultGimpyEngine engine = new DefaultGimpyEngine();
		
		DefaultManageableImageCaptchaService service = new DefaultManageableImageCaptchaService();
//		service.setCaptchaEngine(engine);
		BufferedImage image =  service.getImageChallengeForID("223");
//		System.out.println(list);
//		System.out.println(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		ImageIO.write(image, "png", response.getOutputStream());
		return null;
	}

}
