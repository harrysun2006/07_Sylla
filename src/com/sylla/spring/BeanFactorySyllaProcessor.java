package com.sylla.spring;

import java.sql.Connection;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.sylla.Constants;
import com.sylla.util.CommonUtil;
import com.sylla.util.JNDIUtil;
import com.sylla.util.PropsUtil;
import com.sylla.util.SpringUtil;

public class BeanFactorySyllaProcessor implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		try {
			hookDataSources();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private void hookDataSources() {
		String[] dataSources = PropsUtil
				.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		Connection conn = null;
		String dsName, fregex, kregex;
		Hashtable props;
		for (int i = 0; i < dataSources.length; i++) {
			dsName = dataSources[i].trim();
			try {
				ds = (DataSource) SpringUtil.getBean(dsName);
				conn = ds.getConnection();
			} catch (Exception e) {
				fregex = dsName + "\\..*";
				kregex = dsName + "\\.(.*)";
				props = CommonUtil.getProperties(PropsUtil.getProperties(), fregex,
						kregex);
				JNDIUtil.addResource(props);
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (Exception exp) {
					}
			}
		}
	}
}
