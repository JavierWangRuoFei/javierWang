package com.javier.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/12/28 14:37
 * @Version 1.0
 */
public class EncryptUtil {
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /** 默认的安全服务提供者 */
    // private static final Provider DEFAULT_PROVIDER = new
    // BouncyCastleProvider();
    /**
     * 通过公钥给字符串加密
     *
     * @param key
     * @param plaintext
     * @return
     */
    public static String encode(String key, String plaintext) {
        if (key == null || plaintext == null) {
            return null;
        }
        try {
            PublicKey publicKey = getPublicKey(key);
            byte[] data = plaintext.getBytes();
            Cipher ci = Cipher.getInstance("RSA");
            ci.init(Cipher.ENCRYPT_MODE, publicKey);
            /*int inputLen = data.length;
            int offLen = 0;
            int i = 0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while(inputLen - offLen > 0){
                byte[] cache;
                if(inputLen - offLen > 128){
                    cache = ci.doFinal(data,offLen,128);
                }else{
                    cache = ci.doFinal(data,offLen,inputLen - offLen);
                }
                byteArrayOutputStream.write(cache);
                i++;
                offLen = 128 * i;
            }
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new String(byteArray);*/
            return new String(encodeHex(ci.doFinal(data)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /***
     * 字符串公钥密码转换公钥对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
    public static String getParamter(String pubKey, String username, String password, String begintime, String endtime){
        StringBuilder param = new StringBuilder();
        String result = EncryptUtil.encode(pubKey,password);
        System.out.println("密码密文:" + result);
        try {
            result = URLEncoder.encode(password, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        param.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        param.append("<queryrequest>\n");
        param.append("<username>"+username+"</username >\n");
        param.append("<password>"+result+"</password>\n");
        param.append("<query>\n");
        param.append("<begintime>"+begintime+"</begintime >\n");
        param.append("<endtime>"+endtime+"</endtime>\n");
        param.append("</query>\n");
        param.append("</queryrequest >");
        return param.toString();
    }
    public static void main(String[] args) {
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHC8mG1EvlowpltEvlqElhZtoZW5vXRJxQvqEeUhHr+ezzFJ1//iHOeETRhtdT4wwN6UhFVP/W0nl3OPz6CrrSpzypQiJjEJuMP+TY0hYExijBMNEQY6HD3JuhIUIiv8Q84hsj4WX2eWMNRM9qRQ8CD8RsWsAGO8mVo0cOaJH87wIDAQAB";
        String password = "123456";
        String result = EncryptUtil.encode(pubKey, password);
        System.out.println("加密"+result);

    }

}
