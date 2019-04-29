package cn.javier.client.dao.impl;

import cn.javier.client.dao.InsertTopic;
import cn.javier.client.factory.DatahubClientFactory;
import com.aliyun.datahub.client.DatahubClient;
import com.aliyun.datahub.client.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/16 17:51
 * @Version 1.0
 */
public class InsertTopicImpl implements InsertTopic {
    private DatahubClient datahubClient = null;
    public InsertTopicImpl(){
        datahubClient = DatahubClientFactory.datahubClientFactory();
    }
    @Override
    public void insertTupleRecordsByShard(String projectName, String topicName, String shardId, RecordSchema schema, List<RecordEntry> recordEntries) {
        for (int i = 0; i < 10; ++i) {
            RecordEntry recordEntry = new RecordEntry();
            // set attributes
            recordEntry.addAttribute("key1", "value1");
            // set tuple data
            TupleRecordData data = new TupleRecordData(schema);
            List<Field> fields = schema.getFields();
            for (Field field : fields){
                data.setField(field.getName(),"");
            }
            data.setField("field1", "HelloWorld");
            data.setField("field2", 1234567);
            recordEntry.setRecordData(data);
            recordEntries.add(recordEntry);
        }
        datahubClient.putRecordsByShard(projectName, topicName, shardId, recordEntries);
    }
}
