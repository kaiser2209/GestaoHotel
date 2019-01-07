/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apartamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoriaApartamento.CategoriaApartamentoDAO;
import model.conexao.ConnectionFactory;
import model.entidades.Apartamento;
import model.entidades.CategoriaApartamento;
import util.Mensagem;

/**
 *
 * @author guard
 */
public class ApartamentoDAO {
    public void salvar(Apartamento a) throws SQLException {
        String sql = "INSERT INTO quartos (numero, categoria, ramal)"
                + "VALUE (?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, a.getNumero());
        stmt.setInt(2, a.getCategoria().getId());
        stmt.setInt(3, a.getRamal());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(Apartamento a) throws SQLException {
        String sql = "UPDATE quartos set numero = ?, categoria = ?, "
                + "ramal  = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, a.getNumero());
        stmt.setInt(2, a.getCategoria().getId());
        stmt.setInt(3, a.getRamal());
        stmt.setInt(4, a.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir(Apartamento a) throws SQLException {
        String sql = "DELETE From quartos WHERE id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, a.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<Apartamento> listar() throws SQLException {
        String sql = "SELECT * From quartos";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        ArrayList<Apartamento> lista = new ArrayList<>();
        while (rs.next()) {
            Apartamento a = new Apartamento();
            a.setId(rs.getInt("id"));
            a.setNumero(rs.getString("numero"));
            a.setRamal(rs.getInt("ramal"));
            a.setCategoria(buscarCategoriaPeloId(rs.getInt("categoria")));
            
            lista.add(a);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
    
    public Apartamento buscarPeloNumero(String numero) throws SQLException {
        String sql = "SELECT * From quartos WHERE numero = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, numero);
        
        ResultSet rs = stmt.executeQuery();
        
        Apartamento a = null;
        if (rs.next()) {
            a = new Apartamento();
            a.setId(rs.getInt("id"));
            a.setNumero(rs.getString("numero"));
            a.setRamal(rs.getInt("ramal"));
            a.setCategoria(buscarCategoriaPeloId(rs.getInt("categoria")));
        }
        
        rs.close();
        stmt.close();
        
        return a;
    }
    
    public ArrayList<Apartamento> filtrarPeloNumero(String numero) throws SQLException {
        ArrayList<Apartamento> lista = new ArrayList<>();
        Apartamento a = buscarPeloNumero(numero);
        if (a != null) {
            lista.add(buscarPeloNumero(numero));
        }
        
        return lista;
    }
    
    public ObservableList<CategoriaApartamento> listarCategoriaCrescente() throws SQLException {
        String sql = "SELECT * From categorias_apartamentos Order By codigo";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        ObservableList<CategoriaApartamento> lista = FXCollections.observableArrayList();
        while(rs.next()) {
            CategoriaApartamento a = new CategoriaApartamento();
            a.setId(rs.getInt("id"));
            a.setCodigo(rs.getString("codigo"));
            a.setNomeCategoria(rs.getString("categoria"));
            a.setDescricao(rs.getString("descricao"));
            a.setValorDiaria(rs.getFloat("valor_diaria"));
            
            lista.add(a);
        }
        
        rs.close();
        stmt.close();
               
        return lista;
    }
    
    public CategoriaApartamento buscarCategoriaPeloId(int id) throws SQLException {
        CategoriaApartamentoDAO caDAO = new CategoriaApartamentoDAO();
        return caDAO.buscarPeloId(id);
    }
    
    public Apartamento buscarPeloId(int id) throws SQLException {
        String sql = "SELECT * From quartos WHERE id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        Apartamento a = null;
        if (rs.next()) {
            a = new Apartamento();
            a.setId(rs.getInt("id"));
            a.setNumero(rs.getString("numero"));
            a.setRamal(rs.getInt("ramal"));
            a.setCategoria(buscarCategoriaPeloId(rs.getInt("categoria")));
        }
        
        rs.close();
        stmt.close();
        
        return a;
    }
    
    public ArrayList<Apartamento> listarCrescente() throws SQLException {
        String sql = "SELECT * From quartos Order By numero";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        ArrayList<Apartamento> lista = new ArrayList<>();
        while (rs.next()) {
            Apartamento a = new Apartamento();
            a.setId(rs.getInt("id"));
            a.setNumero(rs.getString("numero"));
            a.setRamal(rs.getInt("ramal"));
            a.setCategoria(buscarCategoriaPeloId(rs.getInt("categoria")));
            
            lista.add(a);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
}
