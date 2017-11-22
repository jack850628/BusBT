package com.example.busbt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Sqllsname extends ActionBarActivity{
	LocationManager lm;								
	LocationListener ll;
	private SQLiteDatabase mydb=null;	
	private final static String ID="_id";
	private final static String gte_NAME="bussql.pref";
	private final static String SQL_NAME="Lssql.db";//SQL名稱
	String BUS_NAME;//景點表名稱
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
	private final static String NO="no";
	private final static String START="start";
	private Spinner sp;
	private Spinner sp2;
	private Spinner sp3;
	private Spinner sp4;
	String[] md =new String[4];
	int[] st=new int[4];
	int pf,i;
	String pf2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtls);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("編輯路線");
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();	//取得Bundle
		pf2=bundle.getString("SELECTED_GREETING");
		BUS_NAME=bundle.getString("SELECTED_GREETING2");
		i=Integer.valueOf(pf2);
		setResult(RESULT_OK, intent);
		TextView tv=(TextView)findViewById(R.id.mtextView1);
		TextView tv1=(TextView)findViewById(R.id.mtextView2);
		TextView tv2=(TextView)findViewById(R.id.mtextView3);
		TextView tv3=(TextView)findViewById(R.id.mtextView4);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		EditText editText3 = (EditText) findViewById(R.id.editText3);
		EditText editText4 = (EditText) findViewById(R.id.editText4);
		ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton ibutton4 = (ImageButton) findViewById(R.id.imageButton4);
		ImageButton ibutton5 = (ImageButton) findViewById(R.id.imageButton5);
		ImageButton ibutton6 = (ImageButton) findViewById(R.id.imageButton6);
		ImageButton ibutton7 = (ImageButton) findViewById(R.id.imageButton7);
		ImageButton ibutton8 = (ImageButton) findViewById(R.id.imageButton8);
		ImageButton ibutton9 = (ImageButton) findViewById(R.id.imageButton9);
		ImageButton ibutton10 = (ImageButton) findViewById(R.id.imageButton10);
		ImageButton ibutton11 = (ImageButton) findViewById(R.id.imageButton12);
		ImageButton ibutton12 = (ImageButton) findViewById(R.id.imageButton11);
		ibutton.setOnClickListener(new iButtonClickListener());
		ibutton2.setOnClickListener(new iButton2ClickListener());
		ibutton3.setOnClickListener(new iButton3ClickListener());
		ibutton4.setOnClickListener(new iButton4ClickListener());
		ibutton5.setOnClickListener(new iButton5ClickListener());
		ibutton6.setOnClickListener(new iButton6ClickListener());
		ibutton7.setOnClickListener(new iButton7ClickListener());
		ibutton8.setOnClickListener(new iButton8ClickListener());
		ibutton9.setOnClickListener(new iButton9ClickListener());
		ibutton10.setOnClickListener(new iButton10ClickListener());
		ibutton11.setOnClickListener(new iButton11ClickListener());
		ibutton12.setOnClickListener(new iButton12ClickListener());
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setOnClickListener(new ClickListener());
		sp = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp3 = (Spinner) findViewById(R.id.spinner3);
		sp4 = (Spinner) findViewById(R.id.spinner4);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(BUS_NAME, new String[] {ID,NO,NAMEA,NAMEB,START,MEDIA,MEDIA2A,MEDIA2B,MEDIA3,ST,ST2,ST3,ST4}, null, null, null, null, null);
		cur.moveToPosition(i);
		pf2=cur.getString(0);
		editText2.setText(cur.getString(1));
		editText1.setText(cur.getString(2));
		editText4.setText(cur.getString(3));
		editText3.setText(cur.getString(4));
		md[0]=cur.getString(5);
		md[1]=cur.getString(6);
		md[2]=cur.getString(7);
		md[3]=cur.getString(8);
		st[0]=cur.getInt(9);
		st[1]=cur.getInt(10);
		st[2]=cur.getInt(11);
		st[3]=cur.getInt(12);
		cur.close();
		mydb.close();
		sp.setSelection(st[0]);
		sp2.setSelection(st[1]);
		sp4.setSelection(st[2]);
		sp3.setSelection(st[3]);
		tv.setText(md[0].substring(md[0].lastIndexOf("/")+1,md[0].length()));
		tv1.setText(md[1].substring(md[1].lastIndexOf("/")+1,md[1].length()));
		tv3.setText(md[2].substring(md[2].lastIndexOf("/")+1,md[2].length()));
		tv2.setText(md[3].substring(md[3].lastIndexOf("/")+1,md[3].length()));
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
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
         	    mtv.setVisibility(View.GONE);
         	    ibutton3.setVisibility(View.GONE);
         	   break;
               case 2:
       			mtv.setVisibility(View.VISIBLE);
       			mtv.setSelected(true);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
               }
               mp.stop();
   	    	   ump.stop();
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
   	    	   ump.stop();
   	    	   st[1]=po;

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp4.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView mtv=(TextView)findViewById(R.id.mtextView4);
            	ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton10);
            	ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton11);
        		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton12);
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
   	    	   ump.stop();
   	    	   st[2]=po;

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
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
         	    mtv.setVisibility(View.GONE);
         	    ibutton3.setVisibility(View.GONE);
         	   break;
               case 2:
       			mtv.setVisibility(View.VISIBLE);
       			mtv.setSelected(true);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
               }
               mp.stop();
   	    	   ump.stop();
               st[3]=po;

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
	}
	MediaPlayer mp = new MediaPlayer();
	MediaPlayer ump = new MediaPlayer();
	class iButtonClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	if(st[0]==1){
	            mp.stop();
		        ump.stop();
	    		ump = new MediaPlayer();
	    		ump=MediaPlayer.create(getApplicationContext(), R.raw.m1);
	    		ump.start();
			}else{
		    	  try {
			  	      mp.stop();
		    		  mp = new MediaPlayer();
	                  mp.setDataSource(md[0]);
	                  mp.prepare();
	               } catch (IllegalArgumentException e) {
	             } catch (IllegalStateException e) {
	             } catch (IOException e) {
	             }
		          ump.stop();
		          mp.start();
			}
	    }
}
	class iButton2ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    	ump.stop();
	    }
}
	class iButton3ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	   ump.stop();
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
    	TextView tv4=(TextView)findViewById(R.id.mtextView4);
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
    		           }else if(pf==2){
    		        	   md[2]=act.getString(actual);  
    		           }else{
    		        	   md[3]=act.getString(actual);
    		           }
    		       }else{
    		    	   try {
    					if(pf==0){
    						   md[0]=URLDecoder.decode(uri.toString(),"UTF-8");
    			           }else if(pf==1){
    			        	   md[1]=URLDecoder.decode(uri.toString(),"UTF-8");  
    			           }else if(pf==2){
    			        	   md[2]=URLDecoder.decode(uri.toString(),"UTF-8");  
    			           }else{
    			        	   md[3]=URLDecoder.decode(uri.toString(),"UTF-8");
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
    		       }else if(pf==2){
    		    	   tv4.setText(md[2].substring(md[2].lastIndexOf("/")+1,md[2].length()));
    		       }else{
    		    	   tv3.setText(md[3].substring(md[3].lastIndexOf("/")+1,md[3].length()));
    		       }
    		    }
    	}catch(Exception e){
    		AlertDialog.Builder builder = new AlertDialog.Builder(Sqllsname.this);
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
	          ump.stop();
	          mp.start();
	    }
}
	class iButton5ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    	ump.stop();
	    }
}
	class iButton6ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	   ump.stop();
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
	    	if(st[3]==1){
	            mp.stop();
		        ump.stop();
	    		ump = new MediaPlayer();
	    		ump=MediaPlayer.create(getApplicationContext(), R.raw.m2);
	            ump.start();
			}else{
		    	  try {
			  	      mp.stop();
		    		  mp = new MediaPlayer();
	                  mp.setDataSource(md[3]);
	                  mp.prepare();
	               } catch (IllegalArgumentException e) {
	             } catch (IllegalStateException e) {
	             } catch (IOException e) {
	             }
		          ump.stop();
		          mp.start();
			}
	    }
}
	class iButton8ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    	ump.stop();
	    }
}
	class iButton9ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	   ump.stop();
	           mp.stop();
		    	pf=3;
	    	Intent intent = new Intent();
	           intent.setType("audio/*");                                   
	            intent.setAction(Intent.ACTION_GET_CONTENT); 
	            startActivityForResult(intent, 1);
	    }
}
	class iButton10ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	if(st[2]==1){
		    	  try {
			  	      mp.stop();
		    		  mp = new MediaPlayer();
	                  mp.setDataSource(md[2]);
	                  mp.prepare();
	               } catch (IllegalArgumentException e) {
	             } catch (IllegalStateException e) {
	             } catch (IOException e) {
	             }
		          ump.stop();
		          mp.start();
			}
	    }
}
	class iButton11ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    	ump.stop();
	    }
}
	class iButton12ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	   ump.stop();
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
		public void onClick(View V) {mp.stop();
    	ump.stop();
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		EditText editText3 = (EditText) findViewById(R.id.editText3);
		EditText editText4 = (EditText) findViewById(R.id.editText4);
		mydb=openOrCreateDatabase(SQL_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		if("".equals(editText1.getText().toString().trim())&&"".equals(editText4.getText().toString().trim())){
			int i=spf.getInt("sa2", 1);
			cv.put(NAMEA,"未命名");
			cv.put(NAMEB,"的路線"+i);
			SharedPreferences.Editor ed=spf.edit();
		    ed.putInt("sa2", i+1);
        	ed.commit();
		}else{
		    cv.put(NAMEA,editText1.getText().toString());
		    cv.put(NAMEB,editText4.getText().toString());
		}
		if("".equals(editText2.getText().toString().trim())){
			i=spf.getInt("no", 0);
			cv.put(NO,i);
			SharedPreferences.Editor ed=spf.edit();
		    ed.putInt("no", i+1);
        	ed.commit();
		}else{
		    cv.put(NO,editText2.getText().toString());
		}
		if("".equals(editText3.getText().toString().trim())){
			cv.put(START,"歡迎搭乘/a客運/b /c 往 /d路線公車，祝您旅途愉快！");
		}else{
		    cv.put(START,editText3.getText().toString());
		}
		String whereClause="_id=?";
		String[] whereArgs={pf2};
		cv.put(MEDIA,md[0]);
		cv.put(MEDIA2A,md[1]);
		cv.put(MEDIA2B,md[2]);
		cv.put(MEDIA3,md[3]);
		cv.put(ST,st[0]);
		cv.put(ST2,st[1]);
		cv.put(ST3,st[2]);
		cv.put(ST4,st[3]);
    	mydb.update(BUS_NAME, cv, whereClause, whereArgs);
	    mydb.close();
        finish();
		}
	}
	class Button2ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
			mp.stop();
	    	ump.stop();
        finish();
		}
	}class ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Sqllsname.this);
			builder.setTitle("說明");
	    	builder.setMessage(getString(R.string.st));
	    	Gn de = new Gn();
	    	builder.setNegativeButton("確定", de);
	    	AlertDialog ad = builder.create();
	    	ad.show(); 
		}
	}
	private class Gn implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == DialogInterface.BUTTON_NEGATIVE){
				
			}
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id){
		case android.R.id.home:
			mp.stop();
        	ump.stop();
			finish();
			break;
		default: return false;
		}
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	mp.stop();
        	ump.stop();
        	finish();
        }
        return false;
    }
}