/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.model;

import java.io.Serializable;

/**
 *
 * @author Skhan
 */
public class MensajePrimerosPasosJR extends MensajeInstruccion implements Serializable{
    private int fichas[][]= new int[4][2];
    
    /**
     * @return the fichas
     */
    public int[][] getFichas() {
        return fichas;
    }

    /**
     * @param fichas the fichas to set
     */
    public void setFichas(int[][] fichas) {
        this.fichas = fichas;
    }
    
}
