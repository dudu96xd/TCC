package grupo9.usjt.usjt.com.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import grupo9.usjt.usjt.com.dao.ContaDAO;
import grupo9.usjt.usjt.com.dto.ValidadorContaDTO;
import grupo9.usjt.usjt.com.dto.ContaDTO;
import grupo9.usjt.usjt.com.helper.crypto.EncriptaHelper;
import grupo9.usjt.usjt.com.helper.utils.UtilsValidation;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

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
        setContentView(R.layout.activity_create_account);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() throws Exception {
        Log.d(TAG, "Signup");

        _signupButton.setEnabled(false);

        ValidadorContaDTO dtoValidador = UtilsValidation.validarCadastro(_emailText,_passwordText,_nameText);

        if (dtoValidador.isValidaEmail()) {
            _emailText.setError("E-mail inválido.");
        }
        if(dtoValidador.isValidaSenha()){
            _passwordText.setError("Senha com pelo menos quatro caracteres.");
        }
        if(dtoValidador.isValidaNome()){
            _nameText.setError("Nome deve conter ao menos três caracteres.");
        }

        //Se algum campo estiver inválido
        if(dtoValidador.isValidaNome() || dtoValidador.isValidaSenha() || dtoValidador.isValidaEmail()){
            _signupButton.setEnabled(true);
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando Conta...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = encriptaHelper.encrypt(_passwordText.getText().toString());

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        ContaDTO dto = new ContaDTO();
                        dto.setEmail(email);
                        dto.setNome(name);
                        dto.setSenha(password);
                        onSignupSuccess(dto);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess(ContaDTO dto){

        boolean b;
        b = new ContaDAO(this).insertConta(dto);
        if(!b) {
            onSignupFailed();
        }
        _signupButton.setEnabled(true);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Criação de conta falhou", Toast.LENGTH_LONG).show();
    }

}
