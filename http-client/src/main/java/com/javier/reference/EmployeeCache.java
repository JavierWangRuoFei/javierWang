package com.javier.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/8/23 17:46
 * @Version 1.0
 */
public class EmployeeCache {
    /**
     * 一个cache实例
     */
    private static EmployeeCache cache;
    /**
     * 用于cache内容的存储
     */
    private Hashtable<String, EmployeeRef> employeeRefs;
    /**
     * 垃圾Reference队列
     */
    private ReferenceQueue<Employee> employeeQueue;
    /**
     * 继承SoftReference，使得每一个实例都具有可识别的标识。
     * 并且该标识与其在HashMap内的key相同。
     */
    private class EmployeeRef extends SoftReference<Employee>{
        private String _key = "";
        public EmployeeRef(Employee employee, ReferenceQueue<Employee> employeeQueue){
            super(employee,employeeQueue);
            _key = employee.getId();
        }
    }
    /**
     * 构建一个缓存器实例
     */
    private EmployeeCache(){
        employeeRefs = new Hashtable<String, EmployeeRef>();
        employeeQueue = new ReferenceQueue<Employee>();
    }
    /**
     * 取得缓存器实例
     * @return
     */
    public static EmployeeCache getInstance(){
        if (cache == null){
            cache = new EmployeeCache();
        }
        return cache;
    }
    /**
     * 以软引用的方式对一个Employee对象的实例进行引用并保存该引用
     * @param employee
     */
    private void cacheEmployee(Employee employee){
        //构建一个软引用
        EmployeeRef ref = new EmployeeRef(employee,employeeQueue);
        employeeRefs.put(employee.getId(),ref);
    }
    /**
     * 根据指定的ID号，重新获取相应的Employee对象的实例
     * @param id
     * @return
     */
    public Employee getEmployee(String id){
        Employee employee = null;
        //缓存中是否有该Employee实例的软引用，如果有，从软引用中取得
        if (employeeRefs.contains(id)){
            //获取软引用
            EmployeeRef ref = employeeRefs.get(id);
            //从软引用中获取实例
            employee = ref.get();
        }
        //如果没有软引用的话，或者从软引用中获得的实例是null，重新构建一个实例，
        //并保存对这个新建实例的软引用
        if (employee == null){
            employee = new Employee();
            employee.setId(id);
            this.cacheEmployee(employee);
        }
        return employee;
    }
    /**
     * 清除那些所软引用的对象已经被回收的EmployeeRef对象
     */
    private void cleanCache(){
        EmployeeRef ref = null;
        while ((ref = (EmployeeRef)employeeQueue.poll()) != null){
            employeeRefs.remove(ref._key);
        }
    }
    //清除cache内的所有内容
    public void clearCache(){
        cleanCache();
        employeeRefs.clear();
        System.gc();
        System.runFinalization();
    }
}
