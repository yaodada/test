package com.example.test2.util;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDB extends SQLiteOpenHelper {

	private static String DB_NAME = "My_DB.db";
	private static int DB_VERSION = 2;
	private SQLiteDatabase db;

	public MyDB(Context context) {
		super(context,DB_NAME,null,DB_VERSION);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// 建立连接
	public SQLiteDatabase openConnection() {
		if (!db.isOpen()) {
			db = getWritableDatabase();
		}
		return db;
	}

	// 关闭连接
	public void closeConnection() {
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 创建表
	public boolean creatTable(String creatTablesql) {
		try {
			openConnection();
			db.execSQL(creatTablesql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;

	}

	// 添加操作

	public boolean save(String tableName, ContentValues values) {
		try {
			openConnection();
			db.insert(tableName, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;

	}

	// 更新操作
	public boolean update (String table,ContentValues values,String whereClause,
			String []whereArgs){
		
		try {
			openConnection();
			db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			closeConnection();
		}
				return true;
		
	}
	
	//删除
	public boolean delete (String table,String deleteSql,String obj[]){
		try {
			openConnection();
			db.delete(table, deleteSql, obj);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			closeConnection();
		}
		return true;
		
	}

	//查询
	public Cursor find(String findSql,String obj[]){
		try {
			openConnection();
			Cursor cursor = db.rawQuery(findSql, obj);
			return cursor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//判断表是否存在 
	public boolean isTableExits(String tabalename){
		try {
			openConnection();
			String str="select * from " +tabalename;
			db.rawQuery(str, null).close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			closeConnection();
		}
		return false;
		
	}
}
