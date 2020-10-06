/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import juegosockets.model.*;
import juegosockets.view.Cliente;


/**
 *
 * @author Skhan
 */
public class ClientSocketListener implements Runnable{
    
    Cliente clt;
    ClientMensajeControlador msjCC;
    private String ipServidor;

    @Override
    public void run() {
        
        try {
            ServerSocket sktServer = new ServerSocket(9999);
            
            while(true){
            
                Socket sktReceptor = sktServer.accept();
                ObjectInputStream entradaObjetos = new ObjectInputStream(sktReceptor.getInputStream());
                
                Object objeto = entradaObjetos.readObject();
            
                msjCC.leeObjeto(objeto, sktReceptor);
                entradaObjetos.close();
            }
        } catch (IOException ex) {
            System.out.println("Error estableciendo Socket Servidor");
        } catch (ClassNotFoundException ex) {
            System.out.println("No mandó nichimba");
        }
        
    }

    public ClientSocketListener(Cliente clt) {
        this.clt = clt;
        msjCC = new ClientMensajeControlador(clt);
        Thread hilo = new Thread(this);
        hilo.start();
    }
    
    public void conectarServidor(String ip){
        ipServidor=ip;
        try {
            MensajeConexion msj = new MensajeConexion();
            Socket skt = new Socket(ip,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);
            
            skt.close();
            streamObjetos.close();
            clt.lConectado.setText("Conectado");
        } catch (IOException ex) {
            clt.lConectado.setText("No conectado");
            System.out.println("Socket no creado correctamente");
        }
        
    }
    
    public void crearP(){
        msjCC.jugadorB=true;
        try {
            MensajeCrearP msj = new MensajeCrearP();
            Socket skt = new Socket(ipServidor,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);
            
            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("No creé socket de creador");
        }
    }

    public void unirse(int IdPartida){
        try {
            MensajeUnirse msj = new MensajeUnirse();
            msj.setIPPartida(IdPartida);
            Socket skt = new Socket(ipServidor,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);
            
            msjCC.esTurno=true;
            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("Socket de unión no creado correctamente");
        }
        
    }
    
    public void PPR(int[][] fichas){
        try {
            MensajePrimerosPasosJR msj = new MensajePrimerosPasosJR();
            msj.setIPPartida(msjCC.idPartida);
            msj.setFichas(fichas);
            Socket skt = new Socket(ipServidor,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);

            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("Socket de PPR no creado correctamente");
        }
    }
    
    public void msjJugador(int[] dados,int ficha,boolean pelea){
        try {
            
            MensajeJ msj = new MensajeJ();
            msj.setPelea(pelea);
            msj.setIPPartida(msjCC.idPartida);
            msj.setFicha(dados);
            msj.setiFicha(ficha);
            
            Socket skt = new Socket(ipServidor,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);
            
            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("Socket de msj Jugador no creado correctamente");
        }
    }
    
    public void puntos(int puntaje, int peleador){
        try {
            
            MensajePuntos msj = new MensajePuntos();
            msj.setPosicion(peleador);
            msj.setIPPartida(msjCC.idPartida);
            msj.setPuntos(puntaje);
            
            Socket skt = new Socket(ipServidor,9999);
            ObjectOutputStream streamObjetos = new ObjectOutputStream(skt.getOutputStream());
            streamObjetos.writeObject(msj);

            System.out.println("Envié el mensaje de puntos: "+msj.getPuntos());
            
            skt.close();
            streamObjetos.close();
        } catch (IOException ex) {
            System.out.println("Socket de puntos no creado correctamente");
        }
    }
    
    //Programar envio de Pelea y Puntos
    
}
