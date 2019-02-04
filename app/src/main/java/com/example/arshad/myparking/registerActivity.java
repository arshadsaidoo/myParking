package com.example.arshad.myparking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class registerActivity extends AppCompatActivity {
    private static final String LOCAL_URL = "http://ec2-18-222-194-128.us-east-2.compute.amazonaws.com:8009/register";
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText confirmPassword;
    private EditText password;
    private EditText email;
    private EditText phoneNumber;
    private Button btnSignup;
    private String user_name;
    private String pwrd;
    private String first_Name;
    private String last_Name;
    private String con_pwrd;
    private String e_mail;
    private String p_num;
    private String url_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        confirmPassword = (EditText) findViewById(R.id.conPass);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phone);
        btnSignup = (Button) findViewById(R.id.signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             first_Name=firstName.getText().toString();
                                             last_Name=lastName.getText().toString();
                                             user_name = username.getText().toString();
                                             pwrd = password.getText().toString();
                                             con_pwrd=confirmPassword.getText().toString();
                                             e_mail=email.getText().toString();
                                             p_num=phoneNumber.getText().toString();


                                             if (TextUtils.isEmpty(user_name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(pwrd)) {
                                                 Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (TextUtils.isEmpty(first_Name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter First Name!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(last_Name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter Last Name!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (TextUtils.isEmpty(con_pwrd)) {
                                                 Toast.makeText(getApplicationContext(), "Enter password confirmation!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(e_mail)) {
                                                 Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }


                                             if (TextUtils.isEmpty(p_num)) {
                                                 Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (con_pwrd.equals(pwrd)){
                                                 url_send = getRegisterURL(LOCAL_URL, user_name, pwrd,last_Name,first_Name,p_num,e_mail);

                                             }

                                             else {
                                                 Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
                                             }

                                             try {
                                                 whenSendPostRequest_thenCorrect();

                                             } catch (IOException e) {
                                                 e.printStackTrace();
                                             }
                                             //Intent intent = new Intent(registerActivity.this, loginActivity.class);
                                             //intent.putExtra("userId", id);
                                             //startActivity(intent);
                                             //finish();
                                         }
                                     }
        );


    }


    protected String getRegisterURL(String url_base, String username, String password, String firstName, String lastName, String email, String pNumber) {
        String url = url_base;// + "?user_name=" + username + "&password=" + password + "&first_name=" + firstName + "&last_name=" + lastName + "&email=" + email + "&phone=" + pNumber;
        Log.i("name",url);
        return url;
    }

    public void whenSendPostRequest_thenCorrect()
            throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder().add("user_name", user_name).add("password",pwrd ).add("first_name",first_Name).add("last_name",last_Name).add("phone",p_num).add("email",e_mail).build();
        Request request = new Request.Builder().url(url_send).post(formBody).build();
        Call call = client.newCall(request);


    }






}

