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

public class St2 extends ActionBarActivity{
	private SQLiteDatabase mydb=null;	
	LocationManager lm;			
	private final static String ID="_id";
	private final static String SQL_NAME="st.db";//SQL名稱
	private final static String NAME="name";
	private final static String ST2="st2";
	private ArrayList<HashMap> tiem2;
	int i,j,pf,etag,SHOSW=0;
	String id;
	boolean et=false;
	boolean lvst=true;
	Listview lv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.st2);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("跑馬燈文字");
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
			Cursor cur=mydb.query(ST2, new String[] {NAME}, null, null, null, null, null);
            j=cur.getCount();
            if(j==0){
            	tv.setVisibility(View.VISIBLE);
            	listview.setVisibility(View.INVISIBLE);
            }else{
            	tv.setVisibility(View.INVISIBLE); 
            	listview.setVisibility(View.VISIBLE);
                for(i=0;i<j;i++){
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
			Intent intent = new Intent();
			if(et){
				etag=position;
				mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(ST2, new String[] {NAME}, null, null, null, null, null);
				cur.moveToPosition(pf);
				String n1=cur.getString(0);
				cur.moveToPosition(position);
				String n2=cur.getString(0);
				AlertDialog.Builder builder = new AlertDialog.Builder(St2.this);
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
				AlertDialog.Builder builder = new AlertDialog.Builder(St2.this);
				builder.setTitle("確認");
		    	builder.setMessage("請問您是要?");
		    	Gn de = new Gn();
		    	builder.setNegativeButton("刪除", de);
		    	builder.setNeutralButton("編輯", de);
		    	builder.setPositiveButton("移動", de);
		    	builder.show();
		    	pf=arg2;
		    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(ST2, new String[] {ID}, null, null, null, null, null);
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
			Cursor cur=mydb.query(ST2, new String[] {NAME}, null, null, null, null, null);
			cur.moveToPosition(pf);
			AlertDialog.Builder builder = new AlertDialog.Builder(St2.this);
			builder.setTitle("確認");
	    	builder.setMessage("您確定要刪除\""+cur.getString(0)+"\"?");
			cur.close();
			mydb.close();
	    	Gn3 de = new Gn3();
	    	builder.setNegativeButton("取消", de);
	    	builder.setPositiveButton("確定", de);
	    	builder.show();
		}else if (which == DialogInterface.BUTTON_NEUTRAL){
			Intent intent =new Intent(St2.this, Sqlst2.class);
			Bundle bundle = new Bundle();
			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
			intent.putExtras(bundle);
			St2.this.startActivityForResult(intent,SHOSW);
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
    	ListView listview = (ListView) findViewById(R.id.listView1);
    	String na;
    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
    	Cursor cur=mydb.query(ST2, new String[] {ID,NAME}, null, null, null, null, null);
    	cur.moveToPosition(pf);
    	String in=cur.getString(0);
   	    na=cur.getString(1);
   	    String whereClet="_id=?";
	    String[] whereAet={in};
	    mydb.delete(ST2, whereClet, whereAet);
		cur.close();
		mydb.close();
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		cur=mydb.query(ST2, new String[] {ID,NAME}, null, null, null, null, null);
        int j=cur.getCount();
	    int i=etag;
	    if(pf<etag){
	    	i--;
	    }
	    if(j==i){
	    	ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,na);
		    mydb.insert(ST2, null, cv);
	    }else{
            ContentValues cv=new ContentValues();
            cur.moveToPosition(j-1);
            cv.put(NAME,cur.getString(1));
		    mydb.insert(ST2, null, cv);
        	String r;
            for(j=cur.getCount()-1;j>i;j--){
            	cur.moveToPosition(j);
            	r=cur.getString(0);
            	cur.moveToPosition(j-1);
                cv.put(NAME,cur.getString(1));
 			    String whereClause="_id=?";
 				String[] whereArgs={r};
 				mydb.update(ST2, cv, whereClause, whereArgs);
            }
            cur.moveToPosition(j);
        	r=cur.getString(0);
            cv.put(NAME,na);
			    String whereClause="_id=?";
				String[] whereArgs={r};
				mydb.update(ST2, cv, whereClause, whereArgs);
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
				mydb.delete(ST2, whereClause, whereArgs);
			    mydb.close();
			    sql();
		}
		}
	}
	private class Del implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == DialogInterface.BUTTON_NEGATIVE){
		        dialog.dismiss();
			}else if (which == DialogInterface.BUTTON_POSITIVE){
				mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
				mydb.delete(ST2,null, null);
			    mydb.close();
			    sql();
		}
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.st2, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
    	int item_id = item.getItemId();
    	switch (item_id){
    		case R.id.st2:
    			Intent intent =new Intent(St2.this, Gtst2.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("SELECTED_GREETING",String.valueOf(pf));
    			intent.putExtras(bundle);
    			St2.this.startActivityForResult(intent,SHOSW);
    			break;
    		case R.id.st22:
    			AlertDialog.Builder builder = new AlertDialog.Builder(St2.this);
    			builder.setTitle("確認");
    	    	builder.setMessage("您確定要清空所有文字?");
    	    	Del de = new Del();
    	    	builder.setNegativeButton("取消", de);
    	    	builder.setPositiveButton("確定", de);
    	    	builder.show();
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