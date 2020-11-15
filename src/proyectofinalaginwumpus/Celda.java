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
    private Celda [][] mapa = new Celda [TAM_TABLERO][TAM_TABLERO];
    
    int contWumpus = 0, contTreasure = 0, contTrap = 0, contHunter = 0;
    
    final int N_WUMPUS = 1, N_TREASURE = 1, N_TRAP = TAM_TABLERO, N_HUNTER = 1;
    //final int MIN_EMPITY_SPACE = (TAM_TABLERO*TAM_TABLERO)/2;
    
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
                
                //mapa [i][j] = new Celda(setRandomNumber(0, 2), 1);
                //System.out.print("[" + mapa[i][j].valor + ", " + mapa[i][j].status + "], "); // Mostrar todo
                rellenarEntidades(i,j);
                //Mostrar en consola qué hay en casilla
                if(j == TAM_TABLERO-1){
                    //System.out.print("[" + mapa[i][j].valor + "]\n");
                    System.out.print(mapa[i][j].valor + "\n");
                } else {
                    //System.out.print("[" + mapa[i][j].valor + "], ");
                    System.out.print(mapa[i][j].valor + "  ");
                }

                //Validando entidades perdidas al final de mapa
                if(i == TAM_TABLERO-1 && j == TAM_TABLERO-1){
                    for(int k = 0; k <= 4; k++) {
                        validarEntedadesPerdidas(k);
                    }
                    System.out.println("-----------------------\n"
                            + "Espacio libre = 0\n"
                            + "Cazador = 1\n"
                            + "Tesoro = 2\n"
                            + "Trampa = 3\n"
                            + "Monstruo = 4\n-----------------------");
                    System.out.println("Cazadores: " + contHunter + "\n"
                            + "Wumpus: " + contWumpus + "\n"
                            + "Tesoros: " + contTreasure + "\n"
                            + "Trampas: " + contTrap + "\n-----------------------");
                }
            }
        }
        

    }

    // Esta función revisa las entidades que se producen para poder validarlas y distribuirlas de
    // mejor manera para la generación del mapa
    public void rellenarEntidades(int i, int j){
        
        mapa [i][j] = new Celda(setRandomNumber(0, 5), 1);
                
        switch (mapa[i][j].valor) {
            
            case 1: // Entidad que será validadda según los límites asignados
                if(probailidadDeEntidad() == 1){
                    if(contHunter < N_HUNTER) {
                    mapa [i][j].valor = 1;
                    contHunter++;
                    } else if (contHunter >= N_HUNTER){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa [i][j].valor = 0;
                }
                
                break;
            case 2: // Limitando Tesoro
                if(probailidadDeEntidad() == 1) {
                    if(contTreasure < N_TREASURE) {
                        mapa [i][j].valor = 2;
                        contTreasure++;
                    } else if (contTreasure >= N_TREASURE){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa [i][j].valor = 0;
                }
                break;
            case 3: // Limitando Hoyo
                if(probailidadDeTrampa() == 1) {
                    if(contTrap < N_TRAP) {
                        mapa [i][j].valor = 3;
                        contTrap++;
                    } else if (contTrap >= N_TRAP){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa [i][j].valor = 0;
                }
                break;
            case 4: // Limitando Wumpus
                if (probailidadDeEntidad() == 1) {
                    if(contWumpus <= 0) {
                        mapa [i][j].valor = 4;
                        contWumpus++;
                    } else if (contWumpus >= N_WUMPUS){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa [i][j].valor = 0;
                }
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                //mapa[i][j].valor = 1777;
                break;
            default:
                //Aquí llega un valor que no puede llegar => Null
                break;
        }
    }
    
    public int setRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    // Método para mejorar la distribución de entidades por el mapa y que no se creen todas
    // en las primeras filas de la matriz
    public int probailidadDeEntidad() {
        int probabilidad = setRandomNumber(0, 11);
        
        if(probabilidad >= 9){
            return 1;
        } else {
            return 0;
        }
    }
    public int probailidadDeTrampa() {
        int probabilidad = setRandomNumber(0, 4);
        
        if(probabilidad > 1){
            return 1;
        } else {
            return 0;
        }
    }
    
    public void validarEntedadesPerdidas(int entidad){
        // Si no existe un hunter se seteará un mapa específico
        if (entidad == 1 && contHunter == 0){ // Caso sin cazador
            //Funcion para borrar matriz y crear un nuevo mapa
            System.out.println("Mapa Nuevo - Hunter Validado");
            contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
            crearMapa();
        }
        if (entidad == 2 && contTreasure == 0){ // Caso sin tesoro
            System.out.println("Mapa Nuevo - Tesoro Validado");
            contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
            crearMapa();
        }
        if (entidad == 4 && contWumpus == 0){ // Caso sin wumpus
            System.out.println("Mapa Nuevo - Wumpus Validado");
            contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
            crearMapa();
        }
    }
    
    public void borrarTablero(){
        
    }
    
    public void resetearValoresIniciales(){
        
    }
    
    
}
