/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author guard
 */
public class ApartamentoExistenteException extends Exception {
    public ApartamentoExistenteException() {
        super("Já existe um apartamento cadastrado com este número!");
    }
}
