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
public class MensajeJ extends MensajeInstruccion implements Serializable{
    private int iFicha;
    private int ficha[] =  new int[2];
    private boolean pelea;
    
    /**
     * @return the ficha
     */
    public int[] getFicha() {
        return ficha;
    }

    /**
     * @param ficha the ficha to set
     */
    public void setFicha(int[] ficha) {
        this.ficha = ficha;
    }

    /**
     * @return the iFicha
     */
    public int getiFicha() {
        return iFicha;
    }

    /**
     * @param iFicha the iFicha to set
     */
    public void setiFicha(int iFicha) {
        this.iFicha = iFicha;
    }

    /**
     * @return the pelea
     */
    public boolean isPelea() {
        return pelea;
    }

    /**
     * @param pelea the pelea to set
     */
    public void setPelea(boolean pelea) {
        this.pelea = pelea;
    }
    
}
