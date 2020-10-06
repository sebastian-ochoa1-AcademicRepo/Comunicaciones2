/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.controller;

import java.awt.Color;
import java.net.Socket;
import juegosockets.model.*;
import juegosockets.view.Cliente;

/**
 *
 * @author Skhan
 */
public class ClientMensajeControlador {

    Cliente clt;
    boolean jugadorB=false;
    int idPartida,peleador=0;
    boolean pelea=false;
    JugadorBlanco JB = new JugadorBlanco(3);
    JugadorRojo JR = new JugadorRojo(4);
    boolean esTurno=false;
    
    public ClientMensajeControlador(Cliente clt) {
        this.clt = clt;
    }
    
    public void leeObjeto(Object obj, Socket skt){
        
        try {
                MensajeConexion conexion = (MensajeConexion)obj;
                this.msjConexion(conexion,skt);
        } catch (ClassCastException ex) {
        }
          
        try {
                MensajeUnirse msj = (MensajeUnirse)obj;
                this.msjUnirse(msj, skt);
        } catch (ClassCastException ex) {
        }
        
        try {
                MensajePrimerosPasosJR msj = (MensajePrimerosPasosJR)obj;
                this.msjPPJR(msj, skt);
        } catch (ClassCastException ex) {
        }
        
        try {
                MensajeJ msj = (MensajeJ)obj;
                this.msjJugador(msj, skt);
        } catch (ClassCastException ex) {
        }
 
        try {
                MensajePuntos msj = (MensajePuntos)obj;
                this.msjPuntos(msj, skt);
        } catch (ClassCastException ex) {
        }
        
    }
    
    
    public void msjConexion(MensajeConexion msj, Socket skt){
        idPartida=msj.getIPPartida();
        clt.lNumTablero.setText("Tablero #"+msj.getIPPartida());
        clt.pnlCrearUnirse.setVisible(false);
        clt.pnlTablero.setVisible(true);
        actualizarTurno();
        clt.btLanzar.setEnabled(false);
    }
    
    public void msjUnirse (MensajeUnirse msj, Socket skt){
        
        if(msj.getIPPartida()<0){
            clt.noConectado();
        }else{
            clt.lIPContra1.setText(msj.getIpContrario().getHostAddress());
            clt.lNumTablero.setText("Tablero #"+msj.getIPPartida());
            clt.pnlUnirse.setVisible(false);
            clt.pnlTablero.setVisible(true);
            actualizarVidas();
            instruccionesPPRojo();
            actualizarTurno();
        }
    }
    
    public void msjPPJR (MensajePrimerosPasosJR msj, Socket skt){
        JR.setFichas(msj.getFichas());
        clt.posicion[JR.getFichas()[0][0]][JR.getFichas()[0][1]].setBackground(Color.red);
        clt.posicion[JR.getFichas()[1][0]][JR.getFichas()[1][1]].setBackground(Color.red);
        clt.posicion[JR.getFichas()[2][0]][JR.getFichas()[2][1]].setBackground(Color.red);
        clt.posicion[JR.getFichas()[3][0]][JR.getFichas()[3][1]].setBackground(Color.red);
        if(jugadorB){
            esTurno=true;
            clt.lInstruccion.setText("Lanza los dados");
        }else{
            clt.lInstruccion.setText("Esperando contrincante...");
        }
        actualizarTurno();
    }
    
    public void msjJugador (MensajeJ msj, Socket skt){
        if(msj.getiFicha()<0){
            try {
                clt.posicion[JB.getFicha()[0]][JB.getFicha()[1]].setBackground(Color.white);
            } catch (NullPointerException ex) {
            }
            JB.setFicha(msj.getFicha());
            clt.posicion[JB.getFicha()[0]][JB.getFicha()[1]].setBackground(Color.BLACK);
            if(!msj.isPelea()){
                if(jugadorB){
                    esTurno=false;
                    clt.lInstruccion.setText("Esperando contrincante...");
                }else{
                    esTurno=true;
                    clt.lInstruccion.setText("Lanza los dados");
                }
                actualizarTurno();
            }else{
                pelea=true;
                clt.btLanzar.setEnabled(true);
                clt.lInstruccion.setText("Lanza los dados");
            }
        }else{
            int fichas[][] = JR.getFichas();
            clt.posicion[fichas[msj.getiFicha()][0]][fichas[msj.getiFicha()][1]].setBackground(Color.WHITE);
            
            fichas[msj.getiFicha()]=msj.getFicha();
            JR.setFichas(fichas);
            clt.posicion[JR.getFichas()[msj.getiFicha()][0]][JR.getFichas()[msj.getiFicha()][1]].setBackground(Color.RED);
            if(!msj.isPelea()){
                if(!jugadorB){
                    esTurno=false;
                    clt.lInstruccion.setText("Esperando contrincante...");
                }else{
                    esTurno=true;
                    clt.lInstruccion.setText("Lanza los dados");
                }
                actualizarTurno();
            }else{
                pelea=true;
                clt.btLanzar.setEnabled(true);
                clt.lInstruccion.setText("Lanza los dados");
            }
        }
        
    }

    
    public void msjPuntos (MensajePuntos msj, Socket skt){
        if((msj.getPuntos()==1 && jugadorB)||(msj.getPuntos()==0 && !jugadorB)){
            
            int[][] fichas = JR.getFichas();
            JR.setVidas(JR.getVidas()-1);
            clt.posicion[fichas[msj.getPosicion()][0]][fichas[msj.getPosicion()][1]].setBackground(Color.white);
            fichas[msj.getPosicion()][0]=-3;
            fichas[msj.getPosicion()][1]=-3;
            JR.setFichas(fichas);
            if(JR.getVidas()==0)BGanador();
        }else if((msj.getPuntos()==0 && jugadorB)||(msj.getPuntos()==1 && !jugadorB)){
            int[]ficha = JB.getFicha();
            JB.setVidas(JB.getVidas()-1);
            clt.posicion[ficha[0]][ficha[1]].setBackground(Color.white);
            ficha[0]=1;
            ficha[1]=1;
            clt.posicion[ficha[0]][ficha[1]].setBackground(Color.BLACK);
            JB.setFicha(ficha);
            if(JB.getVidas()==0)RGanador();
        }
        if(jugadorB){esTurno=false;} else {esTurno=true;}
        actualizarVidas();
        actualizarTurno();
        
    }
    
    
    public void instruccionesPPRojo(){
        if(!jugadorB){
            clt.lInstruccion.setText("Ubique sus 4 fichas rojas");
        }
        
    }
    
    public void actualizarVidas(){
        if(jugadorB)clt.lVidas.setText(""+JB.getVidas());
        else clt.lVidas.setText(""+JR.getVidas());
    }
    
    public void actualizarTurno(){
        if(!esTurno){
            clt.btLanzar.setEnabled(false);
            clt.lTurno.setText("Esperando turno");
            
        }else{
            clt.btLanzar.setEnabled(true);
            clt.lTurno.setText("Tu turno");
        }
    }

    private void BGanador() {
        if(jugadorB){
            clt.AnunciaGanador("¡FELICIDADES!", "Has ganado el juego");
        }else{
            clt.AnunciaGanador("¡Lo sentimos!", "Has perdido el juego");
        }
        reiniciar();
    }

    private void RGanador() {
        if(!jugadorB){
            clt.AnunciaGanador("¡FELICIDADES!", "Has ganado el juego");
        }else{
            clt.AnunciaGanador("¡Lo sentimos!", "Has perdido el juego");
        }
        reiniciar();
    }
    
    private void reiniciar(){
        clt.pnlTablero.setVisible(false);
        clt.pnlCrearUnirse.setVisible(true);
        JB = new JugadorBlanco(3);
        JR = new JugadorRojo(4);
        jugadorB=false;
    }
    
}
