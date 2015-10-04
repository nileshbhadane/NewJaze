package com.jazeit.jazeitapp.custom_class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jazeit.jazeitapp.Product_listing;
import com.jazeit.jazeitapp.R;
import com.jazeit.jazeitapp.SubCatagories;

import java.util.ArrayList;

/**
 * Created by root on 11/9/15.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result = new ArrayList<String>();
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(SubCatagories mainActivity, ArrayList<String> prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_row, null);
        holder.tv=(TextView) rowView.findViewById(R.id.title);
        int imgSize = 0;

        holder.tv.setText(result.get(position));
        holder.img=(ImageView) rowView.findViewById(R.id.list_image);

//        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               // Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Product_listing.class);
                intent.putExtra("SubCategory",result.get(position));
                context.startActivity(intent);

            }
        });
        return rowView;
    }

}