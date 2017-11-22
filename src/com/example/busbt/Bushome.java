package com.example.busbt;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Bushome extends ActionBarActivity {

	private final static String ID="_id";
	private final static String SQL_NAME="Bussql.db";//SQL名稱
	private final static String BUS_NAME="busname";//景點表名稱
	private final static String NAME="name";//資料項目:景點名稱
	private final static String SQLNAME="sqlname";
	private final static String CREATE_TABLE="CREATE TABLE "+BUS_NAME+" ("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT,"+SQLNAME+" TEXT)";
	private final static String ST="st";
	private final static String ST2="st2";
	private final static String CREATE_TABLE3="CREATE TABLE "+ST2+" ("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT)";
	private final static String SP="sp";
	private final static String SP2="sp2";
	private final static String SP3="sp3";
	private final static String SP4="sp4";
	private final static String EX="ex";
	private final static String EX2="ex2";
	private final static String EX3="ex3";
	private final static String SD="sd";
	private final static String NS="ns";
	private final static String NS2="ns2";
	private final static String MEDIA="media";
	private final static String MEDIA2="media2";
	private final static String MEDIA3="media3";
	private final static String ST3="st3";
	private final static String EX4="ex4";
	private final static String SP5="sp5";
	private final static String CREATE_TABLE2="CREATE TABLE "+ST+" ("+ID+" INTEGER PRIMARY KEY,"+SP+" TEXT,"+SP2+" TEXT,"+SP3+" TEXT,"+SP4+" TEXT,"+SP5+" TEXT,"+EX+" TEXT,"+EX2+" TEXT,"+EX3+" TEXT,"+EX4+" TEXT,"+SD+" TEXT,"+NS+" TEXT,"+NS2+" TEXT)";
	private final static String CBOX="cbox";
	private final static String NAME2="name2";
	private final static String GPSX="gpsx";
	private final static String GPSY="gpsy";
	LocationManager lm;
	LocationListener ll;
	String[][] bu;
	String[] bu2;
	DisplayMetrics dm;
	int ScreenWidth,ScreenHeight;
	int tin=0;
	long time1,time2;
	private GpsStatus.Listener statusListener;
	private SQLiteDatabase mydb=null;
	private SQLiteDatabase mydb2=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dm = getResources().getDisplayMetrics();
		// 取得螢幕顯示的資料
		ScreenWidth = dm.widthPixels;
		ScreenHeight = dm.heightPixels;
		// 螢幕寬和高的Pixels
		if (ScreenHeight > ScreenWidth){
			setContentView(R.layout.bushome);
		}
		else{
			setContentView(R.layout.bushome2);
		}
		setTitle("首頁");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		// TODO Auto-generated method stub
					lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE); 	
					Toast.makeText(getApplicationContext(),"GPS定位開始!!", Toast.LENGTH_SHORT).show();
					statusListener = new GpsStatus.Listener()
					{
						public void onGpsStatusChanged(int event) {
							lm.getGpsStatus(null);
							if (event==GpsStatus.GPS_EVENT_FIRST_FIX)
							{
								Toast.makeText(getApplicationContext(), "GPS定位完成!!", Toast.LENGTH_LONG).show();
							}
						}
					};
					lm.addGpsStatusListener(statusListener);
					
					ll=new LocationListener()
					{
						public void onLocationChanged(Location location) 
						{

						}
						public void onProviderDisabled(String provider) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "GPS未開啟", Toast.LENGTH_LONG).show();
						}
						@Override
						public void onProviderEnabled(String provider) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "GPS已開啟", Toast.LENGTH_LONG).show();
						}
						@Override
						public void onStatusChanged(String provider, int status,
								Bundle extras) {
							// TODO Auto-generated method stub
						}
					};
					lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
					mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
					
					try{
						Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NAME,SQLNAME}, null, null, null, null, null);
						cur.close();
						}

					catch(Exception e){
				      mydb.execSQL(CREATE_TABLE);
				      }
				    mydb.close();
                    mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
					try{
						Cursor cur=mydb.query(ST, new String[] {ID}, null, null, null, null, null);
						cur.close();
						}

					catch(Exception e){
				      mydb.execSQL(CREATE_TABLE2);
				      ContentValues cv=new ContentValues();
						cv.put(SP,"0");
						cv.put(SP2,"0");
						cv.put(SP3,"0");
						cv.put(SP4,"0");
						cv.put(SP5,"0");
						cv.put(EX,"0");
						cv.put(EX2,"0");
						cv.put(EX3,"0");
						cv.put(EX4,"50");
						cv.put(SD,"5");
						cv.put(NS,"0");
						cv.put(NS2,"0");
						mydb.insert(ST, null, cv);
				      }
				    mydb.close();
				    mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
				    try{
						Cursor cur=mydb.query(ST2, new String[] {ID}, null, null, null, null, null);
						cur.close();
						}

					catch(Exception e){
				      mydb.execSQL(CREATE_TABLE3);
				      }
				    mydb.close();
				    
				    /**------------------更新碼------------------------**/
				    boolean updatapf=false;
				    try{
				    	/**
				    	 * 先從客運名稱(busname)資料表的最後一個格(sglname)取得路線資料庫(lssql)中
				         * 所有資料表名稱。
				         * 再從資料庫(lssql)中取得車站資料庫(Hgsql)所有資料表名稱。
				    	 * 檢查路線資料庫(Hgsql)中的資料表是否有"CBOX"資料格。
				    	 * 沒"CBOX"表格則視為舊資料表並進行更新。
				    	 * **/
				    	mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
						Cursor cur=mydb.query(BUS_NAME, new String[] {SQLNAME}, null, null, null, null, null);
				    	for(int i=0;i<cur.getCount();i++){
							cur.moveToPosition(i);
							mydb2=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
					    	Cursor cur2=mydb2.query(cur.getString(0), new String[] {SQLNAME}, null, null, null, null, null);
					    	for(int j=0;j<cur2.getCount();j++){
								cur2.moveToPosition(j);
								SQLiteDatabase mydb3=openOrCreateDatabase("Hgsql.db", MODE_PRIVATE, null);
						    	Cursor  cur3=mydb3.query(cur2.getString(0), new String[] {CBOX}, null, null, null, null, null);
						    	cur3.close();
							    mydb3.close();
							}
					    	cur2.close();
						    mydb2.close();
						}
						cur.close();
					    mydb.close();
					}
					catch(Exception e){
						/**
						 * 以樹枝狀開檔方式開始更新路線資料庫。
						 * **/
						/**
						 * 先從客運名稱(busname)資料表的最後一個格(sglname)取得一個路線資料庫(lssql)中的資料表名稱。
						 * **/
						mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
						Cursor cur=mydb.query(BUS_NAME, new String[] {SQLNAME}, null, null, null, null, null);
						int j=cur.getCount();
						for(int i=0;i<j;i++){
							cur.moveToPosition(i);
							/**
							 * 然後在由取得到的路線資料庫(lssql)中的資料表名稱開啟資料表。
							 * **/
							mydb2=openOrCreateDatabase("Lssql.db", MODE_PRIVATE, null);
							Cursor cur2=mydb2.query(cur.getString(0), new String[] {SQLNAME}, null, null, null, null, null);
							for(int j2=0;j2<cur2.getCount();j2++){
								cur2.moveToPosition(j2);
								/**
								 * 然後在由取得到的車站資料庫(Hgsql)中的資料表名稱開啟資料表。
								 * **/
								SQLiteDatabase mydb3=openOrCreateDatabase("Hgsql.db", MODE_PRIVATE, null);
						    	Cursor  cur3=mydb3.query(cur2.getString(0), new String[] {ID,NAME,NAME2,GPSX,GPSY,MEDIA,MEDIA2,MEDIA3,ST,ST2,ST3}, null, null, null, null, null);
						    	int jj=cur3.getCount();
						    	bu=new String[jj][11];
								/**
								 * 用for取得所有資料。
								 * **/
								for(int i2=0;i2<jj;i2++){
									cur3.moveToPosition(i2);
									bu[i2][0]=cur3.getString(0);
									bu[i2][1]=cur3.getString(1);
									bu[i2][2]=cur3.getString(2);
									bu[i2][3]=cur3.getString(3);
									bu[i2][4]=cur3.getString(4);
									bu[i2][5]=cur3.getString(5);
									bu[i2][6]=cur3.getString(6);
									bu[i2][7]=cur3.getString(7);
									bu[i2][8]=cur3.getString(8);
									bu[i2][9]=cur3.getString(9);
									bu[i2][10]=cur3.getString(10);
								}
						    	cur3.close();
								/**
								 * 取完資料後刪除資料表。
								 * **/
								mydb3.execSQL("DROP TABLE "+cur2.getString(0));
								/**
								 * 建立相同名稱的資料表並放回資料。
								 * **/
								String CREATE_TABLE="CREATE TABLE "+cur2.getString(0)+" ("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT,"+NAME2+" TEXT,"+GPSX+" TEXT,"+GPSY+" TEXT,"+MEDIA+" TEXT,"+MEDIA2+" TEXT,"+MEDIA3+" TEXT,"+ST+" TEXT,"+ST2+" TEXT,"+ST3+" TEXT,"+CBOX+" TEXT)";
								mydb3.execSQL(CREATE_TABLE);
								for(int i2=0;i2<jj;i2++){
									ContentValues cv=new ContentValues();
									cv.put(ID,bu[i2][0]);
								    cv.put(NAME,bu[i2][1]);
									cv.put(NAME2,bu[i2][2]);
									cv.put(GPSX,bu[i2][3]);
								    cv.put(GPSY,bu[i2][4]);
									cv.put(MEDIA,bu[i2][5]);
									cv.put(MEDIA2,bu[i2][6]);
									cv.put(MEDIA3,bu[i2][7]);
									cv.put(ST,bu[i2][8]);
									cv.put(ST2,bu[i2][9]);
									cv.put(ST3,bu[i2][10]);
									cv.put(CBOX,"0");
								    mydb3.insert(cur2.getString(0), null, cv);
								}
							    mydb3.close();
							}
							cur2.close();
						    mydb2.close();
						}
						cur.close();
						updatapf=true;
					}
				    try{
				    	/**
				    	 * 檢查路線資料庫(st)中的資料表是否有"ET4"資料格。
				    	 * 沒"NO"表格則視為舊資料表並進行更新。
				    	 * **/
				    	mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
				    	Cursor cur=mydb.query(ST, new String[] {EX4,SP5}, null, null, null, null, null);
				    	cur.close();
				    	mydb.close();
					}
				    catch(Exception e){
				    	bu2=new String[10];
				    	mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
				    	Cursor cur=mydb.query(ST, new String[] {SP,SP2,SP3,SP4,EX,EX2,EX3,SD,NS,NS2}, null, null, null, null, null);
				    	cur.moveToPosition(0);
				    	bu2[0]=cur.getString(0);
				    	bu2[1]=cur.getString(1);
				    	bu2[2]=cur.getString(2);
				    	bu2[3]=cur.getString(3);
				    	bu2[4]=cur.getString(4);
				    	bu2[5]=cur.getString(5);
				    	bu2[6]=cur.getString(6);
				    	bu2[7]=cur.getString(7);
				    	bu2[8]=cur.getString(8);
				    	bu2[9]=cur.getString(9);
				    	cur.close();
				    	mydb.execSQL("DROP TABLE "+ST);
				    	mydb.execSQL(CREATE_TABLE2);
					      ContentValues cv=new ContentValues();
							cv.put(SP,bu2[0]);
							cv.put(SP2,bu2[1]);
							cv.put(SP3,bu2[2]);
							cv.put(SP4,bu2[3]);
							cv.put(SP5,"0");
							cv.put(EX,bu2[4]);
							cv.put(EX2,bu2[5]);
							cv.put(EX3,bu2[6]);
							cv.put(EX4,"50");
							cv.put(SD,bu2[7]);
							cv.put(NS,bu2[8]);
							cv.put(NS2,bu2[9]);
							mydb.insert(ST, null, cv);
				    	mydb.close();
				    	updatapf=true;
				    }
				    if(updatapf)
				    	updata();
				    /**-----------------------------------------------**/
				    
					Button button = (Button) findViewById(R.id.button1);
					button.setOnClickListener(new ButtonClickListener());
					Button button2 = (Button) findViewById(R.id.button2);
					button2.setOnClickListener(new Button2ClickListener());
	}
	public void updata(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("恭喜您成功更新至版本："+getString(R.string.ver));
    	builder.setMessage(R.string.About);
    	builder.setNegativeButton("確定", null);
    	builder.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	if(tin<=1){
            	tin++;
        	}
        	if(tin==1){
            	time1 = System.currentTimeMillis();
        		Toast.makeText(getApplicationContext(), "在按一次返回鍵即可退出程式", Toast.LENGTH_LONG).show();
        	}else{
        		time2 = System.currentTimeMillis();
        		if(time2-time1<=2000){
        			lm.removeUpdates(ll);							
					lm.removeGpsStatusListener(statusListener);
					 System.exit(0);
        		}
        		time1 = System.currentTimeMillis();
        		Toast.makeText(getApplicationContext(), "在按一次返回鍵即可退出程式", Toast.LENGTH_LONG).show();
        	}
        }
        return false;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bushome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.about,(ViewGroup) findViewById(R.layout.about));
			new AlertDialog.Builder(this).setTitle("關於").setView(layout)
			.setPositiveButton("確定", null).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bushome,
					container, false);
			return rootView;
		}
	}

	class ButtonClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		    Intent intent = new Intent ();
		    intent.setClass(Bushome.this,Busname.class);
		    Bushome.this.startActivity(intent);
		}
	}

	class Button2ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		    Intent intent = new Intent ();
		    intent.setClass(Bushome.this,St.class);
		    Bushome.this.startActivity(intent);
		}
	}

}
