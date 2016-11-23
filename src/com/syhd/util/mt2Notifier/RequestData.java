package com.syhd.util.mt2Notifier;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestData {
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/*public boolean isValidate(){
		return false;
	}*/
}
