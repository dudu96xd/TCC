package grupo9.usjt.usjt.com.dto;

public class ValidadorContaDTO {
    private boolean validaEmail;
    private boolean validaSenha;
    private boolean validaNome;

    public boolean isValidaEmail() {
        return validaEmail;
    }

    public void setValidaEmail(boolean validaEmail) {
        this.validaEmail = validaEmail;
    }

    public boolean isValidaSenha() {
        return validaSenha;
    }

    public void setValidaSenha(boolean validaSenha) {
        this.validaSenha = validaSenha;
    }

    public boolean isValidaNome() {
        return validaNome;
    }

    public void setValidaNome(boolean validaNome) {
        this.validaNome = validaNome;
    }
}
