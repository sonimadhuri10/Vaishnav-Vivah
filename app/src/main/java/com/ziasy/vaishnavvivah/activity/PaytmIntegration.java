package com.ziasy.vaishnavvivah.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.R;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PaytmIntegration extends AppCompatActivity {

    private String orderID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paytm);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public synchronized void onStartTransaction(View view){
        PaytmPGService service = PaytmPGService.getStagingService();
        orderID = "OrderID" + System.currentTimeMillis();
        String checkSum = checksumGeneration(
                "SayExp21866103977508",
                        orderID,
                "CUST0001453",
                "Retail",
                "WAP",
                "10.00",
                "APPSTAGING",
                "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderID);//Creating checksum to pass it for transaction.
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "SayExp21866103977508");//Replace your merchant id here.
        paramMap.put("ORDER_ID", orderID);//This is entered by yourself but must be unique. Any unique here unique created by system timestamp.        paramMap.put("CUST_ID", "CUST0001453");//Created by yourself.//Add your customer id like CUST123        paramMap.put("INDUSTRY_TYPE_ID", "Retail");//fix retail        paramMap.put("CHANNEL_ID", "WAP");//Fix WAP for Mobile App and WEB for website        paramMap.put("TXN_AMOUNT", "10.00");//Transaction Ammount like 1.00        paramMap.put("WEBSITE", "APPSTAGING");//APP_STAGING or WEB_STAGING or your own website host name if you have entered (STAGING for given name)        paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderID);//Callback url if you want to set yourself.        paramMap.put("CHECKSUMHASH", checkSum);//Checksum generate by given above data need to pass here.        Log.e("Checksum2", checkSum);
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);
        //PaytmClientCertificate clientCertificate = new PaytmClientCertificate(password,filename);
        service.initialize(order, null);
        service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.e("LOG", "Payment Transaction : " + inResponse);
                Toast.makeText(getApplicationContext(),
                        "Payment Transaction response " + inResponse, Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkNotAvailable() {
                Toast.makeText(PaytmIntegration.this,
                        "No Internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                if (inErrorMessage != null)
                    Toast.makeText(PaytmIntegration.this, inErrorMessage,
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                if (inErrorMessage != null)
                    Toast.makeText(PaytmIntegration.this,
                            inErrorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                try {
                    if (inErrorMessage != null)
                        Toast.makeText(PaytmIntegration.this,
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
                Log.e("LOG", "Payment Transaction Failed " + inErrorMessage);
                Toast.makeText(getBaseContext(), "Payment Transaction Failed ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    public synchronized String checksumGeneration(String mid, String orderId, String cust_id, String industry_type_id, String channel_id, String amount, String website, String callbackurl) {
        TreeMap<String, String> paramMap = new TreeMap<String, String>();
        paramMap.put("MID", mid);
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", cust_id);
        paramMap.put("INDUSTRY_TYPE_ID", industry_type_id);
        paramMap.put("CHANNEL_ID", channel_id);
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("WEBSITE", website);
        paramMap.put("CALLBACK_URL", callbackurl);
        String checkSum = "";
        try {
          //  checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("c@kiszD1vwtGc6Xd", paramMap);
            paramMap.put("CHECKSUMHASH", checkSum);
            Log.e("Checksum1", checkSum);
        } catch (Exception e) {
            // TODO Auto-generated catch block            e.printStackTrace();
        }
        return checkSum;
    }

}
