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
    int valor;
    int status, advertencia, cordX, cordY;
    private int contWumpus = 0, contTreasure = 0, contTrap = 0, contHunter = 0;
    final int TAM_TABLERO = 6; // Se recomienda valores menores a 100
    private Celda [][] mapa = new Celda [TAM_TABLERO][TAM_TABLERO];
    private Celda [][] mapaMental = new Celda [TAM_TABLERO][TAM_TABLERO];
    
    boolean isWumpus = false; boolean isTrampa = false; boolean isTesoro= false;
    
    double mitadTablero = Math.pow(TAM_TABLERO, TAM_TABLERO)/2;
    final int N_WUMPUS = 1, N_TREASURE = 1, N_TRAP = (int)mitadTablero, N_HUNTER = 1;
    int vidas = 5;
    
    int xActual = 0, yActual = 0;
    
    boolean isDerecha = true;
    boolean isArriba = true;
    boolean isTop = false, isLeft = false;
    boolean isZigD = false, isZigA = false, isZigI = false, isLastD = false, isRepZig = false;
    
    
    // TODO List
    // Guardar en Memoria una matriz del mapa mental
    // Funcionar normal
    // Heurística
    // Cuando acabe, trazar una ruta óptima que sea paralela al resultado (a estrella)
    // Asignar vidas = 5
    
    //Constructor
    public Celda(int valor, int status, int advertencia, int cordX, int cordY, boolean isWumpus, boolean isTrampa, boolean isTesoro)  {
        //Vacío = 0, Cazador = 1, Tesoro = 2, Hoyo = 3, Wumpus 4, Brillo = 5, Viento = 6, Hedor = 7
        this.valor = valor; 
        this.status = status; //Descubierto = 1, No decubierto = 0
        this.advertencia = advertencia;
        this.cordX = cordX;
        this.cordY = cordY;
        this.isWumpus = isWumpus;
        this.isTrampa = isTrampa;
        this.isTesoro = isTesoro;
    }
    
    public Celda(){}
    
    public void crearCeldas(){
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                mapa [i][j] = new Celda(setRandomNumber(0, 5), 0, 0,i,j,false,false,false);
                mapaMental [i][j] = new Celda(0, 0, 0, 0, 0,false,false,false);
            }
        }
    }

    public void crearMapa() {
        crearCeldas();
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                rellenarEntidades(i,j);
            }
        }
        //Validar mapa por si no fue correctamente construido
        if(contHunter==0 || contTreasure == 0 || contWumpus == 0){
            resetearPartida();
            //System.out.println("Se reinició el mapa");
        } else {
            imprimirMapa();
            imprimirDatos();
        }
        imprimirPosActualHunter();
    }
    
    public Celda[][] getMapa() {
        return mapa;
    }
    
    public void controlarHunter(){
        moverCazador(1);
        moverCazador(2);
        moverCazador(3);
        moverCazador(4);
    }
    
    public void imprimirDatos(){
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
    
    public void imprimirMapa() {
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                //System.out.print("[" + mapa[i][j].valor + ", " + mapa[i][j].status + ", " + mapa[i][j].advertencia + "] "); // Mostrar todo
                //Mostrar en consola qué hay en casilla
                if(j == TAM_TABLERO-1){
                    //System.out.print("[" + mapa[i][j].valor + "]\n");
                    System.out.print(mapa[i][j].valor + "\n");
                    //System.out.println("i: " + i + " j: " + j);
                } else {
                    //System.out.print("[" + mapa[i][j].valor + "], ");
                    System.out.print(mapa[i][j].valor + "  ");
                    //System.out.println("i: " + i + " j: " + j);
                }
            }
        }
    }

    // Esta función revisa las entidades que se producen para poder validarlas y distribuirlas de
    // mejor manera para la generación del mapa
    public void rellenarEntidades(int i, int j){
        switch (mapa[i][j].valor) {
            case 0: break;
            
            case 1: // Limitando Cazador
                if(probailidadDeEntidad() == 1){
                    if(contHunter < N_HUNTER) {
                        mapa [i][j].valor = 1;
                        guardarPosInicial(i, j);
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
                        mapa [i][j].isTesoro = true;
                        contTreasure++;
                        agregarAdyacentes(j,i,5);
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
                        mapa [i][j].isTrampa = true;
                        contTrap++;
                        agregarAdyacentes(j,i,6);
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
                        mapa [i][j].isWumpus = true;
                        contWumpus++;
                        agregarAdyacentes(j, i, 7);
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
    
   /*public Celda[][] getMapa() {
        Celda newArr[][] = null;
        //newArr = new Celda[][];
        for(int i=0; i< mapa.length; i++) {
            for(int j=0; j< mapa.length; j++) {
                newArr[i][j].valor = mapa[i][j].valor;
            }
        }
        return newArr;
    }*/
   
    
    public int setRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    // Método para mejorar la distribución de entidades por el mapa y que no se creen todas
    // en las primeras filas de la matriz
    public int probailidadDeEntidad() {
        int probabilidad = setRandomNumber(0, 15);
        if(probabilidad >= 12){
            return 1;
        } else {
            return 0;
        }
    }
    
    public int probailidadDeTrampa() {
        //A mayor rango máximo, mayor cantidad de trampas
        int probabilidad = setRandomNumber(0, 2);
        if(probabilidad >= 1){
            return 1;
        } else {
            return 0;
        }
    }
    
    // Crea un nuevo mapa en caso de haberse generado mal
    /*
    public void validarEntedadesPerdidas(int entidad){
        // Si no existe un hunter se seteará un mapa específico
        if (entidad == 1 && contHunter == 0){ // Caso sin cazador
            //Funcion para borrar matriz y crear un nuevo mapa
            System.out.println("Mapa Nuevo - Hunter Validado");
            resetearPartida();
        } else
        if (entidad == 2 && contTreasure == 0){ // Caso sin tesoro
            System.out.println("Mapa Nuevo - Tesoro Validado");
            resetearPartida();
        } else 
        if (entidad == 4 && contWumpus == 0){ // Caso sin wumpus
            System.out.println("Mapa Nuevo - Wumpus Validado");
            resetearPartida();
        }
    }
    */
    
    public void resetearPartida() {
        contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
        xActual = 0; yActual = 0;
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                mapa [i][j].valor = 0;
                mapa [i][j].status = 0;
                mapa [i][j].advertencia = 0;
                mapaMental [i][j] = null;
                isLeft = false;
                isTop = false;
            }
        }
        crearMapa();
    }
    
    // Valida los avisos de las entidades en sus posiciones adyacentes
    public void agregarAdyacentes(int i, int j, int adver) {
        if(i-1 >= 0){ //si Arriba existe
            //System.out.println("antes: " + mapa[i-1][j].valor);
            //mapa[i-1][j].valor = adver;
            mapa[i-1][j].advertencia = adver;
            //System.out.println("después: " + mapa[i-1][j].valor);
        }

        if(i+1 < TAM_TABLERO) { //si Abajo existe
            //mapa[i+1][j].valor = adver;
            mapa[i+1][j].advertencia = adver;
        }

        if(j-1 >= 0) { // si Izquierda existe
            //mapa[i][j-1].valor = adver;
            mapa[i][j-1].advertencia = adver;
        }

        if(j+1 < TAM_TABLERO) { // si Derecha existe
            //mapa[i][j+1].valor = adver;
            mapa[i][j+1].advertencia = adver;
        }
    }
    
    public void guardarPosInicial(int filas, int columnas){
        mapaMental[columnas][filas].cordX = columnas; xActual = columnas;
        mapaMental[columnas][filas].cordY = filas; yActual = filas;
        //System.out.println("PosActual de Cazador "
        //+ "X: " + mapaMental[columnas][filas].cordX + ", Y: " + mapaMental[columnas][filas].cordY);
    }
    
    public void imprimirPosActualHunter() {
        System.out.println("PosActual de Cazador " + "X: " + xActual + ", Y: " + yActual);
    }
    
    public void canMove(int sentido) {
        switch(sentido){
            
        }
    }
    
    public void moverCazador(int sentido) { //arriba = 1, derecha = 2, abajo = 3, izquierda = 4;
        switch(sentido){
            case 1:
                if(yActual-1 >= 0) { // si Arriba existe
                    System.out.println("yActual: "+ yActual + " yActual-1: " + (yActual-1));
                    mapa[yActual][xActual].valor = 0;
                    yActual--;
                    mapa[yActual][xActual].valor = 1;
                } else {
                    System.out.println("Fuera de límites (no es posible moverse arriba)");
                }
                break;
            case 2:
                if(xActual+1 < TAM_TABLERO) { // si Derecha existe
                    System.out.println("xActual: "+ xActual + " xActual+1: " + (xActual+1));
                    mapa[yActual][xActual].valor = 0;
                    xActual++;
                    mapa[yActual][xActual].valor = 1;
                } else {
                    System.out.println("Fuera de límites (no es posible moverse derecha)");
                }
                break;
            case 3: 
                if(yActual+1 < TAM_TABLERO) { // si Abajo existe
                    System.out.println("yActual: "+ yActual + " yActual+1: " + (yActual+1));
                    mapa[yActual][xActual].valor = 0;
                    yActual++;
                    mapa[yActual][xActual].valor = 1;
                } else {
                    System.out.println("Fuera de límites (no es posible moverse abajo)");
                }
                break;
            case 4:
                if(xActual-1 >= 0) { // si Izquierda existe
                    System.out.println("xActual: "+ xActual + " xActual-1: " + (xActual-1));
                    mapa[yActual][xActual].valor = 0;
                    xActual--;
                    mapa[yActual][xActual].valor = 1;
                } else {
                    System.out.println("Fuera de límites (no es posible moverse izquierda)");
                }
                break;
            default:
                break;
        }
        
        imprimirMapa();
    }
    
    public void explorarMapa() {
        int caso = 0;
        if(!isTop) caso = 1;
        if(isTop) caso = 2;
        
        /*
        if(isTop && isLeft) {
            isZigA = true;
            caso = 3;
        }
        if(isZigD && !isZigA) {
            caso = 4;
        }
        if(isLastD) caso = 3;
        if(isZigI) caso = 5;
        if(isRepZig) caso = 3;
        */
        
        if(isTop && isLeft) {
            isZigA = true;
            if(isZigA){
                caso = 3;
            }
            
        }
        
        switch(caso){
            case 1:
                if(!isTop && yActual != 0){
                    irArriba();
                } else if(yActual == 0){
                    isTop = true;
                }
            break;
            
            case 2:
               if(!isLeft && isTop && xActual > 0){
                    irIzquierda();
                } else if(xActual == 0){
                    isLeft = true;
                }
            break;
            
            case 3:
                 if(yActual+1 <= 8){
                    irAbajo();
                } else {
                    isZigA = false;
                    isZigD = true;
                }
                break;
                
            case 4:
                irIzquierda();
                break;
                
            case 5:
                break;
                
            case 6:
                break;
                
                default:
                    System.out.println("");
        }
        
    }
    
    public void irArriba(){
        moverCazador(1);
    }
    
    public void irDerecha(){
        moverCazador(2);
    }
    
    public void irAbajo(){
        moverCazador(3);
    }
    
    public void irIzquierda(){
        moverCazador(4);
    }
    
    public void explorarY(){
        // Validar movimiento en eje Y
        if(yActual > 0 && isArriba){
            moverCazador(1);
        } else {
            System.out.println(yActual);
            isArriba = false;
        }
        if(yActual < TAM_TABLERO-1 && !isArriba){
            moverCazador(3);
        } else {
            System.out.println(yActual);
            isArriba = true;
        }
    }
    
    public void explorarX(){
        // Validar movimiento en eje X
        if(xActual > 0 && !isDerecha){
            moverCazador(4);
        } else {
            isDerecha = true;
        }
        if(xActual < TAM_TABLERO && isDerecha){
            moverCazador(2);
            System.out.println(xActual);
        } else {
            isDerecha = false;
        }
    }
    
}
