package ru.kepper104.easymath
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Options : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        val version_text = findViewById<TextView>(R.id.version_text)
        version_text.text = BuildConfig.VERSION_NAME

        val reset_highscores_button = findViewById<Button>(R.id.reset_highscores_button)

        reset_highscores_button.setOnClickListener {
            val prefs = getSharedPreferences("highscores", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            Toast.makeText(applicationContext, "Рекорды Сброшены!", Toast.LENGTH_SHORT).show()
        }

        val reset_count_button = findViewById<Button>(R.id.reset_count_button)
        reset_count_button.setOnClickListener {
            val prefs = getSharedPreferences("totals", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            Toast.makeText(applicationContext, "Счёт решённых задач сброшен!", Toast.LENGTH_SHORT).show()
        }


        val back_button = findViewById<Button>(R.id.button_back)
        back_button.setOnClickListener { finish() }
    }
}