package com.example.quizmaster

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import android.util.Log
import android.graphics.Color
import android.os.CountDownTimer
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
    private var cantPreguntas=0
    private var cantCorrectas=0
    private var timer:CountDownTimer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        val categoriaSeleccionada = intent.getStringExtra("categoria")
        val textViewCategoria = findViewById<TextView>(R.id.textViewCategoria)
        val textViewNombre = findViewById<TextView>(R.id.textViewNombre)
        textViewCategoria.text = categoriaSeleccionada
        val extras = intent.extras

        botonesOpcion = listOf(findViewById(R.id.opcion0), findViewById(R.id.opcion1), findViewById(R.id.opcion2), findViewById(R.id.opcion3))
        textViewNombre.text = extras?.getString("jugador")

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
            cantPreguntas++
            botonesOpcion.forEach { it.isEnabled = true }
            val btnComodin = findViewById<Button>(R.id.comodin)
            val btnAyuda = findViewById<ImageButton>(R.id.helpButton)
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

            btnAyuda.setOnClickListener{
                val intent = Intent(this, AyudaActivity::class.java)
                startActivity(intent)
            }

            btnComodin.setOnClickListener {
                usarComodin()
            }

            iniciarTimer()

            if (preguntaSeleccionada != null) {
                preguntasSeleccionadas.add(preguntaSeleccionada)
            }
            preguntasRestantes--
        } else {
            finDelJuego(cantCorrectas,cantPreguntas,seUsoComodin)
        }
    }

    private fun comprobarRespuesta(opcionSeleccionada: Int) {
        desactivarBotonesYTimer()
        val contadorRespuestasCorrectas = findViewById<TextView>(R.id.contador)
        if (opcionSeleccionada != opcionCorrectaIndex) {
            botonesOpcion[opcionSeleccionada].setBackgroundColor(Color.RED)
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GRAY)
        }else{
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GREEN)
            cantCorrectas++
        }
        contadorRespuestasCorrectas.text = (cantCorrectas).toString()+"/"+cantPreguntas
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

    private fun iniciarTimer(){
        val timerId=findViewById<TextView>(R.id.timer)
        timer?.cancel()
        timer=object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                timerId.text="${millisUntilFinished/1000}s"
            }

            override fun onFinish() {
                seAcaboElTiempo()
            }
        }.start()

    }
    private fun seAcaboElTiempo(){
        desactivarBotonesYTimer()
        botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GRAY)
        val contadorRespuestasCorrectas = findViewById<TextView>(R.id.contador)
        contadorRespuestasCorrectas.text = (cantCorrectas).toString()+"/"+cantPreguntas
        mainHandler.postDelayed({
            mostrarSiguientePregunta(preguntasCategoria)
        }, 2000)
    }
    private fun usarComodin() {
        desactivarBotonesYTimer()
        val btnComodin = findViewById<Button>(R.id.comodin)
        if (!seUsoComodin) {
            seUsoComodin = true
            preguntasRestantes++
            cantPreguntas--
            btnComodin.visibility= View.INVISIBLE
            botonesOpcion[opcionCorrectaIndex!!].setBackgroundColor(Color.GRAY)
            val contadorRespuestasCorrectas = findViewById<TextView>(R.id.contador)
            contadorRespuestasCorrectas.text = (cantCorrectas).toString()+"/"+cantPreguntas
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
    private fun finDelJuego (contador:Int,cantPreg:Int,seUsoComo:Boolean){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Se terminó el juego")

        val comodin = if (seUsoComo) {
            "se usó"
        } else {
            "no se usó"
        }

        builder.setMessage("Respuestas: $contador/$cantPreg\nEl comodín $comodin")

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Reiniciar") { dialog, which ->
            preguntasRestantes = 3
            seUsoComodin = false
            cantPreguntas = 0
            mostrarSiguientePregunta(preguntasCategoria)
            preguntasSeleccionadas.clear()
        }

        builder.setNeutralButton("Compartir") { dialog, which ->
            val puntaje = "Mi puntaje es $contador/$cantPreg y ${if (seUsoComo) "usé" else "no usé"} el comodín."
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, puntaje)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent,"Compartir tu puntaje a traves de"))
        }
            builder.setCancelable(false)
        builder.show()
    }
    private fun desactivarBotonesYTimer(){
        botonesOpcion.forEach { it.isEnabled = false }
        val btnComodin = findViewById<Button>(R.id.comodin)
        btnComodin.isEnabled=false
        timer?.cancel()
    }
}