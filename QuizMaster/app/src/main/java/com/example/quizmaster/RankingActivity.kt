package com.example.quizmaster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ranking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("ranking", Context.MODE_PRIVATE)

        for (i in 0..4) {  // Cambiar el rango del índice a 0..4
            val idJugador = resources.getIdentifier("jugador${i + 1}", "id", packageName)
            val idPuntaje = resources.getIdentifier("puntaje${i + 1}", "id", packageName)
            val auxJugador = findViewById<TextView>(idJugador)
            val auxPuntaje = findViewById<TextView>(idPuntaje)

            val jugador = sharedPreferences.getString("jugador_$i", null)
            val puntaje = sharedPreferences.getInt("puntaje_$i", -1)

            if (jugador != null && puntaje != -1) {
                auxJugador.text = jugador
                auxPuntaje.text = puntaje.toString()
            }
        }

        val nombre = intent.getStringExtra("jugador")

        val volver = findViewById<Button>(R.id.volverAlMenu)
        volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("jugador", nombre)
            startActivity(intent)
            finish()
        }
    }
}