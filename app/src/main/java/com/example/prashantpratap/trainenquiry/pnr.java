package com.example.prashantpratap.trainenquiry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Prashant Pratap on 10-05-2017.
 */

public class pnr extends Activity {

    String API_KEY = "rayr3fsp";
    String PNR;
    EditText pnr;
    TextView pnr_no, train_name, source_dest, date, boarding, res_upto, res_class, chart_status;
    ProgressDialog pd;
    LinearLayout PNR_Entery,PNR_Details;
    ListView list;

    ArrayList<String> Book_st;
    ArrayList<String> Curr_st;
    ArrayList<String> Pass_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pnr);

        Book_st = new ArrayList<String>();
        Curr_st = new ArrayList<String>();
        Pass_no = new ArrayList<String>();

        list = (ListView)findViewById(R.id.passen_list);
        PNR_Entery = (LinearLayout)findViewById(R.id.pnr_entry);
        PNR_Details = (LinearLayout)findViewById(R.id.pnr_details);
        pnr = (EditText) findViewById(R.id.PNR);
        pnr_no = (TextView) findViewById(R.id.pnr_no);
        train_name = (TextView) findViewById(R.id.train_name);

        date = (TextView) findViewById(R.id.date);
        boarding = (TextView) findViewById(R.id.Boarding_point);
        res_upto = (TextView) findViewById(R.id.reserved_upto);
        res_class = (TextView) findViewById(R.id.res_class);
        chart_status = (TextView) findViewById(R.id.charting_status);

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////Async Task/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = params[0];




            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line);

                }

                return sb.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();

            try {



                JSONObject myJson = new JSONObject(sb.toString());

                String code = myJson.getString("response_code");
                String doj = myJson.getString("doj");
                String pnr = myJson.getString("pnr");
                String chart = myJson.getString("chart_prepared");
                String r_class = myJson.getString("class");
                String t_name = myJson.getString("train_name");
                String t_num = myJson.getString("train_num");

                JSONObject json;
                /////////////////////////////////////
                json = myJson.getJSONObject("boarding_point");
                String source_code =  json.getString("code");
                String source_name = json.getString("name");
                /////////////////////////////////////

                /////////////////////////////////////
                 json = myJson.getJSONObject("reservation_upto");
                String dest_code =  json.getString("code");
                String dest_name = json.getString("name");
                /////////////////////////////////////



                pnr_no.setText("PNR : "+pnr);
                train_name.setText(t_name+" ("+t_num+")");

               date.setText("Boarding Date : "+doj);
                boarding.setText("Boarding Point : "+source_name+" ("+source_code+")");
                res_upto.setText("Reserved Upto : "+dest_name+" ("+dest_code+")");

                res_class.setText("CLASS : "+r_class);
                if(chart.equals("N"))
                {
                    chart_status.setText("CHART NOT PREPARED");
                }
                else
                {
                    chart_status.setText("CHART PREPARED");
                }

                //////////////////////////////////////////////////////
                JSONObject j;

                String booking_st,curr_st,no;
                JSONArray pass = myJson.getJSONArray("passengers");
                for(int p=0;p<pass.length();p++)
                {
                     j = pass.getJSONObject(p);
                     booking_st = j.getString("booking_status");
                    curr_st = j.getString("current_status");
                    no = j.getString("no");

                    Book_st.add(booking_st);
                    Pass_no.add(no);
                    Curr_st.add(curr_st);
                }


                customise_pass_list adp = new customise_pass_list(pnr.this);
                list.setAdapter(adp);

                ViewGroup.LayoutParams params = list.getLayoutParams();
                params.height = 75* (adp.getCount());

                list.setLayoutParams(params);
                list.requestLayout();
                ////////////////////////////////////////////////////////

                PNR_Entery.setVisibility(View.GONE);
                PNR_Details.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();


            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(pnr.this, "", "Fetching data..Please Wait...", false);

        }


    }

    ///////////////////////////Task End//////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////


    public void pnr_status(View view) {

        PNR = pnr.getText().toString();
        if (PNR.length() != 10) {
            Toast.makeText(this, "Please Enter a Valid PNR Number", Toast.LENGTH_SHORT).show();
        }
        else {
            String link = "http://api.railwayapi.com/pnr_status/pnr/"+PNR+"/apikey/rayr3fsp/";
            /*String link = "http://onlinevotingnitp.000webhostapp.com/pnr.php";*/


                  MyTask t = new MyTask();
                  t.execute(link);


        }
    }
}