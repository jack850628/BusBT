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
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Lsname extends ActionBarActivity{
	private SQLiteDatabase mydb=null,mydb2=null;	
	LocationManager lm;			
	private final static String ID="_id";
	private final static String SQL_NAME="Lssql.db";//SQL名稱
	String BUS_NAME;//景點表名稱
	private final static String NAME="name";
	private final static String NAMEA="namea";
	private final static String NAMEB="nameb";//資料項目:景點名稱
	private final static String MEDIA="media";
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
	private ArrayList<HashMap> tiem2;
	int pf,etag,SHOSW=0;
	String ph,id,no,start,na2;
	String[] na=new String[2],me=new String[4],st=new String[4];
	boolean et=false;
	Listview lv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lsname);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("選擇路線");
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		mydb=openOrCreateDatabase("Bussql.db", MODE_PRIVATE, null);
		Cursor cur=mydb.query("busname", new String[] {NAME,SQLNAME}, null, null, null, null, null);
		cur.moveToPosition(Integer.valueOf(bundle.getString("SELECTED_GREETING")));
		na2=cur.getString(0);
		BUS_NAME=cur.getString(1);
		cur.close();
		mydb.close();
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
			Cursor cur=mydb.query(BUS_NAME, new String[] {NO,NAMEA,NAMEB}, null, null, null, null, null);
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
             		hh.put("name", cur.getString(0)+" "+cur.getString(1)+"-"+cur.getString(2));
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
				Cursor cur=mydb.query(BUS_NAME, new String[] {NO,NAMEA,NAMEB}, null, null, null, null, null);
				cur.moveToPosition(pf);
				String n1=cur.getString(0)+" "+cur.getString(1)+"-"+cur.getString(2);
				cur.moveToPosition(position);
				String n2=cur.getString(0)+" "+cur.getString(1)+"-"+cur.getString(2);
				AlertDialog.Builder builder = new AlertDialog.Builder(Lsname.this);
				builder.setTitle("確認");
		    	builder.setMessage("請問您是將\""+n1+"\"移動到\""+n2+"\"的?");
				cur.close();
				mydb.close();
		    	Gn2 de = new Gn2();
		    	builder.setNegativeButton("上方", de);
		    	builder.setPositiveButton("下方", de);
		    	builder.show();
			}else{
				intent =new Intent(Lsname.this, Hgname.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("SELECTED_GREETING",BUS_NAME);
    			bundle.putString("SELECTED_GREETING2",String.valueOf(position));
    			bundle.putString("SELECTED_GREETING3",na2);
    			intent.putExtras(bundle);
    			Lsname.this.startActivityForResult(intent,SHOSW);
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
				AlertDialog.Builder builder = new AlertDialog.Builder(Lsname.this);
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
			Cursor cur=mydb.query(BUS_NAME, new String[] {NO,NAMEA,NAMEB}, null, null, null, null, null);
			cur.moveToPosition(pf);
			AlertDialog.Builder builder = new AlertDialog.Builder(Lsname.this);
			builder.setTitle("確認");
	    	builder.setMessage("您確定要刪除\""+cur.getString(0)+" "+cur.getString(1)+"-"+cur.getString(2)+"\"?");
			cur.close();
			mydb.close();
	    	Gn3 de = new Gn3();
	    	builder.setNegativeButton("取消", de);
	    	builder.setPositiveButton("確定", de);
	    	builder.show();
		}else if (which == DialogInterface.BUTTON_NEUTRAL){
			Intent intent =new Intent(Lsname.this, Sqllsname.class);
			Bundle bundle = new Bundle();
			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
			bundle.putString("SELECTED_GREETING2",BUS_NAME);
			intent.putExtras(bundle);
			Lsname.this.startActivityForResult(intent,SHOSW);
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
    	Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NO,NAMEA,NAMEB,START,MEDIA,MEDIA2A,MEDIA2B,MEDIA3,ST,ST2,ST3,ST4,SQLNAME}, null, null, null, null, null);
    	cur.moveToPosition(pf);
    	String in=cur.getString(0);
    	no=cur.getString(1);
   	    na[0]=cur.getString(2);
   	    na[1]=cur.getString(3);
   	    start=cur.getString(4);
   	    me[0]=cur.getString(5);
   	    me[1]=cur.getString(6);
     	me[2]=cur.getString(7);
     	me[3]=cur.getString(8);
		st[0]=cur.getString(9);
		st[1]=cur.getString(10);
		st[2]=cur.getString(11);
		st[3]=cur.getString(12);
   	    ph=cur.getString(13);
   	    String whereClet="_id=?";
	    String[] whereAet={in};
	    mydb.delete(BUS_NAME, whereClet, whereAet);
		cur.close();
		mydb.close();
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		cur=mydb.query(BUS_NAME, new String[] {ID,NO,NAMEA,NAMEB,START,MEDIA,MEDIA2A,MEDIA2B,MEDIA3,ST,ST2,ST3,ST4,SQLNAME}, null, null, null, null, null);
        int j=cur.getCount();
	    int i=etag;
	    if(pf<etag){
	    	i--;
	    }
	    if(j==i){
	    	ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NO,no);
            cv.put(NAMEA,na[0]);
            cv.put(NAMEB,na[1]);
            cv.put(START,start);
			cv.put(MEDIA,me[0]);
			cv.put(MEDIA2A,me[1]);
			cv.put(MEDIA2B,me[2]);
			cv.put(MEDIA3,me[3]);
			cv.put(ST,st[0]);
			cv.put(ST2,st[1]);
			cv.put(ST3,st[2]);
			cv.put(ST4,st[3]);
			cv.put(SQLNAME,ph);
		    mydb.insert(BUS_NAME, null, cv);
	    }else{
            ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NO,cur.getString(1));
            cv.put(NAMEA,cur.getString(2));
            cv.put(NAMEB,cur.getString(3));
            cv.put(START,cur.getString(4));
            cv.put(MEDIA,cur.getString(5));
            cv.put(MEDIA2A,cur.getString(6));
            cv.put(MEDIA2B,cur.getString(7));
            cv.put(MEDIA3,cur.getString(8));
            cv.put(ST,cur.getString(9));
            cv.put(ST2,cur.getString(10));
            cv.put(ST3,cur.getString(11));
            cv.put(ST4,cur.getString(12));
		    cv.put(SQLNAME,cur.getString(13));
		    mydb.insert(BUS_NAME, null, cv);
        	String r;
            for(j=cur.getCount()-1;j>i;j--){
            	cur.moveToPosition(j);
            	r=cur.getString(0);
            	cur.moveToPosition(j-1);
            	cv.put(NO,cur.getString(1));
                cv.put(NAMEA,cur.getString(2));
                cv.put(NAMEB,cur.getString(3));
                cv.put(START,cur.getString(4));
                cv.put(MEDIA,cur.getString(5));
                cv.put(MEDIA2A,cur.getString(6));
                cv.put(MEDIA2B,cur.getString(7));
                cv.put(MEDIA3,cur.getString(8));
                cv.put(ST,cur.getString(9));
                cv.put(ST2,cur.getString(10));
                cv.put(ST3,cur.getString(11));
                cv.put(ST4,cur.getString(12));
    		    cv.put(SQLNAME,cur.getString(13));
 			    String whereClause="_id=?";
 				String[] whereArgs={r};
 				mydb.update(BUS_NAME, cv, whereClause, whereArgs);
            }
            cur.moveToPosition(j);
        	r=cur.getString(0);
        	cv.put(NO,no);
            cv.put(NAMEA,na[0]);
            cv.put(NAMEB,na[1]);
            cv.put(START,start);
			cv.put(MEDIA,me[0]);
			cv.put(MEDIA2A,me[1]);
			cv.put(MEDIA2B,me[2]);
			cv.put(MEDIA3,me[3]);
			cv.put(ST,st[0]);
			cv.put(ST2,st[1]);
			cv.put(ST3,st[2]);
			cv.put(ST4,st[3]);
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
			mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
			mydb2=openOrCreateDatabase("Hgsql.db", MODE_PRIVATE, null);
			Cursor cur=mydb.query(BUS_NAME, new String[] {SQLNAME}, null, null, null, null, null);
	    	cur.moveToPosition(pf);
	    	mydb2.execSQL("DROP TABLE "+cur.getString(0));
	    	cur.close();
		    String whereClause="_id=?";
			String[] whereArgs={id};
			mydb.delete(BUS_NAME, whereClause, whereArgs);
		    mydb.close();
		    sql(false);
	}
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.lsmenu, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
    	int item_id = item.getItemId();
    	switch (item_id){
    		case R.id.sql:
    			Intent intent =new Intent(Lsname.this, Gtls.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("SELECTED_GREETING",BUS_NAME);
    			intent.putExtras(bundle);
    			Lsname.this.startActivityForResult(intent,SHOSW);
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
