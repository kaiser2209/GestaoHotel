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
public class FuncaoExistenteException extends Exception {
    public FuncaoExistenteException() {
        super("Já existe uma função cadastrada com este nome!");
    }
}
