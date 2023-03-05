package ru.kepper104.easymath

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.CreationExtras
import org.w3c.dom.Text
import kotlin.math.pow

class Game : AppCompatActivity() {
    var score = 0
    var digits = 0
    var sign = +1
    var game_name = ""

    lateinit var extras: Bundle
    lateinit var text_problem: TextView
    lateinit var answer_button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        extras = intent.extras!!

        val button_exit = findViewById<Button>(R.id.exit_button)
        button_exit.setOnClickListener { update_highscore(); finish() }


        sign = extras.getInt("sign")
        digits = extras.getInt("digits")
        game_name = extras.getString("game_name").toString()

        text_problem = findViewById<TextView>(R.id.problem_number)
        answer_button = findViewById<Button>(R.id.answer_button)

        game_cycle()

    }

    private fun generate_new_problem(digits: Int, sign: Int): MutableList<Int> {
        val n1 = (10.toDouble().pow(digits - 1).toInt()..10.toDouble().pow(digits).toInt()).random()
        var n2 = ((10.toDouble().pow(digits - 1).toInt()..10.toDouble().pow(digits).toInt()).random()) * sign

        // Check for no negative answers
        while(kotlin.math.abs(n2) > n1){
            n2 = ((10.toDouble().pow(digits - 1).toInt()..10.toDouble().pow(digits).toInt()).random()) * sign
        }

        return mutableListOf(n1, n2)
    }

    private fun game_cycle(){
        val nums = generate_new_problem(digits, sign)
        var res = nums[0].toString()

        if (sign == +1){
            res += " + " + nums[1].toString()
        } else {
            res += " - " +  kotlin.math.abs(nums[1]).toString()
        }

        text_problem.text = res
        answer_button.setOnClickListener { check_ans(nums[0], nums[1]) }
    }

    private fun check_ans(n1: Int, n2: Int){
        val ans_input = findViewById<EditText>(R.id.answer_input_number)

        if (ans_input.text.toString() == "") return

        reset_view()

        val user_answer = ans_input.text.toString().toInt()
        set_correct_answer(n1 + n2, user_answer)

        if (n1 + n2 == user_answer){
            ans_input.setTextColor(Color.GREEN)
            answer_button.text = "Продолжить"
            score++

            val current_highscore = findViewById<TextView>(R.id.current_highscore_number)

            current_highscore.text = (score).toString()
            answer_button.setOnClickListener {
                ans_input.setTextColor(Color.WHITE)
                ans_input.clearFocus()
                ans_input.text.clear()
                answer_button.text = "Ответить"

                update_totals()
                reset_correct_answer()
                game_cycle()
                }

        } else {
            ans_input.setTextColor(Color.RED)
            answer_button.text = "В меню"
            answer_button.setOnClickListener{ finish() }

        }
    }

    private fun update_highscore(){
        val extras = intent.extras
        val game_name = extras!!.getString("game_name")

        val sharedPrefs = getSharedPreferences("highscores", Context.MODE_PRIVATE)
        if(sharedPrefs.getInt(game_name, 0) < score){
            val editor = sharedPrefs.edit()
            editor.putInt(extras.getString("game_name"), score)
            editor.apply()
        }
    }
    private fun update_totals(){
        val sharedPrefs = getSharedPreferences("totals", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(game_name, sharedPrefs.getInt(game_name, 0) + 1)
        editor.apply()
    }

    private fun reset_view(){
        val view = this.currentFocus
        if (view != null) {
            val manager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE)
                    as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun set_correct_answer(answer: Int, user_answer: Int){
        val correct_answer_text = findViewById<TextView>(R.id.real_answer_text)
        val correct_answer_value = findViewById<TextView>(R.id.real_answer_value)
        correct_answer_text.visibility = View.VISIBLE

        if(answer == user_answer){
            correct_answer_text.text = "Правильно!"
        }else{
            correct_answer_text.text = "Правильный ответ:"
            correct_answer_value.visibility = View.VISIBLE
            correct_answer_value.text = answer.toString()

        }
    }
    fun reset_correct_answer(){
        findViewById<TextView>(R.id.real_answer_text).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.real_answer_value).visibility = View.INVISIBLE
    }


}