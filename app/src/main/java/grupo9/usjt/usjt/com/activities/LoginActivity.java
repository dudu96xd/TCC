package grupo9.usjt.usjt.com.activities;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import butterknife.ButterKnife;
import butterknife.InjectView;
import grupo9.usjt.usjt.com.dao.ContaDAO;
import grupo9.usjt.usjt.com.dto.ContaDTO;
import grupo9.usjt.usjt.com.dto.UsuarioDTO;
import grupo9.usjt.usjt.com.dto.ValidadorContaDTO;
import grupo9.usjt.usjt.com.helper.crypto.EncriptaHelper;
import grupo9.usjt.usjt.com.helper.utils.LoginHelper;
import grupo9.usjt.usjt.com.helper.utils.UtilsValidation;

public class LoginActivity extends AppCompatActivity {
    /***/private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_LOGIN_FB = 64206;
    CallbackManager callbackManager;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.login_button_fb) LoginButton _fbLogin;

    public static EncriptaHelper encriptaHelper;
    static {
        try {
            encriptaHelper = new EncriptaHelper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("jk", "Exception(NameNotFoundException) : " + e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("mkm", "Exception(NoSuchAlgorithmException) : " + e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.printKeyHash();
        if(LoginHelper.isLoggedIn()){
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            LoginManager.getInstance().logOut();
        }
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _fbLogin.setReadPermissions("email");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // Callback registration
        _fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(Profile.getCurrentProfile().getId()!=null){
                    final ContaDTO dto = new ContaDTO();
                    dto.setIdUsuario(Profile.getCurrentProfile().getId());
                    dto.setSenha("1234567890");
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                dto.setEmail(object.getString("email"));
                                dto.setNome(object.getString("first_name")+" "+object.getString("last_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ContaDAO dao = new ContaDAO(getApplicationContext());
                            if(!dao.findContaByIdUsuario(dto)){
                                dao.insertConta(dto);
                            }
                            LoginHelper.ID_USER = Profile.getCurrentProfile().getId();
                            LoginHelper.EMAIL = dto.getEmail();
                            Log.d("userId",LoginHelper.ID_USER);
                            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                            startActivityForResult(intent, REQUEST_SIGNUP);
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "email,first_name,last_name");
                    request.setParameters(parameters);
                    request.executeAndWait();
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                //Error
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login(){
        Log.d(TAG, "Login");

        _loginButton.setEnabled(false);

        ValidadorContaDTO validadorContaDTO = UtilsValidation.validarEntrada(_emailText,_passwordText);

        if (validadorContaDTO.isValidaEmail()) {
            _emailText.setError("E-mail inválido.");
        }
        if(validadorContaDTO.isValidaSenha()){
            _passwordText.setError("Senha com pelo menos quatro caracteres.");
        }

        //Se algum campo de login estiver inválido
        if(validadorContaDTO.isValidaEmail() || validadorContaDTO.isValidaSenha()){
            _loginButton.setEnabled(true);
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        final UsuarioDTO dto = preencheUsuarioDTO(email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        validarLogin(dto);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public UsuarioDTO preencheUsuarioDTO(String email, String password){
        final UsuarioDTO dto = new ContaDTO();
        dto.setEmail(email);
        try {
            dto.setSenha(
                    encriptaHelper.encrypt(password));
        } catch (Exception e) {

            Log.e(TAG,"Erro ao criptografar.");

        }
        return dto;
    }

    @Override
    public void onDestroy(){
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //validador do codigo de retorno conhecido
        //TODO: fazer ENUM de código de retorno
        if (requestCode == REQUEST_SIGNUP || requestCode == REQUEST_LOGIN_FB) {

            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void validarLogin(UsuarioDTO dto){
        if(new ContaDAO(this).findConta(dto)) {
            Toast.makeText(this, "Login realizado com Sucesso!!", Toast.LENGTH_SHORT).show();

            onLoginSuccess();

            finish();
        }
        else {
            onLoginFailed();
        }
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "E-mail e/ou senha inválidos.", Toast.LENGTH_LONG).show();

    }


}
