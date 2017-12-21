package com.example.kidos;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kidos.beans.KidosClientBean;
import com.example.kidos.beans.KidosRegistrationBean;
import com.example.kidos.databinding.ActivityKidosRegistrationBinding;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosCryptoUtils;
import com.example.kidos.utils.KidosRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class KidosRegistration extends Activity implements IKidosRestClientWrapper {

    ActivityKidosRegistrationBinding registrationBinding;
    KidosRegistrationBean registrationdetails =new KidosRegistrationBean()	;
    private String registerClientUri=KidosConstants.REGISTER_CLIENT_URI;
    private KidosClientBean userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidos_registration);

        registrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_kidos_registration);

        registrationBinding.setRegistrationdetails(registrationdetails);

    }

    public void Register(View v) {

        if(validateForm()) {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> registrationDetailsMap = new Gson().fromJson(new Gson().toJson(registrationdetails), type);
            //encode password before sending to server
            String encodedPass= KidosCryptoUtils.encodeData((String)registrationDetailsMap.get("password"));
            registrationDetailsMap.put("password",encodedPass);

            restRequest(KidosRegistration.this, registrationDetailsMap, KidosConstants.POST, registerClientUri);
        }
        //Intent intent=new Intent(this,KidosPartnersWelcome.class);
        //this.startActivity(intent);
    }

    private boolean validateForm()
    {
        boolean validForm = false;

        EditText firstName = (EditText)findViewById(R.id.txt_first_name);
        EditText lastName = (EditText)findViewById(R.id.txt_last_name);
        EditText phNo = (EditText)findViewById(R.id.txt_phno);
        EditText email = (EditText)findViewById(R.id.txt_emailid);
        EditText pass = (EditText)findViewById(R.id.txt_password);
        EditText repass = (EditText)findViewById(R.id.txt_retypepass);

        if( firstName.getText().toString().length() == 0 ) {
            firstName.setError("First name is required!");
            return validForm;
        }

        if( lastName.getText().toString().length() == 0 ) {
            lastName.setError("Last name is required!");
            return validForm;
        }

        if( phNo.getText().toString().length() == 0 ) {
            phNo.setError("Phone number is required!");
            return validForm;
        }

        if( email.getText().toString().length() == 0 ) {
            email.setError("Email is required!");
            return validForm;
        }

        if( pass.getText().toString().length() == 0 ) {
            pass.setError("Password is required!");
            return validForm;
        }

        if( repass.getText().toString().length() == 0 ) {
            repass.setError("Re-type Password is required!");
            return validForm;
        }

        if( pass.getText().toString().length() != 0 && !pass.getText().toString().equals(repass.getText().toString())) {
            repass.setError("Password and re-type password should match!");
            return validForm;
        }

        validForm=true;
        return validForm;
    }


    @Override
    public void restRequest(IKidosRestClientWrapper context, Map<String, Object> args, String method, String url) {
        KidosRestClient.sendRequest(context,args,method,url);
    }

    @Override
    public void restCallBack(String restOutput) {
        JsonObject response=null;
        System.out.println("In Rest Call back method of KidosRegistration::"+restOutput);

        Gson gson = new Gson();
        if(restOutput!=null) {
            response = gson.fromJson(restOutput, JsonObject.class);
        }

        if(response!=null && response.get("errmsg")==null)
        {
            userDetails = gson.fromJson(restOutput, new TypeToken<KidosClientBean>() {}.getType());

            Toast.makeText(KidosRegistration.this, "Congratulations!! Successfully registered.", Toast.LENGTH_LONG).show();

            Intent activity = new Intent(KidosRegistration.this, KidosFindLocation.class);
            activity.putExtra("user", gson.toJson(userDetails));
            startActivity(activity);
            KidosRegistration.this.finish();
        }
        else
        {
            Toast.makeText(KidosRegistration.this, response.get("errmsg").toString(), Toast.LENGTH_LONG).show();
        }

    }
}
