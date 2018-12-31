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
public class HospedeExistenteException extends Exception {
    public HospedeExistenteException() {
        super("Já existe um hóspdede cadastrado com este CPF!");
    }
}
