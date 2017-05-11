package com.example.prashantpratap.trainenquiry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Prashant Pratap on 10-05-2017.
 */

public class customise_pass_list extends BaseAdapter {

    pnr P;
    Context mContext;


    TextView CURR_ST,BOOK_ST,NO;
    ListView list;

    customise_pass_list(pnr p)
    {
        P=p;



    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(P);
        View v = inf.inflate(R.layout.customise_pass_list, null);

        CURR_ST = (TextView)v.findViewById(R.id.curr_st);
        BOOK_ST = (TextView)v.findViewById(R.id.book_st);
        NO = (TextView)v.findViewById(R.id.s_no);


        CURR_ST.setText(P.Curr_st.get(position));
        BOOK_ST.setText(P.Book_st.get(position));
        NO.setText("Passenger "+P.Pass_no.get(position));




        return v;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return P.Curr_st.size();
    }


}
