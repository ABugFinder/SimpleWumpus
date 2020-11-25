/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalaginwumpus;

/**
 *
 * @author bicho
 */
public class ProyectoFinalAgInWumpus {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // MyFrame frame = new MyFrame();
        MyFrame myFrame = new MyFrame();
        
        Celda celda = new Celda();
        
        celda.crearMapa();
        //celda.moverCazador(4);
        
    }
    
}

/* 
    1.- Crear matriz de n*n. En este caso de 8x8 para testing la matriz debe contener un objeto casilla
    dónde pueden existir los objetos jugador, wumpus, tesoro, agujero parámetros de una casilla que no
    esté ocupada: puede tener hedor, brillo, briza o nada.

        jugador: debe poder analizar su entorno en las casillas adyacentes y guardar en su memoria los
        lugares visitados, deberá por indicación evitar al wumpus (no puede matarlo).
    
        wumpus: deja un hedor en casillas adyacentes, mata al jugador si lo toca.
        
        tesoro: deja un brillo en casillas adyacentes, si el jugador lo toca gana el juego.

        agujero: deja una briza en casillas adyacentes, si el jugador lo toca muere.

    2.- Menú para reiniciar partida, pausarla o terminar el juego. Deberá haber un menú como barra
    superior durante el juego para controlar el progreso del mismo, la idea es agilizar las pruebas
    en general, o bien, crear una partida nueva en caso de que el agente falle.

    3.- Guardar lugares visitados para recordarlos (arreglo de coordenadas en el mapa mental del jugador).
    Se refiere a una matriz idéntica al mapa del juego, pero que existe dentro del jugador y se va
    actualizando conforme va visitando el mapa.

    4.- buscar peligros antes de avanzar. El jugador deberá recorrer el mapa, intentando conocerlo para
    poder encontrar el tesoro, conforme lo visite encontrará peligros o pistas que lo ayuden a tomar
    decisiones sobre cuál debería ser la casilla más segura para visitar.

    5.- Peligros posiciones aleatorias. Cada partida generará un mapa con cosas aleatorias, siendo
    la posición del wumpus o de agujeros se deberá validar una cantidad de éstos que puedan existir
    así como los lugares dónde no puedan (siendo la propia ubicación inicial del jugador, del tesoro
    o del wumpus).

    6.- Posición de inicio y meta aleatoria. Se deberá validar la ubicación inicial del jugador y el
    tesoro de manera aleatoria por cada inicio de partida.

    7.- Menú de inicio de juego o bienvenida al proyecto.

    Actualizar mapa, evitar obstáculos, usar a estrella, valor alto para obstáculos, camino libre valor
    bajo, una vez creada la ruta, mostrar la animación del recorrido final (si existe). 

    
*/
