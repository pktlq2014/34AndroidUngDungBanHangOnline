package com.example.a34androidungdungbanhangonline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    Button buttonDangNhap;
    TextView textViewDangKy;
    EditText editText1, editText2;
    ProgressBar loading;
    CheckBox checkBox1;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);

        anhXa();
        editText1.setText(sharedPreferences.getString("taikhoan", ""));
        editText2.setText(sharedPreferences.getString("matkhau", ""));
        checkBox1.setChecked(sharedPreferences.getBoolean("checked", false));



        textViewDangKy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = editText1.getText().toString().trim();
                String pass = editText2.getText().toString().trim();
                if(!email.isEmpty() || !pass.isEmpty())
                {
                    loGin(email, pass);
                }
                else
                {
                    editText1.setError("Please Insert Email");
                    editText2.setError("Please Insert Password");
                }
            }
        });
    }
    private void loGin(final String email, final String pass)
    {
        loading.setVisibility(View.VISIBLE);
        buttonDangNhap.setVisibility(View.GONE);
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDangNhap,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if(success.equals("1"))
                            {
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    if(checkBox1.isChecked())
                                    {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("taikhoan", email);
                                        editor.putString("matkhau", pass);
                                        editor.putBoolean("checked", true);
                                        editor.commit();
                                    }
                                    Toast.makeText(LoginActivity.this, "Success Login. \nYour Name : " + name + "\nYour Email : " + email, Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    startActivity(new Intent(LoginActivity.this, ThongTinActivity.class));
                                    finish();
                                }
                            }
                            else
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("taikhoan");
                                editor.remove("matkhau");
                                editor.remove("checked");
                                editor.commit();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            buttonDangNhap.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                loading.setVisibility(View.GONE);
                buttonDangNhap.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void anhXa()
    {
        textViewDangKy = findViewById(R.id.textViewDangKy1);
        buttonDangNhap = findViewById(R.id.butTonDangNhap1);
        editText1 = findViewById(R.id.email1);
        editText2 = findViewById(R.id.password1);
        loading = findViewById(R.id.loading);
        checkBox1 = findViewById(R.id.checkBox1);
    }
}
