package com.demo.netty.socket.server.handler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 1. 创建HttpClient对象。
 * 2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 3. 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 * 4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
 * 5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
 * 6. 释放连接。无论执行方法是否成功，都必须释放连接
 */
public class SendHttpByHttpClient {

    private static String url = "http://localhost:8080/uploadDatas";
    private HttpPost httpPost;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;

    public void connectHttp(){
        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost(url);
    }

    public void sendHttpAndJson(String info) throws IOException {
        if(httpPost==null || httpClient ==null){
            connectHttp();
        }
        HttpEntity entity = new StringEntity(info,"UTF-8");
        httpPost.setEntity(entity);
        response = httpClient.execute(httpPost);
    }

    public void closdHttp() throws IOException {
        response.close();
        httpClient.close();
    }

    public static void main(String[] args) throws IOException {
        SendHttpByHttpClient sendHttp = new SendHttpByHttpClient();
        sendHttp.connectHttp();
        String info="{\n" +
                "\t\"datas\": [{\n" +
                "\t\t\"avgHeartRate\": \"12\",\n" +
                "\t\t\"calorie\": \"12\",\n" +
                "\t\t\"distance\": \"12e\",\n" +
                "\t\t\"heartRate\": \"233\",\n" +
                "\t\t\"position\": \"321.1212,1213.1212\",\n" +
                "\t\t\"sleep\": \"12\",\n" +
                "\t\t\"stepCounter\": \"12344\",\n" +
                "\t\t\"timeStamp\": \"2018-10-10 09:10:12\"\n" +
                "\t}],\n" +
                "\t\"deviceId\": \"25fab30a-45c3-4de5-872e-4cc811ae6296\",\n" +
                "\t\"deviceType\": \"band\",\n" +
                "\t\"userId\": \"1222293\",\n" +
                "\t\"battery\": \"90\",\n" +
                "\t\"factoryId\": \"122345788\"\n" +
                "\n" +
                "}\n";
        sendHttp.sendHttpAndJson(info);
        HttpEntity entity = sendHttp.response.getEntity();
        String string = EntityUtils.toString(entity);
        System.out.println(string);
        sendHttp.closdHttp();
    }
}
