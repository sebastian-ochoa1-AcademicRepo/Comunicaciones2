/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosockets.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegosockets.model.Jugador;
import juegosockets.view.Cliente;

/**
 *
 * @author Skhan
 */
public class ClientListener implements ActionListener{
    
    Cliente client;
    ClientSocketListener sktClient;
    
    int sumaDados=0;
    int iFichaS;
    int peleada;
    boolean hayFicha=false;
    int[] fichaSeleccionada = new int[2];
    
    int turno=0;
    int movimiento[] = new int[2];
    
    int fichasPPR[][]=new int[4][2];
    int f=0;
    //OPCIONAL: CONTROLAR QUE APARICION EN MISMA FICHA QUE OTRA, ORGANIZAR GODIGO POR REPSONSABILIDADES, DESAPARECER EL BOTON PELEA DEL JR, 
    
    public ClientListener(Cliente client) {
        this.client= client;
        sktClient= new ClientSocketListener(client);
        Listeners();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == client.btConectarServidor){
            sktClient.conectarServidor(client.IPServidor.getText());
            client.pnlIServer.setVisible(false);
            client.pnlCrearUnirse.setVisible(true);
        }
        
        if(e.getSource() == client.btCrear){
            sktClient.crearP();
        }
        
        if(e.getSource() == client.btUnirse){
            client.pnlCrearUnirse.setVisible(false);
            client.pnlUnirse.setVisible(true);
        }
        
        if(e.getSource() == client.btUnirsePartida){
            sktClient.unirse(Integer.parseInt(client.idPartida.getText()));
        }
        
        if(e.getSource() == client.btLanzar){
            if(!sktClient.msjCC.pelea){
                if(turno==0&&sktClient.msjCC.jugadorB){
                    int[] dados=Jugador.dados();
                    client.lDado1.setText(""+dados[0]);
                    client.lDado2.setText(""+dados[1]);
                    sktClient.msjJugador(dados, -1, false);
                    turno++;
                }if(turno!=0){
                    int[] dados=Jugador.dados();
                    if(!sktClient.msjCC.jugadorB){
                        client.lDado1.setText(""+dados[0]/2);
                        client.lDado2.setText(""+dados[1]/2);
                        sumaDados = (dados[0]/2) + (dados[1]/2);
                        if(sumaDados==0)sumaDados=1;
                    }else{
                        client.lDado1.setText(""+dados[0]);
                        client.lDado2.setText(""+dados[1]);
                        sumaDados = dados[0] + dados[1];
                    }
                    client.btLanzar.setEnabled(false);
                }
            }else{
                int[] dados=Jugador.dados();
                sktClient.puntos(dados[0] + dados[1],sktClient.msjCC.peleador);
                client.lDado1.setText(""+dados[0]);
                client.lDado2.setText(""+dados[1]);
                client.btLanzar.setEnabled(false);
                sktClient.msjCC.pelea=false;
            }
            
        }
        
        
        if(sktClient.msjCC.esTurno){
            
            for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    
                    if(e.getSource()==client.posicion[i][j]){
                        
                        if(turno==0&&!sktClient.msjCC.jugadorB){
                            boolean puede=true;
                            for(int k =0;i<f;i++){
                                if(fichasPPR[k][0]==i&&fichasPPR[k][1]==j){
                                    puede=false;
                                }
                            }
                            if(puede){
                                client.posicion[i][j].setBackground(Color.red);
                                fichasPPR[f][0]=i;
                                fichasPPR[f][1]=j;
                                f++;
                            }
                            if(f==4){
                                turno++;
                                sktClient.msjCC.esTurno=false;
                                client.posicion[fichasPPR[0][0]][fichasPPR[0][1]].setBackground(Color.white);
                                client.posicion[fichasPPR[1][0]][fichasPPR[1][1]].setBackground(Color.white);
                                client.posicion[fichasPPR[2][0]][fichasPPR[2][1]].setBackground(Color.white);
                                client.posicion[fichasPPR[3][0]][fichasPPR[3][1]].setBackground(Color.white);
                                sktClient.PPR(fichasPPR);
                            }
                        }
                        if(turno!=0&&sumaDados!=0){
                            
                            if(hayFicha){
                                
                                //Si es igual a la posisión de otra ficha,no haga nada
                                
                                int Conteo = Math.abs(fichaSeleccionada[0] - i) + Math.abs(fichaSeleccionada[1] - j);
                                if(Conteo<=sumaDados){
                                    sumaDados-=Conteo;
                                    
                                    if(iFichaS<0){
                                        client.posicion[fichaSeleccionada[0]][fichaSeleccionada[1]].setBackground(Color.white);
                                        client.posicion[i][j].setBackground(Color.black);
                                    }else{
                                        client.posicion[fichaSeleccionada[0]][fichaSeleccionada[1]].setBackground(Color.white);
                                        client.posicion[i][j].setBackground(Color.pink);
                                    }
                                    fichaSeleccionada[0]=i;
                                    fichaSeleccionada[1]=j;
                                    if(sumaDados==0){
                                        turno++;
                                        if(sktClient.msjCC.jugadorB){
                                            if(puedePelear(fichaSeleccionada,sktClient.msjCC.JR.getFichas()[0])){
                                                sktClient.msjCC.peleador=0;
                                                sktClient.msjJugador(fichaSeleccionada, iFichaS, true);
                                                sktClient.msjCC.pelea=true;
                                            }else if(puedePelear(fichaSeleccionada,sktClient.msjCC.JR.getFichas()[1])){
                                                sktClient.msjCC.peleador=1;
                                                sktClient.msjJugador(fichaSeleccionada, iFichaS, true);
                                                sktClient.msjCC.pelea=true;
                                            }else if(puedePelear(fichaSeleccionada,sktClient.msjCC.JR.getFichas()[2])){
                                                sktClient.msjCC.peleador=2;
                                                sktClient.msjJugador(fichaSeleccionada, iFichaS, true);
                                                sktClient.msjCC.pelea=true;
                                            }else if(puedePelear(fichaSeleccionada,sktClient.msjCC.JR.getFichas()[3])){
                                                sktClient.msjCC.peleador=3;
                                                sktClient.msjJugador(fichaSeleccionada, iFichaS, true);
                                                sktClient.msjCC.pelea=true;
                                            }else{
                                                sktClient.msjJugador(fichaSeleccionada, iFichaS, false);
                                            }
                                        }else{
                                            sktClient.msjJugador(fichaSeleccionada, iFichaS, false);
                                        }
                                        hayFicha=false;
                                    }
                                }
                            }else{
                                if(!sktClient.msjCC.jugadorB){
                                    if(sktClient.msjCC.JR.getFichas()[0][0]==i&&sktClient.msjCC.JR.getFichas()[0][1]==j){iFichaS=0;hayFicha=true;fichaSeleccionada=sktClient.msjCC.JR.getFichas()[0];client.posicion[i][j].setBackground(Color.pink);}
                                    if(sktClient.msjCC.JR.getFichas()[1][0]==i&&sktClient.msjCC.JR.getFichas()[1][1]==j){iFichaS=1;hayFicha=true;fichaSeleccionada=sktClient.msjCC.JR.getFichas()[1];client.posicion[i][j].setBackground(Color.pink);}
                                    if(sktClient.msjCC.JR.getFichas()[2][0]==i&&sktClient.msjCC.JR.getFichas()[2][1]==j){iFichaS=2;hayFicha=true;fichaSeleccionada=sktClient.msjCC.JR.getFichas()[2];client.posicion[i][j].setBackground(Color.pink);}
                                    if(sktClient.msjCC.JR.getFichas()[3][0]==i&&sktClient.msjCC.JR.getFichas()[3][1]==j){iFichaS=3;hayFicha=true;fichaSeleccionada=sktClient.msjCC.JR.getFichas()[3];client.posicion[i][j].setBackground(Color.pink);}

                                }else{
                                    if(sktClient.msjCC.JB.getFicha()[0]==i&&sktClient.msjCC.JB.getFicha()[1]==j){iFichaS=-1;hayFicha=true;fichaSeleccionada=sktClient.msjCC.JB.getFicha();}
                                }
                            }
                        }
                    }
                    
                }
            }
            
            
        }
            
        
        
    }
    
    
    private boolean puedePelear(int[] fichaB, int[] fichaR){
        if(fichaB[0]==fichaR[0]&&(fichaB[1]==(fichaR[1]-1)||fichaB[1]==(fichaR[1]+1)))return true;
        if(fichaB[1]==fichaR[1]&&(fichaB[0]==(fichaR[0]-1)||fichaB[0]==(fichaR[0]+1)))return true;
        return false;
    }
    
    private void Listeners(){
        client.btConectarServidor.addActionListener(this);
        client.btUnirsePartida.addActionListener(this);
        client.btUnirse.addActionListener(this);
        client.btLanzar.addActionListener(this);
        client.btCrear.addActionListener(this);
        for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    client.posicion[i][j].addActionListener(this);
                }
            }

    }
    
}
