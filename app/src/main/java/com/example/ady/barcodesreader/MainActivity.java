package com.example.ady.barcodesreader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.leakcanary.RefWatcher;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private IntentIntegrator integrator;
    private TextView scannedItem;
    private TextView tvHandler;
    private Handler leakGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannedItem = findViewById(R.id.listofScaneditmes);
        integrator = new IntentIntegrator(this);
        tvHandler = findViewById(R.id.handler);
        leakGenerator = new Handler();


    }

    private void CustomizeBarCideReader() {
        integrator.setPrompt("Scan a barcode. Make sure The Bar code is in the red line");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        //integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult myBarCode = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (myBarCode != null){
            if (myBarCode.getContents()== null){
                Toast.makeText(this,"Not Results", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,
                        "Scanned. The Product is being added to the list "
                        , Toast.LENGTH_LONG).show();
                scannedItem.append(myBarCode.getContents() + "\n");
                Log.d(TAG, "onActivityResult: "+ myBarCode.getBarcodeImagePath());
            }
        }else
            super.onActivityResult(requestCode, resultCode, data);

    }

    public void ScanItem(View view) {
       CustomizeBarCideReader();
        integrator.initiateScan();

    }

    public void CausingMemoryLeaking(View view) {

        leakGenerator.postDelayed(new Runnable() {
            @Override
            public void run() {
                new MyAsyncTask().execute(this);
                tvHandler.append("Thread has Finished \n");
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        },1000*10);

       tvHandler.setText("1more \n");




    }
    public class MyAsyncTask extends AsyncTask<Object, String, String> {
        private Context context;

        @Override
        protected String doInBackground(Object... params) {
            context = (Context)params[0];

            // Invoke the leak!
            SingletonSavesContext.getInstance().setContext(context);

            // Simulate long running task
            try {
                Thread.sleep(6*1000);
            } catch (InterruptedException e) {
            }

            return "result";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvHandler.setText("Completed " + new Date().getTime()+"form "+context.getClass().getSimpleName().toString() +"\n");

            //Intent intent = new Intent(context,Main2Activity.class);
            //startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       RefWatcher refWatcher = LeaksdectectionsClass.getRefWatcher(this);
       refWatcher.watch(this);
    }
}
