package com.syhd.util.mt2Notifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @company 北京视游互动科技有限公司<br>
 * @author puci<br>
 * @version 创建时间：2015年7月6日 下午7:18:01
 * 
 */
public class ReflectBeanUtil {
	/**
	 * java反射bean的get方法
	 * 
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	public static Method getGetMethod(Class<?> objectClass, String fieldName) {
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		try {
			return objectClass.getMethod(sb.toString());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * java反射bean的set方法
	 * 
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	public static Method getSetMethod(Class<?> objectClass, String fieldName) {
		try {
			Class<?>[] parameterTypes = new Class[1];
			Field field = objectClass.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = objectClass
					.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行set方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 * @param value值
	 */
	public static void invokeSet(Object o, String fieldName, Object value) {
		Method method = getSetMethod(o.getClass(), fieldName);
		try {
			method.invoke(o, new Object[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行get方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 */
	public static Object invokeGet(Object o, String fieldName) {
		Method method = getGetMethod(o.getClass(), fieldName);
		try {
			return method.invoke(o, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * bean转map
	 * @param objectClass 执行对象
	 */
	public static Map<String, Object> bean2Map(Object objectClass) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> cls = objectClass.getClass();
		try {
			Field[] fields = cls.getDeclaredFields();
			Object objVal = null;
			for (Field field : fields) {
				objVal = invokeGet(objectClass, field.getName());
				if(objVal != null){
					map.put(field.getName(), objVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * bean转map
	 * @param Map<String,String>对象
	 */
	public static Map<String, String> bean2MapString(Object objectClass) {
		Map<String, String> map = new HashMap<String, String>();
		Class<?> cls = objectClass.getClass();
		try {
			Field[] fields = cls.getDeclaredFields();
			Object objVal = null;
			for (Field field : fields) {
				objVal = invokeGet(objectClass, field.getName());
				if(objVal != null){
					map.put(field.getName(), Obj2String(objVal));
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, String> MapObj2MapString(Map<String, Object> mapObj){
		Map<String, String> map = new HashMap<String, String>();
		for (String key : mapObj.keySet()) {
            Object value = mapObj.get(key);
            if(value != null){
				map.put(key, Obj2String(value));
			}
        }
		
		return map;
	}
	/**
	 * Object对象转换成String对象
	 * @param value
	 * @return
	 */
	public static String Obj2String(Object value) {
		String strValue = null;

		if (value == null) {
			strValue = null;
		} else if (value instanceof String) {
			strValue = (String) value;
		} else if (value instanceof Integer) {
			strValue = ((Integer) value).toString();
		} else if (value instanceof Long) {
			strValue = ((Long) value).toString();
		} else if (value instanceof Float) {
			strValue = ((Float) value).toString();
		} else if (value instanceof Double) {
			strValue = ((Double) value).toString();
		} else if (value instanceof Boolean) {
			strValue = ((Boolean) value).toString();
		} else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			strValue = format.format((Date) value);
		} else {
			strValue = value.toString();
		}

		return strValue;
	}

}
