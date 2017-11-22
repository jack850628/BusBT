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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Hgname extends ActionBarActivity{
	private SQLiteDatabase mydb=null;	
	LocationManager lm;			
	private final static String ID="_id";
	private final static String SQL_NAME="Hgsql.db";//SQL名稱
	String BUS_NAME;
	private final static String NAMEA="namea";
	private final static String NAMEB="nameb";
	private final static String NAME="name";
	private final static String NAME2="name2";
	private final static String MEDIA="media";
	private final static String MEDIA2="media2";
	private final static String MEDIA3="media3";
	private final static String ST="st";
	private final static String ST2="st2";
	private final static String ST3="st3";
	private final static String GPSX="gpsx";
	private final static String GPSY="gpsy";
	private final static String SQLNAME="sqlname";
	private ArrayList<HashMap> tiem2;
	ArrayAdapter<String> adapter;
	int pf,pf2,etag,SHOSW=0;
	String id;
	String[] me=new String[3],sql=new String[3],na=new String[2],gps=new String[2],st=new String[3];
	boolean et=false;
	boolean lvst=true;
	Listview lv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hgname);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("路線站名");
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		sql[0]=bundle.getString("SELECTED_GREETING");
		sql[1]=bundle.getString("SELECTED_GREETING2");
		sql[2]=bundle.getString("SELECTED_GREETING3");
		mydb=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
		Cursor cur=mydb.query(sql[0], new String[] {SQLNAME}, null, null, null, null, null);
		pf2=Integer.valueOf(bundle.getString("SELECTED_GREETING2"));
		cur.moveToPosition(pf2);
		BUS_NAME=cur.getString(0);
		cur.close();
		mydb.close();
		sql();
	}
	public void sql(){
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
				lvst=false;
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
			if(et){
				etag=position;
				mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(BUS_NAME, new String[] {NAME}, null, null, null, null, null);
				cur.moveToPosition(pf);
				String n1=cur.getString(0);
				cur.moveToPosition(position);
				String n2=cur.getString(0);
				AlertDialog.Builder builder = new AlertDialog.Builder(Hgname.this);
				builder.setTitle("確認");
		    	builder.setMessage("請問您是將\""+n1+"\"移動到\""+n2+"\"的?");
				cur.close();
				mydb.close();
		    	Gn2 de = new Gn2();
		    	builder.setNegativeButton("上方", de);
		    	builder.setPositiveButton("下方", de);
		    	builder.show();
			}else{
				
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
				AlertDialog.Builder builder = new AlertDialog.Builder(Hgname.this);
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
			AlertDialog.Builder builder = new AlertDialog.Builder(Hgname.this);
			builder.setTitle("確認");
	    	builder.setMessage("您確定要刪除\""+cur.getString(0)+"\"?");
			cur.close();
			mydb.close();
	    	Gn3 de = new Gn3();
	    	builder.setNegativeButton("取消", de);
	    	builder.setPositiveButton("確定", de);
	    	builder.show();
		}else if (which == DialogInterface.BUTTON_NEUTRAL){
			Intent intent =new Intent(Hgname.this, Sqlhgname.class);
			Bundle bundle = new Bundle();
			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
			bundle.putString("SELECTED_GREETING2",BUS_NAME);
			intent.putExtras(bundle);
			Hgname.this.startActivityForResult(intent,SHOSW);
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
    	Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NAME,NAME2,GPSX,GPSY,MEDIA,MEDIA2,MEDIA3,ST,ST2,ST3}, null, null, null, null, null);
    	cur.moveToPosition(pf);
    	String in=cur.getString(0);
   	    na[0]=cur.getString(1);
   	    na[1]=cur.getString(2);
   	    gps[0]=cur.getString(3);
   	    gps[1]=cur.getString(4);
   	    me[0]=cur.getString(5);
   	    me[1]=cur.getString(6);
   	    me[2]=cur.getString(7);
		st[0]=cur.getString(8);
		st[1]=cur.getString(9);
		st[2]=cur.getString(10);
   	    String whereClet="_id=?";
	    String[] whereAet={in};
	    mydb.delete(BUS_NAME, whereClet, whereAet);
		cur.close();
		mydb.close();
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		cur=mydb.query(BUS_NAME, new String[] {ID,NAME,NAME2,GPSX,GPSY,MEDIA,MEDIA2,MEDIA3,ST,ST2,ST3}, null, null, null, null, null);
        int j=cur.getCount();
	    int i=etag;
	    if(pf<etag){
	    	i--;
	    }
	    if(j==i){
	    	ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,na[0]);
            cv.put(NAME2,na[1]);
            cv.put(GPSX,gps[0]);
            cv.put(GPSY,gps[1]);
			cv.put(MEDIA,me[0]);
			cv.put(MEDIA2,me[1]);
			cv.put(MEDIA3,me[2]);
			cv.put(ST,st[0]);
			cv.put(ST2,st[1]);
			cv.put(ST3,st[2]);
		    mydb.insert(BUS_NAME, null, cv);
	    }else{
            ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,cur.getString(1));
            cv.put(NAME2,cur.getString(2));
            cv.put(GPSX,cur.getString(3));
            cv.put(GPSY,cur.getString(4));
            cv.put(MEDIA,cur.getString(5));
            cv.put(MEDIA2,cur.getString(6));
            cv.put(MEDIA3,cur.getString(7));
            cv.put(ST,cur.getString(8));
            cv.put(ST2,cur.getString(9));
            cv.put(ST3,cur.getString(10));
		    mydb.insert(BUS_NAME, null, cv);
        	String r;
            for(j=cur.getCount()-1;j>i;j--){
            	cur.moveToPosition(j);
            	r=cur.getString(0);
            	cur.moveToPosition(j-1);
                cv.put(NAME,cur.getString(1));
                cv.put(NAME2,cur.getString(2));
                cv.put(GPSX,cur.getString(3));
                cv.put(GPSY,cur.getString(4));
                cv.put(MEDIA,cur.getString(5));
                cv.put(MEDIA2,cur.getString(6));
                cv.put(MEDIA3,cur.getString(7));
                cv.put(ST,cur.getString(8));
                cv.put(ST2,cur.getString(9));
                cv.put(ST3,cur.getString(10));
 			    String whereClause="_id=?";
 				String[] whereArgs={r};
 				mydb.update(BUS_NAME, cv, whereClause, whereArgs);
            }
            cur.moveToPosition(j);
        	r=cur.getString(0);
            cv.put(NAME,na[0]);
            cv.put(NAME2,na[1]);
            cv.put(GPSX,gps[0]);
            cv.put(GPSY,gps[1]);
			cv.put(MEDIA,me[0]);
			cv.put(MEDIA2,me[1]);
			cv.put(MEDIA3,me[2]);
			cv.put(ST,st[0]);
			cv.put(ST2,st[1]);
			cv.put(ST3,st[2]);
			    String whereClause="_id=?";
				String[] whereArgs={r};
				mydb.update(BUS_NAME, cv, whereClause, whereArgs);
	    }
		cur.close();
		mydb.close();
    	et=false;
	    sql();
	}
	private class Gn3 implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
	        dialog.dismiss();
		}else if (which == DialogInterface.BUTTON_POSITIVE){
			mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		    String whereClause="_id=?";
			String[] whereArgs={id};
			mydb.delete(BUS_NAME, whereClause, whereArgs);
		    mydb.close();
		    sql();
	}
		}
	}
	class lvon implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(Hgname.this, Ne.class);
			Bundle bundle = new Bundle();
			bundle.putString("SELECTED_GREETING",BUS_NAME);
			bundle.putString("SELECTED_GREETING2",sql[0]);
			bundle.putString("SELECTED_GREETING3",sql[1]);
			bundle.putString("SELECTED_GREETING4",sql[2]);
			bundle.putInt("SELECTED_GREETING5",position);
			intent.putExtras(bundle);
			Hgname.this.startActivityForResult(intent,SHOSW);
			ad.dismiss();
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.hgmenu, menu);
	    return true;
	}
	AlertDialog ad;
	public boolean onOptionsItemSelected(MenuItem item) {
    	int item_id = item.getItemId();
		Intent intent =new Intent(Hgname.this, Gthg.class);
		Bundle bundle = new Bundle();
    	switch (item_id){
    		case R.id.sql:
    			bundle.putString("SELECTED_GREETING",BUS_NAME);
    			intent.putExtras(bundle);
    			Hgname.this.startActivityForResult(intent,SHOSW);
    			break;
    		case R.id.sql2:
    			LayoutInflater inflater = getLayoutInflater();
    			View layout = inflater.inflate(R.layout.me,(ViewGroup) findViewById(R.layout.me));
    			ad=new AlertDialog.Builder(this).setTitle("請選擇方向").setView(layout).show();
    			ListView lv=(ListView)layout.findViewById(R.id.listView1);
    			lv.setOnItemClickListener(new lvon());
    			mydb=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
    			Cursor cur=mydb.query(sql[0], new String[] {NAMEA,NAMEB}, null, null, null, null, null);
    			cur.moveToPosition(pf2);
    			adapter = new ArrayAdapter<String>(this, R.layout.list,new String[]{cur.getString(0)+" 往 "+cur.getString(1),cur.getString(1)+" 往 "+cur.getString(0)}); 
    			lv.setAdapter(adapter);
    			cur.close();
    			mydb.close();
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
			    sql();
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
