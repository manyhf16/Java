package zpark.test.mvc;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/zpark/test/mvc/test-mvc.xml")
public class TestMvc {

    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private ContentNegotiationManager contentNegotiationManager;
    
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    
    @Autowired
    private BeanNameUrlHandlerMapping beanNameUrlHandlerMapping;
    
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void test1() {
        for(String name: context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(contentNegotiationManager.getAllFileExtensions());
        System.out.println(requestMappingHandlerMapping.getPathMatcher());
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for(RequestMappingInfo key : handlerMethods.keySet()) {
            System.out.println("========================");
            System.out.println(key);
            System.out.println(handlerMethods.get(key));
        }
        Map<String, AbstractHandlerMapping> map = context.getBeansOfType(AbstractHandlerMapping.class);
        for(String key: map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key).getOrder());
        }
        System.out.println(objectMapper);
    }
    
    @Test
    public void test2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/a/b/test")).andDo(
                new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String r2 = result.getResponse().getContentAsString();
                        System.out.println(r2);
                    }
                });
    }
    
    @Test
    public void test3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/simple")).andDo(
                new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        String r2 = result.getResponse().getContentAsString();
                        System.out.println(r2);
                    }
                });
    }

}
