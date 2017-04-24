package com.yyx.utils.mybatis.entity;

import org.apache.ibatis.session.RowBounds;

public class RowBoundsImpl extends RowBounds {
	// 1正常，2list权限，3list count 权限，4单个select 权限
//	private int type = 1;
	private String modelId;
	public static final int NO_ROW_OFFSET = 0;
	public static final int NO_ROW_LIMIT = 2147483647;
	public static final RowBounds DEFAULT = new RowBounds();
	private int offset = 0;
	private int limit = 10;
	private String leftJoinFk;
	private String countSql;

	public RowBoundsImpl(int offset, int limit, String modelId, String leftJoinFk, String countSql) {
		this.limit = limit;
		this.offset = offset;
//		this.type = type;
		this.modelId = modelId;
		this.leftJoinFk = leftJoinFk;
		this.countSql = countSql;
	}

	

	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public RowBoundsImpl() {
		this.offset = 0;
		this.limit = 2147483647;
	}

	public RowBoundsImpl(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int getOffset() {
		return this.offset;
	}

	public int getLimit() {
		return this.limit;
	}

	public String getLeftJoinFk() {
		return leftJoinFk;
	}

	public void setLeftJoinFk(String leftJoinFk) {
		this.leftJoinFk = leftJoinFk;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

}
