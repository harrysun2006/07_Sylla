package com.sylla.util;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sylla.Constants;

public class SpringUtil {

	static String[] configs = PropsUtil
			.getArray(Constants.PROP_SPRING_CONFIG_FILES);

	private static Map _beans = new Hashtable();

	// private static ApplicationContext _ctx = new
	// LazyClassPathApplicationContext(configs);
	private static AbstractApplicationContext _ctx = null;

	static {
		try {
			_ctx = new ClassPathXmlApplicationContext(configs);
		} catch (Exception e) {
		}
	}

	public static ApplicationContext getContext() {
		return _ctx;
	}

	public static void refresh() {
		if (_ctx != null) {
			_ctx.refresh();
		} else {
			_ctx = new ClassPathXmlApplicationContext(configs);
		}
	}

	public static Object getBean(String name) {
		Object obj;
		if (_beans.containsKey(name)) {
			return _beans.get(name);
		} else {
			obj = _ctx.getBean(name);
			if (obj != null)
				_beans.put(name, obj);
		}
		return obj;
	}

}
