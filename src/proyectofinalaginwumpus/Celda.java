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
    int status, advertencia, cordX, cordY, h;
    private int contWumpus = 0, contTreasure = 0, contTrap = 0, contHunter = 0;
    final int TAM_TABLERO = 8; // Se recomienda valores menores a 100
    private Celda [][] mapa = new Celda [TAM_TABLERO][TAM_TABLERO];
    private Celda [][] mapaMental = new Celda [TAM_TABLERO][TAM_TABLERO];
    
    boolean isWumpus = false, isTrampa = false, isTesoro= false, isPlayer = false;
    
    double mitadTablero = Math.pow(TAM_TABLERO, TAM_TABLERO)/2;
    final int N_WUMPUS = 1, N_TREASURE = 1, N_TRAP = (int)mitadTablero, N_HUNTER = 1;
    
    int vidas = 5;
    
    int xActual = 0, yActual = 0;
    int xTesoro = 0, yTesoro = 0;
    int xInicial = 0, yInicial = 0;
    
    int contMov  = 0;
    
    boolean isDerecha = true;
    boolean isArriba = true;
    boolean isTop = false, isLeft = false, isBotom = false;
    boolean isZigD = false, isZigA = false, isZigI = false, isLastD = false, isRepZig = false;
    
    
    // TODO List
    // Guardar en Memoria una matriz del mapa mental
    // Funcionar normal
    // Heurística
    // Cuando acabe, trazar una ruta óptima que sea paralela al resultado (a estrella)
    // Asignar vidas = 5
    
    //Constructor
    public Celda(int valor, int status, int advertencia, int cordX, int cordY, 
                           boolean isWumpus, boolean isTrampa, boolean isTesoro, int h, boolean isPlayer)  {
        //Vacío = 0, Cazador = 1, Tesoro = 2, Hoyo = 3, Wumpus 4, Brillo = 5, Viento = 6, Hedor = 7
        this.valor = valor; 
        this.status = status; //Descubierto = 1, No decubierto = 0
        this.advertencia = advertencia;
        this.cordX = cordX;
        this.cordY = cordY;
        this.isWumpus = isWumpus;
        this.isTrampa = isTrampa;
        this.isTesoro = isTesoro;
        this.h = h;
        this.isPlayer = isPlayer;
    }
    
    public Celda(){}
    
    public void crearCeldas(){
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                mapa [i][j] = new Celda(setRandomNumber(0, 5), 0, 0,i,j,false,false,false, -1, false);
                mapaMental [i][j] = new Celda(0, 0, 0, 0, 0,false,false,false,-1, false);
            }
        }
    }

    public void crearMapa() {
        crearCeldas();
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                rellenarEntidades(j,i);
            }
        }
        
        //Validar mapa por si no fue correctamente construido
        if(contHunter != 1 || contTreasure != 1 || contWumpus != 1){
            resetearPartida();
            printMapas();
            //System.out.println("Se reinició el mapa");
        } else {
            //imprimirMapa();
            //imprimirDatos();
            //imprimirPosActualHunter();
            printMapas();
        }
    }
    
    // Obteniendo Heurística
    public void asignarH(){
        for(int j = 0; j < TAM_TABLERO; j++){
            for(int i = 0; i < TAM_TABLERO; i++){
                //Resta absoluta entre ejes X y Y respecto a una celda y el tesoro
                if(mapa[j][i].isWumpus || mapa[j][i].isTrampa){
                    mapa[j][i].h = 9;
                } else {
                    mapa[j][i].h = Math.abs(yTesoro-j) + Math.abs(xTesoro-i);
                }
                //System.out.print(/*"H vale: " +*/ mapa[j][i].h + " ");
            }
            //Qué ricoSystem.out.println("");
        }
    }
    
    public void printHBoard(){
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                System.out.print(/*"H vale: " +*/ mapa[j][i].valor + " ");
            }
            System.out.println("");
        }
    }
    
    public Celda[][] getMapa() {
        return mapa;
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
                    if(mapa[j][i].isPlayer){
                        System.out.println("1");
                    } else {
                        System.out.print(mapa[j][i].h + "\n");
                        //System.out.println("i: " + i + " j: " + j);
                    }
                    
                } else {
                    if(mapa[j][i].isPlayer){
                        System.out.print("1  ");
                    } else {
                        //System.out.print("[" + mapa[i][j].valor + "], ");
                        System.out.print(mapa[j][i].h + "  ");
                        //System.out.println("i: " + i + " j: " + j);
                    }
                }
            }
        }
    }

    // Esta función revisa las entidades que se producen para poder validarlas y distribuirlas de
    // mejor manera para la generación del mapa
    public void rellenarEntidades(int i, int j){
        switch (mapa[j][i].valor) {
            case 1: // Limitando Cazador
                if(probailidadDeEntidad() == 1){
                    if(contHunter < N_HUNTER) {
                        mapa[j][i].valor = 1;
                        mapa[j][i].status = 1;
                        isPlayer = true;
                        guardarPosInicial(i, j);
                        guardarHunterInicial(i, j);
                        contHunter++;
                    } else if (contHunter >= N_HUNTER){
                        mapa[j][i].valor = 0;
                    }
                } else {
                    mapa[j][i].valor = 0;
                }
                break;
                
            case 2: // Limitando Tesoro
                if(probailidadDeEntidad() == 1) {
                    if(contTreasure < N_TREASURE) {
                        mapa[j][i].valor = 2;
                        mapa[j][i].isTesoro = true;
                        guardarTesoro(i,j);
                        contTreasure++;
                        agregarAdyacentes(j,i,5);
                    } else if (contTreasure >= N_TREASURE){
                        mapa [i][j].valor = 0;
                    }
                } else {
                    mapa[j][i].valor = 0;
                }
                break;
                
            case 3: // Limitando Hoyo
                if(probailidadDeTrampa() == 1) {
                    if(contTrap < N_TRAP) {
                    mapa[j][i].valor = 3;
                    mapa[j][i].isTrampa = true;
                    contTrap++;
                    agregarAdyacentes(j,i,6);
                } else if (contTrap >= N_TRAP){
                    mapa[j][i].valor = 0;
                }
            } else {
                mapa[j][i].valor = 0;
            }
            break;

        case 4: // Limitando Wumpus
            if (probailidadDeEntidad() == 1) {
                if(contWumpus <= 0) {
                    mapa[j][i].valor = 4;
                    mapa[j][i].isWumpus = true;
                    contWumpus++;
                    agregarAdyacentes(j, i, 7);
                } else if (contWumpus >= N_WUMPUS){
                    mapa[j][i].valor = 0;
                }
            } else {
                mapa[j][i].valor = 0;
            }
            break;

        default:
            //Aquí llega un valor que no puede llegar => Null
            System.out.println("Ha habido un problema, saludos cordiales.");
            break;
    }
}

public void guardarHunterInicial(int x, int y){
    xInicial = x;
    yInicial = y;
}

public void guardarTesoro(int x, int y){
    xTesoro = x;
    yTesoro = y;
}

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
    int probabilidad = setRandomNumber(0, 5);
    if(probabilidad == 1){
        return 1;
    } else {
        return 0;
    }
}

public void resetearPartida() {
    contWumpus = 0; contTreasure = 0; contTrap = 0; contHunter = 0;
    xActual = 0; yActual = 0;
    for(int i = 0; i < TAM_TABLERO; i++){
        for(int j = 0; j < TAM_TABLERO; j++){
            mapa[j][j].valor = 0;
            mapa[i][j].status = 0;
            mapa[i][j].advertencia = 0;
            //mapaMental [i][j] = null;
            isLeft = false;
            isTop = false;
            isZigA = false;
            isZigD = false;
            isZigI = false;
            //isArriba = false;
            isBotom = false;
            contMov = 0;
            vidas = 5;
            menor = 100;
        }
    }
    crearMapa();
    asignarH();
    
    //System.out.println(gethActual());
}

// Valida los avisos de las entidades en sus posiciones adyacentes
public void agregarAdyacentes(int i, int j, int adver) {

    if(i-1 >= 0){ //si Izquierda existe
        mapa[i-1][j].advertencia = adver;
    }
    if(i+1 < TAM_TABLERO) { //si Derecha existe
        mapa[i+1][j].advertencia = adver;
    }
    if(j-1 >= 0) { // si Arriba existe
        mapa[i][j-1].advertencia = adver;
    }
    if(j+1 < TAM_TABLERO) { // si Abajo existe
        mapa[i][j+1].advertencia = adver;
    }
    if(i+1 < TAM_TABLERO && j-1 >= 0){ //si Arriba-Derecha existe
        mapa[i+1][j-1].advertencia = adver;
    }
    if(j-1 >= 0 && i-1 >= 0){ //si Arriba-Izquierda existe
        mapa[i-1][j-1].advertencia = adver;
    }
    if(i+1 < TAM_TABLERO && j+1 < TAM_TABLERO){ //si Abajo-Derecha existe
        mapa[i+1][j+1].advertencia = adver;
    }
    if(i-1 >= 0 && j+1 < TAM_TABLERO){ //si Abajo-Derecha existe
        mapa[i-1][j+1].advertencia = adver;
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
/*
    Mapa(Y,X) porque está invertido jiji
    y-1 = arriba
    x+1 = derecha
    y+1 = abajo
    x-1 = izquierda
*/
public void moverCazador(int sentido) { //arriba = 1, derecha = 2, abajo = 3, izquierda = 4;
    switch(sentido){
        case 1:
            if(yActual-1 >= 0) { // si Arriba existe
                //System.out.println("yActual: "+ yActual + " yActual-1: " + (yActual-1));
                System.out.println("X: " + xActual + " Y: "+ yActual);
                mapa[yActual][xActual].isPlayer = false;
                yActual--;
                mapa[yActual][xActual].isPlayer = true;
                mapa[yActual][xActual].status = 1;
            } else {
                System.out.println("Fuera de límites (no es posible moverse arriba)");
            }
            break;
        case 2:
            if(xActual+1 < TAM_TABLERO) { // si Derecha existe
                //System.out.println("xActual: "+ xActual + " xActual+1: " + (xActual+1));
                System.out.println("X: " + xActual + " Y: "+ yActual);
                mapa[yActual][xActual].isPlayer = false;
                xActual++;
                mapa[yActual][xActual].isPlayer = true;
                mapa[yActual][xActual].status = 1;
            } else {
                System.out.println("Fuera de límites (no es posible moverse derecha)");
            }
            break;
        case 3: 
            if(yActual+1 < TAM_TABLERO) { // si Abajo existe
                //System.out.println("yActual: "+ yActual + " yActual+1: " + (yActual+1));
                System.out.println("X: " + xActual + " Y: "+ yActual);
                mapa[yActual][xActual].isPlayer = false;
                yActual++;
                mapa[yActual][xActual].isPlayer = true;
                mapa[yActual][xActual].status = 1;
            } else {
                System.out.println("Fuera de límites (no es posible moverse abajo)");
            }
            break;
        case 4:
            if(xActual-1 >= 0) { // si Izquierda existe
                //System.out.println("xActual: "+ xActual + " xActual-1: " + (xActual-1));
                System.out.println("X: " + xActual + " Y: "+ yActual);
                mapa[yActual][xActual].isPlayer = false;
                xActual--;
                mapa[yActual][xActual].isPlayer = true;
                mapa[yActual][xActual].status = 1;
            } else {
                System.out.println("Fuera de límites (no es posible moverse izquierda)");
            }
            break;
        default:
            System.out.println("Solo se permiten valores 1-4");
            break;
    }
    imprimirMapa();
}

    public int irArriba(){
        moverCazador(1);
        return 1;
    }

    public int irDerecha(){
        moverCazador(2);
        return 1;
    }

    public int irAbajo(){
        moverCazador(3);
        return 1;
    }

    public int irIzquierda(){
        moverCazador(4);
        return 1;
    }

    public int gethActual(){
        return mapa[xActual][yActual].h;
    }
/*
    Mapa(Y,X) porque está invertido jiji
    y-1 = arriba
    x+1 = derecha
    y+1 = abajo
    x-1 = izquierda
*/
int menor = 100;
//int hMenor
    public int compararH(){
        // Preguntar todos, guardarlos y ver quién es el más pequeño
        // finalmente moverse mientras no tenga alerta
        //int menor = 0;
        int hActual = gethActual();
        int sentido = 0;

        System.out.println("Actual: " + hActual);
        
        if(mapa[yActual][xActual].h != 0){
            //arriba
            if(yActual-1 >= 0 && mapa[xActual][yActual-1].h < hActual){
                menor = mapa[yActual][xActual].h;
                sentido = 1;
                System.out.println("arriba " + mapa[yActual][xActual].h);
            }
            // derecha
            if(xActual+1 < TAM_TABLERO && mapa[xActual+1][yActual].h < hActual){
                menor = mapa[yActual][xActual+1].h;
                sentido = 2;
                System.out.println("derecha " + mapa[yActual][xActual].h);
            }
            // abajo
            if(yActual+1 < TAM_TABLERO && mapa[yActual+1][xActual].h < hActual){
                menor = mapa[yActual+1][xActual].h;
                sentido = 3;
                System.out.println("abajo " + mapa[yActual][xActual].h);
            }
            // izquierda
            if(xActual-1 >= 0 && mapa[yActual][xActual-1].h < hActual){
                menor = mapa[yActual][xActual-1].h;
                sentido = 4;
                System.out.println("izquierda " + mapa[yActual][xActual].h);
            }
        } else {
            sentido = 0;
        }
       /*if(){

        }*/
        System.out.println("*----------------------------*");
        imprimirMapa();
        System.out.println("*----------------------------*");
        printHBoard();
        return sentido;
    }
    
    public void explorarMapa2(){
        int caso = compararH();
        
        switch(caso){
            case 0:
                System.out.println("Ya llegó");
                break;
            case 1:
                irArriba();
                break;
            case 2:
                irDerecha();
                break;
            case 3: 
                irAbajo();
                break;
            case 4:
                irIzquierda();
                break;
            default:
                    System.out.println("Caso nulo jiji");;
        }
    }
    
    public void printMapas(){
        printHBoard();
        imprimirMapa();
        imprimirPosActualHunter();
    }
    
}
