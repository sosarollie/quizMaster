package com.example.quizmaster

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCiencia = findViewById<Button>(R.id.ciencia)
        botonCiencia.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Ciencia")
            startActivity(intent)
        }

        val botonHistoria = findViewById<Button>(R.id.historia)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Historia")
            startActivity(intent)
        }
        val botonGeografia = findViewById<Button>(R.id.geografia)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Geografia")
            startActivity(intent)
        }
        val botonDeporte = findViewById<Button>(R.id.deporte)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Deporte")
            startActivity(intent)
        }
        val botonEntretenimiento = findViewById<Button>(R.id.entretenimiento)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Entretenimiento")
            startActivity(intent)
        }
        val botonTecnologia = findViewById<Button>(R.id.tecnologia)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Tecnologia")
            startActivity(intent)
        }
        val botonArteyCultura = findViewById<Button>(R.id.arte_y_cultura)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "ArteyCultura")
            startActivity(intent)
        }
        val botonLiteratura = findViewById<Button>(R.id.literatura)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Literatura")
            startActivity(intent)
        }
        val botonFilosofia = findViewById<Button>(R.id.filosofia)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Filosofia")
            startActivity(intent)
        }
        val botonGastronomia = findViewById<Button>(R.id.gastronomia)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Gastronomia")
            startActivity(intent)
        }
        val botonMusica = findViewById<Button>(R.id.musica)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Musica")
            startActivity(intent)
        }
        val botonIdiomas = findViewById<Button>(R.id.idiomas)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Idiomas")
            startActivity(intent)
        }
        val botonMedicina = findViewById<Button>(R.id.medicina)
        botonHistoria.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Medicina")
            startActivity(intent)
        }
    }
