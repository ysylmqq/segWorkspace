package cc.chinagps.seat.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONPopulator;
import com.googlecode.jsonplugin.annotations.JSON;

public class MyJSONPopulator extends JSONPopulator {
	
	@SuppressWarnings("rawtypes")
	public void populateObject(Object object, Map elements)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IntrospectionException,
			IllegalArgumentException, JSONException, InstantiationException {
		Class clazz = object.getClass();

		BeanInfo info = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] props = info.getPropertyDescriptors();

		// iterate over class fields
		for (int i = 0; i < props.length; ++i) {
			PropertyDescriptor prop = props[i];
			String name = prop.getName();

			Method method = prop.getWriteMethod();
			
			JSON json = null;
			if (method != null) {
				json = (JSON) prop.getWriteMethod().getAnnotation(
						JSON.class);
			}
			// 增加判断是否配置了json并根据json的name来进行赋值
			if (json != null && json.name() != null) {
				name = json.name();
			}
			if (elements.containsKey(name)) {
				Object value = elements.get(name);

				if (method != null) {
					if ((json != null) && !json.deserialize()) {
						continue;
					}

					// use only public setters
					if (Modifier.isPublic(method.getModifiers())) {
						Class[] paramTypes = method.getParameterTypes();
						Type[] genericTypes = method.getGenericParameterTypes();

						if (paramTypes.length == 1) {
							Object convertedValue = this.convert(paramTypes[0],
									genericTypes[0], value, method);
							method.invoke(object,
									new Object[] { convertedValue });
						}
					}
				}
			}
		}
	}
}
