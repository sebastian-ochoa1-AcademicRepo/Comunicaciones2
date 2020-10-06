/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegosockets.model.*;
import juegosockets.view.Servidor;


/**
 *
 * @author Skhan
 */
public class ServerMensajeControlador {
    
    Servidor srv;
    int p=0,pun1=0,pun2=0,peleador=-1;
    Partida[] partidas = new Partida[100];

    public ServerMensajeControlador(Servidor srv) {
        this.srv=srv;
    }

    
    
    public void leeObjeto(Object obj, Socket skt){
        
        try {
                MensajeConexion conexion = (MensajeConexion)obj;
                this.msjConexion(conexion,skt);
        } catch (ClassCastException ex) {
        }
        
        try {
                MensajeCrearP msj = (MensajeCrearP)obj;
                this.msjCrearP(msj, skt);
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
        String ip = skt.getInetAddress().getHostAddress();
        srv.lUltimaConexión.setText("Conexion establecida con: " +ip);
    }
    
    
    public void msjCrearP (MensajeCrearP msj, Socket skt){
        partidas[p] = new Partida(skt.getInetAddress());
        try {
            MensajeConexion msjC = new MensajeConexion();
            msjC.setIPPartida(p);
            Socket sktT = new Socket(skt.getInetAddress().getHostAddress(),9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(sktT.getOutputStream());
            streamObjetos.writeObject(msjC);
            
            //Copiar IP en blancos
            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("No mandé socket de creador");
        }
        p++;
        
    }
    
    public void msjUnirse (MensajeUnirse msj, Socket skt){
        try{
          if(!partidas[msj.getIPPartida()].getLlena()){  
            partidas[msj.getIPPartida()].setLlena(true);
            partidas[msj.getIPPartida()].setIpJR(skt.getInetAddress());
            
            
            
            try {
                MensajeUnirse msjU = new MensajeUnirse();
                msjU.setIPPartida(msj.getIPPartida());
                
                //Mensaje retorno al que se une
                msjU.setIpContrario(partidas[msj.getIPPartida()].getIpJB());
                Socket sktU = new Socket(skt.getInetAddress(),9999);
                ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjU);
                
                //Mensaje al creador de la partida
                msjU.setIpContrario(skt.getInetAddress());
                sktU = new Socket(partidas[msj.getIPPartida()].getIpJB().getHostAddress(),9999);
                streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjU);
                
                
                
                skt.close();
                sktU.close();
                streamObjetos.close();
            }catch (IOException ex) {
                System.out.println("Socket de unión no creado correctamente");
            }
          }else{
            MensajeUnirse msjU = new MensajeUnirse();
            msjU.setIPPartida(-1);
            try {
                Socket sktU = new Socket(skt.getInetAddress(),9999);
                ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjU);
                
                skt.close();
                sktU.close();
                streamObjetos.close();
            } catch (IOException ex) {
                System.out.println("No mandé error de creación");
            }
          }
        }catch (NullPointerException e){
            MensajeUnirse msjU = new MensajeUnirse();
            msjU.setIPPartida(-1);
            try {
                Socket sktU = new Socket(skt.getInetAddress(),9999);
                ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjU);
                
                skt.close();
                sktU.close();
                streamObjetos.close();
            } catch (IOException ex) {
                System.out.println("No mandé error de creación");
            }
                
        }
    }
    
    public void msjPPJR (MensajePrimerosPasosJR msj, Socket skt){
        try {
            
            //Mensaje al JB
            Socket sktU = new Socket(partidas[msj.getIPPartida()].getIpJB().getHostAddress(),9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
            streamObjetos.writeObject(msj);
            
            //Mensaje al JR
            sktU = new Socket(partidas[msj.getIPPartida()].getIpJR().getHostAddress(),9999);
            streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
            streamObjetos.writeObject(msj);
            
            
            
            skt.close();
            sktU.close();
            streamObjetos.close();
        }catch (IOException ex) {
            System.out.println("Socket de PPR en server no creado correctamente");
        }
        
    }
    
    
    public void msjJugador (MensajeJ msj, Socket skt){
        try {
            
            //Mensaje al JB
            Socket sktU = new Socket(partidas[msj.getIPPartida()].getIpJB().getHostAddress(),9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
            streamObjetos.writeObject(msj);
            
            //Mensaje al JR
            sktU = new Socket(partidas[msj.getIPPartida()].getIpJR().getHostAddress(),9999);
            streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
            streamObjetos.writeObject(msj);
            
            
            
            skt.close();
            sktU.close();
            streamObjetos.close();
        }catch (IOException ex) {
            System.out.println("Socket de msjJugador en server no creado correctamente");
        }
    }
    

    public void msjPuntos (MensajePuntos msj, Socket skt){
        System.out.println("Recibí unos puntos");
        if(skt.getInetAddress().equals(partidas[msj.getIPPartida()].getIpJB())){
            pun1=msj.getPuntos();
            System.out.println("Recibi puntos blanco = "+pun1);
            peleador=msj.getPosicion();
        } else if(skt.getInetAddress().equals(partidas[msj.getIPPartida()].getIpJR())){
            pun2=msj.getPuntos();
            System.out.println("Recibi puntos rojo = "+pun1);
        }
        if(pun1!=0&&pun2!=0){
            System.out.println("Entré en la diff");
            if(pun1>=pun2){
                pun1=1;
                pun2=0;
            }else{
                pun1=0;
                pun2=1;
            }
            System.out.println("Recibí ambos puntos y el ganador: "+pun1 + ", "+ pun2);
            try {
                MensajePuntos msjP = new MensajePuntos();
                msjP.setPosicion(peleador);
                
                //Mensaje al JB
                msjP.setPuntos(pun1);
                Socket sktU = new Socket(partidas[msj.getIPPartida()].getIpJB().getHostAddress(),9999);
                ObjectOutputStream streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjP);

                //Mensaje al JR
                msjP.setPuntos(pun2);
                sktU = new Socket(partidas[msj.getIPPartida()].getIpJR().getHostAddress(),9999);
                streamObjetos = new ObjectOutputStream(sktU.getOutputStream());
                streamObjetos.writeObject(msjP);

                skt.close();
                sktU.close();
                streamObjetos.close();
            }catch (IOException ex) {
                System.out.println("Socket de msjPuntos en server no creado correctamente");
            }
            pun1=0;
            pun2=0;
        }
            
    }
    
}
