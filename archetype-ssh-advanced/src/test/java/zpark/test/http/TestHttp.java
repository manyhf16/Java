package zpark.test.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

public class TestHttp {
    
    public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
        
        ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(
                    HttpResponse response,
                    HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    // Keep connections alive 5 seconds if a keep-alive value
                    // has not be explicitly set by the server
                    keepAlive = 5000;
                }
                return keepAlive;
            }

        };
        CloseableHttpClient httpclient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrat)
                .build();
        HttpGet get = new HttpGet("http://localhost:8080/archetype-ssh-advanced/hello/a");
        System.out.println(1);
        CloseableHttpResponse response = httpclient.execute(get);
        response.close();
        Thread.sleep(500);
        
        System.out.println(2);
        response = httpclient.execute(get);
        response.close();
        Thread.sleep(500);
        
        System.out.println(3);
        response = httpclient.execute(get);
        response.close();
        Thread.sleep(500);
        httpclient.close();
    }

    @Test
    public void test01() throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8080/archetype-ssh-advanced/hello/a");
        get.addHeader("If-Modified-Since", "Mon, 08 Dec 2014 08:05:37 GMT");
        get.addHeader("Accept-Encoding", "gzip");
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
        get.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
        get.addHeader("Cache-Control","max-age=0");
        get.addHeader("Connection","keep-alive");
        get.addHeader("Host","localhost:8080");
        
        CloseableHttpResponse response = client.execute(get);
        // System.out.println(response.getStatusLine());
        // System.out.println(Arrays.toString(response.getAllHeaders()));
        // System.out.println(response);
        Header[] headers = response.getAllHeaders();
        for(Header h : headers) {
            System.out.println(h.getName() + ":" + h.getValue());
        }
        HttpEntity entity = new BufferedHttpEntity(response.getEntity());
        // String str = EntityUtils.toString(entity);
        // System.out.println(str);
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line;
        while (true) {
            line = reader.readLine();
            if (line == null)
                break;
            else {
                System.out.println(line);
            }
        }
//        System.out.println("======================");
//        String str = EntityUtils.toString(entity);
//        System.out.println(str);
//        EntityUtils.consume(entity);
//        response.close();
    }

}
