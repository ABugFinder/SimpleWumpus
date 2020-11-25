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

/**
 *
 * @author bicho
 */
public class Graficos extends JPanel implements ActionListener {
    
    //Timer timer = new Timer(10, this);
    int seconds = 0;
    
    int x = 0, y = 0, velX = 3, velY = 1;
    
    int xInicial = 50, yInicial = 50;
    int aCuadrado=50;
    int tam = 5;
    
    int espacio = aCuadrado+15;
    
    JLabel imagen;
    
    String baseImagePath = "src/proyectofinalaginwumpus/";

    public Graficos() {
        //timer.start();
        /*imagen = new JLabel(new ImageIcon("src/proyectofinalaginwumpus/Cazador.png"));
        imagen.setSize(aCuadrado, aCuadrado);
        imagen.setLocation(200, 200);
        this.setLayout(null);
        add(imagen);*/
    }
    
   
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        this.setBackground(new Color(77,77,77)); // Morado: new Color(77,12,177)
        
       // pintarCirculoAmarillo(g);
        
        pintarMatriz(g);
        
        //pintarCazador(g);
       
    }
    
    public void pintarCirculoAmarillo(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.yellow);
        g2D.fillOval(x, y, 100, 100);
    }
    
    /*
    public void pintarImagen(Graphics g){
        imagen = new JLabel(new ImageIcon("src/proyectofinalaginwumpus/Cazador.png"));
        imagen.setSize(aCuadrado, aCuadrado);
        imagen.setLocation(200, 200);
        this.setLayout(null);
        add(imagen);
    }*/
    
    public void pintarCazador(Graphics g, int x, int y, int tam){
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image image = t.getImage(baseImagePath+"Wumpus.png");  
        
        g.drawImage(image, x, y, tam, tam, null, this);
    }
    
    public void pintarCuadrado(Graphics g, int x1, int y1){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(new Color(250,250,250));
        g2D.fillRect(x1, y1, aCuadrado, aCuadrado);
    }
    
    public void pintarMatriz(Graphics g){
        // pintarCuadrado(g, xInicial, yInicial);
        for(int j = 0; j < tam ; j++){
             for(int i = 0; i < tam; i++){
                 pintarCuadrado(g, xInicial, yInicial);
                 pintarCazador(g, xInicial, yInicial, aCuadrado);
                 xInicial += espacio;
                 //System.out.println("Hola");
             }
             yInicial += espacio;
             xInicial = 25;
         }
        yInicial = 25;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
       //seconds++;
       //System.out.println("Segundos: " + seconds);
       /*
       if(x+ancho >= 750 || x < 0){
           velX*=-1;
       }
       x+=velX;*/
       
       repaint();
    }
    
}
