
package com.example.pm_proyecto

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pm_proyecto.Model.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tablero = Tablero();

        val but0_0 = findViewById<Button>(R.id.but0_0)
        val but0_1 = findViewById<Button>(R.id.but0_1)
        val but0_2 = findViewById<Button>(R.id.but0_2)

        val but1_0 = findViewById<Button>(R.id.but1_0)
        val but1_1 = findViewById<Button>(R.id.but1_1)
        val but1_2 = findViewById<Button>(R.id.but1_2)

        val but2_0 = findViewById<Button>(R.id.but2_0)
        val but2_1 = findViewById<Button>(R.id.but2_1)
        val but2_2 = findViewById<Button>(R.id.but2_2)

        val text = findViewById<TextView>(R.id.text)



        // ------------------------------------------
        //  FUNCIONES
        // ------------------------------------------
        fun actualizarPantalla(){
            but0_0.text = tablero.obtenerValor(0 , 0);
            but0_0.setBackgroundColor(Color.parseColor(tablero.obtenerColor(0 , 0)))

            but0_1.text = tablero.obtenerValor(0 , 1);
            but0_1.setBackgroundColor(Color.parseColor(tablero.obtenerColor(0 , 1)))

            but0_2.text = tablero.obtenerValor(0 , 2);
            but0_2.setBackgroundColor(Color.parseColor(tablero.obtenerColor(0 , 2)))

            but1_0.text = tablero.obtenerValor(1 , 0);
            but1_0.setBackgroundColor(Color.parseColor(tablero.obtenerColor(1 , 0)))

            but1_1.text = tablero.obtenerValor(1 , 1);
            but1_1.setBackgroundColor(Color.parseColor(tablero.obtenerColor(1 , 1)))

            but1_2.text = tablero.obtenerValor(1 , 2);
            but1_2.setBackgroundColor(Color.parseColor(tablero.obtenerColor(1 , 2)))

            but2_0.text = tablero.obtenerValor(2 , 0);
            but2_0.setBackgroundColor(Color.parseColor(tablero.obtenerColor(2 , 0)))

            but2_1.text = tablero.obtenerValor(2 , 1);
            but2_1.setBackgroundColor(Color.parseColor(tablero.obtenerColor(2 , 1)))

            but2_2.text = tablero.obtenerValor(2 , 2);
            but2_2.setBackgroundColor(Color.parseColor(tablero.obtenerColor(2 , 2)))
        }

        /*
        * ---------------------------------
        *   COMPORTAMIENTO DE CASILLAS
        * ---------------------------------
        * 0. Verificamos que no haya un ganador
        *   0.1 Si existe, entonces reiniciamos el juego
        * 1. Obtenemos la posición del botón
        * 2. Insertamos dentro de la grilla el valor
        * 3. Actualizamos la pantalla
        * 4. Verificamos si hay ganador o empateo o nada
        *   4.1 si no pasó nada, entonces pasamos de turno
        *   4.2 Si hubo ganador, paramos el juego hasta que se presione otra casilla
        *       ,también mandamos el mansaje al footer.
        *   4.3 Si hubo empate, paramos el juego hasta que se presione otra casilla
        *       ,también mandamos el mansaje.
        *
        * */
        val butOnClickListener : (view : View) -> Unit = {  view ->
            //0. Verificamos que no haya un ganador
            if(tablero.resultado != null){
                // 0.1 Reiniciamos el juego
                tablero.reiniciarTablero();
                actualizarPantalla();
                text.text = "Le toca al jugador " + if(tablero.turno == 1) tablero.jugador1 else tablero.jugador2
            }
            else{
                //1.Obtenemos la posición del botón
                val viewIdStr = view.resources.getResourceName(view.id)
                    .split("/")[1]
                val cad = viewIdStr.substring(3) // ejm: 0_1
                val arrCoordenadas = cad.split("_")
                val row = arrCoordenadas[0].toInt()
                val col = arrCoordenadas[1].toInt()
                //2. Insertamos dentro de la grilla
                if(tablero.marcarCasilla(row, col)) {
                    // 3. Actualizamos la pantalla
                    actualizarPantalla();
                    //4. verificamos si hay ganador
                    tablero.resultado = tablero.verificarGanador();
                    Log.i("MainActivity", tablero.toStr());
                    // 4.1 si no hay respuesta, entonces
                    if(tablero.resultado == null){
                        tablero.cambiarTurno();
                        text.text = "Le toca al jugador " + if(tablero.turno == 1) tablero.jugador1 else tablero.jugador2
                    }
                    /// 4.2 caso de empate
                    else if(tablero.resultado == EMPATE){
                        text.text = "Ha ocurrido un empate, porfavor pulsar cualquier botón para reiniciar."
                        // Caso donde un jugador gana
                    }else{
                        text.text = "Ha ganado el jugador ${tablero.resultado}"
                    }
                }
            }
        }

        // ------------------------------------------
        //  Adicion de listener
        // ------------------------------------------
        but0_0.setOnClickListener(butOnClickListener)
        but0_1.setOnClickListener(butOnClickListener)
        but0_2.setOnClickListener(butOnClickListener)
        but1_0.setOnClickListener(butOnClickListener)
        but1_1.setOnClickListener(butOnClickListener)
        but1_2.setOnClickListener(butOnClickListener)
        but2_0.setOnClickListener(butOnClickListener)
        but2_1.setOnClickListener(butOnClickListener)
        but2_2.setOnClickListener(butOnClickListener)

        // ------------------------------------------
        //  Muestra inicial de la pantalla
        // ------------------------------------------
        actualizarPantalla();
    }
}