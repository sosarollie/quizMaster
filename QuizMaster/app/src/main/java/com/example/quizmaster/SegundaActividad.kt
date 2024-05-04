package com.example.quizmaster
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SegundaActividad : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        val categoriaSeleccionada = intent.getStringExtra("categoria")
        val textViewCategoria = findViewById<TextView>(R.id.textViewCategoria)
        textViewCategoria.text = categoriaSeleccionada

        val jsonStr: String? = loadJSONFromAsset("preguntas.json")

        if (jsonStr != null && categoriaSeleccionada != null) {
            try {
                val jsonArray = JSONArray(jsonStr)
                val preguntasCategoria = mutableListOf<JSONObject>()

                for (i in 0 until jsonArray.length()) {
                    val pregunta = jsonArray.getJSONObject(i)
                    if (pregunta.has(categoriaSeleccionada)) {
                        val preguntasCategoriaArray = pregunta.getJSONArray(categoriaSeleccionada)
                        for (j in 0 until preguntasCategoriaArray.length()) {
                            preguntasCategoria.add(preguntasCategoriaArray.getJSONObject(j))
                        }
                    }
                }

                val preguntaSeleccionada = preguntasCategoria.random()
                val preguntaText = preguntaSeleccionada.getString("question")
                val opcionesArray = preguntaSeleccionada.getJSONArray("options")
                val opcionCorrectaIndex = preguntaSeleccionada.getInt("correctAnswerIndex")
                val opcionCorrecta = opcionesArray.getString(opcionCorrectaIndex)

                val textViewPregunta = findViewById<TextView>(R.id.textViewPregunta)
                textViewPregunta.text = preguntaText

                val btnOpcion0 = findViewById<TextView>(R.id.opcion0)
                val btnOpcion1 = findViewById<TextView>(R.id.opcion1)
                val btnOpcion2 = findViewById<TextView>(R.id.opcion2)
                val btnOpcion3 = findViewById<TextView>(R.id.opcion3)

                btnOpcion0.text = opcionesArray.getString(0)
                btnOpcion1.text = opcionesArray.getString(1)
                btnOpcion2.text = opcionesArray.getString(2)
                btnOpcion3.text = opcionesArray.getString(3)

                // aca se verifica la respuesta
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // esto carga el string :)
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
}