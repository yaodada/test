package com.example.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.Intents.Insert;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.controller.AddContactsActivity;
import com.example.test2.controller.ContactsMessageActivity;
import com.example.test2.controller.ContactsTable;
import com.example.test2.controller.UpdateContactsActivity;
import com.example.test2.util.User;

public class MainActivity extends Activity {

	private ListView listView;
	private BaseAdapter listViewAdapter;
	public List<User> list;
	private User users[];
	private int selectectItem = 0;
	private LayoutInflater layoutInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("通讯录");
		listView = (ListView) findViewById(R.id.listView1);
		layoutInflater=LayoutInflater.from(this);
		loadContacts();
		for (int i = 0; i < users.length; i++) {
			System.out.println(users[i]);
		}
		
		

		
	}

	private void loadContacts() {
		ContactsTable ct = new ContactsTable(this);
		users = ct.getAllUsers();
	
		
		

		list=new ArrayList<User>();
		for (int i = 0; i < users.length; i++) {
			list.add(new User(users[i].getName(), users[i].getDepart()));
		}
		
		listViewAdapter = new BaseAdapter() {
	       
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView=layoutInflater.inflate(R.layout.item, null);
				}
				TextView name=(TextView)convertView.findViewById(R.id.textView1);
				TextView depart=(TextView) convertView.findViewById(R.id.textView2);
				name.setText(list.get(position).getName());
				depart.setText(list.get(position).getDepart());
				if (position == selectectItem) {
				//	convertView.setBackgroundColor(Color.YELLOW);
				} else {
					convertView.setBackgroundColor(0);
				}
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return list.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
		};
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selectectItem = arg2;
				listViewAdapter.notifyDataSetChanged();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, 1,Menu.NONE, "添加");
		menu.add(Menu.NONE, 2,Menu.NONE, "编辑");
		menu.add(Menu.NONE, 3, Menu.NONE, "查看信息");
		menu.add(Menu.NONE, 4,Menu.NONE, "删除");
		menu.add(Menu.NONE, 5,Menu.NONE, "查询");
		menu.add(Menu.NONE, 6,Menu.NONE, "导入到手机电话簿");
		menu.add(Menu.NONE, 7,Menu.NONE, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1 :// 添加
			
			Intent intent = new Intent(MainActivity.this,
					AddContactsActivity.class);
			startActivity(intent);
			break;
		case 2 :// 编辑
			if (users[selectectItem].getId_DB() > 0) {
				intent = new Intent(MainActivity.this,
						UpdateContactsActivity.class);
				intent.putExtra("user_ID", users[selectectItem].getId_DB());
				startActivity(intent);
			} else {
				Toast.makeText(this, "无结果记录，无法操作", Toast.LENGTH_LONG).show();
			}
			break;
		case 3 :// 查看信息
			if (users[selectectItem].getId_DB() > 0) {
				intent = new Intent(MainActivity.this,
						ContactsMessageActivity.class);
				intent.putExtra("user_ID", users[selectectItem].getId_DB());
				startActivity(intent);
			} else {
				Toast.makeText(this, "无结果记录，无法操作", Toast.LENGTH_LONG).show();
			}
			break;
		case 4 :// 删除
			if (users[selectectItem].getId_DB() > 0) {
				delete();
			}
			break;
		case 5 :// 查询
			new FindDialog(this).show();
			break;
		case 6 :// 导入到电话簿
			if (users[selectectItem].getId_DB() > 0) {
				importPhone(users[selectectItem].getName());
				Toast.makeText(this, "成功导入", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "导入失败", Toast.LENGTH_LONG).show();
			}
			break;
		case 7 :// 退出
			finish();
			break;

		default:
			break;

		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	protected void onResume() {
		super.onResume();
		ContactsTable ct = new ContactsTable(this);
		users = ct.getAllUsers();
		listViewAdapter.notifyDataSetChanged();

	}

	// 查询
	public class FindDialog extends Dialog {

		public FindDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.find);
			setTitle("联系人查询");
			Button find = (Button) findViewById(R.id.button1);
			Button cancle = (Button) findViewById(R.id.button2);
			find.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					EditText value = (EditText) findViewById(R.id.findtx);
					ContactsTable ct = new ContactsTable(MainActivity.this);
					users = ct.findUserByKey(value.getText().toString());
					for (int i = 0; i < users.length; i++) {
						System.out.println(users[i].getName());
					}
					listViewAdapter.notifyDataSetChanged();
					selectectItem = 0;
					dismiss();
				}
			});

			cancle.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});

		}
	}

	private void importPhone(String name) {
	    Uri phoneURL=android.provider.ContactsContract.Data.CONTENT_URI;
	    ContentValues values=new ContentValues();
	    Uri rawContactUri = this.getContentResolver().insert(RawContacts.CONTENT_URI, values);
	    values.clear();
	    long rawContactId=ContentUris.parseId(rawContactUri);
	    values.put(Data.RAW_CONTACT_ID, rawContactId);
	    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
	    values.put(StructuredName.GIVEN_NAME, name);
	    this.getContentResolver().insert(phoneURL, values);
	}

	private void delete() {
		// TODO Auto-generated method stub
		Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("系统消息");
		alert.setMessage("是否删除联系人");
		alert.setPositiveButton("是", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				ContactsTable ct = new ContactsTable(MainActivity.this);
				if (ct.deleteByUser(users[selectectItem])) {
					users = ct.getAllUsers();
					listViewAdapter.notifyDataSetChanged();
					selectectItem = 0;
					Toast.makeText(MainActivity.this, "删除成功",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(MainActivity.this, "删除失败",
							Toast.LENGTH_SHORT).show();

				}

			}
		});
		alert.setNegativeButton("否", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
                
			}
		});
          alert.show();
	}

}
