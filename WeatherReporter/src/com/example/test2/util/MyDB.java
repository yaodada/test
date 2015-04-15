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

	// ��������
	public SQLiteDatabase openConnection() {
		if (!db.isOpen()) {
			db = getWritableDatabase();
		}
		return db;
	}

	// �ر�����
	public void closeConnection() {
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ������
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

	// ��Ӳ���

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

	// ���²���
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
	
	//ɾ��
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

	//��ѯ
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
	
	//�жϱ��Ƿ���� 
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
