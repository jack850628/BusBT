package com.example.busbt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Gthg extends ActionBarActivity{
	LocationManager lm;								
	LocationListener ll;
	private SQLiteDatabase mydb=null;	
	private final static String gte_NAME="bussql.pref";
	private final static String SQL_NAME="Hgsql.db";//SQL名稱
	String BUS_NAME;
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
	private final static String CBOX="cbox";
	private Spinner sp;
	private Spinner sp2;
	private Spinner sp3;
	CheckBox cbox;
	String[] md =new String[]{"","",""};
	int[] st=new int[3];
	int pf;
	private GpsStatus.Listener statusListener;
	double latitude;
	double longitude;
	int ScreenWidth, ScreenHeight;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gthg);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("建立車站");
		final TextView tvgp=(TextView)findViewById(R.id.tvgp);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		lm.addGpsStatusListener(statusListener);
		ll=new LocationListener()
		{
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				latitude=location.getLatitude();				//取得緯度
				longitude=location.getLongitude();
				
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
		ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton ibutton4 = (ImageButton) findViewById(R.id.imageButton4);
		ImageButton ibutton5 = (ImageButton) findViewById(R.id.imageButton5);
		ImageButton ibutton6 = (ImageButton) findViewById(R.id.imageButton6);
		ImageButton ibutton7 = (ImageButton) findViewById(R.id.imageButton7);
		ImageButton ibutton8 = (ImageButton) findViewById(R.id.imageButton8);
		ImageButton ibutton9 = (ImageButton) findViewById(R.id.imageButton9);
		ibutton.setOnClickListener(new iButtonClickListener());
		ibutton2.setOnClickListener(new iButton2ClickListener());
		ibutton3.setOnClickListener(new iButton3ClickListener());
		ibutton4.setOnClickListener(new iButton4ClickListener());
		ibutton5.setOnClickListener(new iButton5ClickListener());
		ibutton6.setOnClickListener(new iButton6ClickListener());
		ibutton7.setOnClickListener(new iButton7ClickListener());
		ibutton8.setOnClickListener(new iButton8ClickListener());
		ibutton9.setOnClickListener(new iButton9ClickListener());
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new Button3ClickListener());
		cbox = (CheckBox)findViewById(R.id.checkBox1);
		sp = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp3 = (Spinner) findViewById(R.id.spinner3);
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView mtv=(TextView)findViewById(R.id.mtextView1);
            	ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton1);
            	ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton2);
        		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton3);
               switch(po){
               case 0:
            	   mtv.setVisibility(View.GONE);
          			ibutton3.setVisibility(View.GONE);
          			ibutton.setVisibility(View.GONE);
       			ibutton2.setVisibility(View.GONE);
            	   break;
               case 1:
       			mtv.setVisibility(View.VISIBLE);
       			mtv.setSelected(true);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
               }
               mp.stop();
               mp.start();
               st[0]=po;

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp2.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView mtv=(TextView)findViewById(R.id.mtextView2);
            	ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton4);
            	ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton5);
        		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton6);
               switch(po){
               case 0:
            	   mtv.setVisibility(View.GONE);
          			ibutton3.setVisibility(View.GONE);
          			ibutton.setVisibility(View.GONE);
       			ibutton2.setVisibility(View.GONE);
            	   break;
               case 1:
       			mtv.setVisibility(View.VISIBLE);
       			mtv.setSelected(true);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
               }
               mp.stop();
               mp.start();
   	    	   st[1]=po;

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp3.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView mtv=(TextView)findViewById(R.id.mtextView3);
            	ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton7);
            	ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton8);
        		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton9);
               switch(po){
               case 0:
            	   mtv.setVisibility(View.GONE);
          			ibutton3.setVisibility(View.GONE);
          			ibutton.setVisibility(View.GONE);
       			ibutton2.setVisibility(View.GONE);
            	   break;
               case 1:
       			mtv.setVisibility(View.VISIBLE);
       			mtv.setSelected(true);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
               }
               mp.stop();
               mp.start();
   	    	   st[2]=po;

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
	}
	MediaPlayer mp = new MediaPlayer();
	class iButtonClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	 try {
		  	     mp.stop();
	    		 mp = new MediaPlayer();
                 mp.setDataSource(md[0]);
                 mp.prepare();
              } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
	          mp.start();
	    }
}
	class iButton2ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    }
}
	class iButton3ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	           mp.stop();
		    	pf=0;
	    	Intent intent = new Intent();
	           intent.setType("audio/*");                                   
	            intent.setAction(Intent.ACTION_GET_CONTENT); 
	            startActivityForResult(intent, 1);
	    }
}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	TextView tv=(TextView)findViewById(R.id.mtextView1);
    	TextView tv2=(TextView)findViewById(R.id.mtextView2);
    	TextView tv3=(TextView)findViewById(R.id.mtextView3);
    	try{
    		if (resultCode == RESULT_OK) {
    		       Uri uri = data.getData();
    		       String[] gh = uri.toString().split(":");
    		       if(gh[0].equals("content")){
    		           String[] proj = { MediaStore.Images.Media.DATA };
    		           Cursor act = managedQuery(uri,proj,null,null,null);
    		           int actual = act.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    		           act.moveToFirst();
    		           if(pf==0){
    		        	   md[0]=act.getString(actual);
    		           }else if(pf==1){
    		        	   md[1]=act.getString(actual);  
    		           }else{
    		        	   md[2]=act.getString(actual);  
    		           }
    		       }else{
    		    	   try {
    					if(pf==0){
    						   md[0]=URLDecoder.decode(uri.toString(),"UTF-8");
    			           }else if(pf==1){
    			        	   md[1]=URLDecoder.decode(uri.toString(),"UTF-8");  
    			           }else{
    			        	   md[2]=URLDecoder.decode(uri.toString(),"UTF-8");  
    			           }
    				} catch (UnsupportedEncodingException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    		       }
    		       if(pf==0){
    		    	   tv.setText(md[0].substring(md[0].lastIndexOf("/")+1,md[0].length()));
    		       }else if(pf==1){
    		    	   tv2.setText(md[1].substring(md[1].lastIndexOf("/")+1,md[1].length()));
    		       }else{
    		    	   tv3.setText(md[2].substring(md[2].lastIndexOf("/")+1,md[2].length()));
    		       }
    		    }
    	}catch(Exception e){
    		AlertDialog.Builder builder = new AlertDialog.Builder(Gthg.this);
			builder.setTitle("抱歉");
	    	builder.setMessage("檔案讀取錯誤!!");
	    	builder.setNegativeButton("確定", null);
	    	builder.show();
    	}
      super.onActivityResult(requestCode, resultCode, data);
    }
	class iButton4ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	try {
	  	    	mp.stop();
	    		mp = new MediaPlayer();
                mp.setDataSource(md[1]);
                mp.prepare();
             } catch (IllegalArgumentException e) {
           } catch (IllegalStateException e) {
           } catch (IOException e) {
           }
	          mp.start();
	    }
}
	class iButton5ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    }
}
	class iButton6ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	           mp.stop();
		    	pf=1;
	    	Intent intent = new Intent();
	           intent.setType("audio/*");                                   
	            intent.setAction(Intent.ACTION_GET_CONTENT); 
	            startActivityForResult(intent, 1);
	    }
}
	class iButton7ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	try {
	  	    	mp.stop();
	    		mp = new MediaPlayer();
                mp.setDataSource(md[2]);
                mp.prepare();
             } catch (IllegalArgumentException e) {
           } catch (IllegalStateException e) {
           } catch (IOException e) {
           }
	          mp.start();
	    }
}
	class iButton8ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    }
}
	class iButton9ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	           mp.stop();
		    	pf=2;
	    	Intent intent = new Intent();
	           intent.setType("audio/*");                                   
	            intent.setAction(Intent.ACTION_GET_CONTENT); 
	            startActivityForResult(intent, 1);
	    }
}
	class ButtonClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		mp.stop();
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		EditText editText3 = (EditText) findViewById(R.id.editText3);
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		EditText editText4 = (EditText) findViewById(R.id.editText4);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		if("".equals(editText1.getText().toString().trim())){
			int i=spf.getInt("sa3", 1);
			cv.put(NAME,"未命名的車站"+i);
			SharedPreferences.Editor ed=spf.edit();
		    ed.putInt("sa3", i+1);
        	ed.commit();
		}else{
		    cv.put(NAME,editText1.getText().toString());
		}
		cv.put(NAME2,editText4.getText().toString());
     	cv.put(GPSX, editText2.getText().toString());
	    cv.put(GPSY, editText3.getText().toString());
		cv.put(MEDIA,md[0]);
		cv.put(MEDIA2,md[1]);
		cv.put(MEDIA3,md[2]);
		cv.put(ST,st[0]);
		cv.put(ST2,st[1]);
		cv.put(ST3,st[2]);
		cv.put(CBOX,cbox.isChecked()?"1":"0");
    	mydb.insert(BUS_NAME, null, cv);
	    mydb.close();
        finish();
		}
	}
	class Button2ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
			mp.stop();
        finish();
		}
	}
	class Button3ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
			EditText editText2 = (EditText) findViewById(R.id.editText2);
			EditText editText3 = (EditText) findViewById(R.id.editText3);
			editText2.setText(new Double(latitude).toString());
			editText3.setText(new Double(longitude).toString());
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id){
		case android.R.id.home:
			mp.stop();
			finish();
			break;
		default: return false;
		}
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	mp.stop();
        	finish();
        }
        return false;
    }
}