package com.yyx.common.mongo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yyx.utils.ReflectionUtils;
import com.yyx.utils.pageInfo.PageInfo;


public abstract class BaseMongoDaoImpl<T> implements BaseMongoDao<T> {

	private static String ID = "id";

	// private static final int DEFAULT_SKIP = 0;
	// private static final int DEFAULT_LIMIT = 200;

	/*
	 * spring mongodb　集成操作类　
	 */
	protected MongoTemplate mongoTemplate;

	@Override
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public void update(Query query, Update update) {
		mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	@Override
	public T save(T entity) {
		mongoTemplate.insert(entity);
		return entity;
	}

	@Override
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	@Override
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}

	@Override
	public PageInfo<T> findPage(PageInfo<T> page, Query query) {
		Long count = this.count(query);
		page.setTotal(count);
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		query.skip((pageNumber - 1) * pageSize).limit(pageSize);
		List<T> rows = this.find(query);
		page.setRows(rows);
		return page;
	}

	@Override
	public Long count(Query query) {
		return mongoTemplate.count(query, this.getEntityClass());
	}

	@Override
	public int deleteById(String id) {
		try {
			Query query = new Query(Criteria.where(ID).is(id));
			mongoTemplate.remove(query, this.getEntityClass());
		} catch (Exception e) {
			return 0;
		}

		return 1;
	}

	@Override
	public int deleteObject(Query query) {
		try {
			mongoTemplate.remove(query, this.getEntityClass());
		} catch (Exception e) {
			return 0;
		}

		return 1;
	}

	@Override
	public void updateByPrimaryKeySelective(String id, T t) {
		if (1 == deleteById(id)) {
			this.save(t);
		}
	}

	/*
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	private Class<T> getEntityClass() {
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/*
	 * 注入mongodbTemplate
	 * 
	 * @param mongoTemplate
	 */
	protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

}
