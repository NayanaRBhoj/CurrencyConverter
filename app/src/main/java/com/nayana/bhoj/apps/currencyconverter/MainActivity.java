package com.nayana.bhoj.apps.currencyconverter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "http://openexchangerates.org/api/latest.json?app_id=8a4c9504c3414932b7e9e6a9aa2dfe65";
    LinearLayout ll_go;
    EditText edtText;
    IconTextView icontxt1, icontxt2, icontxt3, icontxt4, icontxt_spin;
    IconTextView icontxt_1_inr;
    //IconTextView icontxt_spin1;
    ConnectionDetector connectionDetector;
    LinearLayout ll_all, ll_single;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String cuurentDate = "";
    int comparison = 0;
    LinearLayout ll_upper, ll_lower;
    Spinner spinner_taskstatus;
    ArrayAdapter<String> taskstatus_adapter;
    String str_taskstatus = "";
    ArrayList<String> list_taskstatus;

    Spinner spinFrom;
    ArrayList<String> arrfrom = new ArrayList<>();
    ArrayAdapter<String> fromdataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        connectionDetector = new ConnectionDetector(MainActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        list_taskstatus = new ArrayList<>();
        list_taskstatus.add("USD");
        list_taskstatus.add("AED");
        list_taskstatus.add("EURO");
        list_taskstatus.add("YEN");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        cuurentDate = dateFormat.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        /*try {
            Date date1 = sdf.parse(cuurentDate);
            Date date2 = sdf.parse(sharedpreferences.getString("cuurentDate", ""));
            comparison = date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            comparison = 0;
        }*/

        ll_single = (LinearLayout) findViewById(R.id.ll_single);
        ll_single.setVisibility(View.GONE);
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        ll_all.setVisibility(View.GONE);
        ll_upper = (LinearLayout) findViewById(R.id.ll_upper);
        ll_lower = (LinearLayout) findViewById(R.id.ll_lower);
        ll_upper.setVisibility(View.GONE);
        ll_lower.setVisibility(View.GONE);
        icontxt_1_inr = (IconTextView) findViewById(R.id.icontxt_1_inr);
        //icontxt_spin1 = (IconTextView) findViewById(R.id.icontxt_spin1);
        //icontxt_spin1.setVisibility(View.GONE);
        ll_go = (LinearLayout) findViewById(R.id.ll_go);
        edtText = (EditText) findViewById(R.id.edtText);
        icontxt1 = (IconTextView) findViewById(R.id.icontxt1);
        icontxt2 = (IconTextView) findViewById(R.id.icontxt2);
        icontxt3 = (IconTextView) findViewById(R.id.icontxt3);
        icontxt4 = (IconTextView) findViewById(R.id.icontxt4);
        icontxt_spin = (IconTextView) findViewById(R.id.icontxt_spin);
        icontxt_spin.setVisibility(View.GONE);
        /*if (!(comparison == 0)) {
            if (connectionDetector.isConnectingToInternet()) {
                getValueFromOpenExchangeRates("1", "single");
            } else {
                showAlertDialog(MainActivity.this, "No Internet Connection",
                        "You don't have internet connection.", false);
            }
        } else {
            String JSON = sharedpreferences.getString("Jsonstr", "");
            if (JSON.trim().toString().length() > 0)
                getOfflineData(JSON, "1");
            else
                getValueFromOpenExchangeRates("1", "single");
        }*/

        ll_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtText.getWindowToken(), 0);

                ll_all.setVisibility(View.VISIBLE);
                icontxt_spin.setVisibility(View.VISIBLE);
                if (!edtText.getText().toString().equals("")) {
                    //String JSON = sharedpreferences.getString("Jsonstr", "");
                    /*if (JSON.trim().toString().length() > 0) {
                        getOfflineData(JSON, edtText.getText().toString());
                    } else {
                        getValueFromOpenExchangeRates(edtText.getText().toString(), "multiple");
                    }*/
                    getValueFromOpenExchangeRates(edtText.getText().toString(), "multiple");
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter a USD value!",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        spinFrom = (Spinner) findViewById(R.id.spnFrom);
        arrfrom.clear();
        arrfrom.add("Select Currency");
        arrfrom.add("USD");
        arrfrom.add("AED");
        arrfrom.add("EURO");
        arrfrom.add("YEN");

        fromdataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrfrom) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        fromdataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinFrom.setAdapter(fromdataAdapter);
        fromdataAdapter.notifyDataSetChanged();

        spinFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!spinFrom.getSelectedItem().equals("Select Currency")) {

                    Toast.makeText(MainActivity.this, "" + spinFrom.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }




    public static void showAlertDialog(Context context, String title,
                                       String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void getValueFromOpenExchangeRates(final String str_usd, final String label) {
        if (connectionDetector.isConnectingToInternet()) {
            //icontxt_spin1.setVisibility(View.VISIBLE);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(API_URL, new AsyncHttpResponseHandler() {

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    // TODO Auto-generated method stub
                    super.onFailure(arg0, arg1);
                    //if (icontxt_spin1.getVisibility() == View.VISIBLE)
                    //icontxt_spin1.setVisibility(View.GONE);
                    if (icontxt_spin.getVisibility() == View.VISIBLE)
                        icontxt_spin.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    super.onFinish();
                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub
                    super.onStart();
                }

                @Override
                public void onSuccess(String response) {
                    //icontxt_spin1.setVisibility(View.GONE);
                    icontxt_spin.setVisibility(View.GONE);
                    try {
                        Log.d("RESPONSE", response);
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject ratesObject = jsonObj.getJSONObject("rates");

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Jsonstr", ratesObject.toString());
                        editor.putString("cuurentDate", cuurentDate);
                        editor.commit();

                        Double inrRate = ratesObject.getDouble("INR");
                        Double eurRate = ratesObject.getDouble("EUR");
                        Double aedRate = ratesObject.getDouble("AED");
                        Double yenRate = ratesObject.getDouble("JPY");

                        Double usds = Double.valueOf(str_usd);
                        Double inrs = usds * inrRate;
                        Double euros = usds * eurRate;
                        Double aeds = usds * aedRate;
                        Double yen = usds * yenRate;
                        ll_upper.setVisibility(View.VISIBLE);
                        ll_lower.setVisibility(View.VISIBLE);

                        if (label.equalsIgnoreCase("single")) {
                            icontxt_1_inr.setText(String.valueOf(inrs) + " {fa-inr}");
                        } else {
                            icontxt1.setText(" {fa-inr} " + inrs);
                            icontxt2.setText(" {fa-euro} " + euros);
                            icontxt3.setText(" {fa-eur} " + aeds);
                            icontxt4.setText(" {fa-yen} " + yen);

                            ll_single.setVisibility(View.VISIBLE);
                            Double usds_1 = Double.valueOf("1");
                            Double inrs_1 = usds_1 * inrRate;
                            icontxt_1_inr.setText("{fa-inr} " + String.valueOf(inrs_1));
                        }


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        } else {
            showAlertDialog(MainActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    public void getOfflineData(String JSONdate, String str_usd) {
        try {
            JSONObject jsonObj = new JSONObject(JSONdate);
            JSONObject ratesObject = jsonObj.getJSONObject("rates");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Jsonstr", ratesObject.toString());
            editor.putString("cuurentDate", cuurentDate);
            editor.commit();


            Double inrRate = ratesObject.getDouble("INR");
            Double eurRate = ratesObject.getDouble("EUR");
            Double aedRate = ratesObject.getDouble("AED");
            Double yenRate = ratesObject.getDouble("JPY");

            Double usds = Double.valueOf(str_usd);
            Double inrs = usds * inrRate;
            Double euros = usds * eurRate;
            Double aeds = usds * aedRate;
            Double yen = usds * yenRate;
            icontxt1.setText(inrs + " {fa-inr}");
            icontxt2.setText(euros + " {fa-euro}");
            icontxt3.setText(aeds + " {fa-eur}");
            icontxt4.setText(yen + " {fa-yen}");
            icontxt_1_inr.setText(String.valueOf(inrs) + " {fa-inr}");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
