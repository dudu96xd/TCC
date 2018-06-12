package grupo9.usjt.usjt.com.business;

import grupo9.usjt.usjt.com.dao.ContaDAO;
import grupo9.usjt.usjt.com.dto.ContaDTO;

public class ContaBO {

    private ContaDTO contaDTO;
    private ContaDAO contaDAO;
    public ContaBO(ContaDTO dto,ContaDAO dao){
        this.contaDTO = dto;
        this.contaDAO = dao;
    }

    public boolean efetuarLogin(){

        return true;
    }

    public ContaDTO getContaDTO() {
        return contaDTO;
    }

    public void setContaDTO(ContaDTO contaDTO) {
        this.contaDTO = contaDTO;
    }

    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public void setContaDAO(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }
}
