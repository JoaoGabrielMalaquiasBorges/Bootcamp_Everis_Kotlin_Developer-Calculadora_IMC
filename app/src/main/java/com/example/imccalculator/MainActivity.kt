package com.example.imccalculator

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val title: TextView = findViewById(R.id.textView)
        val heightInput: EditText = findViewById(R.id.heightInput)
        val weightInput: EditText = findViewById(R.id.weightInput)
        val button: Button = findViewById(R.id.button)

        heightInput.doOnTextChanged { text, _, _, _ -> title.text = "Altura: $text" }
        weightInput.doOnTextChanged { text, _, _, _ -> title.text = "Peso: $text" }

        button.setOnClickListener {
            val nf = NumberFormat.getInstance()
            val parsedHeight = nf.parse(heightInput.text.toString() ?: "0").toDouble()
            // val parsedHeight = heightInput.text.toString().toDoubleOrNull()
            val parsedWeight = nf.parse(weightInput.text.toString() ?: "0").toDouble()
            // val parsedWeight = weightInput.text.toString().toDoubleOrNull()

            if (parsedHeight != 0.0 && parsedWeight != 0.0) {
                val imc = parsedWeight / (parsedHeight * parsedHeight)
                val formattedImc = nf.parse("%.1f".format(imc)).toDouble()

                var weightRange = when (formattedImc) {
                    in Double.NEGATIVE_INFINITY..18.4 -> R.color.skinny
                    in 18.5..24.9 -> R.color.healthy
                    in 25.0..29.9 -> R.color.overweight
                    in 30.0..34.9 -> R.color.obesity_1
                    in 35.0..39.9 -> R.color.obesity_2
                    in 40.0..Double.POSITIVE_INFINITY -> R.color.obesity_3
                    else -> R.color.purple_700
                }

                if (Build.VERSION.SDK_INT >= 23) {
                    window.statusBarColor = this.resources.getColor(weightRange, null)
                    title.setTextColor(this.resources.getColor(weightRange, null))
                    title.text = "Seu IMC é: ${nf.format(formattedImc)}"
                    button.setBackgroundColor(this.resources.getColor(weightRange, null))
                }

            }
        }
    }
}