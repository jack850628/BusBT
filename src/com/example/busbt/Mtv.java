package com.example.busbt;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

public class Mtv extends TextView { 
	   Context context;  
	   DisplayMetrics dm;
	   Scroller mScroller;  
	   int mDistance;  
	   int mDuration; 
	   int a=0;
	   float mVelocity;  
	   boolean pf=false,gps=false;
	   double x,y,gpsx,gpsy,count,gl;
	   String[] st;
	   Thread th;
	   public Mtv(Context context, AttributeSet attrs, int defStyle) {  
	     super(context, attrs, defStyle);  
	     this.context = context;  
	     setSingleLine();  
	     // 在 new Scroller 時候加入 LinearInterpolater(線性插補器) 參數  
	     // 此參數可以讓我們的跑馬燈以線性等速度移動，不然預設是黏滯地(viscous)移動  
	     mScroller = new Scroller(context, new LinearInterpolator());  
	     setScroller(mScroller);  
	   }  
	   public Mtv(Context context, AttributeSet attrs) {  
	     super(context, attrs);  
	     this.context = context;  
	     setSingleLine();  
	     mScroller = new Scroller(context, new LinearInterpolator());  
	     setScroller(mScroller);  
	   }  
	   public Mtv(Context context) {  
	     super(context);  
	     this.context = context;  
	     setSingleLine();  
	     mScroller = new Scroller(context, new LinearInterpolator());  
	     setScroller(mScroller);  
	   }  
	   @Override  
	   public void computeScroll() {  
	     super.computeScroll(); 
	     if(mScroller.computeScrollOffset()){  
	       scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
	     } else {
	       if(mScroller.isFinished()&&!gps){  
	    	   if(pf&&st.length!=0){
	    		   this.setText(st[a]);
	    		   a++;
		    	   if(a>=st.length){
		    		   a=0;
		    	   }
	    	   }
	         mScroller.startScroll(-dm.widthPixels, 0, calculateMoveDistance(false,mVelocity), 0, mDuration);  
	       }else if(gps){
		    	 th = new Thread(Run);
	             th.start();
	       }
	     }  
	   }  
	   Runnable Run=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
		     if(gps){
		    	 count=Math.sqrt(Math.pow(x-gpsx,2)+Math.pow(y-gpsy,2))/0.00001;
		    	 gps=(count>=gl)?false:true;
		    	 if(gps){
			    	 th = new Thread(Run);
		             th.start();
		    	 }else{
		    		 mHandler.sendEmptyMessage(1);
		    	 }
		     }
		}
	   };
	   private Handler mHandler = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	switch(msg.what) {
	            case 1:
	            	Mtv.this.setSelected(true);
		    		computeScroll();
	        	}
	        }
	    };
	   public void pf(boolean p){
		   pf=p;
	   }
	   public void st2(String[] st,String[] stt,double gl){
		   this.gl=gl;
		   for(int t=0;t<st.length;t++){
			   for(int i=0;i<4;i++)
				   st[t]=st[t].replace((i==0)?"/a":(i==1)?"/b":
						(i==2)?"/c":"/d", stt[i]);
		   }
		   this.st=st;
	   }
	   public void st(){
		   mScroller.startScroll(-dm.widthPixels, 0, calculateMoveDistance(false,mVelocity), 0, mDuration);
	   }
	   public void tg(double x,double y){
		   this.x=x;
		   this.y=y;
		   mScroller.startScroll(-dm.widthPixels, 0, calculateMoveDistance(true,mVelocity), 0, mDuration);
	   }
	   public void gps(double x,double y){
		   gpsx=x;
		   gpsy=y;
	   }
	   /**  
	    * 計算應該移動的距離  
	    * @param velocity 速率  
	    * @return  
	    */  
	   private int calculateMoveDistance(boolean pf,float velocity){  
		 gps=pf;
	     Rect rect = new Rect();  
	     String textString = (String) getText();  
	     getPaint().getTextBounds(textString, 0, textString.length(), rect);  
	     int moveDistance = rect.width();  
	     rect = null;  
	     this.mDistance =(pf)? dm.widthPixels: moveDistance + dm.widthPixels;  
	     this.mDuration = (int) (velocity * mDistance);  
	     return this.mDistance;  
	   }  
	   /**  
	    * 從這裡設定速度值  
	    * @param velocity 速度值越小越快  
	    */  
	   public void scrollText(float velocity){  
	     this.mVelocity = velocity; 
	     dm = getResources().getDisplayMetrics();
	     //mScroller.startScroll(0, 0, calculateMoveDistance(true, velocity), 0, mDuration);  
	     mScroller.startScroll(-dm.widthPixels, 0, calculateMoveDistance(false,velocity), 0, mDuration);
	   }  
	 }  