package bhz.mst.serial;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.alibaba.fastjson.JSONObject;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.com.util.QuickPageContext;
import bhz.mst.entity.ChartData;

public class SerializationOptimizerImpl implements SerializationOptimizer {

    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        //这里可以把所有需要进行序列化的类进行添加
        classes.add(JSONObject.class);
        classes.add(ChartData.class);
        classes.add(Page.class);
        classes.add(QuickPageContext.class);
        classes.add(PageSelect.class);
        return classes;
    }
}
