package com.example.quizmaster

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var nombreJugador = ""
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val editText = findViewById<EditText>(R.id.editNombre)

        fun startMain() {
            nombreJugador = editText.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("jugador", nombreJugador)
            startActivity(intent)
        }

        btnJugar.setOnClickListener{
            startMain()
        }

        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                startMain()
            }
            false
        }

    }

}