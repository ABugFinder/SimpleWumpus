/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalaginwumpus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics2D.*;
import java.util.Arrays;

/**
 *
 * @author bicho
 */
public class Graficos extends JPanel implements ActionListener {
    
    //Timer timer = new Timer(2000, this);
    int seconds = 0;
    
    int x = 0, y = 0, velX = 3, velY = 1;
    
    int xInicial = 50, yInicial = 50;
    int aCuadrado = 50;
    int tam = 8;
    
    Celda mapa[][];
    
    Celda celda = new Celda();
    
    
    int espacio = aCuadrado+15;
    
    JButton reiniciarBtn = new JButton("Reiniciar");
    
    JLabel imagen;
    
    String baseImagePath = "src/proyectofinalaginwumpus/";

    public Graficos() {
        
        celda.crearMapa();
                
        mapa = celda.getMapa();
        
        Timer timer = new Timer(1100, (ActionEvent e) -> {

            celda.moverCazador(3);
            repaint();
        });
       
       System.out.println("Segundos: " + seconds);
       /*
       if(x+ancho >= 750 || x < 0){
           velX*=-1;
       }
       x+=velX;*/
       
        timer.start();
        /*imagen = new JLabel(new ImageIcon("src/proyectofinalaginwumpus/Cazador.png"));
        imagen.setSize(aCuadrado, aCuadrado);
        imagen.setLocation(200, 200);
        this.setLayout(null);
        add(imagen);*/
        
        //celda.imprimirDatos();
        
        //System.out.println(Arrays.toString(celda.getMapa()));
        
        //reiniciarBtn.setLayout(null);
        //reiniciarBtn.setBounds(200, 200, 0, 0);
        reiniciarBtn.setFocusable(false);
        reiniciarBtn.setBackground(new Color(190,190,190));
        this.add(reiniciarBtn);
        
        reiniciarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                celda.resetearPartida();
            }
        });
        
    }
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(77,77,77)); // Morado: new Color(77,12,177)
       // pintarCirculoAmarillo(g);
        
        pintarMatriz(g);
        
        //pintarCazador(g);
    }
    /*
    public void pintarCirculoAmarillo(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.yellow);
        g2D.fillOval(x, y, 100, 100);
    }
    */
    /*
    public void pintarImagen(Graphics g){
        imagen = new JLabel(new ImageIcon("src/proyectofinalaginwumpus/Cazador.png"));
        imagen.setSize(aCuadrado, aCuadrado);
        imagen.setLocation(200, 200);
        this.setLayout(null);
        add(imagen);
    }*/
    
    public void pintarMapa(){
        for(int i = 0; i < mapa.length; i++){
            for(int j = 0; j < mapa.length; j++){
                System.out.print(mapa[i][j].valor + "  ");
            }
            System.out.println("");
        }
    }
    
    public void pintarWumpus(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Wumpus.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
    
    public void pintarCazador(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Cazador.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
    
     public void pintarTesoro(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Tesoro.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
     
      public void pintarTrampa(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Trampa.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
    
    public void pintarCuadrado(Graphics g, int x1, int y1) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(new Color(250,250,250));
        g2D.fillRect(x1, y1, aCuadrado, aCuadrado);
    }
    
    public void pintarMatriz(Graphics g) {
        // pintarCuadrado(g, xInicial, yInicial);
        for(int j = 0; j < tam ; j++){
             for(int i = 0; i < tam; i++){
                 pintarCuadrado(g, xInicial, yInicial);
                 
                 if(mapa[j][i].valor == 1){
                     pintarCazador(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].valor == 2){
                     pintarTesoro(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].valor == 3){
                     pintarTrampa(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].valor == 4){
                     pintarWumpus(g, xInicial, yInicial, aCuadrado);
                 } 
                 xInicial += espacio;
             }
             yInicial += espacio;
             xInicial = 50;
         }
        yInicial = 50;
    }
    /*
    public void gerPosActual(){
        mapa = celda.
    }
    
    public void moverAgente(){
         if(mapa.  >= 0) { // si Arriba existe
            System.out.println("yActual: "+ yActual + " yActual-1: " + (yActual-1));
            mapa[yActual][xActual].valor = 0;
            yActual--;
            mapa[yActual][xActual].valor = 1;
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
