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
public class Mensaje implements Serializable{
    private int IPPartida;
    
    /**
     * @return the IPPartida
     */
    public int getIPPartida() {
        return IPPartida;
    }

    /**
     * @param IPPartida the IPPartida to set
     */
    public void setIPPartida(int IPPartida) {
        this.IPPartida = IPPartida;
    }
    
}
