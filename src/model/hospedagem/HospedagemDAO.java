/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospedagem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Hospedagem;
import model.entidades.Hospede;

/**
 *
 * @author guard
 */
public class HospedagemDAO {
    public void salvar(Hospedagem h) throws SQLException {
        String sql = "INSERT INTO hospedagem (id_quarto, data_entrada, "
                + "procedencia, destino) VALUE (?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, h.getApartamento().getId());
        stmt.setString(2, h.getDataEntrada().toString());
        stmt.setString(3, h.getProcedencia());
        stmt.setString(4, h.getDestino());
        
        stmt.executeUpdate();
        
        stmt.close();
        
        salvarHospedes(ultimoId(), h.getHospedes());
    }
    
    public void salvarHospedes(int id, ArrayList<Hospede> hospedes) throws SQLException {
        String sql = "INSERT INTO cliente_hospedagem (id_hospedagem, id_cliente, "
                + "VALUE (?, ?)";
        
        for (Hospede h : hospedes) {
            PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
            stmt.setInt(id, h.getIdHospede());
            
            stmt.executeUpdate();
            
            stmt.close();
        }
        
    }
    
    public int ultimoId() throws SQLException {
        String sql = "SELECT id From hospedagem Order By id Desc Limit 1";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        
        rs.close();
        stmt.close();
        
        return id;
    }
    
    public void checkout(Hospedagem h) throws SQLException {
        String sql = "UPDATE hospedagem set data_saida = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, h.getDataSaida().toString());
        stmt.setInt(2, h.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
}
