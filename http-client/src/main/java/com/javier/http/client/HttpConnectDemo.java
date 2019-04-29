package com.javier.http.client;

import com.javier.util.EncryptUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/12/28 15:46
 * @Version 1.0
 */
public class HttpConnectDemo {
    public static void main(String[] args) {
        String pubkey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMJIgldkqK+w7PmehLKSUtr0KlN0AgIszcyEjVJVeVOjAt2bYqX24yknCEmbghtdyc0t5uYXA314zM9lwTlRpe8CAwEAAQ==";
        String username = "xa20181228";
        URL url;
        String serviceURL = "http://125.35.63.166/interface/services/queryStandardService?wsdl";
        String password1 = "xa20181228";

//        String password = "";
//        try {
//            password = EncryptUtil.encode(pubkey, password1);
//            // System.out.println("密码密文:" + password);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String soapAction = "";
        String begintime = "2018-12-21 12:12:12";
        String endtime = "2018-12-22 12:12:12";
        try {
//            password = URLEncoder.encode(password, "utf-8");
            String xml = EncryptUtil.getParamter(pubkey,username,password1,begintime,endtime);
            System.out.println(xml);
            url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            // xml=xml.replace("+", "%2B");

            bout.write(xml.getBytes("utf-8"));
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream(), "utf-8");
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
