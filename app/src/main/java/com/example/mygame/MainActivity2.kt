package com.example.mygame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygame.R.id.startBtn

class MainActivity2 : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var highScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScore = findViewById(R.id.highScore)

        mGameView = GameView(this, this)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.imgrd)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            highScore.visibility = View.GONE


            mGameView.resetGame()
        }
    }

    override fun closeGame(mScore: Int, highScore: Int) {
        score.text = "Score: $mScore"

        this.highScore.text = "High Score: $highScore"

        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        this.highScore.visibility = View.VISIBLE
    }
}




