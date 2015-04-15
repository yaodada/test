package com.example.test2.controller;

import com.example.test2.R;
import com.example.test2.util.User;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactsMessageActivity extends Activity {
          private TextView nameTextView;
          private TextView deparTextView;
          private User user;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		nameTextView=(TextView) findViewById(R.id.textView1);
		deparTextView=(TextView) findViewById(R.id.textView2);
		
		Bundle localBundle=getIntent().getExtras();
		int id=localBundle.getInt("user_ID");
		ContactsTable ct= new ContactsTable(this);
		user=ct.getUserByID(id);
		nameTextView.setText("姓名："+user.getName());
		deparTextView.setText("单位"+user.getDepart());
	}

	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE,1,Menu.NONE,"返回");
		return true;
		
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case 1:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
}
