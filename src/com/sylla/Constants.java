package com.sylla;

import com.sylla.util.CommonUtil;
import com.sylla.util.PropsUtil;

public interface Constants {

	public static final String VERSION = "Sylla 1.0.0.006";

	/**
	 * Property file and property names
	 */
	public static final String PROPERTIES_FILE = "sylla.properties";

	public static final String PROP_SPRING_CONFIG_FILES = "spring.config.files";

	public static final String PROP_SPRING_HIBERNATE_DATA_SOURCES = "spring.hibernate.data.sources";

	public static final String PROP_SPRING_HIBERNATE_SESSION_FACTORIES = "spring.hibernate.session.factories";

	public static final String PROP_HIBERNATE_CONFIGS = "hibernate.configs";

	public static final String PROP_MAX_RETRY = "max.retry";

	public static final String PROP_DATE_FORMAT = "format.date";

	public static final String PROP_DATETIME_FORMAT = "format.datetime";

	public static final String PROP_LOG4J_ROOT_LEVEL = "log4j.root.level";

	public static final String PROP_WEB_CLIENT_COUNT = "web.client.count";

	public static final String PROP_CLIENT_THREAD_COUNT = "client.thread.count";

	public static final String PROP_PAGE_THREAD_COUNT = "page.thread.count";

	public static final String PROP_VERIFY_THREAD_COUNT = "verify.thread.count";

	public static final String PROP_INFO_THREAD_COUNT = "info.thread.count";

	public static final String PROP_IMAGE_THREAD_COUNT = "image.thread.count";

	public static final String PROP_OCR_THREAD_COUNT = "ocr.thread.count";

	public static final String PROP_COLLATE_THREAD_COUNT = "collate.thread.count";

	public static final int MAX_RETRY = PropsUtil.getInt(PROP_MAX_RETRY, 3);

	public static final String DATE_FORMAT = PropsUtil.get(PROP_DATE_FORMAT, 
			CommonUtil.getString(PROP_DATE_FORMAT, "yyyy-MM-dd"));

	public static final String DATETIME_FORMAT = PropsUtil.get(PROP_DATETIME_FORMAT, 
			CommonUtil.getString(PROP_DATETIME_FORMAT, "yyyy-MM-dd HH:mm:ss"));

	public static final long WEB_CLIENT_COUNT = PropsUtil.getLong(PROP_WEB_CLIENT_COUNT, 60);

	public static final long CLIENT_THREAD_COUNT = PropsUtil.getLong(PROP_CLIENT_THREAD_COUNT, 6);

	public static final long PAGE_THREAD_COUNT = PropsUtil.getLong(PROP_PAGE_THREAD_COUNT, 2);

	public static final long VERIFY_THREAD_COUNT = PropsUtil.getLong(PROP_VERIFY_THREAD_COUNT, 2);

	public static final long INFO_THREAD_COUNT = PropsUtil.getLong(PROP_INFO_THREAD_COUNT, 30);

	public static final long IMAGE_THREAD_COUNT = PropsUtil.getLong(PROP_IMAGE_THREAD_COUNT, 300);

	public static final long OCR_THREAD_COUNT = PropsUtil.getLong(PROP_OCR_THREAD_COUNT, 300);

	public static final long COLLATE_THREAD_COUNT = PropsUtil.getLong(PROP_COLLATE_THREAD_COUNT, 20);

	public static final long PAGE_SIZE = 55;
}
