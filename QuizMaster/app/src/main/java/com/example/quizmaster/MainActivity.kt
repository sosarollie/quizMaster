package com.example.quizmaster

import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
        val extras = intent.extras
        val jugador = extras?.getString("jugador");
        val categoria = categoriasMap[view.id]
        if (categoria != null) {
            val intent = Intent(this, SegundaActividad::class.java)
            intent.putExtra("categoria", categoria)
            intent.putExtra("jugador", jugador)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater = menuInflater;
        mi.inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.helpButton) {
            val intent = Intent(this, AyudaActivity::class.java)
            startActivity(intent)
        }
        if(id==R.id.puntuacionButton){
            val extras = intent.extras
            val intent=Intent(this,RankingActivity::class.java)
            val jugador = extras?.getString("jugador");
            intent.putExtra("jugador",jugador)
            startActivity(intent)
        }
        return true
    }
}