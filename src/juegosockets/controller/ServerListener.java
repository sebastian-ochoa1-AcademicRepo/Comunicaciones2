/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import juegosockets.view.Servidor;

/**
 *
 * @author Skhan
 */
public class ServerListener implements ActionListener{
    
    Servidor server;
    
    public ServerListener(Servidor server) {
        this.server= server;
        ServerSocketListener SSL = new ServerSocketListener(server);
        Listeners();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        
        
        
        
    }
    
    private void Listeners(){
        //Agregar este listeners a todos los botones de la vista Servidor
    }
    
}
