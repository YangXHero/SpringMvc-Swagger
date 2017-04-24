package com.yyx.utils.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yyx.utils.Dialect;
import com.yyx.utils.StringUtil;
import com.yyx.utils.mybatis.entity.RowBoundsImpl;


@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	public static final String CONFIGURATION = "configuration";
	private static Dialect dialect = null;
	private static final String ROW_BOUNDS = "rowBounds";
	private static final String BOUND_SQL = "boundSql";
	private static final String DIALECT = "dialect";
	private static final String SQL = "sql";
	private static final String OFFSET = "offset";
	private static final String LIMIT = "limit";
	public static final String DELEGATE = "delegate";
	private static final int CONNECTION_INDEX = 0;

	// 日志对象
	protected static Logger log = LoggerFactory.getLogger(PaginationInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
	 * .Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}

		PreparedStatementHandler preparedStatHandler = (PreparedStatementHandler) FieldUtils.readField(statementHandler, DELEGATE, true);
		final Object[] queryArgs = invocation.getArgs();
		Connection connection = (Connection) queryArgs[CONNECTION_INDEX];

		// StatementHandler statementHandler = (StatementHandler)
		// invocation.getTarget();

		BoundSql boundSql = (BoundSql) FieldUtils.readField(preparedStatHandler, BOUND_SQL, true);

		Object object = metaStatementHandler.getValue("delegate.rowBounds");

		String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

		//
	

		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");

//		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");

		if (object instanceof RowBoundsImpl) {

			RowBoundsImpl rowBoundsImpl = (RowBoundsImpl) object;

			listRoleSqlAppend(sql, metaStatementHandler, rowBoundsImpl);

			CountHelper.getCount(rowBoundsImpl.getCountSql(), preparedStatHandler, configuration, boundSql, connection);

		}

		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
		} catch (Exception e) {
			// ignore
		}
		if (databaseType == null) {
			throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : "
					+ configuration.getVariables().getProperty("dialect"));
		}
		Dialect dialect = null;
		switch (databaseType) {
		case ORACLE:
			dialect = new OracleDialect();
			break;
		case MYSQL:
			dialect = new MySQLDialect();
			break;

		}
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		// if (log.isDebugEnabled()) {
		// BoundSql boundSql = statementHandler.getBoundSql();
		// log.debug("生成分页SQL : " + boundSql.getSql());
		// }
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * 拼接list count sql
	 * 
	 * @param sql
	 * @param metaStatementHandler
	 * @param rowBoundsImpl
	 */
	public void listCountRoleSqlAppend(String sql, MetaObject metaStatementHandler, RowBoundsImpl rowBoundsImpl) {

	}

	/*
	 * 拼 数据权限 list sql
	 * 
	 * @param sql
	 * @param metaStatementHandler
	 * @param rowBoundsImpl
	 */
	public void listRoleSqlAppend(String sql, MetaObject metaStatementHandler, RowBoundsImpl rowBoundsImpl) {
		StringBuffer sqlCount = new StringBuffer(rowBoundsImpl.getCountSql());

		if (sql.indexOf("where") == -1) {
			String sqlStr = null;
			String sqlOrder = null;
			if (sql.indexOf("order by") != -1) {
				sqlOrder = sql.substring(sql.lastIndexOf("order by"), sql.length());
				sqlStr = sql.substring(0, sql.lastIndexOf("order by"));
			} else {
				sqlStr = sql;
			}

			StringBuffer sqlBuffer = new StringBuffer(sqlStr);
			sqlBuffer.append(" left join sys_data_role_authority auth on auth.data_id = ").append(rowBoundsImpl.getLeftJoinFk()).append(" ")
					.append(" where 1=1 ");

			sqlCount.append(" left join sys_data_role_authority auth on auth.data_id = ").append(rowBoundsImpl.getLeftJoinFk()).append(" ")
					.append(" where 1=1 ");
			List<Map<String, Object>> roleList = null;
//			List<Map<String, Object>> roleList = UserUtil.getModelViewRoleByModelId(rowBoundsImpl.getModelId());

			if (roleList != null && roleList.isEmpty() == false) {
				sqlBuffer.append(" and (");
				sqlCount.append(" and (");
				int len = roleList.size();
				for (int i = 0; i < len; i++) {
					Map<String, Object> roleEntity = roleList.get(i);
					sqlBuffer.append("INSTR(").append("auth.role_id").append(",'"+roleEntity.get("roleId")+"') >0 ");
					sqlCount.append("INSTR(").append("auth.role_id").append(",'"+roleEntity.get("roleId")+"') >0 ");
					// sqlBuffer.append("INSTR('").append(roleEntity.get("roleId")).append("',auth.role_id) >0 ");
					if (len - 1 != i) {
						sqlBuffer.append(" or ");
						sqlCount.append(" or ");
					}
				}
				sqlBuffer.append(" ) ");
				sqlCount.append(" ) ");
			} else {
				//==================================add by yaofeng start 数据角色为空，则应该传入空的id进行查询
				sqlBuffer.append(" and (");
				sqlCount.append(" and (");
				sqlBuffer.append("INSTR(").append("auth.role_id").append(",'null') >0 ");
				sqlCount.append("INSTR(").append("auth.role_id").append(",'null') >0 ");
				sqlBuffer.append(" ) ");
				sqlCount.append(" ) ");
				//==================================add by yaofeng end 数据角色为空，则应该传入空的id进行查询
			}
			if (StringUtil.isNotEmpty(sqlOrder))
				sqlBuffer.append(sqlOrder);

			metaStatementHandler.setValue("delegate.boundSql.sql", sqlBuffer.toString());

		} else {
			String sqlStr = sql.substring(0, sql.indexOf("where"));
			String sqlWhere = sql.substring(sql.indexOf("where"), sql.length());
			String sqlOrder = null;

			if (sqlWhere.indexOf("order by") != -1) {
				sqlOrder = sql.substring(sql.lastIndexOf("order by"), sql.length());
				sqlWhere = sqlWhere.substring(0, sqlWhere.indexOf("order by"));
			}

			StringBuffer sqlBuffer = new StringBuffer(sqlStr);
			sqlBuffer.append("left join sys_data_role_authority auth on auth.data_id = ").append(rowBoundsImpl.getLeftJoinFk()).append(" ").append(sqlWhere);
			sqlCount.append("left join sys_data_role_authority auth on auth.data_id = ").append(rowBoundsImpl.getLeftJoinFk()).append(" ").append(sqlWhere);
			List<Map<String, Object>> roleList = null;
//			List<Map<String, Object>> roleList = UserUtil.getModelRoleByModelId(rowBoundsImpl.getModelId());

			if (roleList != null && roleList.isEmpty() == false) {
				sqlBuffer.append(" and (");
				sqlCount.append(" and (");
				int len = roleList.size();
				for (int i = 0; i < len; i++) {
					Map<String, Object> roleEntity = roleList.get(i);
					sqlBuffer.append("INSTR(").append("auth.role_id").append(",'"+roleEntity.get("roleId")+"') >0 ");
					sqlCount.append("INSTR(").append("auth.role_id").append(",'"+roleEntity.get("roleId")+"') >0 ");
					// sqlBuffer.append("INSTR('").append(roleEntity.get("roleId")).append("',auth.role_id) >0 ");
					if (len - 1 != i) {
						sqlBuffer.append(" or ");
						sqlCount.append(" or ");
					}
				}
				sqlBuffer.append(" ) ");
				sqlCount.append(" ) ");
			} else {
				//==================================add by yaofeng start 数据角色为空，则应该传入空的id进行查询
				sqlBuffer.append(" and (");
				sqlCount.append(" and (");
				sqlBuffer.append("INSTR(").append("auth.role_id").append(",'null') >0 ");
				sqlCount.append("INSTR(").append("auth.role_id").append(",'null') >0 ");
				sqlBuffer.append(" ) ");
				sqlCount.append(" ) ");
				//==================================add by yaofeng end 数据角色为空，则应该传入空的id进行查询
			}
			if (StringUtil.isNotEmpty(sqlOrder))
				sqlBuffer.append(sqlOrder);

			metaStatementHandler.setValue("delegate.boundSql.sql", sqlBuffer.toString());
		}
		rowBoundsImpl.setCountSql(sqlCount.toString());
	}

}