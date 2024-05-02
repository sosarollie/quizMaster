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
        botonGeografia.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Geografia")
            startActivity(intent)
        }
        val botonDeporte = findViewById<Button>(R.id.deporte)
        botonDeporte.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Deporte")
            startActivity(intent)
        }
        val botonEntretenimiento = findViewById<Button>(R.id.entretenimiento)
        botonEntretenimiento.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Entretenimiento")
            startActivity(intent)
        }
        val botonTecnologia = findViewById<Button>(R.id.tecnologia)
        botonTecnologia.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Tecnologia")
            startActivity(intent)
        }
        val botonArteyCultura = findViewById<Button>(R.id.arte_y_cultura)
        botonArteyCultura.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "ArteyCultura")
            startActivity(intent)
        }
        val botonLiteratura = findViewById<Button>(R.id.literatura)
        botonLiteratura.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Literatura")
            startActivity(intent)
        }
        val botonFilosofia = findViewById<Button>(R.id.filosofia)
        botonFilosofia.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Filosofia")
            startActivity(intent)
        }
        val botonGastronomia = findViewById<Button>(R.id.gastronomia)
        botonGastronomia.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Gastronomia")
            startActivity(intent)
        }
        val botonMusica = findViewById<Button>(R.id.musica)
        botonMusica.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Musica")
            startActivity(intent)
        }
        val botonIdiomas = findViewById<Button>(R.id.idiomas)
        botonIdiomas.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Idiomas")
            startActivity(intent)
        }
        val botonMedicina = findViewById<Button>(R.id.medicina)
        botonMedicina.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", "Medicina")
            startActivity(intent)
        }
    }
}
