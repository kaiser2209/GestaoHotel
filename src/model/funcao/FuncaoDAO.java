/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.funcao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Funcao;

/**
 *
 * @author guard
 */
public class FuncaoDAO {
    public void salvar(Funcao f) throws SQLException {
        String sql = "INSERT INTO funcoes (nome_funcao, descricao, nivel_acesso) "
                + "VALUE (?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, f.getNomeFuncao());
        stmt.setString(2, f.getDescricaoFuncao());
        stmt.setShort(3, f.getNivelAcesso());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(Funcao f) throws SQLException {
        String sql = "UPDATE funcoes SET nome_funcao = ?, descricao = ?, "
                + "nivel_acesso = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, f.getNomeFuncao());
        stmt.setString(2, f.getDescricaoFuncao());
        stmt.setShort(3, f.getNivelAcesso());
        stmt.setInt(4, f.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir(Funcao f) throws SQLException {
        String sql = "DELETE From funcoes where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, f.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<Funcao> listar() throws SQLException {
        String sql = "SELECT * From funcoes";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        ArrayList<Funcao> funcoes = new ArrayList<>();
        while(rs.next()) {
            Funcao f = new Funcao();
            f.setId(rs.getInt("id"));
            f.setNomeFuncao(rs.getString("nome_funcao"));
            f.setDescricaoFuncao(rs.getString("descricao"));
            f.setNivelAcesso(rs.getShort("nivel_acesso"));
            
            funcoes.add(f);
        }
        
        rs.close();
        stmt.close();
        
        return funcoes;
    }
    
    public Funcao buscarPeloNome(String nome) throws SQLException {
        String sql = "SELECT * From funcoes WHERE nome_funcao = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, nome);
        
        ResultSet rs = stmt.executeQuery();
        
        Funcao f = null;
        if (rs.next()) {
            f = new Funcao();
            f.setId(rs.getInt("id"));
            f.setNomeFuncao(rs.getString("nome_funcao"));
            f.setDescricaoFuncao(rs.getString("descricao"));
            f.setNivelAcesso(rs.getShort("nivel_acesso"));
        }
        
        rs.close();
        stmt.close();
        
        return f;
    }
}
