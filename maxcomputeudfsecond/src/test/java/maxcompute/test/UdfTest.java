package maxcompute.test;

import cn.wrf.javier.maxcompute.Udf_lower;
import com.aliyun.odps.udf.UDF;
import com.aliyun.odps.udf.local.LocalRunException;
import com.aliyun.odps.udf.local.runner.BaseRunner;
import com.aliyun.odps.udf.local.runner.UDFRunner;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/1/11 15:32
 * @Version 1.0
 */
public class UdfTest extends UDF {
    @Test
    public void test01(){
        try {
            BaseRunner runner = new UDFRunner(null, Udf_lower.class.getName());
            runner.feed(new Object[]{"a"}).feed(new Object[]{"bC"}).feed(new Object[]{"D"});
            List<Object[]> out = runner.yield();
            Assert.assertEquals(3,out.size());
            Assert.assertEquals("a",out.get(0)[0]);
            Assert.assertEquals("bc",out.get(1)[0]);
            Assert.assertEquals("d",out.get(2)[0]);
            Udf_lower ul = new Udf_lower();
            String s = ul.evaluate("ALIYUN");
            System.out.println(s);
        } catch (LocalRunException e) {
            e.printStackTrace();
        }
    }
}
