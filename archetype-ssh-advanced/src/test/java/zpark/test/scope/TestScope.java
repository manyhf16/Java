package zpark.test.scope;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/zpark/test/scope/test-scope.xml")
public class TestScope {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private ExecutorService pool = Executors.newFixedThreadPool(1);

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test1() throws InterruptedException, ExecutionException {
        final ScopeObject so1 = (ScopeObject) context.getBean("thread");
        final ScopeObject so2 = (ScopeObject) context.getBean("thread");
        /* 同一线程，获取的对象应当相同 */
        Assert.assertEquals(so1, so2);
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                ScopeObject so3 = (ScopeObject) context.getBean("thread");
                return so3.toString();
            }
        });
        /* 不同线程，获取的对象应当不同 */
        Assert.assertNotEquals(so1.toString(), future.get());
    }

    @Test
    public void test2() throws InterruptedException, ExecutionException {
        ScopeService s = context.getBean(ScopeService.class);
        Future<Map<String, String>> future = pool.submit(new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                ScopeService s = context.getBean(ScopeService.class);
                ScopeObject obj = s.getObject();
                map.put("service", s.toString());
                map.put("scopeObject", obj.toString());
                return map;
            }
        });
        Map<String, String> map = future.get();
        /* service对象应当相同 */
        Assert.assertEquals(s.toString(), map.get("service"));
        /* service对象中的scopeObject应当不同 */
        Assert.assertNotEquals(s.getObject().toString(), map.get("scopeObject"));
    }

    @Test
    public void test3() throws Exception {
        final MockHttpSession session = new MockHttpSession();

        // 发送第一个请求
        mvc.perform(MockMvcRequestBuilders.get("/request?name={name}", "zhangsan").session(session)).andDo(
                new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        final String r1 = result.getResponse().getContentAsString();
                        // 发送第二个请求
                        mvc.perform(MockMvcRequestBuilders.get("/request?name={name}", "lisi").session(session)).andDo(
                                new ResultHandler() {
                                    @Override
                                    public void handle(MvcResult result) throws Exception {
                                        String r2 = result.getResponse().getContentAsString();
                                        /*
                                         * 两次请求的结果,
                                         * 即requestScopeObject的toString形式应当不同
                                         */
                                        Assert.assertNotEquals(r1, r2);
                                    }
                                });
                    }
                });
    }

    @Test
    public void test4() throws Exception {
        final MockHttpSession session = new MockHttpSession();

        // 发送第一个请求
        mvc.perform(MockMvcRequestBuilders.get("/session?name={name}", "zhangsan").session(session)).andDo(
                new ResultHandler() {
                    @Override
                    public void handle(MvcResult result) throws Exception {
                        final String r1 = result.getResponse().getContentAsString();
                        // 发送第二个请求
                        mvc.perform(MockMvcRequestBuilders.get("/session?name={name}", "lisi").session(session)).andDo(
                                new ResultHandler() {
                                    @Override
                                    public void handle(MvcResult result) throws Exception {
                                        String r2 = result.getResponse().getContentAsString();
                                        /*
                                         * 两次请求的结果, 即sessionScopeObject
                                         * 的toString形式应当相同
                                         */
                                        Assert.assertEquals(r1, r2);
                                    }
                                });
                    }
                });
    }

}
