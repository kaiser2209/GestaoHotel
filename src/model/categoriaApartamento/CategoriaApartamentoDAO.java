/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.categoriaApartamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.CategoriaApartamento;

/**
 *
 * @author guard
 */
public class CategoriaApartamentoDAO {
    public void salvar(CategoriaApartamento c) throws SQLException {
        String sql = "INSERT INTO categorias_apartamentos (codigo, descricao, "
                + "valor_diaria, categoria) VALUES (?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, c.getCodigo());
        stmt.setString(2, c.getDescricao());
        stmt.setFloat(3, c.getValorDiaria());
        stmt.setString(4, c.getNomeCategoria());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(CategoriaApartamento c) throws SQLException {
        String sql = "UPDATE categorias_apartamentos set codigo = ?, "
                + "categoria = ?, descricao = ?, valor_diaria = ?"
                + "where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, c.getCodigo());
        stmt.setString(2, c.getNomeCategoria());
        stmt.setString(3, c.getDescricao());
        stmt.setFloat(4, c.getValorDiaria());
        stmt.setInt(5, c.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir(CategoriaApartamento c) throws SQLException {
        String sql = "DELETE From categorias_apartamentos where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, c.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<CategoriaApartamento> listar() throws SQLException {
        String sql = "SELECT * From categorias_apartamentos";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        ArrayList<CategoriaApartamento> lista = new ArrayList<>();
        
        while(rs.next()) {
            CategoriaApartamento c = new CategoriaApartamento();
            c.setId(rs.getInt("id"));
            c.setCodigo(rs.getString("codigo"));
            c.setNomeCategoria(rs.getString("categoria"));
            c.setDescricao(rs.getString("descricao"));
            c.setValorDiaria(rs.getFloat("valor_diaria"));
            
            lista.add(c);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
    
    public CategoriaApartamento buscarPeloCodigo(String codigo) throws SQLException {
        String sql = "SELECT * From categorias_apartamentos WHERE codigo = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, codigo);
        
        ResultSet rs = stmt.executeQuery();
        
        CategoriaApartamento c = null;
        
        if(rs.next()) {
            c = new CategoriaApartamento();
            c.setId(rs.getInt("id"));
            c.setCodigo(rs.getString("codigo"));
            c.setNomeCategoria(rs.getString("categoria"));
            c.setDescricao(rs.getString("descricao"));
            c.setValorDiaria(rs.getFloat("valor_diaria"));
        }
        
        rs.close();
        stmt.close();
        
        return c;
    }
    
    public CategoriaApartamento buscarPeloId(int id) throws SQLException {
        String sql = "SELECT * From categorias_apartamentos where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        CategoriaApartamento c = null;
        
        if (rs.next()) {
            c = new CategoriaApartamento();
            c.setId(rs.getInt("id"));
            c.setCodigo(rs.getString("codigo"));
            c.setNomeCategoria(rs.getString("categoria"));
            c.setDescricao(rs.getString("descricao"));
            c.setValorDiaria(rs.getFloat("valor_diaria"));
        }
        
        rs.close();
        stmt.close();
        
        return c;
    }
}
