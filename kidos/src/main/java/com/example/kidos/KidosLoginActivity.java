package com.example.kidos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kidos.beans.KidosClientBean;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosCryptoUtils;
import com.example.kidos.utils.KidosRestClient;
import com.example.kidos.utils.KidosUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KidosLoginActivity extends AppCompatActivity implements IKidosRestClientWrapper {

	// Custom button
	private Button fbbutton;

	// Creating Facebook CallbackManager Value
	public static CallbackManager callbackmanager;
	private KidosClientBean data;
	
	private static SharedPreferences kidosPreferences;
	public static final String PREFS_NAME = "KidosPrefsFile";
	private boolean hasLoggedIn = false;
	private boolean hasSkippedLogin=false;


	private String errorMsg = "Mobile number or password is wrong!! Please retry";

	private String kidosLoginUrl=KidosConstants.KIDOS_LOGIN_URI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Initialize preference
		kidosPreferences= getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		
		 // Initialize SDK before setContentView(Layout ID)
	    FacebookSdk.sdkInitialize(getApplicationContext());


		if(!hasUserLoggedIn() && !hasUserSkippedLogIn()) {


			setContentView(R.layout.activity_kidos_login);

			// Initialize layout button
			fbbutton = (Button) findViewById(R.id.fb_login_button);


			fbbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Call private method
					onFblogin();
				}
			});

			TextView registerLabel = (TextView) findViewById(R.id.label_register);
			registerLabel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), KidosRegistration.class);
					v.getContext().startActivity(intent);

				}
			});

			TextView forgotPassLabel = (TextView) findViewById(R.id.label_forgotpass);
			forgotPassLabel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
				//	Intent intent = new Intent(view.getContext(), KidosForgotPassword.class);
				//	view.getContext().startActivity(intent);
				}
			});
		}
		else
		{
			Intent intent = new Intent(this, KidosFindLocation.class);
			this.startActivity(intent);
			this.finish();
		}

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_skip) {
			System.out.println("Clicked skip button");
			storeToKidosPreference("hasSkipped",true);
			Intent intent = new Intent(this, KidosFindLocation.class);
			this.startActivity(intent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Private method to handle Facebook login and callback
	private void onFblogin()
	{
	    callbackmanager = CallbackManager.Factory.create();
	    // Set permissions 
	    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));

	    LoginManager.getInstance().registerCallback(callbackmanager,
	            new FacebookCallback<LoginResult>() {
	                @Override
	                public void onSuccess(LoginResult loginResult) {

	                    String accessToken= loginResult.getAccessToken().getToken();
	                    System.out.println("Success-------"+accessToken);
	                    
	                    GraphRequest request = GraphRequest.newMeRequest(
	                            loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
	                                @Override
	                                public void onCompleted(JSONObject json, GraphResponse response) {
	                                    if (response.getError() != null) {
	                                        // handle error
	                                        System.out.println("ERROR"+response.getError());
	                                    } else {
	                                        System.out.println("Success");
	                                        try {

	                                            String jsonresult = String.valueOf(json);
	                                            System.out.println("JSON Result"+jsonresult);

	                                            String str_email = json.getString("email");
	                                            String str_id = json.getString("id");
	                                            String str_firstname = json.getString("name");
	                                            //String str_lastname = json.getString("last_name");
	                                            
	                                            
	                                            
	                                            Intent intent=new Intent(KidosLoginActivity.this.getApplicationContext(), KidosFindLocation.class);
	                            				
	                            				startActivity(intent);
	                            				KidosLoginActivity.this.finish();
	                                            

	                                        } catch (JSONException e) {
	                                            e.printStackTrace();
	                                        }
	                                    }
	                                }

	                            });
	                    
	                    Bundle parameters = new Bundle();
	                    parameters.putString("fields", "id,name,email");
	                    request.setParameters(parameters);
	                    request.executeAsync();

	                }

	                @Override
	                public void onCancel() {
	                	System.out.println("Cancel-------");
	                }

	                @Override
	                public void onError(FacebookException error) { 
	                	 System.out.println("Error-------"+error.getLocalizedMessage());
	                	 System.out.println("Error-------"+error.getStackTrace());
	                }
	    });
	}

	private boolean validateForm()
	{
		boolean validForm=false;
		EditText phNo = (EditText)findViewById(R.id.txt_login_phno);
		EditText pass = (EditText)findViewById(R.id.txt_password);

		if( phNo.getText().toString().length() == 0 ) {
			phNo.setError("Mobile number is required!");
			return validForm;
		}
		if( pass.getText().toString().length() == 0 ) {
			pass.setError("Password is required!");
			return validForm;
		}
		validForm=true;
		return validForm;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    callbackmanager.onActivityResult(requestCode, resultCode, data);
	}

	public static void storeToKidosPreference(String key,String val)
	{
		SharedPreferences.Editor editor = kidosPreferences.edit();

		//Set "hasLoggedIn" to true
		editor.putString(key, val);

		// Commit the edits!
		editor.commit();
	}

	public static void storeToKidosPreference(String key,boolean val)
	{
		SharedPreferences.Editor editor = kidosPreferences.edit();

		//Set "hasLoggedIn" to true
		editor.putBoolean(key, val);

		// Commit the edits!
		editor.commit();
	}


	public static String  retrieveFromKidosPartnersPreference(String key)
	{
		String val=kidosPreferences.getString(key, null);
		return val;
	}

	public void Login(View v) {

		if (validateForm()) {
			String mobile = ((EditText) findViewById(R.id.txt_login_phno)).getText().toString();
			String pass = ((EditText) findViewById(R.id.txt_password)).getText().toString();

			//Arg map
			Map<String, Object> argMap = new HashMap<String, Object>();
			argMap.put("mobile", mobile);
			argMap.put("pass", KidosCryptoUtils.encodeData(pass));

			restRequest(KidosLoginActivity.this, argMap, KidosConstants.POST, kidosLoginUrl);

		}
	}

	private boolean hasUserLoggedIn()
	{
		//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
		hasLoggedIn = kidosPreferences.getBoolean("hasLoggedIn", false);
		System.out.println("User logged in :"+hasLoggedIn);
		return hasLoggedIn;
	}

	private boolean hasUserSkippedLogIn()
	{
		hasSkippedLogin = kidosPreferences.getBoolean("hasSkipped", false);
		System.out.println("User Clicked skip login :"+hasSkippedLogin);
		return hasSkippedLogin;
	}

	@Override
	public void restRequest(IKidosRestClientWrapper context, Map<String, Object> args, String method, String url) {

		KidosRestClient.sendRequest(context,args,method,url);
	}

	@Override
	public void restCallBack(String restOutput) {
			System.out.println("In Rest Call back method of KidosLogin:: restOutput="+restOutput);


			if(!"null".equals(restOutput))
			{
				System.out.println("Rest Output is not 'null'");
				Gson gson=new Gson();
				data=gson.fromJson(restOutput,new TypeToken<KidosClientBean>(){}.getType());

				Intent activity=new Intent(KidosLoginActivity.this,KidosFindLocation.class);
				activity.putExtra("user",new Gson().toJson(data));

				CheckBox signedInChkBox = (CheckBox)findViewById(R.id.chk_keepsignedin);
				if(signedInChkBox.isChecked())
				{
					//Also put user attribute in sharedpreferences for future logins
					storeToKidosPreference("user",new Gson().toJson(data));
					storeToKidosPreference("hasLoggedIn", true);
				}

				startActivity(activity);

			}
			else
			{
				System.out.println("About to raise error");
				KidosUtil.createDialog(KidosLoginActivity.this, KidosUtil.TITLE_ERROR, errorMsg, KidosUtil.NONE_DIALOG, null);

			}
	}
}
