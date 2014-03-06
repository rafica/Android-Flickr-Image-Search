package com.example.imagesearch;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagesearch.FlickrParser.Entry;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private List<Entry> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, List<Entry> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item, null);

        TextView owner=(TextView)vi.findViewById(R.id.owner);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        owner.setText(data.get(position).getOwner());
        
       
        
        TextView desc =(TextView)vi.findViewById(R.id.description);
        desc.setText(data.get(position).getDescription());
        
        TextView date = (TextView)vi.findViewById(R.id.date);
        date.setText(data.get(position).getDate());
        
        imageLoader.DisplayImage(data.get(position).getUrl(), image);
        return vi;
    }
    
    

}

