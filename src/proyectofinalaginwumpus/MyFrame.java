/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalaginwumpus;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author bicho
 */
public class MyFrame extends JFrame {
    
    Graficos graficos = new Graficos();
    
    MyFrame() {
        this.setTitle("Wumpus");
        /*
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width/2, height/2);
        */
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        // this.setLayout(null);
        this.setSize(750, 750);
        
        this.add(graficos);
        
        this.setVisible(true);
    }
    
}
