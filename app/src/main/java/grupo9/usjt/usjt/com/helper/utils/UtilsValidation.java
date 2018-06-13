package grupo9.usjt.usjt.com.helper.utils;

import android.widget.EditText;

import grupo9.usjt.usjt.com.dto.ValidadorContaDTO;

public class UtilsValidation {


    private static final CharSequence NULL = null;

    public static ValidadorContaDTO validarEntrada(EditText _emailText, EditText _passwordText) {
        ValidadorContaDTO validadorDTO = new ValidadorContaDTO();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                validadorDTO.setValidaEmail(true);
        } else {
            _emailText.setError(NULL);
        }
        if (password.isEmpty() || password.length() < 4 ) {
            validadorDTO.setValidaSenha(true);
        } else {
            _passwordText.setError(NULL);
        }

        return validadorDTO;
    }

    public static ValidadorContaDTO validarCadastro(EditText _emailText, EditText _passwordText, EditText _nameText){
        ValidadorContaDTO validador = UtilsValidation.validarEntrada(_emailText,_passwordText);
        //Valida a senha
        String textoNome = _nameText.getText().toString();
        if (textoNome.isEmpty() || textoNome.length() < 3) {
            validador.setValidaNome(true);
        }
        else{
            _nameText.setError(NULL);
        }
        return validador;
    }
}
