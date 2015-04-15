package com.example.test2.controller;

import com.example.test2.R;
import com.example.test2.util.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactsActivity extends Activity {
     private EditText nameEditText;
     private EditText departEditText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		setTitle("�����ϵ��");
		nameEditText=(EditText) findViewById(R.id.name);
		departEditText=(EditText) findViewById(R.id.depart);
	}
   public boolean onCreateOptionsMenu(Menu menu){
	    menu.add(Menu.NONE,1,Menu.NONE,"����");
	    menu.add(Menu.NONE,2,Menu.NONE,"����");
	   return super.onCreateOptionsMenu(menu);
	   
   }
   public boolean onOptionsItemSelected(MenuItem item){
	  switch (item.getItemId()) {
	case 1:
		if (!nameEditText.getText().toString().equals("")) {
			User user=new User();
			user.setName(nameEditText.getText().toString());
			user.setDepart(departEditText.getText().toString());
			ContactsTable ct= new ContactsTable(AddContactsActivity.this);
			if (ct.addDate(user)) {
				
			Toast.makeText(AddContactsActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
			System.out.println(user);
			}else {
				Toast.makeText(AddContactsActivity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(AddContactsActivity.this, "����������", Toast.LENGTH_SHORT).show();
		}
		break;
	case 2:
		finish();
		break;
	default:
		break;
	}
	   return super.onOptionsItemSelected(item);
	   
   }
}
