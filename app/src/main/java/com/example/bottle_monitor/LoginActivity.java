 package com.example.bottle_monitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

 public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

     SharedPreferences sharedPreferences;
     String actualPassword;
     String userEmail;
     public static final String AC_PASS_KEY = "actual password";
     public static final String AC_EMAIL_KEY = "email";
     public static final String IS_ALREADY_LOGGED_IN_KEY = "is already logged in";

     EditText etEmail;
     EditText etPassword;
     TextView tv_change_pass;
     Button bLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("com.example.bottle_monitor", MODE_PRIVATE);
        actualPassword = sharedPreferences.getString(AC_PASS_KEY, "");
        userEmail = sharedPreferences.getString(AC_EMAIL_KEY, "");
        boolean isAlreadyLoggedIn = sharedPreferences.getBoolean(IS_ALREADY_LOGGED_IN_KEY, false);
        if(isAlreadyLoggedIn){
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NavActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);

        tv_change_pass = findViewById(R.id.tv_change_pass);
        tv_change_pass.setPaintFlags(tv_change_pass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_change_pass.setOnClickListener(this);
        bLogin = findViewById(R.id.bLogin);

        bLogin.setOnClickListener(this);
    }


    void login(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        //Toast.makeText(getContext(), "Email: "+email, Toast.LENGTH_SHORT).show();
        Log.d("email", "st"+email+"end");
        Log.d("pass", "st"+email+"end");
//        Toast.makeText(this, sharedPreferences.getString(AC_EMAIL_KEY, ""), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, sharedPreferences.getString(AC_PASS_KEY, ""), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), email.length()+"", Toast.LENGTH_SHORT).show();
//        if(email==null || email.length()==0){
//            etEmail.setError("Enter an email");
//            etEmail.requestFocus();
//        }
//        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            etEmail.setError("Enter a valid email address");
//            etEmail.requestFocus();
//        }
//        else if(password==""){
//            etPassword.setError("Please enter a password");
//            etPassword.requestFocus();
//        }
        if(!email.equals(userEmail)){
            etEmail.setError("Wrong email");
        }
        else if (!password.equals(actualPassword)){
            etPassword.setError("Wrong password");
        }
        else {
            sharedPreferences.edit().putBoolean(IS_ALREADY_LOGGED_IN_KEY, true).apply();
            startActivity(new Intent(this, NavActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    void changePassword(Context ctx){
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.change_pass_view,null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(v);
        dialogBuilder.setTitle("Create credentials");
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        EditText et_new_email = v.findViewById(R.id.et_new_email);
        EditText et_old_password = v.findViewById(R.id.et_old_pass);
        EditText et_new_password = v.findViewById(R.id.et_new_pass);
        Button  bt_change_credentials = v.findViewById(R.id.bt_change_credentials);

//        Toast.makeText(ctx, sharedPreferences.getString(AC_PASS_KEY, "nopass"), Toast.LENGTH_SHORT).show();
        bt_change_credentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = et_new_email.getText().toString();
                String oldPass = et_old_password.getText().toString();
                String newPass = et_new_password.getText().toString();

                String tempActPass = sharedPreferences.getString(AC_PASS_KEY, "");
                Log.d("oldPass", oldPass);
                newEmail = newEmail!=null ? newEmail : "";
                oldPass = oldPass!=null ? oldPass : "";
                Log.d("password abcd", oldPass + " " + tempActPass);
                Log.d("password abcde", sharedPreferences.getString(AC_PASS_KEY, ""+"E"));
                if(oldPass.equals(tempActPass)){

                    sharedPreferences.edit().putString(AC_PASS_KEY, newPass).putString(AC_EMAIL_KEY, newEmail).apply();
                    userEmail = newEmail;
                    actualPassword = newPass;
//                    Toast.makeText(ctx, sharedPreferences.getString(AC_PASS_KEY, "nopass"), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                }
                else{
                    et_old_password.setError("Wrong Password");
                }
            }
        });

    }
     @Override
     public void onClick(View view) {
        switch (view.getId()){
            case R.id.bLogin:
                login();
                break;
            case R.id.tv_change_pass:
                changePassword(this);
                break;
        }
     }
 }
