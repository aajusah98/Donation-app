package com.food.donate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText editTxtName,editTxtEmail,editTxtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxtName = (EditText) findViewById(R.id.editTxtName);
        editTxtEmail = (EditText) findViewById(R.id.editTxtEmail);
        editTxtPwd = (EditText) findViewById(R.id.editTxtPwd);
        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button Register
                //here we will Register the user to server
                registerUser();
            }
        });
    }
        private void registerUser() {
            final String username = editTxtName.getText().toString().trim();
            final String email = editTxtEmail.getText().toString().trim();
            final String password = editTxtPwd.getText().toString().trim();


            //first we will do the validations

            if (TextUtils.isEmpty(username)) {
                editTxtName.setError("Please enter username");
                editTxtName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(email)) {
                editTxtEmail.setError("Please enter your email");
                editTxtEmail.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTxtEmail.setError("Enter a valid email");
                editTxtEmail.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                editTxtPwd.setError("Enter a password");
                editTxtPwd.requestFocus();
                return;
            }

            //if it passes all the validations

            class RegisterUser extends AsyncTask<Void, Void, String> {

                private ProgressBar progressBar;

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();
                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    //returing the response
                    return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //displaying the progress bar while user registers on the server
                    progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //hiding the progressbar after completion
                    progressBar.setVisibility(View.GONE);

//                    try {
//                        //converting response to json object
//                        JSONObject obj = new JSONObject(s);
//
//                        //if no error in response
//                        if (!obj.getBoolean("error")) {
//                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                            //getting the user from the response
//                            JSONObject userJson = obj.getJSONObject("user");
//
//                            //creating a new user object
//                            User user = new User(
//                                    userJson.getInt("id"),
//                                    userJson.getString("username"),
//                                    userJson.getString("email")
//                            );
//
//                            //storing the user in shared preferences
//                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                            //starting the profile activity
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            //executing the async task
            RegisterUser ru = new RegisterUser();
            ru.execute();


    }
}
