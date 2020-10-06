/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.model;

/**
 *
 * @author Skhan
 */
public class JugadorBlanco extends Jugador{
    private int ficha[] = new int[2];
    
    public void pelear(){
        //PROGRAMAR
    }

    public JugadorBlanco(int vidas) {
        this.setVidas(vidas);
    }

    
    
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
    
}
