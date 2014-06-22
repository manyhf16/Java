package zpark.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import zpark.entity.SampleEntity;
import zpark.service.SampleService;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Controller
@Scope("prototype")
@JsonSerialize(using = SampleAction.class)
public class SampleAction extends JsonSerializer<SampleAction> {

	private static Logger logger = LoggerFactory.getLogger(SampleAction.class);

	@Autowired
	private SampleService sampleService;

	private String name;

	private List<SampleEntity> result;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SampleEntity> getResult() {
		return result;
	}

	public void setResult(List<SampleEntity> result) {
		this.result = result;
	}

	public String execute() {
		if (name != null) {
			logger.info("sample action method...");
			result = sampleService.findAll();
			return "success";
		} else {
			return "input";
		}
	}

	@Override
	public void serialize(SampleAction action, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("name", action.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", action.getResult());
		jgen.writeObjectField("result", map);
		jgen.writeEndObject();
	}

}
