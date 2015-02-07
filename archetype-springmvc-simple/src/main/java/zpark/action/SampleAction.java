package zpark.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zpark.service.SampleService;

@Controller("m1sample")
public class SampleAction {

    private static Logger logger = LoggerFactory.getLogger(SampleAction.class);

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/sample")
    public String execute(Model model) {
        logger.info("sample action method...m1:" + sampleService);
        sampleService.sample();
        model.addAttribute("test", "ok");
        return "sampleView";
    }

}
