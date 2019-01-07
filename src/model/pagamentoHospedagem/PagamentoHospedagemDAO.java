/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pagamentoHospedagem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.conexao.ConnectionFactory;
import model.entidades.PagamentoHospedagem;

/**
 *
 * @author guard
 */
public class PagamentoHospedagemDAO {
    public void salvar(PagamentoHospedagem pagamento) throws SQLException {
        String sql = "INSERT INTO pagamento_hospedagem (id_hospedagem, "
                + "total_valor_diaria, total_desconto, total_acrescimo) "
                + "VALUE (?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, pagamento.getHospedagem().getId());
        stmt.setFloat(2, pagamento.getTotalValorDiaria());
        stmt.setFloat(3, pagamento.getTotalDesconto());
        stmt.setFloat(4, pagamento.getTotalAcrescimo());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
}
