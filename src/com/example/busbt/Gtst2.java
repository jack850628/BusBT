package com.example.busbt;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Gtst2 extends ActionBarActivity{
	LocationManager lm;								
	LocationListener ll;
	private SQLiteDatabase mydb=null;
	private final static String SQL_NAME="st.db";//SQL名稱
	private final static String NAME="name";//資料項目:景點名稱
	private final static String ST2="st2";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtst2);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("新增文字");
		Intent intent = this.getIntent();
		setResult(RESULT_OK, intent);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
	}
	class ButtonClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		cv.put(NAME,editText1.getText().toString());
	    mydb.insert(ST2, null, cv);
	    mydb.close();
        finish();
		}
	}
	class Button2ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
        finish();
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id){
		case android.R.id.home:
			finish();
			break;
		default: return false;
		}
		return true;
	}
}