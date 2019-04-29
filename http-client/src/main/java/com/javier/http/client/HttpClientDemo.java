package com.javier.http.client;

import com.javier.util.EncryptUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/8/22 14:25
 * @Version 1.0
 */
public final class HttpClientDemo {
    public static Integer RESPONSE_200 = 200;
    public static String doGet(String url, Map<String, String> paramMap){
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            //创建URL
            URIBuilder builder = new URIBuilder(url);
            if (paramMap != null && paramMap.size() > 0){
                for (String key : paramMap.keySet()){
                    builder.addParameter(key, paramMap.get(key));
                }
            }
            URI uri = builder.build();
            //创建http get请求
            HttpGet httpGet = new HttpGet(uri);
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态是否为200
            if (RESPONSE_200.equals(response.getStatusLine().getStatusCode())){
                resultString = EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            closehttp(response,httpClient);
        }
        return resultString;
    }

    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> paramMap){
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //创建http post请求
            HttpPost httpPost = new HttpPost(url);
            //创建参数列表
            if (paramMap != null && paramMap.size() > 0){
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : paramMap.keySet()){
                    paramList.add(new BasicNameValuePair(key,paramMap.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"UTF-8");
                httpPost.setEntity(entity);
            }
            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            closehttp(response,httpClient);
        }

        return resultString;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json){
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //创建httpPost
            HttpPost httpPost = new HttpPost(url);
            //创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closehttp(response,httpClient);
        }
        return resultString;
    }

    public static void closehttp(CloseableHttpResponse response, CloseableHttpClient httpClient){
        try {
            if (response != null){
                response.close();
            }
            if (httpClient != null){
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "http://125.35.63.166/interface/services/queryStandardService?wsdl=queryStandard";
        Map<String, String> param = new HashMap<String, String>();
        String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMJIgldkqK+w7PmehLKSUtr0KlN0AgIszcyEjVJVeVOjAt2bYqX24yknCEmbghtdyc0t5uYXA314zM9lwTlRpe8CAwEAAQ==";
        String username = "xa20181228";
        String password = "xa20181228";
        String begintime = "2018-12-21 12:12:12";
        String endtime = "2018-12-22 12:12:12";
        String parameter = EncryptUtil.getParamter(pubKey,username,password,begintime,endtime);
//        param.put("wsdl","queryStandard");
        param.put("parameter",parameter);
        String result = HttpClientDemo.doGet(url,param);
        System.out.println(result);
    }
}
