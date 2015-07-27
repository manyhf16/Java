package zpark.struts2.action.day1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class TestParameterEncoding {

    @Test
    public void test() throws Exception {
        // 发送一个Post请求，使用utf-8编码，看是否有乱码问题
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/struts2/day1/hello.action");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("name", "张三"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");
        post.setEntity(entity);
        client.execute(post);
    }

}
