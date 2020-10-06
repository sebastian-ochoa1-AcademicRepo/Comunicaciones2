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
import java.util.logging.Level;
import java.util.logging.Logger;
import juegosockets.model.*;
import juegosockets.view.Servidor;

/**
 *
 * @author Skhan
 */
public class ServerSocketListener implements Runnable{

    Servidor srv;
    ServerMensajeControlador msjC;
    
    @Override
    public void run() {
        
        
        
        try {
            ServerSocket sktServer = new ServerSocket(9999);
            
            while(true){
            
                Socket sktReceptor = sktServer.accept();
                ObjectInputStream entradaObjetos = new ObjectInputStream(sktReceptor.getInputStream());
                
                Object objeto = entradaObjetos.readObject();
            
                msjC.leeObjeto(objeto, sktReceptor);
                entradaObjetos.close();
            }
        } catch (IOException ex) {
            System.out.println("Error estableciendo Socket Servidor");
        } catch (ClassNotFoundException ex) {
            System.out.println("No mandó nichimba");
        }
        
        
        
    }

    public ServerSocketListener(Servidor srv) {
        this.srv=srv;
        msjC = new ServerMensajeControlador(srv);
        Thread hilo = new Thread(this);
        hilo.start();
    }
    
    
}
