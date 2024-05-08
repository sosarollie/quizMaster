package com.example.quizmaster

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import android.util.Log
import android.graphics.Color
import android.widget.Button
import android.widget.ImageButton
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


class SegundaActividad : ComponentActivity() {

    private var preguntasRestantes = 3
    private var seUsoComodin = false
    private val preguntasSeleccionadas = mutableSetOf<JSONObject>()
    private lateinit var botonesOpcion: List<TextView>
    private var opcionCorrectaIndex: Int? = null
    private var preguntasCategoria = mutableListOf<JSONObject>()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        val categoriaSeleccionada = intent.getStringExtra("categoria")
        val textViewCategoria = findViewById<TextView>(R.id.textViewCategoria)
        textViewCategoria.text = categoriaSeleccionada

        botonesOpcion = listOf(findViewById(R.id.opcion0), findViewById(R.id.opcion1), findViewById(R.id.opcion2), findViewById(R.id.opcion3))

        val jsonStr: String? = loadJSONFromAsset("preguntas.json")

        if (jsonStr != null && categoriaSeleccionada != null) {
            try {

                val jsonObject = JSONObject(jsonStr)
                val keys = jsonObject.keys()

                while (keys.hasNext()) {
                    val categoria = keys.next()
                    if (categoria == categoriaSeleccionada) {
                        val preguntasCategoriaArray = jsonObject.getJSONArray(categoria)
                        for (j in 0 until preguntasCategoriaArray.length()) {
                            preguntasCategoria.add(preguntasCategoriaArray.getJSONObject(j))
                            Log.d(
                                "Se agrego un preg",
                                preguntasCategoriaArray.getJSONObject(j).toString()
                            )
                        }
                    }
                }

                // Mostrar una pregunta inicial
                mostrarSiguientePregunta(preguntasCategoria)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun mostrarSiguientePregunta(preguntasCategoria: List<JSONObject>) {
        if (preguntasRestantes > 0) {
            botonesOpcion.forEach { it.isEnabled = true }
            val btnComodin = findViewById<ImageButton>(R.id.comodin)
            btnComodin.isEnabled=true
            restablecerColoresBotones()
            var preguntaSeleccionada: JSONObject? = null
            do {
                preguntaSeleccionada = preguntasCategoria.random()
            } while (preguntasSeleccionadas.contains(preguntaSeleccionada))

            val preguntaText = preguntaSeleccionada?.getString("question")
            val opcionesArray = preguntaSeleccionada?.getJSONArray("options")
            opcionCorrectaIndex = preguntaSeleccionada?.getInt("correctAnswerIndex") ?: -1

            val textViewPregunta = findViewById<TextView>(R.id.textViewPregunta)
            textViewPregunta.text = preguntaText

            botonesOpcion.forEachIndexed { index, button ->
                button.text = opcionesArray?.getString(index)

                button.setOnClickListener {
                    comprobarRespuesta(index)
                }
            }

            btnComodin.setOnClickListener {
                usarComodin()
            }

            if (preguntaSeleccionada != null) {
                preguntasSeleccionadas.add(preguntaSeleccionada)
            }
            preguntasRestantes--
        } else {
            val respuestasCorrectas = findViewById<TextView>(R.id.contador)
            val rc = respuestasCorrectas.text.toString().toInt()
            finDelJuego(rc)
        }
    }

    private fun comprobarRespuesta(opcionSeleccionada: Int) {
        botonesOpcion.forEach { it.isEnabled = false }
        val btnComodin = findViewById<ImageButton>(R.id.comodin)
        btnComodin.isEnabled=false
        if (opcionSeleccionada != opcionCorrectaIndex) {
            botonesOpcion[opcionSeleccionada].setBackgroundColor(Color.RED)
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GRAY)
        }else{
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GREEN)
            val contadorRespuestasCorrectas = findViewById<TextView>(R.id.contador)
            val contadorActual = contadorRespuestasCorrectas.text.toString().toInt()
            contadorRespuestasCorrectas.text = (contadorActual + 1).toString()
        }
        mainHandler.postDelayed({
            mostrarSiguientePregunta(preguntasCategoria)
        }, 2000)
    }

    // Método para cargar el archivo JSON
    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
    private fun usarComodin() {
        if (!seUsoComodin) {
            seUsoComodin = true
            preguntasRestantes++
            val btnComodin = findViewById<ImageButton>(R.id.comodin)
            btnComodin.visibility= View.INVISIBLE
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GRAY)
            mainHandler.postDelayed({
            mostrarSiguientePregunta(preguntasCategoria)
            }, 2000)
        }
    }
    private fun restablecerColoresBotones() {
        val opcionCero = findViewById<Button>(R.id.opcion0)
        val opcionUno = findViewById<Button>(R.id.opcion1)
        val opcionDos = findViewById<Button>(R.id.opcion2)
        val opcionTres = findViewById<Button>(R.id.opcion3)
        opcionCero.setBackgroundResource(R.drawable.boton_redondo)
        opcionUno.setBackgroundResource(R.drawable.boton_redondo)
        opcionDos.setBackgroundResource(R.drawable.boton_redondo)
        opcionTres.setBackgroundResource(R.drawable.boton_redondo)
    }
    private fun finDelJuego (contador:Int){
        val mensaje="¡Se termino el juego! Respuestas correctas:$contador"
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()

        mainHandler.postDelayed({
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
