package com.ziasy.vaishnavvivah;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.activity.AboutUs;
import com.ziasy.vaishnavvivah.activity.GridGalleryActivity;
import com.ziasy.vaishnavvivah.activity.NotificationActivity;
import com.ziasy.vaishnavvivah.activity.ResetPassword;
import com.ziasy.vaishnavvivah.activity.SearchActivity;
import com.ziasy.vaishnavvivah.activity.UserSubscription;
import com.ziasy.vaishnavvivah.activity.loginActivity;
import com.ziasy.vaishnavvivah.adapter.ItemArrayAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.fragment.Filter;
import com.ziasy.vaishnavvivah.fragment.dashboard;
import com.ziasy.vaishnavvivah.model.ListModel;
import com.ziasy.vaishnavvivah.model.Login_model;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    TextView tvName, tvEmail;
    CircleImageView circleImageView;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<ListModel> al;
    ItemArrayAdapter arrayAdapter;
    ListView lv;
    LinearLayout container;
    static int coutn1 = 0;
    SessionManagment sd;
    ConnectionDetector cd;
    ProgressDialog pd;
    APIInterface apiInterface;
    String imgPath = "http://joietouch.com/matrimoney/images/";
    static String data = null;
    private String fragmentIntent = null;
    Toolbar toolbar;
    ImageView imagettool;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printHashKey(MainActivity.this);
        imagettool = findViewById(R.id.notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
              setSupportActionBar(toolbar);

             // getSupportActionBar().setHomeButtonEnabled(true);
              //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        container = (LinearLayout) findViewById(R.id.container);
        tvName = (TextView) findViewById(R.id.tvName);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        sd = new SessionManagment(MainActivity.this);
        cd = new ConnectionDetector(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        circleImageView = (CircleImageView) findViewById(R.id.prImage);
        data = getIntent().getStringExtra("fragment");
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo9);*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new dashboard()).commit();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, UserSubscription.class);
                startActivity(in);
            }
        });

        imagettool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inn = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(inn);
            }
        });


       fragmentIntent = getIntent().getStringExtra("fragment");
        if (fragmentIntent != null) {
            if (fragmentIntent.equalsIgnoreCase("tips")) {
             Intent in = new Intent(MainActivity.this,NotificationActivity.class);
             startActivity(in);
            }
        }
    }


    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("MainActivity", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("MainActivity", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("MainActivity", "printHashKey()", e);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

       ActionBar actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
      //  actionBar.setDisplayUseLogoEnabled(true);
       // actionBar.setIcon(R.drawable.logo9);

        actionBar.show();

        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.turnof, R.drawable.ic_launcher_background) {
                public void onDrawerClosed(View view) {

                }

                public void onDrawerOpened(View drawerView) {

                }

                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                public void onDrawerStateChanged(int newState) {

                }
            };
            drawerLayout.setDrawerListener(drawerToggle);
        }
        addData();
        view(sd.getKEY_ID());
        click();
        drawerToggle.syncState();
    }


    public void addData() {

        lv = (ListView) findViewById(R.id.list);
        al = new ArrayList<>();

        ListModel l1 = new ListModel(R.drawable.hut, "Home");
        ListModel l11 = new ListModel(R.drawable.aboutus, "About Us");
        ListModel l2 = new ListModel(R.drawable.filter, "Filter");
        ListModel l3 = new ListModel(R.drawable.sear, "Search");
        ListModel l4 = new ListModel(R.drawable.pdlock, "Change Password");
        ListModel l5 = new ListModel(R.drawable.gallery, "Gallery");
        ListModel l6 = new ListModel(R.drawable.pobtn, "Logout");


        al.add(l1);
        al.add(l11);
        al.add(l2);
        al.add(l3);
        al.add(l5);
        al.add(l4);
        al.add(l6);


        arrayAdapter = new ItemArrayAdapter(MainActivity.this, al);
        lv.setAdapter(arrayAdapter);

    }

    public void click() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
                        fragmentTransaction.replace(R.id.container, new dashboard());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        Intent in11 = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(in11);
                        break;
                    case 2:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                     /*  FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
                        fragmentTransaction1.replace(R.id.container, new Filter());
                        fragmentTransaction1.commit();*/
                        Intent in1 = new Intent(MainActivity.this, Filter.class);
                        startActivity(in1);
                        break;
                    case 3:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                      /*FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
                        fragmentTransaction2.replace(R.id.container, new Search());
                        fragmentTransaction2.commit();*/
                        Intent in = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(in);
                        break;
                    case 4:
                        Intent ins = new Intent(MainActivity.this, GridGalleryActivity.class);
                        startActivity(ins);
                        break;
                    case 5:
                        Intent ing = new Intent(MainActivity.this, ResetPassword.class);
                        startActivity(ing);
                        break;
                    case 6:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        new android.app.AlertDialog.Builder(MainActivity.this)
                                .setTitle("Signout!")
                                .setMessage("Are you sure you want to signout?")
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                sd.setLOGIN_STATUS("false");
                                                sd.setLOGOUT_STATUS("true");
                                                Intent i = new Intent(getApplicationContext(), loginActivity.class);
                                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                                startActivity(i);
                                                finish();                                                int pid = android.os.Process.myPid();
                                                android.os.Process.killProcess(pid);
                                                System.exit(0);
                                                finish();
                                            }

                                        }).setNegativeButton("No", null).show();
                        break;


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);

        return true;
    }


    public void view(String key) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("user_id",key);
            Call<Login_model> call = apiInterface.getProfile(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    try {
                        pd.dismiss();
                        Login_model resource = response.body();
                        if (resource.status.equals("success")) {
                            tvName.setText(resource.user.get(0).user_name);
                            tvEmail.setText(resource.user.get(0).user_email);
                            if (resource.user.get(0).profile_pic.equals("dummy.png")) {
                                circleImageView.setBackgroundResource(R.drawable.man);
                            } else {
                                Picasso.with(MainActivity.this).load(imgPath + resource.user.get(0).profile_pic).into(circleImageView);
                            }
                        }
                    }catch (Exception e){
                        pd.dismiss();
                       // Toast.makeText(MainActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(MainActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }

    }

    public void viewimg(String key) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            JsonObject object =new JsonObject();
            object.addProperty("user_id",key);
            Call<Login_model> call = apiInterface.getProfile(object);

            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    try {
                        Login_model resource = response.body();
                        if (resource.status.equals("success")) {
                            if (resource.user.get(0).profile_pic.equals("dummy.png")) {
                                circleImageView.setBackgroundResource(R.drawable.man);
                            } else {
                                Picasso.with(MainActivity.this).load(imgPath + resource.user.get(0).profile_pic).into(circleImageView);
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(MainActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
//            case R.id.nautification:
//                Intent inn = new Intent(MainActivity.this, NotificationActivity.class);
//                startActivity(inn);
//                return true;
         /*   case R.id.logout:
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Exit!")
                        .setMessage("Are you sure you want to signout?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sd.setLOGIN_STATUS("false");
                                        sd.setLOGOUT_STATUS("true");
                                        Intent i = new Intent(getApplicationContext(), loginActivity.class);
                                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                        startActivity(i);
                                        finish();
                                        int pid = android.os.Process.myPid();
                                        android.os.Process.killProcess(pid);
                                        System.exit(0);
                                    }

                                }).setNegativeButton("No", null).show();
                return true;    */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (coutn1 == 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction.replace(R.id.container, new dashboard());
            fragmentTransaction.commit();
            coutn1++;
        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackCount == 0) {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.warning)
                        .setTitle("Exit!")
                        .setMessage("Are you sure you want to close?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent start = new Intent(Intent.ACTION_MAIN);
                                        start.addCategory(Intent.CATEGORY_HOME);
                                        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(start);
                                    }

                                }).setNegativeButton("No", null).show();
            } else {
                super.onBackPressed();
            }

        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewimg(sd.getKEY_ID());
    }
}
