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
public class MensajeUnirse extends MensajeInstruccion{
    private InetAddress ipContrario;

    /**
     * @return the ipContrario
     */
    public InetAddress getIpContrario() {
        return ipContrario;
    }

    /**
     * @param ipContrario the ipContrario to set
     */
    public void setIpContrario(InetAddress ipContrario) {
        this.ipContrario = ipContrario;
    }
    
}
