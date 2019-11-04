package com.ziasy.vaishnavvivah.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by AndroidIgniter on 01-02-2019.
 */

public class checksum extends AppCompatActivity {
    String custid="", orderId="", mid="";
    SessionManagment sd;
    ConnectionDetector cd;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    String varifyurl="",CHECKSUMHASH="",url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sd = new SessionManagment(this);
        cd = new ConnectionDetector(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        orderId = sd.getKEY_ID() + System.currentTimeMillis();
        custid = "CUST"+sd.getKEY_ID();

         mid = "HVTLQn42097706836186"; /// your marchant id
         url ="https://www.blueappsoftware.com/payment/payment_paytm/generateChecksum.php";
         varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
         CHECKSUMHASH ="";

         checksum(orderId,custid,"500",sd.getEMAIL(),sd.getMOBILE());

    }

    public void payment(String id, String txid,String ptype, String psource, String pstatus, String amt) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(checksum.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            Call<Login_model> call = apiInterface.getPayment(id,txid,ptype,psource,pstatus,amt);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();

                    Login_model resource = response.body();
                    if (resource.status.equals("success"))  {
                        Toast.makeText(checksum.this, "Your Payment has successfully done..", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(checksum.this, MainActivity.class);
                        startActivity(in);
                    }else{
                        Toast.makeText(checksum.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(checksum.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }

    }

    public void checksum(String ordid,String custid, String amt,
                         String email, String mob) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(checksum.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            Call<Login_model> call = apiInterface.getChecksum(ordid,custid,amt,email,mob);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    Login_model resource = response.body();
                    if (resource.status.equals("success"))  {
                        CHECKSUMHASH = resource.checksum;
                        transaction();

                    }else{
                        Toast.makeText(checksum.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(checksum.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }

    }

    public  void transaction(){

        PaytmPGService  Service = PaytmPGService.getProductionService();

        HashMap<String, String> paramMap = new HashMap<String, String>();
        //these are mandatory parameters
        paramMap.put("MID", mid); //MID provided by paytm
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", custid);
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "1");
        paramMap.put("WEBSITE", "WEBSTAGING");
        paramMap.put("CALLBACK_URL" ,varifyurl);
        paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");

        PaytmOrder Order = new PaytmOrder(paramMap);
        Log.e("checksum ", "param "+ paramMap.toString());
        Service.initialize(Order,null);
        // start payment service call here


        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                com.paytm.pgsdk.Log.e("LOG", "Payment Transaction : " + inResponse);
               /* Toast.makeText(getApplicationContext(),
                        "Payment Transaction response " + inResponse, Toast.LENGTH_LONG).show();*/
               Toast.makeText(checksum.this,"SORRY, due to some network issue your payment transaction cant successfully try some time later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkNotAvailable() {
                Toast.makeText(checksum.this,
                        "No Internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                if (inErrorMessage != null)
                    Toast.makeText(checksum.this, inErrorMessage,
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                if (inErrorMessage != null)
                    Toast.makeText(checksum.this,
                            inErrorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                try {
                    if (inErrorMessage != null)
                        Toast.makeText(checksum.this,
                                inErrorMessage + "inFailing Url : " + inFailingUrl +
                                        " iniErrorCode : " + iniErrorCode, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                com.paytm.pgsdk.Log.e("LOG", "Payment Transaction Failed " + inErrorMessage);
                Toast.makeText(getBaseContext(), "Payment Transaction Failed ",
                        Toast.LENGTH_LONG).show();
            }
        });

    }



}
