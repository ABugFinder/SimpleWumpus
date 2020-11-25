/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalaginwumpus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author bicho
 */
public class Graficos extends JPanel implements ActionListener {
    
    Timer timer = new Timer(10, this);
    int seconds = 0;
    
    int x=0, y=0, velX=3, velY=1;
    int ancho = 75;
    
    public Graficos() {
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        this.setBackground(new Color(77,12,177)); // Morado: new Color(77,12,177)
        
        //pintarCirculoAmarillo(g);
        
    }
    
    
    public void pintarCirculoAmarillo(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.yellow);
        g2D.fillOval(x, y, 100, 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       /*
       seconds++;
        System.out.println("Segundos: " + seconds);
        */
       if(x+ancho >= 750 || x < 0){
           velX*=-1;
       }
       x+=velX;
       
       repaint();
       
    }
    
}
