package ru.kepper104.easymath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.button_1)
        val btn2 = findViewById<Button>(R.id.button_2)
        val btn3 = findViewById<Button>(R.id.button_3)
        val btn4 = findViewById<Button>(R.id.button_4)

        btn1.setOnClickListener { redirect(1) }
        btn2.setOnClickListener { redirect(2) }
        btn3.setOnClickListener { redirect(3) }
        btn4.setOnClickListener { redirect(4) }

        val options_button = findViewById<Button>(R.id.button_options)
        options_button.setOnClickListener {
            val intent = Intent(this, Options::class.java)
            startActivity(intent)
        }
    }
    fun redirect(game_id: Int): Unit{
        val intent = Intent(this, GameStart::class.java)
        intent.putExtra("game_id", game_id)
        startActivity(intent)
    }
}