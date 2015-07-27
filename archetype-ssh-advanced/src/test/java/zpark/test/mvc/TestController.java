package zpark.test.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/a")
public class TestController {

    @RequestMapping(value = "b/**", produces = "text/xml;charset=utf-8")
    public @ResponseBody Response test() {
        System.out.println("enter");
        // int i = 1/0;
        Response response = new Response();
        response.getResultStockList().getRow().add(new ResultStock("1"));

        response.getPayStatusList().getRow().add(new PayStatus("a", "0", "正常"));
        response.getPayStatusList().getRow().add(new PayStatus("b", "1", "出错"));
        return response;
    }

}
