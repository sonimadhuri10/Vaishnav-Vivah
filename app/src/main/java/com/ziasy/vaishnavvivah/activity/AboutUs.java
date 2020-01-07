package com.ziasy.vaishnavvivah.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ziasy.vaishnavvivah.R;

public class AboutUs extends AppCompatActivity {


    WebView web1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar
        //getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.logo6);

        web1 = (WebView)findViewById(R.id.web1);


        String s1 = "वैष्णव विवाह \"ऐप \" बनाने का उद्देश्य वैष्णव बैरागी {चतु: संप्रदाय} समाज के शादी योग्य युवक-युवतियों के लिए एक प्लेटफॉर्म उपलब्ध कराना!\n" +
                " जहां वह अपने जीवनसाथी का चयन कर सके!";
        String text1;
        text1 = "<html><body><p align=\"justify\">";
        text1 += s1;
        text1 += "</p></body></html>";
        web1.loadData(text1, "text/html", "utf-8(\\\"color\\\", \\\"blue\\\");");

        web1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        web1.setLongClickable(false);

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









}
