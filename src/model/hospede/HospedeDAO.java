/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospede;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Hospede;

/**
 *
 * @author guard
 */
public class HospedeDAO {
    public void salvar(Hospede h) throws SQLException {
        String sql = "INSERT INTO clientes (nome, end_rua, end_numero, end_bairro, " +
                "end_complemento, end_cidade, end_estado, end_pais, end_cep, rg, " +
                "cpf, data_nascimento, email, telefone, celular, data_cadastro) VALUE "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        stmt.setString(1, h.getNome());
        stmt.setString(2, h.getEndRua());
        stmt.setString(3, h.getEndNumero());
        stmt.setString(4, h.getEndBairro());
        stmt.setString(5, h.getEndComplemento());
        stmt.setString(6, h.getEndCidade());
        stmt.setString(7, h.getEndEstado());
        stmt.setString(8, h.getEndPais());
        stmt.setString(9, h.getEndCep());
        stmt.setString(10, h.getRg());
        stmt.setString(11, h.getCpf());
        stmt.setString(12, h.getDataNascimento().toString());
        stmt.setString(13, h.getEmail());
        stmt.setString(14, h.getTelefone());
        stmt.setString(15, h.getCelular());
        stmt.setString(16, h.getDataCadastro().toString());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(Hospede h) throws SQLException {
        String sql = "UPDATE clientes set nome = ?, end_rua = ?, end_numero = ?, "
                + "end_bairro = ?, end_complemento = ?, end_cidade = ?, end_estado = ?, "
                + "end_pais = ?, end_cep = ?, rg = ?, cpf = ?, data_nascimento = ?, "
                + "email = ?, telefone = ?, celular = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, h.getNome());
        stmt.setString(2, h.getEndRua());
        stmt.setString(3, h.getEndNumero());
        stmt.setString(4, h.getEndBairro());
        stmt.setString(5, h.getEndComplemento());
        stmt.setString(6, h.getEndCidade());
        stmt.setString(7, h.getEndEstado());
        stmt.setString(8, h.getEndPais());
        stmt.setString(9, h.getEndCep());
        stmt.setString(10, h.getRg());
        stmt.setString(11, h.getCpf());
        stmt.setString(12, h.getDataNascimento().toString());
        stmt.setString(13, h.getEmail());
        stmt.setString(14, h.getTelefone());
        stmt.setString(15, h.getCelular());
        stmt.setInt(16, h.getIdHospede());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir (Hospede h) throws SQLException {
        String sql = "DELETE From clientes WHERE id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, h.getIdHospede());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public Hospede buscarPeloCpf (String cpf) throws SQLException {
        String sql = "SELECT * From clientes WHERE cpf = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, cpf);
        
        ResultSet resultado = stmt.executeQuery();
        
        Hospede h = new Hospede();
        
        if (resultado.next()) {
            h.setIdHospede(resultado.getInt("id"));
            h.setNome(resultado.getString("nome"));
            h.setEndRua(resultado.getString("end_rua"));
            h.setEndNumero(resultado.getString("end_numero"));
            h.setEndBairro(resultado.getString("end_bairro"));
            h.setEndComplemento(resultado.getString("end_complemento"));
            h.setEndCidade(resultado.getString("end_cidade"));
            h.setEndEstado(resultado.getString("end_estado"));
            h.setEndCep(resultado.getString("end_cep"));
            h.setEndPais(resultado.getString("end_pais"));
            h.setRg(resultado.getString("rg"));
            h.setCpf(resultado.getString("cpf"));
            h.setEmail(resultado.getString("email"));
            h.setTelefone(resultado.getString("telefone"));
            h.setCelular(resultado.getString("celular"));
            h.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            h.setDataCadastro(LocalDateTime.parse(resultado.getTimestamp("data_cadastro").toString(), formatter));
        }
        
        resultado.close();
        stmt.close();
        
        return h;
    }
    
    public ArrayList<Hospede> listar() throws SQLException {
        String sql = "SELECT * From Clientes";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet resultado = stmt.executeQuery();
        
        ArrayList<Hospede> hospedes = new ArrayList<>();
        
        while (resultado.next()) {
            Hospede h = new Hospede();
            
            h.setIdHospede(resultado.getInt("id"));
            h.setNome(resultado.getString("nome"));
            h.setEndRua(resultado.getString("end_rua"));
            h.setEndNumero(resultado.getString("end_numero"));
            h.setEndBairro(resultado.getString("end_bairro"));
            h.setEndComplemento(resultado.getString("end_complemento"));
            h.setEndCidade(resultado.getString("end_cidade"));
            h.setEndEstado(resultado.getString("end_estado"));
            h.setEndCep(resultado.getString("end_cep"));
            h.setEndPais(resultado.getString("end_pais"));
            h.setRg(resultado.getString("rg"));
            h.setCpf(resultado.getString("cpf"));
            h.setEmail(resultado.getString("email"));
            h.setTelefone(resultado.getString("telefone"));
            h.setCelular(resultado.getString("celular"));
            h.setDataNascimento(LocalDate.parse(resultado.getString("data_nascimento")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            h.setDataCadastro(LocalDateTime.parse(resultado.getTimestamp("data_cadastro").toString(), formatter));
            
            hospedes.add(h);
        }
        
        resultado.close();
        stmt.close();
        
        return hospedes;
    }
    
    public ArrayList<Hospede> filtrar(String pesquisa, String sql) throws SQLException {
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, "%" + pesquisa + "%");
        
        ResultSet resultado = stmt.executeQuery();
        
        ArrayList<Hospede> hospedes = new ArrayList<>();
        
        while (resultado.next()) {
            Hospede h = new Hospede();
            
            h.setIdHospede(resultado.getInt("id"));
            h.setNome(resultado.getString("nome"));
            h.setEndRua(resultado.getString("end_rua"));
            h.setEndNumero(resultado.getString("end_numero"));
            h.setEndBairro(resultado.getString("end_bairro"));
            h.setEndComplemento(resultado.getString("end_complemento"));
            h.setEndCidade(resultado.getString("end_cidade"));
            h.setEndEstado(resultado.getString("end_estado"));
            h.setEndCep(resultado.getString("end_cep"));
            h.setEndPais(resultado.getString("end_pais"));
            h.setRg(resultado.getString("rg"));
            h.setCpf(resultado.getString("cpf"));
            h.setEmail(resultado.getString("email"));
            h.setTelefone(resultado.getString("telefone"));
            h.setCelular(resultado.getString("celular"));
            h.setDataNascimento(LocalDate.parse(resultado.getString("data_nascimento")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            h.setDataCadastro(LocalDateTime.parse(resultado.getTimestamp("data_cadastro").toString(), formatter));
            
            hospedes.add(h);
        }
        
        resultado.close();
        stmt.close();
        
        return hospedes;
    }
    
    public ArrayList<Hospede> filtrarPorNome(String pesquisa) throws SQLException {
        String sql = "SELECT * From clientes WHERE nome LIKE ?";
        
        return filtrar(pesquisa, sql);
    }
    
    public ArrayList<Hospede> filtrarPorCpf(String pesquisa) throws SQLException {
        ArrayList<Hospede> lista = new ArrayList<>();
        lista.add(buscarPeloCpf(pesquisa));
        return lista;
    }
}
