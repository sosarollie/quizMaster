package com.example.quizmaster

import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat


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

                val btnComodin = findViewById<ImageButton>(R.id.comodin)
                btnComodin.setOnClickListener {
                    if (!seUsoComodin) {
                        seUsoComodin = true
                        preguntasRestantes++
                        btnComodin.setBackgroundColor(Color.YELLOW)//una vez que se usa el comodin lo cambio de color
                    }
                }

                if (preguntaSeleccionada != null) {
                    preguntasSeleccionadas.add(preguntaSeleccionada)
                }
                preguntasRestantes--
            } else {
                // puede salir un toast
            }
        }

        private fun comprobarRespuesta(opcionSeleccionada: Int) {
            if (opcionSeleccionada != opcionCorrectaIndex) {
                botonesOpcion[opcionSeleccionada].setBackgroundColor(Color.GRAY)
            }
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GREEN)
            mainHandler.postDelayed({
                mostrarSiguientePregunta(preguntasCategoria)
            },2000)
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
    private fun restablecerColoresBotones() {
        val opcionCero = findViewById<Button>(R.id.opcion0)
        val opcionUno = findViewById<Button>(R.id.opcion1)
        val opcionDos = findViewById<Button>(R.id.opcion2)
        val opcionTres = findViewById<Button>(R.id.opcion3)
        opcionCero.backgroundTintList = ContextCompat.getColorStateList(this, R.color.orange)
        opcionUno.backgroundTintList=ContextCompat.getColorStateList(this,R.color.orange)
        opcionDos.backgroundTintList=ContextCompat.getColorStateList(this,R.color.orange)
        opcionTres.backgroundTintList=ContextCompat.getColorStateList(this,R.color.orange)
        }
    }

