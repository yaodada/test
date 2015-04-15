package com.example.test2.controller;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.util.Log;
import android.webkit.WebChromeClient.CustomViewCallback;

import com.example.test2.util.MyDB;
import com.example.test2.util.User;

//联系人表操作
public class ContactsTable {
	private final static String TABLENAME = "contactsTable"; // 表名
	private MyDB db;

	public ContactsTable(Context context) {
		db = new MyDB(context);
		if (!db.isTableExits(TABLENAME)) {

			String createTableSql = "create table if not exists"
					+  TABLENAME 
				    + (" id_DB integer " + "primary key AUTOINCREMENT,"
				   + User.NAME +  " VARCHAR," + User.DEPART  + " VARCHAR,");
			db.creatTable(createTableSql);

		}
	}

	// 添加数据到联系人表
	public boolean addDate(User user) {

		ContentValues values = new ContentValues();
		values.put(User.NAME, user.getName());
		values.put(User.DEPART, user.getDepart());
		return db.save(TABLENAME, values);
		

	}

	public User[] getAllUsers() {
		Vector<User> v = new Vector<User>();
		Cursor cursor = null;

		try {
			cursor = db.find("select * from " + TABLENAME, null);
			while (cursor.moveToNext()) {
				User temp = new User();
				temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
				temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
				temp.setDepart(cursor.getString(cursor
						.getColumnIndex(User.DEPART)));

				v.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.closeConnection();
		}
		if (v.size() > 0) {
			return v.toArray(new User[] {});

		} else {
			User[] users = new User[1];
			User user = new User();
			user.setName("无结果");
			users[0] = user;
			return users;
		}

	}

	public User getUserByID(int id) {
		Cursor cursor = null;
		try {
			cursor = db.find(
					"select * from " + TABLENAME + "where" + "id_DB=?",
					new String[] { id + "" });
			User temp = new User();
			cursor.moveToNext();
			temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
			temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
			temp.setDepart(cursor.getString(cursor.getColumnIndex(User.DEPART)));

			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.closeConnection();
		}
		return null;

	}

	public User[] findUserByKey(String key) {
		Vector<User> v = new Vector<User>();
		Cursor cursor = null;

		try {
            cursor=db.find("select * from" + TABLENAME+"where"
            		+User.NAME+"like '%"+key+"%'"+
            		"or" + User.DEPART+"like '%" +key+"%'",null);
            while (cursor.moveToNext()) {
				User temp = new User();
				temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
				temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
				temp.setDepart(cursor.getString(cursor.getColumnIndex(User.DEPART)));
				v.add(temp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor!=null) {
				cursor.close();
			}
			db.closeConnection();
		}
		if(v.size()>0){
			return v.toArray(new User[] {});
		}else {
			User[] users =new  User[1];
			User user = new User();
			user.setName("无结果");
			users[0]=user;
			return users;	
		}
	}
	
	public boolean updateUser(User user){
		ContentValues values = new ContentValues();
		values.put(User.NAME, user.getName());
		values.put(User.DEPART, user.getDepart());
		
		return db.update(TABLENAME, null, " id_DB=? ", new String[]{
				user.getId_DB()+""
		});
	}
	
	public boolean deleteByUser(User user){
		return db.delete(TABLENAME, " id_DB=?", new String[]{ user.getId_DB()+""});
	}
}
