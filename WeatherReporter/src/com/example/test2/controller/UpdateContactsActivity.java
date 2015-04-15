package com.example.test2.controller;

import com.example.test2.R;
import com.example.test2.util.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactsActivity extends Activity {
         private EditText nameEditText;
         private EditText departEditText;
         private User user;
	@Override
	public  void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		setTitle("�޸���ϵ��");
		nameEditText=(EditText) findViewById(R.id.name);
		departEditText=(EditText) findViewById(R.id.depart);
		
		Bundle localBundle=getIntent().getExtras();
		int id=localBundle.getInt("user_ID");
		ContactsTable ct= new ContactsTable(this);
	    user=ct.getUserByID(id);
	    nameEditText.setText(user.getName());
	    departEditText.setText(user.getDepart());
	}
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE,1,Menu.NONE,"����");
		menu.add(Menu.NONE,2,Menu.NONE,"����");
		return super.onCreateOptionsMenu(menu);
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1://����
			if (!nameEditText.getText().toString().equals("")) {
				user.setName(nameEditText.getText().toString());
				user.setDepart(departEditText.getText().toString());
				ContactsTable ct= new ContactsTable(UpdateContactsActivity.this);
				if (ct.updateUser(user)) {
					Toast.makeText(UpdateContactsActivity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(UpdateContactsActivity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(UpdateContactsActivity.this, "���ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
		return false;
		
	}
	
    
}
