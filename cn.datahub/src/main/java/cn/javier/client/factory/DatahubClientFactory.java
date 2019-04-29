package cn.javier.client.factory;

import com.aliyun.datahub.client.DatahubClient;
import com.aliyun.datahub.client.DatahubClientBuilder;
import com.aliyun.datahub.client.auth.AliyunAccount;
import com.aliyun.datahub.client.common.DatahubConfig;
import com.aliyun.datahub.client.http.HttpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/16 16:38
 * @Version 1.0
 */
public class DatahubClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(DatahubClientFactory.class);
    private static String endpoint = "";
    private static String accessId = "";
    private static String accessKey = "";
    private DatahubClientFactory(){}
    private static volatile DatahubClient datahubClient = null;
    public static DatahubClient datahubClientFactory(){
        if (null == datahubClient){
            synchronized (DatahubClientFactory.class){
                logger.info("开始构建DatahubClient");
                datahubClient = DatahubClientBuilder.newBuilder().setDatahubConfig(
                        new DatahubConfig(endpoint,
                                new AliyunAccount(accessId,accessKey),
                                // 是否开启二进制传输，服务端2.12版本开始支持
                                true))
                        // HttpConfig可不设置，不设置时采用默认值
                        .setHttpConfig(new HttpConfig().setConnTimeout(10000)).build();
                logger.info("DatahubClient构建完成");
            }
        }
        return datahubClient;
    }
}
