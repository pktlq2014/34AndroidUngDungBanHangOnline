package com.example.a34androidungdungbanhangonline.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    EditText editTextName, editTextEmail, editTextPassword, editTextCPassword;
    Button buttonRegister;
    ProgressBar loading;
    RadioGroup radioGroup;
    String bbb;
    Toolbar toolbarDangKy;
    RadioButton radioButton1, radioButton2;
    static String URL_REGISTER = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhXa();
        toolBarAcTion();
        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reGisTer();
            }
        });
    }

    private void toolBarAcTion()
    {
        setSupportActionBar(toolbarDangKy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDangKy.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // mở ra navigation
                finish();
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }


    public void reGisTer()
    {
        loading.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.GONE);
//        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setIndeterminate(false);
//        progressDialog.setTitle("Registering New Account");
//        progressDialog.show();
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        if(radioButton1.isChecked())
        {
            bbb = radioButton1.getText().toString().trim();
        }
        else
        {
            bbb = radioButton2.getText().toString().trim();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDangKy,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1"))
                            {
                            //    progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Register Success! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonRegister.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
              //  progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                buttonRegister.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", bbb);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void anhXa()
    {
        buttonRegister = findViewById(R.id.butTonRegister);
        loading = findViewById(R.id.loading);
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextCPassword = findViewById(R.id.c_password);
        radioGroup = findViewById(R.id.radiogp);
        radioButton1 = findViewById(R.id.male);
        radioButton2 = findViewById(R.id.female);
        toolbarDangKy = findViewById(R.id.toolbarDangKy);
    }
}
