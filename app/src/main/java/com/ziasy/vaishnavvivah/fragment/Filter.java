package com.ziasy.vaishnavvivah.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.filterAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class Filter extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup ;
    EditText etFromDate, etToDate ;
    Button btnFilter ;
    Spinner spGotra, spProfession;
    ListView listFilter ;
    ConnectionDetector cd;
    SessionManagment sd;
    ProgressDialog pd ;
    String profile="",age="",gotra="",fromDate="",toDate="",first="",second="",
            finalFromDate="",finalToDate="",profession="",occupation="",got="";
    int year=0,month=0,date=0;
    filterAdapter filterAdapter ;
    APIInterface apiInterface ;
    Calendar myCalendar;
    TextView tvFilter ;
    LinearLayout llFilter ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterlayout);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        radioGroup=(RadioGroup)findViewById(R.id.rdProfile);
        spGotra=(Spinner)findViewById(R.id.spGotra);
        spProfession=(Spinner)findViewById(R.id.spProfession);
        etFromDate=(EditText) findViewById(R.id.etFromDate);
        etToDate=(EditText) findViewById(R.id.etToDate);
        tvFilter=(TextView)findViewById(R.id.tvFilter);
        btnFilter=(Button) findViewById(R.id.btnFilter);
        listFilter=(ListView)findViewById(R.id.filterList);
        llFilter=(LinearLayout) findViewById(R.id.llFilterView);
        cd = new ConnectionDetector(Filter.this);
        sd = new SessionManagment(Filter.this);
        pd= new ProgressDialog(Filter.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        myCalendar = Calendar.getInstance();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter profiles");



        spGotra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gotra = (String) spGotra.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                profession = (String) spProfession.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rdBride:
                        profile = "female";
                        break;
                    case R.id.rdGroom:
                        profile = "male";
                        break;

                }
            }
        });

        btnFilter.setOnClickListener(this);
        tvFilter.setOnClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

   

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.btnFilter :

               year = Calendar.getInstance().get(Calendar.YEAR);
               month=Calendar.getInstance().get(Calendar.MONTH);
               date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

               fromDate = etFromDate.getText().toString();
               toDate = etToDate.getText().toString();

               if(!fromDate.isEmpty() && !toDate.isEmpty()) {
                   first = String.valueOf(year - Integer.parseInt(fromDate));
                   second = String.valueOf(year - Integer.parseInt(toDate));

                   finalFromDate = first+"-"+month+"-"+date;
                   finalToDate = second+"-"+month+"-"+date;
               }

               if(profession.equals("occupation")){
                   occupation = "";
               }else{
                   occupation = profession;
               }


               if(gotra.equals("गोत्र")){
                   got = "";
               }else{
                   got = gotra;
               }

               try {
                   if (fromDate.equals("") && toDate.equals("") && gotra.equals("गोत्र") && profile.equals("") && profession.equals("occupation")) {
                       Toast.makeText(Filter.this, "Please select at least one thing for filter the profiles", Toast.LENGTH_SHORT).show();
                   } else if (Integer.parseInt(fromDate) < 18) {
                       Toast.makeText(Filter.this, "Enter Age more than 18 years", Toast.LENGTH_SHORT).show();
                   } else if(Integer.parseInt(toDate) < 18){
                       Toast.makeText(Filter.this, "Enter Age more than 18 years", Toast.LENGTH_SHORT).show();
                   }else {
                       add(finalFromDate, finalToDate, profile, got, occupation);
                   }
               }catch (Exception e){
                   Toast.makeText(Filter.this,"Please enter more filter",Toast.LENGTH_SHORT).show();
               }
               break;
           case R.id.tvFilter:
               tvFilter.setVisibility(View.GONE);
               llFilter.setVisibility(View.VISIBLE);
               listFilter.setVisibility(View.GONE);
               break;
       }
    }


    public  void add(String fage,String tage,String profle,String got,String pro){
        year =   Calendar.getInstance().get(Calendar.YEAR);
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(Filter.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("age_from",fage);
            object.addProperty("age_to",tage);
            object.addProperty("search_for",profle);
            object.addProperty("gotra",got);
            object.addProperty("profession",pro);

            Call<Login_model> call = apiInterface.getFilter(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    try {
                        pd.dismiss();
                        Login_model resource = response.body();

                        if (resource.status.equals("success")) {
                            hideSoftKeyboard();
                            tvFilter.setVisibility(View.VISIBLE);
                            llFilter.setVisibility(View.INVISIBLE);
                            listFilter.setVisibility(View.VISIBLE);
                            filterAdapter = new filterAdapter(Filter.this, resource.user, year);
                            listFilter.setAdapter(filterAdapter);
                        } else if (resource.status.equals("no profile found")) {
                            hideSoftKeyboard();
                            Toast.makeText(Filter.this, "No profile according to this search...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Filter.this, "Something went wrong.....", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(Filter.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    pd.dismiss();
                }
            });
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }




}
