package com.example.busbt;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class Gtsqlname extends ActionBarActivity{
	LocationManager lm;								
	LocationListener ll;
	private SQLiteDatabase mydb=null;	
	private final static String ID="_id";
	private final static String gte_NAME="bussql.pref";
	private final static String SQL_NAME="Bussql.db";//SQL名稱
	private final static String BUS_NAME="busname";//景點表名稱
	private final static String NAME="name";
	private final static String NAMEA="namea";
	private final static String NAMEB="nameb";//資料項目:景點名稱
	private final static String MEDIA="media";//資料項目:景點名稱
	private final static String MEDIA2A="media2a";
	private final static String MEDIA2B="media2b";
	private final static String MEDIA3="media3";
	private final static String ST="st";
	private final static String ST2="st2";
	private final static String ST3="st3";
	private final static String ST4="st4";
	private final static String SQLNAME="sqlname";
	private final static String NO="no";
	private final static String START="start";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtsqlname);
		setTitle("建立客運");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		int i=0;
		if("".equals(editText1.getText().toString().trim())){
			i=spf.getInt("sa", 1);
			cv.put(NAME,"未命名的客運"+i);
			SharedPreferences.Editor ed=spf.edit();
		    ed.putInt("sa", i+1);
        	ed.commit();
		}else{
		    cv.put(NAME,editText1.getText().toString());
		}
		i=spf.getInt("sqlna", 0);
		String na="bus"+i;
	    cv.put(SQLNAME,na);
	    SharedPreferences.Editor ed=spf.edit();
	    ed.putInt("sqlna", i+1);
    	ed.commit();
	    mydb.insert(BUS_NAME, null, cv);
	    mydb.close();
	    String CREATE_TABLE="CREATE TABLE "+na+" ("+ID+" INTEGER PRIMARY KEY,"+NO+" TEXT,"+NAMEA+" TEXT,"+NAMEB+" TEXT,"+START+" TEXT,"+MEDIA+" TEXT,"+MEDIA2A+" TEXT,"+MEDIA2B+" TEXT,"+MEDIA3+" TEXT,"+ST+" TEXT,"+ST2+" TEXT,"+ST3+" TEXT,"+ST4+" TEXT,"+SQLNAME+" TEXT)";
		mydb=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
		try{
			Cursor  cur=mydb.query(na, new String[] {ID}, null, null, null, null, null);
			cur.close();
			}

		catch(Exception e){
	      mydb.execSQL(CREATE_TABLE);
	      }
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