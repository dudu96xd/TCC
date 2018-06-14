package grupo9.usjt.usjt.com.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import grupo9.usjt.usjt.com.dao.ContaDAO;
import grupo9.usjt.usjt.com.dto.ContaDTO;
import grupo9.usjt.usjt.com.dto.UsuarioDTO;
import grupo9.usjt.usjt.com.dto.ValidadorContaDTO;
import grupo9.usjt.usjt.com.helper.crypto.EncriptaHelper;
import grupo9.usjt.usjt.com.helper.utils.UtilsValidation;

public class LoginActivity extends AppCompatActivity {
    /***/private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    public static EncriptaHelper encriptaHelper;
    static {
        try {
            encriptaHelper = new EncriptaHelper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

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
                        try {
                            validarLogin(dto);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void validarLogin(UsuarioDTO dto) throws Exception {
        if(new ContaDAO(this).findConta(dto)) {
            Toast.makeText(this, "Login realizado com Sucesso!!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);

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
