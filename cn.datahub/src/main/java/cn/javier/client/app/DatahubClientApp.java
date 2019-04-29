package cn.javier.client.app;

import cn.javier.client.factory.DatahubClientFactory;
import com.aliyun.datahub.client.DatahubClient;
import com.aliyun.datahub.client.model.*;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/16 16:52
 * @Version 1.0
 */
public class DatahubClientApp {
    private DatahubClient datahubClient = null;
    public DatahubClientApp(){
        datahubClient = DatahubClientFactory.datahubClientFactory();
    }
    public String createProject(String projectName, String comment){
        datahubClient.createProject(projectName,comment);
        return projectName;
    }
    public String createTupleTopic(String projectName, String topicName, String comment, Integer shardCount, Integer lifeCycle){
        RecordSchema schema = new RecordSchema();
        schema.addField(new Field("field1", FieldType.STRING));
        schema.addField(new Field("field2",FieldType.BIGINT));
        datahubClient.createTopic(projectName,topicName,shardCount,lifeCycle, RecordType.TUPLE,schema,comment);
        return topicName;
    }
    public String createBlobTopic(String projectName, String topicName, String comment, Integer shardCount, Integer lifeCycle){
        RecordSchema schema = new RecordSchema();
        schema.addField(new Field("field1", FieldType.STRING));
        schema.addField(new Field("field2",FieldType.BIGINT));
        datahubClient.createTopic(projectName,topicName,shardCount,lifeCycle, RecordType.BLOB,schema,comment);
        return topicName;
    }
    public static void main(String[] args) {
        DatahubClient datahubClient = DatahubClientFactory.datahubClientFactory();
        CreateProjectResult createProjectResult = datahubClient.createProject("","");

    }
}
