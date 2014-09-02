/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.sylla.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.sylla.Constants;

class HibernateUtil {

	private static Log _log = LogFactory.getLog(HibernateUtil.class);

	public static final String[] SPRING_HIBERNATE_DATA_SOURCES = PropsUtil
			.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);

	public static final String[] SPRING_HIBERNATE_SESSION_FACTORIES = PropsUtil
			.getArray(Constants.PROP_SPRING_HIBERNATE_SESSION_FACTORIES);

	public static void closeSession(Session session) {
		try {
			if (session != null) {
				// Let Spring manage sessions
				// session.close();
			}
		} catch (HibernateException he) {

		}
	}

	public static Connection getConnection(String dataSourceName)
			throws SQLException, NamingException {
		Connection con = getDataSource(dataSourceName).getConnection();
		if (con == null) {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) JNDIUtil.lookup(ctx, dataSourceName);
			con = ds.getConnection();
		}
		return con;
	}

	public static DataSource getDataSource(String dataSourceName) {
		return (DataSource) SpringUtil.getBean(dataSourceName);
	}

	public static Dialect getDialect(String sessionFactoryName) {
		return getSessionFactory(sessionFactoryName).getDialect();
	}

	public static SessionFactoryImplementor getSessionFactory(
			String sessionFactoryName) {

		LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean) SpringUtil
				.getBean(sessionFactoryName);
		return (SessionFactoryImplementor) lsfb.getObject();
	}

	public static Session openSession(String sessionFactoryName)
			throws HibernateException {

		SessionFactoryImplementor sessionFactory = getSessionFactory(sessionFactoryName);

		return openSession(sessionFactory);
	}

	public static Session openSession(SessionFactory sessionFactory)
			throws HibernateException {

		// Let Spring manage sessions

		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public static void cleanUp(Connection con) {
		cleanUp(con, null, null);
	}

	public static void cleanUp(Connection con, Statement s) {
		cleanUp(con, s, null);
	}

	public static void cleanUp(Connection con, Statement s, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}

		try {
			if (s != null) {
				s.close();
			}
		} catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}
	}

}