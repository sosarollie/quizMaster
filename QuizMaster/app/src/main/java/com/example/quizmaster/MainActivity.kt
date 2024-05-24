package com.example.quizmaster

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botones = listOf(
            R.id.ciencia, R.id.historia, R.id.geografia, R.id.deporte, R.id.entretenimiento,
            R.id.tecnologia, R.id.arte_y_cultura, R.id.literatura, R.id.filosofia,
            R.id.gastronomia, R.id.musica, R.id.idiomas, R.id.medicina
        )

        botones.forEach { id ->
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }
    private val categoriasMap = mapOf(
        R.id.ciencia to "Ciencia",
        R.id.historia to "Historia",
        R.id.geografia to "Geografia",
        R.id.deporte to "Deporte",
        R.id.entretenimiento to "Entretenimiento",
        R.id.tecnologia to "Tecnologia",
        R.id.arte_y_cultura to "Arte y cultura",
        R.id.literatura to "Literatura",
        R.id.filosofia to "Filosofia",
        R.id.gastronomia to "Gastronomia",
        R.id.musica to "Musica",
        R.id.idiomas to "Idiomas",
        R.id.medicina to "Medicina"
    )
    private val listener = View.OnClickListener { view ->
        val categoria = categoriasMap[view.id]
        if (categoria != null) {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }
    }
}