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
    private int advertencia = 0;
    
    private final int TAM_TABLERO = 8;
    private Celda [][] mapa = new Celda [TAM_TABLERO][TAM_TABLERO];
    //Guardar en Memoria una matriz del mapa mental
    // Funcionar normal
    // Cuando acabe, trazar una ruta óptima que sea paralela al resultado
    // Asignar vidas
    
    int contWumpus = 0, contTreasure = 0, contTrap = 0, contHunter = 0;
    
    final int N_WUMPUS = 1, N_TREASURE = 1, N_TRAP = TAM_TABLERO, N_HUNTER = 1;
    
    //Constructor
    public Celda(int valor, int status, int advertencia) {
        
        this.valor = valor; //Vacío = 0, Cazador = 1, Tesoro = 2, Hoyo = 3, Wumpus 4, Brillo = 5,
                            //Viento = 6, Hedor = 7
        this.status = status; //Descubierto = 1, No decubierto = 0
        this.advertencia = advertencia;
    }
    
    public Celda(){}
    
    
    public void crearCeldas(){
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                mapa [i][j] = new Celda(setRandomNumber(0, 5), 0, 0);
            }
        }
    }

    public void crearMapa() {
        crearCeldas();
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                
                //mapa [i][j] =  Celda(setRandomNumber(0, 2), 1);
                //System.out.print("[" + mapa[i][j].valor + ", " + mapa[i][j].status + "], "); // Mostrar todo
                rellenarEntidades(i,j);

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
        imprimirMapa();
    }
    
    public void imprimirMapa() {
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                //Mostrar en consola qué hay en casilla
                if(j == TAM_TABLERO-1){
                    //System.out.print("[" + mapa[i][j].valor + "]\n");
                    System.out.print(mapa[i][j].valor + "\n");
                } else {
                    //System.out.print("[" + mapa[i][j].valor + "], ");
                    System.out.print(mapa[i][j].valor + "  ");
                }
            }
        }
    }

    // Esta función revisa las entidades que se producen para poder validarlas y distribuirlas de
    // mejor manera para la generación del mapa
    
    boolean isNext = false;
    boolean isMuleta = false;
    
    public void rellenarEntidades(int i, int j){
        
        switch (mapa[i][j].valor) {
            
            case 0: break;
            
            case 1: // Limitando Cazador
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
                        agregarAdyacentes(i,j,5);
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
                        agregarAdyacentes(i,j,6);
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
                        agregarAdyacentes(i, j, 7);
                    } else if (contWumpus >= N_WUMPUS){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa [i][j].valor = 0;
                }
                break;
            default:
                //Aquí llega un valor que no puede llegar => Null
                System.out.println("Ha habido un problema, saludos cordiales.");
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
            
        } else if (entidad == 2 && contTreasure == 0){ // Caso sin tesoro
            System.out.println("Mapa Nuevo - Tesoro Validado");
            contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
            crearMapa();
            
        } else if (entidad == 4 && contWumpus == 0){ // Caso sin wumpus
            System.out.println("Mapa Nuevo - Wumpus Validado");
            contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
            crearMapa();
        }
    }
    /*
    public boolean casillaArriba(int y, Celda[][] mapa){
        boolean hayArriba = false;
        if (mapa != null && y >= 0 && y < mapa.length){
            if ( mapa[y] != null ){
                System.out.println("hay arriba");
                hayArriba = true;
                return hayArriba;
            }
        } else {
            System.out.println("No hay arriba");
            return hayArriba;
        }
        return hayArriba;
    }*/
    
    public void agregarAdyacentes(int i, int j, int adver){
        if(i-1 >= 0){ //si Arriba existe
            //System.out.println("antes: " + mapa[i-1][j].valor);
            //mapa[i-1][j].valor = adver;
            mapa[i-1][j].advertencia = adver;
            //System.out.println("después: " + mapa[i-1][j].valor);
        }

        if(i+1 < TAM_TABLERO){ //si Abajo existe
            //mapa[i+1][j].valor = adver;
            mapa[i+1][j].advertencia = adver;
        }

        if(j-1 >= 0){ // si Izquierda existe
            //mapa[i][j-1].valor = adver;
            mapa[i][j-1].advertencia = adver;
        }

        if(j+1 < TAM_TABLERO){ // si Derecha existe
            //mapa[i][j+1].valor = adver;
            mapa[i][j+1].advertencia = adver;
        }
    }
    public void borrarTablero(){
        
    }
    
    public void resetearValoresIniciales(){
        
    }
    
    
}
