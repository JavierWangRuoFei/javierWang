package cn.wrf.javier.maxcompute;

import com.aliyun.odps.udf.UDF;

public class Udf_lower extends UDF {
    // TODO define parameters and return type, e.g:  public String evaluate(String a, String b)
    public String evaluate(String s) {
        if (s == null){
            return null;
        }
        return s.toLowerCase();
    }
}