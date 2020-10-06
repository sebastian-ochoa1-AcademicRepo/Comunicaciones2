/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.model;

import java.net.InetAddress;

/**
 *
 * @author Skhan
 */
public class Partida {
    private InetAddress ipJB;
    private InetAddress ipJR;
    private Boolean llena=false;
    private int IPPartida;

    
    public Partida(InetAddress ipJB) {
        this.ipJB = ipJB;   
    }

    public void entradaJR(InetAddress ipJR){
        this.setIpJR(ipJR);
    }
    
    
    /**
     * @return the ipJB
     */
    public InetAddress getIpJB() {
        return ipJB;
    }

    /**
     * @return the ipJR
     */
    public InetAddress getIpJR() {
        return ipJR;
    }

    /**
     * @param ipJR the ipJR to set
     */
    public void setIpJR(InetAddress ipJR) {
        this.ipJR = ipJR;
    }

    /**
     * @return the IPPartida
     */
    public int getIPPartida() {
        return IPPartida;
    }

    /**
     * @return the llena
     */
    public Boolean getLlena() {
        return llena;
    }

    /**
     * @param llena the llena to set
     */
    public void setLlena(Boolean llena) {
        this.llena = llena;
    }

    /**
     * @param IPPartida the IPPartida to set
     */
    public void setIPPartida(int IPPartida) {
        this.IPPartida = IPPartida;
    }
    
    
    
}
