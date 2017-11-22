package com.example.busbt;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Sqlbusname extends ActionBarActivity{
	private SQLiteDatabase mydb=null;	
	private final static String ID="_id";
	private final static String gte_NAME="bussql.pref";
	private final static String SQL_NAME="Bussql.db";//SQL名稱
	private final static String BUS_NAME="busname";//景點表名稱
	private final static String NAME="name";//資料項目:景點名稱
	String pf;
	int i;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlbusname);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("編輯客運");
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();	//取得Bundle
		pf=bundle.getString("SELECTED_GREETING");
		i=Integer.valueOf(pf);
		setResult(RESULT_OK, intent);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NAME}, null, null, null, null, null);
		cur.moveToPosition(i);
		pf=cur.getString(0);
		editText1.setText(cur.getString(1));
		cur.close();
		mydb.close();
		
	}
	class ButtonClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		if("".equals(editText1.getText().toString().trim())){
			int i=spf.getInt("sa", 1);
			cv.put(NAME,"未命名的客運"+i);
			SharedPreferences.Editor ed=spf.edit();
		    ed.putInt("sa", i+1);
        	ed.commit();
		}else{
		    cv.put(NAME,editText1.getText().toString());
		}
		String whereClause="_id=?";
		String[] whereArgs={pf};
		mydb.update(BUS_NAME, cv, whereClause, whereArgs);
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