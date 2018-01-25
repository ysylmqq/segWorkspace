package bhz.sys.serial;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.entity.Alarm;
import bhz.sys.entity.Fault;
import bhz.sys.entity.SysUser;
import bhz.sys.entity.Vehicle;

public class SerializationOptimizerImpl implements SerializationOptimizer {

    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        //这里可以把所有需要进行序列化的类进行添加
        classes.add(SysUser.class);
        classes.add(JSONObject.class);
        classes.add(Vehicle.class);
        classes.add(Fault.class);
        classes.add(Alarm.class);
        classes.add(Page.class);
        /*classes.add(QuickPageContext.class);*/
        classes.add(PageSelect.class);
        return classes;
    }
}
