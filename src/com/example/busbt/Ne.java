package com.example.busbt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class Ne extends ActionBarActivity{
	LocationManager lm;								
	LocationListener ll;
	private SQLiteDatabase mydb=null;	
	private final static String gte_NAME="bussql.pref";
	private final static String SQL_NAME="Hgsql.db";//SQL名稱
	String BUS_NAME;
	private final static String NAME="name";
	private final static String NAME2="name2";
	private final static String NAMEA="namea";
	private final static String NAMEB="nameb";
	private final static String MEDIA="media";
	private final static String MEDIA2="media2";
	private final static String MEDIA2A="media2a";
	private final static String MEDIA2B="media2b";
	private final static String MEDIA3="media3";
	private final static String ST="st";
	private final static String ST2="st2";
	private final static String ST3="st3";
	private final static String ST4="st4";
	private final static String GPSX="gpsx";
	private final static String GPSY="gpsy";
	private final static String SP="sp";
	private final static String SP2="sp2";
	private final static String SP3="sp3";
	private final static String SP4="sp4";
	private final static String SP5="sp5";
	private final static String EX="ex";
	private final static String EX2="ex2";
	private final static String EX3="ex3";
	private final static String EX4="ex4";
	private final static String SD="sd";
	private final static String NS="ns";
	private final static String NS2="ns2";
	private final static String NO="no";
	private final static String START="start";
	private final static String CBOX="cbox";
	private DrawerLayout mDrawerLayout = null;
	private ArrayList<HashMap> tiem2;
	String no,na,start;
	String[] sql=new String[2],me=new String[4],s,s2=new String[4];
	double[] st3=new double[4];
	int[] st=new int[4],st2=new int[5],sd=new int[3],togt;
	private GpsStatus.Listener statusListener;
	double gpsx,gpsy;
	int ScreenWidth, ScreenHeight,j;
	int gt=0,g,to,pf=0;
	double XY[][];
	Mtv tv;
	Listview lv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ne);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
		final TextView tvgp=(TextView)findViewById(R.id.tvgp);
		tv = new Mtv(this);
		tv.pf(true);
		tv.setSelected(true);
		mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
		Cursor cur=mydb.query(ST, new String[] {SP,SP2,SP3,SP4,SP5,EX,EX2,EX3,EX4,SD,NS,NS2}, null, null, null, null, null);
		cur.moveToPosition(0);
		st2[0]=cur.getInt(0);
		st2[1]=cur.getInt(1);
		st2[2]=cur.getInt(2);
		st2[3]=cur.getInt(3);
		st2[4]=cur.getInt(4);
		st3[0]=cur.getDouble(5);
		st3[1]=cur.getDouble(6);
		st3[2]=cur.getDouble(7);
		st3[3]=cur.getDouble(8);
		sd[0]=cur.getInt(9);
		sd[1]=cur.getInt(10);
		sd[2]=cur.getInt(11);
		cur=mydb.query(ST2, new String[] {NAME}, null, null, null, null, null);
		s=new String[cur.getCount()];
		for(int i=0;i<s.length;i++){
        	cur.moveToPosition(i);
        	s[i]=cur.getString(0);
        }
		cur.close();
		mydb.close();
		if(st2[0]==0){
			st3[0]=0.005;
		}else{
			st3[0]=st3[0]*0.00001;
		}
        if(st2[1]==0){
        	st3[1]=0.003;
		}else{
			st3[1]=st3[1]*0.00001;
		}
        if(st2[3]==0){
        	st3[2]=0.001;
		}else{
			st3[2]=st3[2]*0.00001;
		}
        st3[3]=(st2[4]==0)?50:st3[3];
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		lm.addGpsStatusListener(statusListener);
		ll=new LocationListener()
		{
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				gpsx=location.getLatitude();
				gpsy=location.getLongitude();	
				tv.gps(gpsx,gpsy);
				tvgp.setText("GPS定位完成!!");
				if(j!=0){
					if(gt<XY.length){
						switch(pf){
						case 0:
							if(Math.pow(XY[gt][0]-gpsx, 2)+Math.pow(XY[gt][1]-gpsy, 2)<=Math.pow(st3[0], 2)){
								st();
						  		pf=1;
							}
							break;
						case 1:
							if(Math.pow(XY[gt][0]-gpsx, 2)+Math.pow(XY[gt][1]-gpsy, 2)<=Math.pow(st3[1], 2)){
								st2();
						  		pf=2;
							}
							break;
						case 2:
							if(Math.pow(XY[gt][0]-gpsx, 2)+Math.pow(XY[gt][1]-gpsy, 2)<=Math.pow(st3[2], 2)){
								st3();
						  		pf=0;
						  		gt++;
						  		sql(false);
							}
							break;
						}
					}
				}
			}
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				tvgp.setText("GPS未開啟!!");
			}
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				tvgp.setText("GPS定位中....");
			}
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		Intent intent = this.getIntent();
		setResult(RESULT_OK, intent);
		Bundle bundle = intent.getExtras();	//取得Bundle
		BUS_NAME=bundle.getString("SELECTED_GREETING");
		sql[0]=bundle.getString("SELECTED_GREETING2");
		na=bundle.getString("SELECTED_GREETING4");
		to=bundle.getInt("SELECTED_GREETING5");
		mydb=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
		cur=mydb.query(sql[0], new String[] {MEDIA,MEDIA2A,MEDIA2B,MEDIA3,ST,ST2,ST3,ST4,NAMEA,NAMEB,NO,START}, null, null, null, null, null);
		cur.moveToPosition(Integer.valueOf(bundle.getString("SELECTED_GREETING3")));
		me[0]=cur.getString(0);
		me[1]=cur.getString(1);
		me[2]=cur.getString(2);
		me[3]=cur.getString(3);
		st[0]=cur.getInt(4);
		st[1]=cur.getInt(5);
		st[2]=cur.getInt(6);
		st[3]=cur.getInt(7);
		sql[0]=(to==0)?cur.getString(8):cur.getString(9);
		sql[1]=(to==0)?cur.getString(9):cur.getString(8);
		no=cur.getString(10);
		start=cur.getString(11);
		setTitle(sql[0]+" "+no+" "+sql[1]);
		s2[0]=na;
		s2[1]=no;
		s2[2]=sql[0];
		s2[3]=sql[1];
		tv.st2(s,s2,st3[3]);
		for(int i=0;i<4;i++)
			start=start.replace((i==0)?"/a":(i==1)?"/b":
				(i==2)?"/c":"/d", s2[i]);
		tv.setText(start);
		if(st2[2]==0){
        	tv.setTextSize(70);
		}else if(st2[2]==1){
			tv.setTextSize(100);
		}else if(st2[2]==2){
			tv.setTextSize(130);
		}else if(st2[2]==3){
			tv.setTextSize(160);
		}else if(st2[2]==4){
			tv.setTextSize(190);
		}else if(st2[2]==5){
			tv.setTextSize(220);
		}else if(st2[2]==6){
			tv.setTextSize(250);
		}
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle DT=new drawerToggle(
                this, 
                mDrawerLayout,
                R.drawable.ic_drawer,0,0);
        DT.syncState();
        mDrawerLayout.setDrawerListener(DT);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);  
	    ll.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    tv.scrollText(sd[0]);
	    switch(sd[1]){
        case 0:
     	   ll.setBackgroundColor(Color.BLACK);
     	   break;
        case 1:
     	   ll.setBackgroundColor(Color.WHITE);
		        break;
        }
	    switch(sd[2]){
        case 0:
     	   tv.setTextColor(android.graphics.Color.RED);
     	   break;
        case 1:
     	   tv.setTextColor(android.graphics.Color.YELLOW);
		        break;
        case 2:
     	   tv.setTextColor(android.graphics.Color.BLUE);
		        break;
        case 3:
     	   tv.setTextColor(android.graphics.Color.BLACK);
		        break;
        case 4:
     	   tv.setTextColor(android.graphics.Color.WHITE);
		        break;
        }
		cur.close();
		mydb.close();
		play1();
		sql(true);
	}
	public void sql(boolean lvst){
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		TextView tv=(TextView)findViewById(R.id.textView1);
		ListView listview = (ListView) findViewById(R.id.listView1);
		tiem2 = new ArrayList<HashMap>();
		HashMap hh;
		int i;
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		
		try
		{
			Cursor cur=mydb.query(BUS_NAME, new String[] {NAME,GPSX,GPSY}, null, null, null, null, null);
            j=cur.getCount();
            if(j==0){
            	tv.setVisibility(View.VISIBLE);
            	listview.setVisibility(View.INVISIBLE);
            }else{
            	tv.setVisibility(View.INVISIBLE); 
            	listview.setVisibility(View.VISIBLE);
            	XY=new double[j][2];
            	togt=new int[j];
            	if(to==0){
            		 for(i=0;i<j;i++){
                     	cur.moveToPosition(i);
                     	hh = new HashMap();
                 		hh.put("id", i);
                 		hh.put("name", (i+1)+"."+cur.getString(0));
                 		if(i<gt){
                     		hh.put("tp", 2);
                 		}else if(i==gt){
                 			hh.put("tp", 1);
                 		}else if(i>gt){
                 			hh.put("tp", 0);
                 		}
                 		hh.put("pf", 1);
                 		XY[i][0]=cur.getDouble(1);
                 		XY[i][1]=cur.getDouble(2);
                 		togt[i]=i;
                     	tiem2.add(hh);
                     }
    			}else{
    				int i2=0;
    				 for(i=j-1;i>=0;i--){
    	                	cur.moveToPosition(i);
    	                	hh = new HashMap();
    	            		hh.put("id", i2);
    	            		hh.put("name", (i2+1)+"."+cur.getString(0));
    	            		if(i2<gt){
    	                		hh.put("tp", 2);
    	            		}else if(i2==gt){
    	            			hh.put("tp", 1);
    	            		}else if(i2>gt){
    	            			hh.put("tp", 0);
    	            		}
    	            		hh.put("pf", 1);
    	            		XY[i2][0]=cur.getDouble(1);
    	            		XY[i2][1]=cur.getDouble(2);
    	            		togt[i2]=i;
    	                	tiem2.add(hh);
    	                	i2++;
    	                }
    			}
            }
			cur.close();
			if(lvst){
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
		mydb.close();
	    System.gc();
	}
	class drawerToggle extends ActionBarDrawerToggle {

        public drawerToggle(Activity activity, DrawerLayout drawerLayout,
				int drawerImageRes, int openDrawerContentDescRes,
				int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes);
			// TODO Auto-generated constructor stub
		}

		@Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }
    }
	class ListViewLongClickListener implements OnItemLongClickListener{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			Vibrator myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
			myVibrator.vibrate(50);
			AlertDialog.Builder builder = new AlertDialog.Builder(Ne.this);
			builder.setTitle("確認");
			mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
			Cursor cur=mydb.query(BUS_NAME, new String[] {NAME}, null, null, null, null, null);
			g=arg2;
			cur.moveToPosition(g);
			builder.setTitle("確認");
	    	builder.setMessage("請問您是要前往\""+cur.getString(0)+"\"?");
			cur.close();
			mydb.close();
	    	Gn de = new Gn();
	    	builder.setNegativeButton("取消", de);
	    	builder.setPositiveButton("前往", de);
	    	builder.show();
			return true;
		}
	}
	private class Gn implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
   			dialog.dismiss();
		}else if (which == DialogInterface.BUTTON_POSITIVE){
			gt=g;
			pf=0;
			sql(false);
			tv.setText(start);
			tv.st();
	}
		}
	}
	MediaPlayer mp = new MediaPlayer();
	public void st(){
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(BUS_NAME, new String[] {MEDIA,ST,NAME,NAME2}, null, null, null, null, null);
		cur.moveToPosition(togt[gt]);
		tv.setText("下一站："+cur.getString(2)+" "+cur.getString(3));
		tv.st();
  	    mp.stop(); 
  	  try {
  		mp = new MediaPlayer();
          mp.setDataSource(cur.getString(0));
          mp.prepare();
       } catch (IllegalArgumentException e) {
     } catch (IllegalStateException e) {
     } catch (IOException e) {
     }
      mp.start();
         cur.close();
  		mydb.close();
	}
    public void st2(){
    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(BUS_NAME, new String[] {MEDIA2,ST2,NAME,NAME2}, null, null, null, null, null);
		cur.moveToPosition(togt[gt]);
		tv.setText("接近："+cur.getString(2)+" "+cur.getString(3));
		tv.st();
  	    mp.stop();
		try {
			mp = new MediaPlayer();
            mp.setDataSource(cur.getString(0));
            mp.prepare();
         } catch (IllegalArgumentException e) {
       } catch (IllegalStateException e) {
       } catch (IOException e) {
       }
        mp.start();
        cur.close();
  		mydb.close();
	}
    public void st3(){
    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(BUS_NAME, new String[] {MEDIA3,ST3,NAME,NAME2,CBOX}, null, null, null, null, null);
		cur.moveToPosition(togt[gt]);
		tv.setText((cur.getInt(4)==1)?cur.getString(2)+" "+cur.getString(3):cur.getString(2));
		tv.tg(gpsx,gpsy);
  	    mp.stop(); 
		try {
			mp = new MediaPlayer();
            mp.setDataSource(cur.getString(0));
            mp.prepare();
         } catch (IllegalArgumentException e) {
       } catch (IllegalStateException e) {
       } catch (IOException e) {
       }
        mp.start();
        cur.close();
  		mydb.close();
	}
    MediaPlayer mp2 = new MediaPlayer();
	MediaPlayer ump = new MediaPlayer();
	public void play1(){
		if(st[0]==1){
    		ump.stop();
    		ump=MediaPlayer.create(getApplicationContext(), R.raw.m1);
            ump.start();
            mp2.stop();
            ump.setOnCompletionListener(new OnCompletionListener()   
            {  
                public void onCompletion(MediaPlayer arg0)  
                {  
                	play2(); 
                }  
            });  
		}else if(st[0]==2){
	    	  try {
                  mp2.setDataSource(me[0]);
                  mp2.prepare();
               } catch (IllegalArgumentException e) {
             } catch (IllegalStateException e) {
             } catch (IOException e) {
             }
	          mp2.start();
	          mp2.setOnCompletionListener(new OnCompletionListener()   
	            {  
	                public void onCompletion(MediaPlayer arg0)  
	                {  
	                	play2(); 
	                }  
	            });  
		}else{
			play2();
		}
	}
	public void play2(){
		if(st[1]==1&&to==0||st[2]==1&&to==1){
			  try {
				  mp2 = new MediaPlayer();
                mp2.setDataSource((to==0)?me[1]:me[2]);
                mp2.prepare();
             } catch (IllegalArgumentException e) {
           } catch (IllegalStateException e) {
           } catch (IOException e) {
           }
	          mp2.start();
	          mp2.setOnCompletionListener(new OnCompletionListener()   
	            {  
	                public void onCompletion(MediaPlayer arg0)  
	                {  
	                	play3(); 
	                }  
	            }); 
		}else{
			play3();
		}
	}
	public void play3(){
		if(st[3]==1){
    		ump.stop();
    		ump=MediaPlayer.create(getApplicationContext(), R.raw.m2);
            ump.start();
            mp2.stop(); 
		}else if(st[3]==2){
	    	  try {
	    		  mp2 = new MediaPlayer();
                  mp2.setDataSource(me[3]);
                  mp2.prepare();
               } catch (IllegalArgumentException e) {
             } catch (IllegalStateException e) {
             } catch (IOException e) {
             }
	          mp2.start();
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	mp.stop();
        	mp2.stop();
        	ump.stop();
        	j=0;
        	finish();
        }
        return false;
    }
	public boolean onOptionsItemSelected(MenuItem item) {
    	int item_id = item.getItemId();
    	switch (item_id){
    		case android.R.id.home:
    			if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
    				mDrawerLayout.closeDrawer(Gravity.START);
    			else
    				mDrawerLayout.openDrawer(Gravity.LEFT);
    			break;
    		default: return false;
    	}
    	return true;
    }
}