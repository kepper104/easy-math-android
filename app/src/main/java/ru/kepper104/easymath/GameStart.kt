package ru.kepper104.easymath

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameStart : AppCompatActivity() {
    var game_name = ""
    var game_sign = 0
    var game_digits = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_start)
        val back_button = findViewById<Button>(R.id.back_button)
        val start_button = findViewById<Button>(R.id.start_button)
        back_button.setOnClickListener { finish() }
        val header_text = findViewById<TextView>(R.id.header_text)

        val extras = intent.extras

        val game_id = extras?.getInt("game_id")


        when(game_id){
            1 -> {
                game_name = "Сложение Двухзначных Чисел"
                game_sign = +1
                game_digits = 2
            }
            2 -> {
                game_name = "Сложение Трёхзначных Чисел"
                game_sign = +1
                game_digits = 3
            }
            3 -> {
                game_name = "Вычитание Двухзначных Чисел"
                game_sign = -1
                game_digits = 2
            }
            4 -> {
                game_name = "Вычитание Трёхзначных Чисел"
                game_sign = -1
                game_digits = 3
            }
        }
        header_text.text = game_name

        update_highscore()
        update_total()

        start_button.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("sign", game_sign)
            intent.putExtra("digits", game_digits)
            intent.putExtra("game_name", game_name)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        update_highscore()
        update_total()
    }

    fun update_highscore(){
        val sharedPrefs = getSharedPreferences("highscores", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        if (!sharedPrefs.contains(game_name)){
            editor.putInt(game_name, 0)
            editor.apply()
        }
        val highscore_text = findViewById<TextView>(R.id.highscore_value_text)

        highscore_text.text = sharedPrefs.getInt(game_name, 0).toString()
    }

    fun update_total(){
        val sharedPrefs = getSharedPreferences("totals", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        if (!sharedPrefs.contains(game_name)){
            editor.putInt(game_name, 0)
            editor.apply()
        }

        val total_value = findViewById<TextView>(R.id.total_value_text)

        total_value.text = sharedPrefs.getInt(game_name, 0).toString()
    }
}