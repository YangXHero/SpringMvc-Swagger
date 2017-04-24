package com.yyx.utils;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.yyx.utils.file.MD5Filter;
import com.yyx.utils.spring.SpringUtils;

public class MongoUtils {

	private static MongoTemplate mongoTemplate;

	static {
		mongoTemplate = SpringUtils.getBean("mongoTemplate", MongoTemplate.class);
	}

	public static String upload(File file) {
		DB db = mongoTemplate.getDb();
		GridFS gridFS = new GridFS(db);

		/*
		 * 2、上传文件
		 */
		// 创建测试文件，mongo 默认存在该文件
		// File file = new
		// File("F://工作内容//项目//极速蜗牛//手机钱包//钱包文档//sp2p//UI//国安生活宝app//切图//C转账@3x.png");
		// FileInputStream fileInputStream;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);

			// 创建gridFS文件数据流
			GridFSInputFile createFile = gridFS.createFile(fileInputStream);
			// createFile.getMD5()

//			String md5 = createFile.getMD5();
//			System.out.println(md5);
			 String md5 = MD5Filter.getMd5ByFile(file);
			GridFSDBFile findOne = gridFS.findOne(new BasicDBObject("md5", md5));
			if (null == findOne) {
				// 设置文件属性
				// createFile.put("filename", fileName);
				createFile.put("filename", file.getName());
				// createFile.put("contentType", new
				// MimetypesFileTypeMap().getContentType(f));
				createFile.save();
				findOne = gridFS.findOne(new BasicDBObject("md5", md5));
				System.out.println(createFile.getMD5());
				System.out.println(file.getName());
				return file.getName();
			} else {
				return findOne.getFilename();
			}

			// MultipartFile file1;
			//
			// file1.getInputStream()

			/*
			 * 3、根据id查询上传文件
			 */

//			System.out.println(findOne.getId());

			/*
			 * 4、查询所有文件列表 DBCursor 数据库游标
			 */
//			DBCursor fileList = gridFS.getFileList();
//			while (fileList.hasNext()) {
//				System.out.println(fileList.next());
//			}

			/*
			 * 5、 删除文件
			 */
			// gridFS.remove(new BasicDBObject("_id",createFile.getId()));
			// client.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}

}
