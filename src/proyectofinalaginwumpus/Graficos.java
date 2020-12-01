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
    
    //Timer time = new Timer(1100, this);
    int seconds = 0;
    //int x = 0, y = 0, velX = 3, velY = 1;
    int xInicial = 30, yInicial = 30;
    int aCuadrado = 80;
    int tam = 6;
    int espacio = aCuadrado+15;

    Celda mapa[][];
    Celda celda = new Celda();
    
    JButton reiniciarBtn = new JButton("Reiniciar");
    JLabel imagen;
    String baseImagePath = "src/proyectofinalaginwumpus/";

    public Graficos() {
        
        celda.crearMapa();
                
        mapa = celda.getMapa();
        
        Timer timer = new Timer(400, (ActionEvent e) -> {
            
            
            celda.explorarMapa();
            repaint();
        });
       
       System.out.println("Segundos: " + seconds);
       /*
       if(x+ancho >= 750 || x < 0){
           velX*=-1;
       }
       x+=velX;*/
       
        timer.start();

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
    
    Color blanco = new Color(250,250,250);
    Color verde = new Color(180,250,180);
    Color azul = new Color(3,80,215);
    
    public void pintarCuadrado(Graphics g, int x1, int y1) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(blanco);
        g2D.fillRect(x1, y1, aCuadrado, aCuadrado);
    }
    
    public void pintarAdvertencia(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Alerta.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
      
    public void pintarDestello(Graphics g, int x, int y, int tam) {
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Destello.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
    
    
    public void pintarMatriz(Graphics g) {
        // pintarCuadrado(g, xInicial, yInicial);
        for(int j = 0; j < tam ; j++){
             for(int i = 0; i < tam; i++){
                 pintarCuadrado(g, xInicial, yInicial);
                 
                 if(mapa[j][i].valor == 1){ // Hay cazador
                     pintarCazador(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].isTesoro){ // Hay tesoro
                     pintarTesoro(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].isTrampa){ // Hay trampa
                     pintarTrampa(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[j][i].isWumpus){ // Hay Wumpus
                     pintarWumpus(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[i][j].advertencia == 5){ // Hay brillo
                     pintarDestello(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[i][j].advertencia == 6){ // Hay viento
                     pintarAdvertencia(g, xInicial, yInicial, aCuadrado);
                 } else if(mapa[i][j].advertencia == 7){ // Hay hedor
                     pintarAdvertencia(g, xInicial, yInicial, aCuadrado);
                 }
                 xInicial += espacio;
             }
             yInicial += espacio;
             xInicial = 50;
         }
        yInicial = 50;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
