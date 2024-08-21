package com.example.mygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat





class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var highScore = 0
    private var myCarPosition = 0
    private val otherCars = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    fun resetGame() {
        time = 0
        score = 0
        highScore = 0
        myCarPosition = 0
        otherCars.clear()
        speed = 1
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth =this. measuredWidth
        viewHeight =this. measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherCars.add(map)
        }

        time = time + 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d=resources.getDrawable(R.drawable.imgyellow,null)


        d.setBounds(
            myCarPosition * viewWidth / 3 + viewWidth / 15 + 25, // left
            viewHeight - 2 - carHeight,                          // top
            myCarPosition * viewWidth / 3 + viewWidth / 15 + carWidth - 25, // right
            viewHeight - 2                                          // bottom
        )

        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore=0



        for (i in otherCars.indices) {
            try {
                val carX = otherCars[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var carY = time - otherCars[i]["startTime"] as Int
                val d2=resources.getDrawable(R.drawable.imgred,null)
//                val d2 = ContextCompat.getDrawable(context, R.drawable.imgred)

                d2.setBounds(
                    carX + 25, carY - carHeight, carX + carWidth - 25, carY
                )
                d2.draw(canvas)

                if (otherCars[i]["lane"] as Int == myCarPosition) {
                    if (carY > viewHeight - 2 - carHeight && carY < viewHeight - 2) {

                        if (score > highScore) {
                            highScore = score
                        }

                        gameTask.closeGame(score, highScore)
                    }
                }

                if (carY > viewHeight + carHeight) {
                    otherCars.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore) {
                        highScore = score
                    }
                }


                } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.BLACK
        myPaint!!.textSize = 40f
        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed: $speed", 260f, 80f, myPaint!!)
        canvas.drawText("High Score: $highScore", 460f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {

                    if (myCarPosition >0) {
                        myCarPosition--
                    }
                } else {

                    if (myCarPosition <2) {
                        myCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {}
        }
        return true
    }

}
