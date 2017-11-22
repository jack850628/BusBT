package com.example.busbt;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Listview extends BaseAdapter {
	
	private LayoutInflater myInflater;
    ArrayList<HashMap> list = null;
 
    public Listview(Context context, ArrayList<HashMap> list){
        myInflater = LayoutInflater.from(context);
        this.list = list;
    }
    
    public void setListview(ArrayList<HashMap> list){
    	this.list = list;
    }
 
    public int getCount() {
        return list.size();
    }
    
    public Object getItem(int position) {
        return list.get(position);
    }
    
    public long getItemId(int position) {
        return Long.valueOf((Integer)list.get(position).get("id"));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	 
        ViewTag viewTag;
 
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.listview, null);
 
            viewTag = new ViewTag(
                    (TextView) convertView.findViewById(R.id.textView1),
                    (ImageView) convertView.findViewById(R.id.imageView1)
            );
 
            convertView.setTag(viewTag);
        } else {
            viewTag = (ViewTag) convertView.getTag();
        }
 
        viewTag.text1.setText((CharSequence) list.get(position).get("name"));
        if((Integer) list.get(position).get("pf")==1){
        	if((Integer) list.get(position).get("tp")==1){
           	 viewTag.image1.setImageResource(R.drawable.run);
           }else if((Integer) list.get(position).get("tp")==2){
          	     viewTag.image1.setImageResource(R.drawable.ok);
           }else{
           	viewTag.image1.setImageResource(R.drawable.on);
           }
        }else{
        	viewTag.image1.setVisibility(View.GONE);
        }
 
        return convertView;
    }
    
    public class ViewTag{
        TextView text1;
        ImageView image1;
 
        public ViewTag(TextView textview1,ImageView imageView1){
            this.text1 = textview1;
            this.image1 = imageView1;
        }
    }
}
