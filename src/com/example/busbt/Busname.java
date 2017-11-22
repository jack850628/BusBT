package com.example.busbt;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.AlertDialog;
import android.app.Service;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Busname extends ActionBarActivity{
	private SQLiteDatabase mydb=null,mydb2=null,mydb3=null;	
	private final static String ID="_id";
	private final static String SQL_NAME="Bussql.db";//SQL名稱
	private final static String BUS_NAME="busname";//景點表名稱
	private final static String SQLNAME="sqlname";
	private final static String NAME="name";//資料項目:景點名稱
	private ArrayList<HashMap> tiem2;
	double gpsx;
	double gpsy;
	int pf,etag,SHOSW=0;
	String na,ph,id;
	boolean et=false;
	Listview lv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busname);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("選擇客運");
		sql(true);
	}
	public void sql(boolean lvst){
		TextView tv=(TextView)findViewById(R.id.textView1);
		ListView listview = (ListView) findViewById(R.id.listView1);
		tiem2 = new ArrayList<HashMap>();
		HashMap hh;
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		try
		{
			Cursor cur=mydb.query(BUS_NAME, new String[] {NAME}, null, null, null, null, null);
            int j=cur.getCount();
            if(j==0){
            	tv.setVisibility(View.VISIBLE);
            	listview.setVisibility(View.INVISIBLE);
            }else{
            	tv.setVisibility(View.INVISIBLE); 
            	listview.setVisibility(View.VISIBLE);
                for(int i=0;i<j;i++){
                	cur.moveToPosition(i);
                	hh = new HashMap();
                	hh.put("id", i);
             		hh.put("name", cur.getString(0));
             		hh.put("pf", 0);
                 	tiem2.add(hh);
                }
            }
			cur.close();
			mydb.close();
			if(lvst){
				listview.setOnItemClickListener(new ListViewClickListener());
				listview.setOnItemLongClickListener(new ListViewLongClickListener());
				lv=new Listview(this, tiem2);
				listview.setAdapter(lv);
			}else{
				lv.setListview(tiem2);
				lv.notifyDataSetChanged();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_SHORT).show();
		}
	    System.gc();
	}
	class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent,
				View view,
				int position,
				long id) {
			Intent intent = new Intent();
			if(et){
				etag=position;
				mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(BUS_NAME, new String[] {NAME}, null, null, null, null, null);
				cur.moveToPosition(pf);
				String n1=cur.getString(0);
				cur.moveToPosition(position);
				String n2=cur.getString(0);
				AlertDialog.Builder builder = new AlertDialog.Builder(Busname.this);
				builder.setTitle("確認");
		    	builder.setMessage("請問您是將\""+n1+"\"移動到\""+n2+"\"的?");
				cur.close();
				mydb.close();
		    	Gn2 de = new Gn2();
		    	builder.setNegativeButton("上方", de);
		    	builder.setPositiveButton("下方", de);
		    	builder.show();
			}else{
				intent =new Intent(Busname.this, Lsname.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("SELECTED_GREETING",String.valueOf(position));
    			intent.putExtras(bundle);
    			Busname.this.startActivityForResult(intent,SHOSW);
			}
		}
	}
	class ListViewLongClickListener implements OnItemLongClickListener{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			if(!et){
				Vibrator myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
				myVibrator.vibrate(100);
				AlertDialog.Builder builder = new AlertDialog.Builder(Busname.this);
				builder.setTitle("確認");
		    	builder.setMessage("請問您是要?");
		    	Gn de = new Gn();
		    	builder.setNegativeButton("刪除", de);
		    	builder.setNeutralButton("編輯", de);
		    	builder.setPositiveButton("移動", de);
		    	builder.show(); 
		    	pf=arg2;
		    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		    	Cursor cur=mydb.query(BUS_NAME, new String[] {ID}, null, null, null, null, null);
				cur.moveToPosition(pf);
				id=cur.getString(0);
				cur.close();
				mydb.close();
				return true;
			}
			return false;
		}
	}
	private class Gn implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
			mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
			Cursor cur=mydb.query(BUS_NAME, new String[] {NAME}, null, null, null, null, null);
			cur.moveToPosition(pf);
			AlertDialog.Builder builder = new AlertDialog.Builder(Busname.this);
			builder.setTitle("確認");
	    	builder.setMessage("您確定要刪除\""+cur.getString(0)+"\"?");
			cur.close();
			mydb.close();
	    	Gn3 de = new Gn3();
	    	builder.setNegativeButton("取消", de);
	    	builder.setPositiveButton("確定", de);
	    	builder.show();
		}else if (which == DialogInterface.BUTTON_NEUTRAL){
			Intent intent =new Intent(Busname.this, Sqlbusname.class);
			Bundle bundle = new Bundle();
			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
			intent.putExtras(bundle);
			Busname.this.startActivityForResult(intent,SHOSW);
	    }else if (which == DialogInterface.BUTTON_POSITIVE){
			et=true; 
			Toast.makeText(getApplicationContext(), "請點選你要移動至的位置", Toast.LENGTH_SHORT).show();
	}
		}
	}
	private class Gn2 implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
			supet();
		}else if (which == DialogInterface.BUTTON_POSITIVE){
			etag++;
			supet();
	}
		}
	}
	public void supet(){
    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
    	Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NAME,SQLNAME}, null, null, null, null, null);
    	cur.moveToPosition(pf);
    	String in=cur.getString(0);
   	    na=cur.getString(1);
   	    ph=cur.getString(2);
   	    String whereClet="_id=?";
	    String[] whereAet={in};
	    mydb.delete(BUS_NAME, whereClet, whereAet);
		cur.close();
		mydb.close();
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		cur=mydb.query(BUS_NAME, new String[] {ID,NAME,SQLNAME}, null, null, null, null, null);
        int j=cur.getCount();
	    int i=etag;
	    if(pf<etag){
	    	i--;
	    }
	    if(j==i){
	    	ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,na);
			cv.put(SQLNAME,ph);
		    mydb.insert(BUS_NAME, null, cv);
	    }else{
            ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,cur.getString(1));
		    cv.put(SQLNAME,cur.getString(2));
		    mydb.insert(BUS_NAME, null, cv);
        	String r;
            for(j=cur.getCount()-1;j>i;j--){
            	cur.moveToPosition(j);
            	r=cur.getString(0);
            	cur.moveToPosition(j-1);
 	            cv.put(NAME,cur.getString(1));
 			    cv.put(SQLNAME,cur.getString(2));
 			    String whereClause="_id=?";
 				String[] whereArgs={r};
 				mydb.update(BUS_NAME, cv, whereClause, whereArgs);
            }
            cur.moveToPosition(j);
        	r=cur.getString(0);
	            cv.put(NAME,na);
			    cv.put(SQLNAME,ph);
			    String whereClause="_id=?";
				String[] whereArgs={r};
				mydb.update(BUS_NAME, cv, whereClause, whereArgs);
	    }
		cur.close();
		mydb.close();
    	et=false;
	    sql(false);
	}
	private class Gn3 implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
	        dialog.dismiss();
		}else if (which == DialogInterface.BUTTON_POSITIVE){
			mydb3=openOrCreateDatabase("Hgsql.db", MODE_PRIVATE, null);
			mydb2=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
			mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
	    	Cursor cur=mydb.query(BUS_NAME, new String[] {SQLNAME}, null, null, null, null, null);
	    	cur.moveToPosition(pf);
	    	Cursor cur2=mydb2.query(cur.getString(0), new String[] {SQLNAME}, null, null, null, null, null);
	    	int j=cur2.getCount();
	    	for(int i=0;i<j;i++){
            	cur2.moveToPosition(i);
            	mydb3.execSQL("DROP TABLE "+cur2.getString(0));
            }
	    	cur2.close();
			mydb2.execSQL("DROP TABLE "+cur.getString(0));
			String whereClause="_id=?";
			String[] whereArgs={id};
			mydb.delete(BUS_NAME, whereClause, whereArgs);
			cur.close();
		    mydb.close();
			mydb2.close();
			mydb3.close();
		    sql(false);
	}
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.busname, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
    	int item_id = item.getItemId();
    	switch (item_id){
    		case R.id.sql:
    			Intent intent =new Intent(Busname.this, Gtsqlname.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
    			intent.putExtras(bundle);
    			Busname.this.startActivityForResult(intent,SHOSW);
    			break;
    		case android.R.id.home:
    			if(et){
    		    	Toast.makeText(getApplicationContext(), "已取消移動!", Toast.LENGTH_SHORT).show();
    		    	et=false;
    		    }else{
    			    finish();
    		    }
    			break;
    		default: return false;
    	}
    	return true;
    }
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == SHOSW) {
			if (resultCode == RESULT_OK) {
			    sql(false);
			}
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	if(et){
		    	Toast.makeText(getApplicationContext(), "已取消移動!", Toast.LENGTH_SHORT).show();
		    	et=false;
		    }else{
			    finish();
		    }
        }
        return false;
    }
}
