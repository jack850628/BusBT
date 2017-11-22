package com.example.busbt;

import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class St extends ActionBarActivity{
	private Spinner sp,sp2,sp3,sp4,sp5,sp6,sp7;
	private SQLiteDatabase mydb=null;
	private final static String ST="st";
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
	int[] pf=new int[5];
	String[] pf2=new String[]{"0","0","0","50"};
	int[] sd=new int[3];
	Mtv tv5;
	LinearLayout ll;
	TextView tv,tv2,tv3,tv4;
	EditText editText1,editText2,editText3,editText4,editText5;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.st);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("設定");
		tv=(TextView)findViewById(R.id.textView2);
		tv.setVisibility(View.GONE);
		tv2=(TextView)findViewById(R.id.textView4);
		tv2.setVisibility(View.GONE);
		tv3=(TextView)findViewById(R.id.textView13);
		tv3.setVisibility(View.GONE);
		tv4=(TextView)findViewById(R.id.textView16);
		tv4.setVisibility(View.GONE);
		tv5 = new Mtv(this);
		tv5.setText("跑馬燈樣式範例!!");
		tv5.setSelected(true);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.addTextChangedListener(watcher); 
		editText2 = (EditText) findViewById(R.id.editText2);
		editText2.addTextChangedListener(watcher2); 
		editText3 = (EditText) findViewById(R.id.editText3);
		editText3.addTextChangedListener(watcher3); 
		editText4 = (EditText) findViewById(R.id.editText4);
		editText4.addTextChangedListener(watcher4); 
		editText5 = (EditText) findViewById(R.id.editText5);
		editText5.addTextChangedListener(watcher5); 
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new Button3ClickListener());
		sp = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp3 = (Spinner) findViewById(R.id.spinner3);
		sp4 = (Spinner) findViewById(R.id.spinner4);
		sp5 = (Spinner) findViewById(R.id.spinner5);
		sp6 = (Spinner) findViewById(R.id.spinner6);
		sp7 = (Spinner) findViewById(R.id.spinner7);
		mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
		Cursor cur=mydb.query(ST, new String[] {SP,SP2,SP3,SP4,SP5,EX,EX2,EX3,EX4,SD,NS,NS2}, null, null, null, null, null);
		cur.moveToPosition(0);
		pf[0]=cur.getInt(0);
		pf[1]=cur.getInt(1);
		pf[2]=cur.getInt(2);
		pf[3]=cur.getInt(3);
		pf[4]=cur.getInt(4);
		pf2[0]=cur.getString(5);
		pf2[1]=cur.getString(6);
		pf2[2]=cur.getString(7);
		pf2[3]=cur.getString(8);
		sd[0]=cur.getInt(9);
		sd[1]=cur.getInt(10);
		sd[2]=cur.getInt(11);
		cur.close();
		mydb.close();
		ll = (LinearLayout) findViewById(R.id.ll);  
	    ll.addView(tv5, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tv5.scrollText(sd[0]);  
		editText1.setText(pf2[0]);
		editText2.setText(pf2[1]);
		editText3.setText(String.valueOf(sd[0]));
		editText4.setText(pf2[2]);
		editText5.setText(pf2[3]);
		sp.setSelection(pf[0]);
		sp2.setSelection(pf[1]);
		sp3.setSelection(pf[2]);
		sp4.setSelection(sd[1]);
		sp5.setSelection(sd[2]);
		sp6.setSelection(pf[3]);
		sp7.setSelection(pf[4]);
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView tv3=(TextView)findViewById(R.id.textView7);
               switch(po){
               case 0:
            	   tv.setVisibility(View.GONE);
          			tv3.setVisibility(View.GONE);
          			editText1.setVisibility(View.GONE);
            	   break;
               case 1:
    			tv.setVisibility(View.VISIBLE);
      			editText1.setVisibility(View.VISIBLE);
   			    if(Double.valueOf(editText1.getText().toString())>500){
   			    	tv3.setVisibility(View.VISIBLE);
   			    }
         	   break;
               }
               pf[0]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp2.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView tv4=(TextView)findViewById(R.id.textView8);
               switch(po){
               case 0:
            	   tv2.setVisibility(View.GONE);
          			tv4.setVisibility(View.GONE);
          			editText2.setVisibility(View.GONE);
            	   break;
               case 1:
    			tv2.setVisibility(View.VISIBLE);
      			editText2.setVisibility(View.VISIBLE);
   			    if(Double.valueOf(editText2.getText().toString())>300){
   			    	tv4.setVisibility(View.VISIBLE);
   			    }
         	   break;
               }
  			    pf[1]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp6.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView tv4=(TextView)findViewById(R.id.textView14);
               switch(po){
               case 0:
            	   tv3.setVisibility(View.GONE);
          			tv4.setVisibility(View.GONE);
          			editText4.setVisibility(View.GONE);
            	   break;
               case 1:
    			tv3.setVisibility(View.VISIBLE);
      			editText4.setVisibility(View.VISIBLE);
   			    if(Double.valueOf(editText4.getText().toString())>100){
   			    	tv4.setVisibility(View.VISIBLE);
   			    }
         	   break;
               }
  			    pf[3]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp7.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView tv=(TextView)findViewById(R.id.textView17);
               switch(po){
               case 0:
            	   tv4.setVisibility(View.GONE);
          			tv.setVisibility(View.GONE);
          			editText5.setVisibility(View.GONE);
            	   break;
               case 1:
    			tv4.setVisibility(View.VISIBLE);
      			editText5.setVisibility(View.VISIBLE);
   			    if(Double.valueOf(editText5.getText().toString())>50){
   			    	tv.setVisibility(View.VISIBLE);
   			    }
         	   break;
               }
  			    pf[4]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp3.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
               switch(po){
               case 0:
            	   tv5.setTextSize(70);
            	   break;
               case 1:
            	   tv5.setTextSize(100);
   			 break;
               case 2:
            	   tv5.setTextSize(130);
         	   break;
               case 3:
            	   tv5.setTextSize(160);
         	   break;
               case 4:
            	   tv5.setTextSize(190);
         	   break;
               case 5:
            	   tv5.setTextSize(220);
         	   break;
               case 6:
            	   tv5.setTextSize(250);
         	   break;
               }
  			    pf[2]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp4.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
               switch(po){
               case 0:
            	   ll.setBackgroundColor(Color.BLACK);
            	   break;
               case 1:
            	   ll.setBackgroundColor(Color.WHITE);
   			        break;
               }
			        sd[1]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		sp5.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
               switch(po){
               case 0:
            	   tv5.setTextColor(android.graphics.Color.RED);
            	   break;
               case 1:
            	   tv5.setTextColor(android.graphics.Color.YELLOW);
   			        break;
               case 2:
            	   tv5.setTextColor(android.graphics.Color.BLUE);
   			        break;
               case 3:
            	   tv5.setTextColor(android.graphics.Color.BLACK);
   			        break;
               case 4:
            	   tv5.setTextColor(android.graphics.Color.WHITE);
   			        break;
               }
			        sd[2]=po;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
	}
	private  TextWatcher watcher =  new  TextWatcher(){  
		  
        @Override  
        public  void  afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  beforeTextChanged(CharSequence s,  int  start,  int  count,  
                int  after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  onTextChanged(CharSequence s,  int  start,  int  before,  
                int  count) {  
        	TextView tv=(TextView)findViewById(R.id.textView7);
			if("".equals(editText1.getText().toString().trim())){
				tv.setVisibility(View.GONE);
				pf2[0]="0";
			}else{
				pf2[0]=editText1.getText().toString();
				tv.setVisibility(View.GONE);
				if(Double.valueOf(editText1.getText().toString())>500){
   			    	tv.setVisibility(View.VISIBLE);
   			    }
			}
        }  
          
    };
    private  TextWatcher watcher2 =  new  TextWatcher(){  
		  
        @Override  
        public  void  afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  beforeTextChanged(CharSequence s,  int  start,  int  count,  
                int  after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  onTextChanged(CharSequence s,  int  start,  int  before,  
                int  count) {  
        	TextView tv=(TextView)findViewById(R.id.textView8);
        	if("".equals(editText2.getText().toString().trim())){
				tv.setVisibility(View.GONE);
				pf2[1]="0";
			}else{
				pf2[1]=editText2.getText().toString();
				tv.setVisibility(View.GONE);
				if(Double.valueOf(editText2.getText().toString())>300){
   			    	tv.setVisibility(View.VISIBLE);
   			    }
			}
        }  
          
    };
    private  TextWatcher watcher4 =  new  TextWatcher(){  
		  
        @Override  
        public  void  afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  beforeTextChanged(CharSequence s,  int  start,  int  count,  
                int  after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  onTextChanged(CharSequence s,  int  start,  int  before,  
                int  count) {  
        	TextView tv=(TextView)findViewById(R.id.textView14);
        	if("".equals(editText4.getText().toString().trim())){
				tv.setVisibility(View.GONE);
				pf2[2]="0";
			}else{
				pf2[2]=editText4.getText().toString();
				tv.setVisibility(View.GONE);
				if(Double.valueOf(editText4.getText().toString())>100){
   			    	tv.setVisibility(View.VISIBLE);
   			    }
			}
        }  
          
    };
    private  TextWatcher watcher5 =  new  TextWatcher(){  
		  
        @Override  
        public  void  afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  beforeTextChanged(CharSequence s,  int  start,  int  count,  
                int  after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  onTextChanged(CharSequence s,  int  start,  int  before,  
                int  count) {  
        	TextView tv=(TextView)findViewById(R.id.textView17);
			if("".equals(editText5.getText().toString().trim())){
				tv.setVisibility(View.GONE);
				pf2[3]="0";
			}else{
				pf2[3]=editText5.getText().toString();
				tv.setVisibility(View.GONE);
				if(Double.valueOf(editText5.getText().toString())>50){
   			    	tv.setVisibility(View.VISIBLE);
   			    }
			}
        }  
          
    };
    private  TextWatcher watcher3 =  new  TextWatcher(){  
		  
        @Override  
        public  void  afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  beforeTextChanged(CharSequence s,  int  start,  int  count,  
                int  after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public  void  onTextChanged(CharSequence s,  int  start,  int  before,  
                int  count) {
        	if("".equals(editText3.getText().toString().trim())){
    			sd[0]=1;
			}else{
				sd[0]=Integer.valueOf(editText3.getText().toString());
			}
    		if(sd[0]>100){
    			editText3.setText("100");
    			sd[0]=100;
    			AlertDialog.Builder builder = new AlertDialog.Builder(St.this);
    			builder.setTitle("注意!!");
    	    	builder.setMessage("速度值不可以大於100");
    	    	Gn de = new Gn();
    	    	builder.setNegativeButton("確定", de);
    	    	builder.show();
    		}
			tv5.scrollText(sd[0]);
        }  
          
    };
    private class Gn implements
	android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE){
   			dialog.dismiss();
		}
		}
	}
	class ButtonClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
		mydb=openOrCreateDatabase("st.db", MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		String whereClause="_id=?";
		String[] whereArgs={"1"};
		cv.put(SP,pf[0]);
		cv.put(SP2,pf[1]);
		cv.put(SP3,pf[2]);
		cv.put(SP4,pf[3]);
		cv.put(SP5,pf[4]);
		cv.put(EX,pf2[0]);
		cv.put(EX2,pf2[1]);
		cv.put(EX3,pf2[2]);
		cv.put(EX4,pf2[3]);
		cv.put(SD,sd[0]);
		cv.put(NS,sd[1]);
		cv.put(NS2,sd[2]);
    	mydb.update(ST, cv, whereClause, whereArgs);
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
	class Button3ClickListener implements  OnClickListener {
		@Override
		public void onClick(View V) {
			 Intent intent = new Intent ();
			    intent.setClass(St.this,St2.class);
			    St.this.startActivity(intent);
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
