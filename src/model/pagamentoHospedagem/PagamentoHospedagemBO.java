/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pagamentoHospedagem;

import java.sql.SQLException;
import model.entidades.PagamentoHospedagem;

/**
 *
 * @author guard
 */
public class PagamentoHospedagemBO {
    private PagamentoHospedagemDAO dao;
    
    public PagamentoHospedagemBO() {
        dao = new PagamentoHospedagemDAO();
    }
    
    public void salvar(PagamentoHospedagem pagamento) throws SQLException {
        dao.salvar(pagamento);
    }
}
