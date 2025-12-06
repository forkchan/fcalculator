package com.example.fcalculator

import android.R.id.input
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fcalculator.databinding.ActivityMainBinding
import com.example.fcalculator.ui.theme.FCalculatorTheme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.tvQuestion.text = ""
        binding.tvAnswer.text = ""
        startSnowfall()


        binding.tvQuestion.text = ""
        binding.tvAnswer.text = ""

        fun calc() {

            binding.llNumZero.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "0"
                updateAnswer()
            }
            binding.llNumOne.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "1"
                updateAnswer()
            }
            binding.llNumTwo.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "2"
                updateAnswer()
            }
            binding.llNumThree.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "3"
                updateAnswer()
            }
            binding.llNumFour.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "4"
                updateAnswer()
            }
            binding.llNumFive.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "5"
                updateAnswer()
            }
            binding.llNumSix.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "6"
                updateAnswer()
            }
            binding.llNumSeven.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "7"
                updateAnswer()
            }
            binding.llNumEight.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "8"
                updateAnswer()
            }
            binding.llNumNine.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "9"
                updateAnswer()
            }

            binding.llPoint.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "."
                updateAnswer()
            }

            binding.llPlus.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "+"
                updateAnswer()
            }
            binding.llMinus.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "-"
                updateAnswer()
            }
            binding.llMultiply.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "×"
                updateAnswer()
            }
            binding.llDivision.setOnClickListener {
                binding.tvQuestion.text = binding.tvQuestion.text.toString() + "÷"
                updateAnswer()
            }

            binding.llClearAll.setOnClickListener {
                binding.tvAnswer.text = ""
                binding.tvQuestion.text = ""
            }
            binding.llBackspace.setOnClickListener {
                val currentText = binding.tvQuestion.text.toString()
                if (currentText.isNotEmpty()) {
                    binding.tvQuestion.text = currentText.dropLast(1)
                }
            }

            binding.llEquals.setOnClickListener {

                val answerText = binding.tvAnswer.text.toString()

                binding.tvQuestion.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction {
                        binding.tvQuestion.text = answerText

                        binding.tvQuestion.animate()
                            .alpha(1f)
                            .setDuration(200)
                            .start()
                    }.start()

                binding.tvAnswer.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction {
                        binding.tvAnswer.text = ""
                        binding.tvAnswer.alpha = 1f
                    }.start()
            }

        }
        calc()
        }
    fun parseExpression(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var number = ""
        for (c in expr) {
            if (c.isDigit() || c == '.') {
                number += c
            } else if (c in listOf('+','-','×','÷')) {
                if (number.isNotEmpty()) {
                    tokens.add(number)
                    number = ""
                }
                tokens.add(c.toString())
            }
        }
        if (number.isNotEmpty()) tokens.add(number)
        return tokens
    }

    fun evaluateExpression(tokens: List<String>): Double? {
        if (tokens.isEmpty()) return null
        val temp = tokens.toMutableList()

        var i = 0
        while (i < temp.size) {
            if ((temp[i] == "×" || temp[i] == "÷") && i > 0 && i < temp.size - 1) {
                val left = temp[i - 1].toDouble()
                val right = temp[i + 1].toDouble()
                val result = if (temp[i] == "×") left * right else left / right
                temp[i - 1] = result.toString()
                temp.removeAt(i)
                temp.removeAt(i)
                i--
            } else {
                i++
            }
        }

        i = 0
        while (i < temp.size) {
            if ((temp[i] == "+" || temp[i] == "-") && i > 0 && i < temp.size - 1) {
                val left = temp[i - 1].toDouble()
                val right = temp[i + 1].toDouble()
                val result = if (temp[i] == "+") left + right else left - right
                temp[i - 1] = result.toString()
                temp.removeAt(i)
                temp.removeAt(i)
                i--
            } else {
                i++
            }
        }

        return temp.firstOrNull()?.toDouble()
    }


    private fun updateAnswer() {
        val expr = binding.tvQuestion.text.toString()
        val tokens = parseExpression(expr)
        val result = evaluateExpression(tokens)
        binding.tvAnswer.text = result?.toString() ?: ""
    }

    private fun startSnowfall() {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val template = binding.ivSnow

        fun addFallingSnow() {
            val snow = ImageView(this)
            snow.setImageDrawable(template.drawable)

            val rotate = (0..180).random()
            val size = (35..150).random()
            val params = FrameLayout.LayoutParams(size, size)
            params.leftMargin = (0..(screenWidth - size).toInt()).random()
            params.topMargin = -size
            snow.layoutParams = params
            binding.container.addView(snow)

            val duration = (3000..6000).random().toLong()
            val animator = ObjectAnimator.ofFloat(snow, "translationY", -size.toFloat(), screenHeight)
            animator.duration = duration
            animator.interpolator = LinearInterpolator()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.container.removeView(snow)
                    addFallingSnow()
                }
            })
            animator.start()
        }

        repeat(7) {
            addFallingSnow()
        }
    }
}

    private var expression = ""
    private fun MainActivity.addSymbol(symbol: String) {
        expression += symbol

    }

    private fun MainActivity.addOperator(op: String) {
        expression += op
    }

    private fun MainActivity.backspace() {
        if (expression.isNotEmpty()) {
            expression = expression.dropLast(1)
    }

}

