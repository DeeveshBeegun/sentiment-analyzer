package com.example.sentimentanalyzer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sentimentanalyzer.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ProgressBar progressBar_compound;
    private ProgressBar progressBar_neg;
    private ProgressBar progressBar_neu;
    private ProgressBar progressBar_pos;

    TextInputLayout textInputLayout;

    String mlpApi = "https://machine-learning-playground-api.onrender.com/analyse";
    //String mlpApi = "http://10.0.2.2:5000/analyse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
        textInputLayout = (TextInputLayout)findViewById(R.id.textInputLayout);

        Intent intent = new Intent(this, HomeActvity.class);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);
    }

    public void displayProgressBar(int per_compound, int per_neg, int per_neu, int per_pos) {
        progressBar_compound = (ProgressBar) findViewById(R.id.progressBar_compound);
        if(per_compound < 0) {
            per_compound = per_compound * -1;
            progressBar_compound.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            progressBar_compound.setProgress(per_compound);
        }
        else {
            progressBar_compound.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            progressBar_compound.setProgress(per_compound);
        }

        progressBar_neu = (ProgressBar) findViewById(R.id.progressBar_neu);
        progressBar_neu.setProgress(per_neu);

        progressBar_neg = (ProgressBar) findViewById(R.id.progressBar_neg);
        progressBar_neg.setProgress(per_neg);
        progressBar_neg.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        progressBar_pos = (ProgressBar) findViewById(R.id.progressBar_pos);
        progressBar_pos.setProgress(per_pos);
    }

    public void analyzeText(View view) throws IOException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mlpApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Data added to api", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                    System.out.println(respObj);

                    displayProgressBar((int)Double.parseDouble(respObj.getString("compound")),
                            (int)Double.parseDouble(respObj.getString("neg")),
                            (int)Double.parseDouble(respObj.getString("neu")),
                            (int)Double.parseDouble(respObj.getString("pos")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("text", textInputLayout.getEditText().getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



