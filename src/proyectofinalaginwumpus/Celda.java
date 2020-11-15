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
public class Celda {
    
    //Variables globales
    private int valor;
    //private boolean isWumpus = false, isHunter = false, isTreasure = false;
    private int status = 0;
    
    private final int TAM_TABLERO = 8;
    private final Celda [][] MAPA = new Celda [TAM_TABLERO][TAM_TABLERO];
    
    final int N_WUMPUS = 1, N_TREASURE = 1, N_TRAP = 4, N_HUNTER = 1;
    final int MIN_EMPITY_SPACE = (TAM_TABLERO*TAM_TABLERO)/2;
    
    //Constructor
    public Celda(int valor, int status ) {
        
        this.valor = valor; //Vacío = 0, Cazador = 1, Tesoro = 2, Hoyo = 3, Wumpus 4, Brillo = 5,
                            //Viento = 6, Hedor = 7
        this.status = status; //Descubierto = 1, No decubierto = 0
        
    }
    
    public Celda(){}

    public void crearMapa() {
        
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                
                //MAPA [i][j] = new Celda(setRandomNumber(0, 2), 1);
                //System.out.print("[" + MAPA[i][j].valor + ", " + MAPA[i][j].status + "], "); // Mostrar todo
                rellenarEntidades(i,j);
                System.out.print("[" + MAPA[i][j].valor + "], "); //Mostrar que hay en casilla
            }
            System.out.println("");
        }
    }
    int contWumpus = 0, contTreasure = 0, contTrap = 0, contHunter = 0;
    // Esta función revisa las entidades que se producen para poder validarlas y distribuirlas de mejor manera
    // para la generación del mapa
    public void rellenarEntidades(int i, int j){
        
        MAPA [i][j] = new Celda(setRandomNumber(0, 5), 1);
                
        switch (MAPA[i][j].valor) {
            
            case 1: // Entidad que será validadda según los límites asignados
                if(contHunter <= 0) {
                    MAPA [i][j].valor = 1;
                    contHunter++;
                } else if (contHunter >= N_HUNTER){
                    MAPA [i][j].valor = 0;
                }
                break;
            case 2: // Limitando Tesoro
                if(contTrap <= 0) {
                    MAPA [i][j].valor = 2;
                    contTreasure++;
                } else if (contTreasure >= N_TREASURE){
                    MAPA [i][j].valor = 0;
                }
                break;
            case 3: // Limitando Hoyo
                if(contTrap <= 0) {
                    MAPA [i][j].valor = 3;
                    contTrap++;
                } else if (contTrap >= N_TRAP){
                    MAPA [i][j].valor = 0;
                }
                break;
            case 4: // Limitando Wumpus
                if(contWumpus <= 0) {
                    MAPA [i][j].valor = 4;
                    contWumpus++;
                } else if (contWumpus >= N_WUMPUS){
                    MAPA [i][j].valor = 0;
                }
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                //MAPA[i][j].valor = 1777;
                break;
            default:
                //Aquí llega un valor que no puede llegar => Null
                break;
        }
    }
    
    public int setRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    
}
