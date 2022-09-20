package com.example.pm_proyecto.Model

//definimos constantes, que definen el tamaño de la grilla que se va a utilizar
const val FILAS = 3;
const val COLUMNAS = 3;
// contante para empate
const val EMPATE = "draw"

//COLORES
const val GRIS = "#808080"
const val ROJO = "#FF5733"
const val MORADO = "#AC33FF"
const val CELESTE = "#3377FF"
const val TURQUEZA = "#33FFE9"
const val NARANJA = "#ff3300"
class Tablero() {
    val matriz: Array<Array<Casilla>>
    var jugador1: String = "O"
    var jugador2: String = "X"
    var color : String = "#808080"
    //variable que puede ser 1 o 2
    // 1: jugador1
    // 2: jugador2
    var turno: Int = 1
    var resultado: String? = null

    /*
    * - CONSTRUCTOR
    *   Se esta definiendo el tipo de datao para la grilla
    *   un tipo de valor string optional, debido a que en
    *   el comienzo del juego, los botones contienen el valor
    *   de nulo.
    *
    *   Procedimientos:
    *
    *   1. Se define las filas del arreglo
    *   2. Asignamos a cada fila otro arreglo de tamaño 3
    */
    init {
        // 1.
        matriz = Array<Array<Casilla>>(FILAS) { _ -> arrayOf() }
        // 2.
        for (i in 0 until COLUMNAS) {
            matriz[i] = Array<Casilla>(COLUMNAS) { Casilla(null , this.color) };
        }
    }

    /*
    * Función para imprimir en consola el tablero
    */

    fun toStr() : String{
        var str : String = "\n";
        matriz.forEach {
            it.forEach {
                str += "${if (it.valor == null) "_" else it.valor} ";
            }
            str += '\n';
        }
        return str;
    }

    //Funcion para limpiar casillas
    fun limpiarCasillas() {
        for (i in 0 until FILAS) {
            for (j in 0 until COLUMNAS) {
                matriz[i][j] = Casilla(null , this.color , false);
            }
        }
    }

    /*
    * VERIFICAR GANADOR
    *   Función que verificará si existe un ganador en el juego
    *   en caso de que encuentre un ganador o un empate, va a devolver true
    *   si no encuentra ganador, devuelve false
    *
    *   Procedimientos:
    *   1. Verifica por cada fila
    *   2. Verifica por cada columna
    *   3. Verifica en las 2 digonales posibles
    *   4. verifica si hay empate
    *   5. Si no encuentra ganador, devuelve nulo
    *
    *
    */
    fun verificarGanador(): Boolean {
        //Recorrido por filas
        for (i in 0 until FILAS) {
            if (matriz[i][0].valor.equals(matriz[i][1].valor) && matriz[i][1].valor.equals(matriz[i][2].valor) && matriz[i][1].valor != null) {
                resultado = matriz[i][1].valor;
                matriz[i][0].win = true;
                matriz[i][1].win = true;
                matriz[i][2].win = true;
                return true;
            }
        }
        //Recorrido por columnas
        for (j in 0 until COLUMNAS) {
            if (matriz[0][j].valor.equals(matriz[1][j].valor) && matriz[1][j].valor.equals(matriz[2][j].valor) && matriz[1][j].valor != null) {
                resultado =  matriz[1][j].valor;
                matriz[0][j].win = true;
                matriz[1][j].win = true;
                matriz[2][j].win = true;
                return true;
            }
        }
        //Casos especiales, diagonales
        if (matriz[0][0].valor.equals(matriz[1][1].valor) && matriz[1][1].valor.equals(matriz[2][2].valor) && matriz[1][1].valor != null) {
            resultado = matriz[1][1].valor;
            matriz[0][0].win = true;
            matriz[1][1].win = true;
            matriz[2][2].win = true;
            return true;
        }
        if (matriz[0][2].valor.equals(matriz[1][1].valor) && matriz[1][1].valor.equals(matriz[2][0].valor) && matriz[1][1].valor != null) {
            resultado = matriz[1][1].valor;
            matriz[0][2].win = true;
            matriz[1][1].win = true;
            matriz[2][0].win = true;
            return true;
        }
        //Si no entra en ningún caso
        //verificamos si se ha completado la matriz
        if(estaCompleto()){
            //si esta completo y no se ha encontrado ganador, entonces es empate
            resultado = EMPATE;
            return true;
        }
        //si no esta completo, quiere decir que el juego sigue
        return false;
    }

    //true: esta completo
    //false: tiene algun nulo
    fun estaCompleto() : Boolean{
        for(i in 0 until FILAS){
            for(j in 0 until COLUMNAS){
                if(matriz[i][j].valor == null) return false;
            }
        }
        return true;
    }

    /*
    * CAMBIAR TURNO
    *   Funcion que cambia de turno al jugador
    *
    */
    fun cambiarTurno()  {
        turno = if (turno == 1) 2 else 1;
    }

    /*
    *   CAMBIAR COLOR
    *   función que cambia de color a las casillas,
    *   al llamarla se cambia de color.
    */
    fun cambiarColor() {
        if(color == GRIS) color = ROJO
        else if (color == ROJO ) color = MORADO
        else if (color == MORADO) color = CELESTE
        else if (color == CELESTE) color = NARANJA
        else color = GRIS
    }

    /*
    * MARCAR CASILLA
    *   Funcion que marcara la casilla, validando si existe o no un valor
    *
    */
    fun marcarCasilla(fila:Int , col:Int) : Boolean{
        //Validamos de que no exista ese valor
        if(matriz[fila][col].valor == null){
            //asignamos el valor, dependiendo del turno.
            if(turno == 1) matriz[fila][col].valor = jugador1;
            else if(turno == 2) matriz[fila][col].valor = jugador2;
            return true;
        }
        //como ya existia un valor en esa casilla, devolvemos false
        return false;
    }

    fun asignarLetras(){
        //declaramos una cadena con las letras que serán asignadas de forma aleateoria
        val abc : String = "abcdefghijklmnopqrstuvwxyz"
        //Almacenamos el indice de la cadena en rand1 y rand2
        var rand1 : Int = (abc.indices).random();
        var rand2 : Int = (abc.indices).random();
        //en caso los valores rand1 y rand2 sean iguales
        while(rand1 == rand2){
            //se modificar el valor de ellos hasta que sean diferentes
            rand1 = (abc.indices).random();
            rand2 = (abc.indices).random();
        }

        //una vez corroborado que los valores de rand1 y rand2 son diferentes, asignamos
        //una letra para cada jugador
        jugador1 = abc[rand1].toString();
        jugador2 = abc[rand2].toString();
    }

    /*
    * REINICIAR Tablero
    *   funcion que va a reiniciar el juego
    *
    *   Procedimiento:
    *   1. Limpia casillas
    *   2. Asigna nuevas letras a los jugadores
    *   3. Limpia resultado
    */

    fun reiniciarTablero(){
        cambiarColor();
        limpiarCasillas();
        asignarLetras();
        resultado = null;
    }

    //get value
    fun obtenerValor( fila : Int , col : Int) : String?{
        return matriz[fila][col].valor;
    }
    fun obtenerColor( fila : Int , col : Int) : String{
        //si se encuentra un ganador devolvemos color turqueza para que las
        //casillas tomen ese color
        if(matriz[fila][col].win){
            return TURQUEZA
        }
        return matriz[fila][col].color;
    }
}