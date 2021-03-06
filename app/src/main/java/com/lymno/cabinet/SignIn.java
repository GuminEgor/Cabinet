package com.lymno.cabinet;

import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Colored on 02.04.2015.
 */
public class SignIn extends ActionBarActivity implements View.OnClickListener{
    public Button signin_button;
    public EditText loginEdit;
    public EditText passwordEdit;
    public TextView forgot_pass;

    private final String magicRequest = "api/users/entranceMob?";

    SharedPreferences cache;
    private KidsDataBase db = new KidsDataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_in);
        signin_button = (Button) findViewById(R.id.signin_button);
        signin_button.setOnClickListener(this);
        loginEdit = (EditText) findViewById(R.id.login);
        passwordEdit = (EditText) findViewById(R.id.password);


//        forgot_pass = (TextView) findViewById(R.id.forgot_pass);
//        forgot_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //������ ����� � �������. ����� ��������� ������������� ��������� ������ � ������ � ������.
            //��������, ����� � �������.
            case R.id.signin_button:
                String email = loginEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                try {
                    email = URLEncoder.encode(email, "UTF-8");
                    password = URLEncoder.encode(password, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String query = magicRequest + "Email=" + email + "&Password=" + password;
                new SignInRequest().execute(query);
                break;


            case R.id.forgot_pass:
//                Intent forgot_pass_intent = new Intent(this, ForgotPassword.class);
//                startActivity(forgot_pass_intent);
                break;

            default:
                break;
        }
    }

    private class SignInRequest extends BasicRequest {
        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);
                String responseResult = obj.getString("Result");

                if (responseResult.equals("Fail")) {
                    Toast.makeText(SignIn.this,"�������� ������ ��� ������", Toast.LENGTH_LONG).show();
                }
                else {
                    JSONObject parent = obj.getJSONObject("Parent");
                    cache = getSharedPreferences("cache", MODE_PRIVATE);
                    SharedPreferences.Editor ed = cache.edit();
                    ed.putString("IDToken", parent.getString("Token"));
                    ed.putString("firstName", parent.getString("FirstName"));
                    ed.putString("lastName", parent.getString("SecondName"));
                    ed.putString("middleName", parent.getString("MiddleName"));
                    ed.putString("email", parent.getString("Email"));
                    ed.apply();

                    try {
                        JSONArray kidsArray = obj.getJSONArray("Children");
                        db.recreateDataBase(new ArrayList<Kid>());
                        try {
                            for (int i = 0; i < kidsArray.length(); ++i) {
                                JSONObject kidJSON = kidsArray.getJSONObject(i);
                                final int parentId = kidJSON.getInt("ParentId");
                                final String firstName = kidJSON.getString("FirstName");
                                Log.d("my", firstName);
                                final String lastName = kidJSON.getString("SecondName");
                                final String middleName = kidJSON.getString("MiddleName");
                                final String school = kidJSON.getString("School");
                                final String classOfSchool = kidJSON.getString("Class");
                                final String dateOfBirth = kidJSON.getString("DateBirth");
                                final String bornCertificate = kidJSON.getString("BornCertificate");

                                Kid kid = new Kid(firstName, lastName, middleName, school, classOfSchool, dateOfBirth, parentId, bornCertificate);
                                db.addKid(kid);
                            }

                        }
                        catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
