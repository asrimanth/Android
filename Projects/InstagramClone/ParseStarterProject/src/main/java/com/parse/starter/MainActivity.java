/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {

    Boolean loginModeActive=false;
    TextView loginTextView;
    EditText usernameEditText,passwordEditText;
    ImageView logoImageView;


    public void showUserList()
    {
        Intent intent=new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN)
        {
            signUpClicked(v);
        }

        return false;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.loginTextView)
        {
            Button signUpButton=findViewById(R.id.signUpButton);

            if (loginModeActive)
            {
                loginModeActive=false;
                signUpButton.setText("login");
                loginTextView.setText("or, Sign Up!");
            }
            else
            {
                loginModeActive=true;
                signUpButton.setText("Sign Up!");
                loginTextView.setText("or, Login!");
            }
        }
        else if (v.getId()==R.id.imageView || v.getId()==R.id.backgroundLayout)
        {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void signUpClicked(View view)
    {
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this, "Username or Password is missing!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (loginModeActive)
            {
                ParseUser user=new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null)
                        {
                            Log.i("Sign up","Success!");
                            showUserList();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                //Login
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null)
                        {
                            Log.i("Login","Done!");
                            showUserList();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        usernameEditText=findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        logoImageView=findViewById(R.id.imageView);
        RelativeLayout backgroundLayout=findViewById(R.id.backgroundLayout);

        loginTextView=findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        if (ParseUser.getCurrentUser()!=null)
        {
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}