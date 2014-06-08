package zpark.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import zpark.service.SampleService;

@Controller
@Scope("prototype")
public class SampleAction {

	private static Logger logger = LoggerFactory.getLogger(SampleAction.class);

	@Autowired
	private SampleService sampleService;

	private String name;

	private String result;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() {
		if (name != null) {
			logger.info("sample action method...");
			result = sampleService.sample();
			return "success";
		} else {
			result = "name can't be null";
			return "input";
		}
	}

}
