/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.model;

import java.util.Random;

/**
 *
 * @author Skhan
 */
public class Jugador {
    private int Vidas;
    
    public static int[] dados(){
        int[] dados = new int[2];
        Random r = new Random();
        
        int valorDado = r.nextInt(6)+1;
        dados[0]=valorDado;
        
        valorDado = r.nextInt(6)+1;
        dados[1]=valorDado;
        
        return dados;
    }
    
    /**
     * @return the Vidas
     */
    public int getVidas() {
        return Vidas;
    }

    /**
     * @param Vidas the Vidas to set
     */
    public void setVidas(int Vidas) {
        this.Vidas = Vidas;
    }
}
