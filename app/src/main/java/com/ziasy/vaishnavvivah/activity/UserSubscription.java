package com.ziasy.vaishnavvivah.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.BuildConfig;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class UserSubscription extends AppCompatActivity implements View.OnClickListener {

    Calendar myCalendar;
    CircleImageView circleImageView ;
    DatePickerDialog.OnDateSetListener date;
    EditText etGender,etPrefix,etName,etmobile,etemail,etMother,etFather,
    etAddress,etCity,etState;
    TextView tvDob ;
    String strName="",strMobile="",strEmail="",strEducation="",strGotra="",strMaritStatus="",
    stroccupation="",strDob="",strGender="",strMother="",strFather="",strMotherOCc="",strFatherOCc="",
    strAddress="",strCity="",strstate="",strIncome="",strProfilefor="",strPrefix="";
    Spinner spEducation,spOccupation,spMarit,spFatherOcc,spMotherOcc,spIncome,spGender,spGotra,spProfileFor,spPrefix;
    TextView tvUpdate ;
    ConnectionDetector cd;
    SessionManagment sd ;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    String selectedPath1 = "";
    private static int RESULT_LOAD_IMAGE = 1;
    String encImage = "",path="false";
    String imgPath= "http://joietouch.com/matrimoney/images/";
    String currentDateTimeString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_layout);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User profile");

        etName=(EditText)findViewById(R.id.etName);
        etName.setFocusable(false);
        etName.setClickable(false);

        etemail=(EditText)findViewById(R.id.etEmail);
        etemail.setFocusable(false);
        etemail.setClickable(false);

        etmobile=(EditText)findViewById(R.id.etMobile);
        etmobile.setFocusable(false);
        etmobile.setClickable(false);

        tvDob=(TextView) findViewById(R.id.tvDOB);
        etMother=(EditText)findViewById(R.id.etMother);
        etFather=(EditText)findViewById(R.id.etFather);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etCity=(EditText)findViewById(R.id.etCity);
        etState=(EditText)findViewById(R.id.etState);


        tvUpdate=(TextView)findViewById(R.id.tvUpdate);

        spEducation=(Spinner) findViewById(R.id.spEducation);
        spOccupation=(Spinner) findViewById(R.id.spOccupation);
        spMarit=(Spinner) findViewById(R.id.spMaritSttaus);
        spMotherOcc=(Spinner) findViewById(R.id.spMotherOcc);
        spFatherOcc=(Spinner) findViewById(R.id.spFatheroccu);
        spIncome=(Spinner) findViewById(R.id.spIncome);
        spGender=(Spinner) findViewById(R.id.spGnder);
        spGotra=(Spinner) findViewById(R.id.spGotra);
        spProfileFor=(Spinner) findViewById(R.id.spProfileFor);
        spPrefix=(Spinner) findViewById(R.id.spprefix);
        circleImageView =(CircleImageView)findViewById(R.id.profile_image);

        myCalendar = Calendar.getInstance();
        myCalendar.set(1990, Calendar.JANUARY , 1);

        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Prefix, R.layout.myspinner);
       // adapter.setDropDownViewResource(R.layout.myspinner);
        spPrefix.setAdapter(adapter);



       date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Datepicker();
            }

        };


        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strGender = (String) spGender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPrefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPrefix = (String) spPrefix.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spGotra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strGotra = (String) spGotra.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       spProfileFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strProfilefor = (String) spProfileFor.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strEducation = (String) spEducation.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stroccupation = (String) spOccupation.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMarit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMaritStatus = (String) spMarit.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMotherOcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMotherOCc = (String) spMotherOcc.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spFatherOcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strFatherOCc = (String) spFatherOcc.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strIncome = (String) spIncome.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        currentDateTimeString = formatter.format(todayDate);

      tvDob.setOnClickListener(this);
      tvUpdate.setOnClickListener(this);
      circleImageView.setOnClickListener(this);

      view(sd.getKEY_ID());



    }

  public void Datepicker() {
        strDob = String.valueOf(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this.myCalendar.getTime()));
        if (this.strDob.equals(BuildConfig.FLAVOR)) {
            strDob = BuildConfig.FLAVOR;
        }
        tvDob.setText(this.strDob);

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

    public void profile(){

        strName = etName.getText().toString();
        strEmail = etemail.getText().toString();
        strMobile = etmobile.getText().toString();
        strDob = tvDob.getText().toString();
        strAddress = etAddress.getText().toString();
        strCity=etCity.getText().toString();
        strstate=etState.getText().toString();
        strMother=etMother.getText().toString();
        strFather=etFather.getText().toString();


        if(strName.equals("undefined")){
            etName.setError("Please Enter Name");
        }else if(strPrefix.equals("Prefix")){
            Toast.makeText(UserSubscription.this, "Please Select Prefix", Toast.LENGTH_SHORT).show();
        }else if(strMobile.equals("undefined")){
            etmobile.setError("Please Enter Mobile No.");
        }else if(strEmail.equals("undefined")){
            etemail.setError("Please Enter Email");
        }else if(strDob.equals("0000-00-00")){
            Toast.makeText(UserSubscription.this,"Please Enter Your DOB",Toast.LENGTH_SHORT).show();
        }else if(strDob.equals(currentDateTimeString)){
            Toast.makeText(UserSubscription.this,"Please Enter Your Correct DOB",Toast.LENGTH_SHORT).show();
        }else if(strGender.equals("Gender")){
            Toast.makeText(UserSubscription.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
        }else if(strGotra.equals("गोत्र")){
            Toast.makeText(UserSubscription.this, "Please Select गोत्र", Toast.LENGTH_SHORT).show();
        }else if(strProfilefor.equals("Profile For")){
            Toast.makeText(UserSubscription.this, "Please Select Profile For", Toast.LENGTH_SHORT).show();
        }else if(strFather.equals("")){
            etFather.setError("Please Enter Father Name");
        }else if(strFatherOCc.equals("occupation")){
            Toast.makeText(UserSubscription.this, "Please Select Father's Occupation", Toast.LENGTH_SHORT).show();
        } else if(strMother.equals("")){
            etMother.setError("Please Enter Mother Name");
        } else if(strMotherOCc.equals("occupation")){
            Toast.makeText(UserSubscription.this, "Please Select Mother's Occupation", Toast.LENGTH_SHORT).show();
        }else if(strMaritStatus.equals("Marital Status")){
            Toast.makeText(UserSubscription.this, "Please Select Marital Status", Toast.LENGTH_SHORT).show();
        }else if(strEducation.equals("Education")){
            Toast.makeText(UserSubscription.this, "Please Select Education", Toast.LENGTH_SHORT).show();
        }else if(stroccupation.equals("occupation")){
            Toast.makeText(UserSubscription.this, "Please Select Occupation", Toast.LENGTH_SHORT).show();
        }else if(strIncome.equals("Select Income Annualy")){
            Toast.makeText(UserSubscription.this, "Please Enter Incmoe", Toast.LENGTH_SHORT).show();
        }else if(strAddress.equals("")){
            Toast.makeText(UserSubscription.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
        }else if(strCity.equals("")){
            Toast.makeText(UserSubscription.this, "Please Enter City", Toast.LENGTH_SHORT).show();
        }else if(strstate.equals("")){
            Toast.makeText(UserSubscription.this, "Please Enter State", Toast.LENGTH_SHORT).show();
        }else if (path.equals("false")){
            Toast.makeText(UserSubscription.this, "Please Upload your image on click of dummy image", Toast.LENGTH_SHORT).show();
        } else{
            update(sd.getKEY_ID(),strPrefix,strDob,strGender,strGotra,strProfilefor,strFather,strMother,strEducation,stroccupation,strMotherOCc,strFatherOCc,strAddress,strCity,strstate,strIncome,strMaritStatus);
        }

    }


    public void view(String key) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(UserSubscription.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
           pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("user_id",key);
            Call<Login_model> call = apiInterface.getProfile(object)   ;
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                   pd.dismiss();
                   try {
                       Login_model resource = response.body();
                       if (resource.status.equals("success")) {
                           sd.setGENDER(resource.user.get(0).gender);

                           etName.setText(resource.user.get(0).user_name);
                           etmobile.setText(resource.user.get(0).user_mobile);
                           etemail.setText(resource.user.get(0).user_email);
                           tvDob.setText(resource.user.get(0).dob.substring(0, 10));

                           if (resource.user.get(0).mother_name.equals("undefined")) {
                               etMother.setText("");
                           } else {
                               etMother.setText(resource.user.get(0).mother_name);

                           }

                           if (resource.user.get(0).father_name.equals("undefined")) {
                               etFather.setText("");
                           } else {
                               etFather.setText(resource.user.get(0).father_name);

                           }


                           if (resource.user.get(0).address.equals("undefined")) {
                               etAddress.setText("");
                           } else {
                               etAddress.setText(resource.user.get(0).address);

                           }

                           if (resource.user.get(0).city.equals("undefined")) {
                               etCity.setText("");
                           } else {
                               etCity.setText(resource.user.get(0).city);

                           }


                           if (resource.user.get(0).state.equals("undefined")) {
                               etState.setText("");
                           } else {
                               etState.setText(resource.user.get(0).state);

                           }

                           if (!resource.user.get(0).profile_pic.equals("dummy.png")) {
                               path = "true";
                               Picasso.with(UserSubscription.this).load(imgPath + resource.user.get(0).profile_pic).into(circleImageView);
                           }


                           String set_pre = resource.user.get(0).pri_fix;
                           ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.Prefix, R.layout.spin_layout);
                           adapter11.setDropDownViewResource(R.layout.spin_layout);
                           spPrefix.setAdapter(adapter11);
                           if (!set_pre.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter11.getPosition(set_pre);
                               spPrefix.setSelection(spinnerPosition);
                           }


                           String set_gen = resource.user.get(0).gender;
                           ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.gender, R.layout.spin_layout);
                           adapter1.setDropDownViewResource(R.layout.spin_layout);
                           spGender.setAdapter(adapter1);
                           if (!set_gen.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter1.getPosition(set_gen);
                               spGender.setSelection(spinnerPosition);
                           }

                           String set_edu = resource.user.get(0).education;
                           ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.education, R.layout.spin_layout);
                           adapter2.setDropDownViewResource(R.layout.spin_layout);
                           spEducation.setAdapter(adapter2);
                           if (!set_edu.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter2.getPosition(set_edu);
                               spEducation.setSelection(spinnerPosition);
                           }

                           String set_occu = resource.user.get(0).occupation;
                           ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.occupasion, R.layout.spin_layout);
                           adapter3.setDropDownViewResource(R.layout.spin_layout);
                           spOccupation.setAdapter(adapter3);
                           if (!set_occu.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter3.getPosition(set_occu);
                               spOccupation.setSelection(spinnerPosition);
                           }

                           String set_marit = resource.user.get(0).marital_status;
                           ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.marit, R.layout.spin_layout);
                           adapter4.setDropDownViewResource(R.layout.spin_layout);
                           spMarit.setAdapter(adapter4);
                           if (!set_marit.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter4.getPosition(set_marit);
                               spMarit.setSelection(spinnerPosition);
                           }


                           String set_motOcc = resource.user.get(0).mother_occupation;
                           ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.motoccupasion, R.layout.spin_layout);
                           adapter5.setDropDownViewResource(R.layout.spin_layout);
                           spMotherOcc.setAdapter(adapter5);
                           if (!set_motOcc.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter5.getPosition(set_motOcc);
                               spMotherOcc.setSelection(spinnerPosition);
                           }

                           String set_father = resource.user.get(0).father_occupation;
                           ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.occupasion, R.layout.spin_layout);
                           adapter6.setDropDownViewResource(R.layout.spin_layout);
                           spFatherOcc.setAdapter(adapter6);
                           if (!set_father.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter6.getPosition(set_father);
                               spFatherOcc.setSelection(spinnerPosition);
                           }

                           String set_income = resource.user.get(0).income;
                           ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.income, R.layout.spin_layout);
                           adapter7.setDropDownViewResource(R.layout.spin_layout);
                           spIncome.setAdapter(adapter7);
                           if (!set_income.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter7.getPosition(set_income);
                               spIncome.setSelection(spinnerPosition);
                           }


                           String set_gotra = resource.user.get(0).gotra;
                           ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.gotra, R.layout.spin_layout);
                           adapter9.setDropDownViewResource(R.layout.spin_layout);
                           spGotra.setAdapter(adapter9);
                           if (!set_gotra.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter9.getPosition(set_gotra);
                               spGotra.setSelection(spinnerPosition);
                           }

                           String set_profilefor = resource.user.get(0).profile_for;
                           ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(UserSubscription.this, R.array.profilefor, R.layout.spin_layout);
                           adapter10.setDropDownViewResource(R.layout.spin_layout);
                           spProfileFor.setAdapter(adapter10);
                           if (!set_profilefor.equalsIgnoreCase(null)) {
                               int spinnerPosition = adapter10.getPosition(set_profilefor);
                               spProfileFor.setSelection(spinnerPosition);
                           }

                       }
                   }catch (Exception e){
                       pd.dismiss();
                       Toast.makeText(UserSubscription.this, "please try again..!...", Toast.LENGTH_SHORT).show();

                   }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(UserSubscription.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }

    public void update(String id, String pre,String dob, String gen, String got, String profor,String fat, String mot,String edu,String ocu,String motocc, String fatocc, String add, String cit,
                       String sta, String incm,String marit) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(UserSubscription.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("user_id",id);
            object.addProperty("pri_fix",pre);
            object.addProperty("dob",dob);
            object.addProperty("gender",gen);
            object.addProperty("gotra",got);
            object.addProperty("profile_for",profor);
            object.addProperty("father_name",fat);
            object.addProperty("mother_name",mot);
            object.addProperty("education",edu);
            object.addProperty("occupation",ocu);
            object.addProperty("mother_occupation",motocc);
            object.addProperty("father_occupation",fatocc);
            object.addProperty("address",add);
            object.addProperty("city",cit);
            object.addProperty("state",sta);
            object.addProperty("income",incm);
            object.addProperty("marital_status",marit);

            Call<Login_model> call = apiInterface.getUpdateProfile(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    Login_model resource = response.body();
                    if (resource.status.equals("success"))  {
                        sd.setLOGIN_STATUS("true");
                        view(sd.getKEY_ID());
                        Intent in = new Intent(UserSubscription.this,MainActivity.class);
                        startActivity(in);
                        Toast.makeText(UserSubscription.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(UserSubscription.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(UserSubscription.this, "please check your internet connection... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }

    }


    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.tvDOB:
             new DatePickerDialog(UserSubscription.this, date, myCalendar
                     .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                     myCalendar.get(Calendar.DAY_OF_MONTH)).show();
             break;
         case R.id.tvUpdate:
             profile();
             break;
         case R.id.profile_image:
             Intent i = new Intent(
                     Intent.ACTION_PICK,
                     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                     startActivityForResult(i, RESULT_LOAD_IMAGE);
             break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                // Picasso.with(UserSubscription.this).load(picturePath).into(circleImageView);
                circleImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                sd.setIMAGEPATH(picturePath);
                path = "true";
                //Toast.makeText(UserSubscription.this,picturePath,Toast.LENGTH_SHORT).show();

                File imagefile = new File(picturePath);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imagefile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bm = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                encImage = Base64.encodeToString(b, Base64.DEFAULT);

                //Toast.makeText(UserSubscription.this, encImage, Toast.LENGTH_SHORT).show();
                if(!encImage.isEmpty()){
                    uploadimage(sd.getKEY_ID(), encImage);
                }
            }

        } catch (Exception e) {
            Toast.makeText(UserSubscription.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadimage (String id, String img){
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("id",id);
            object.addProperty("profile",img);
            Call<Login_model> call = apiInterface.getUpload(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    Login_model resource = response.body();
                    if(resource.status.equals("success")) {
                       // view(sd.getKEY_ID());
                        //Picasso.with(UserSubscription.this).load(imgPath+resource.status).into(circleImageView);
                    //    Toast.makeText(UserSubscription.this, " Your image has Successfully Updated", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    pd.dismiss();
                }
            });
        }

    @Override
    protected void onStop() {
        super.onStop();
        if(pd!=null){
            pd.dismiss();
        }
    }


}
