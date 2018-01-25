package bhz.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import bhz.entity.Simple;
import bhz.entity.User;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

public class SerializationOptimizerImpl implements SerializationOptimizer {

    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        //这里可以把所有需要进行序列化的类进行添加
        classes.add(User.class);
        classes.add(Simple.class);
        return classes;
    }
}
