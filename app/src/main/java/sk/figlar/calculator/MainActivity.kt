package sk.figlar.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var display: TextView? = null

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
    }

    fun onDigit(view: View) {
        display?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        display?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric) {
            display?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        display?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                display?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var displayValue = display?.text.toString()
            var prefix = ""

            try {
                if (displayValue.startsWith("-")) {
                    prefix = "-"
                    displayValue = displayValue.substring(1)
                }

                if (displayValue.contains("-")) {
                    val splitValue = displayValue.split("-")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }

                    val result = first.toDouble() - second.toDouble()
                    display?.text = removeDotAndZero(result.toString())
                } else if (displayValue.contains("+")) {
                    val splitValue = displayValue.split("+")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }

                    val result = first.toDouble() + second.toDouble()
                    display?.text = removeDotAndZero(result.toString())
                } else if (displayValue.contains("/")) {
                    val splitValue = displayValue.split("/")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }

                    val result = first.toDouble() / second.toDouble()
                    display?.text = removeDotAndZero(result.toString())
                } else if (displayValue.contains("*")) {
                    val splitValue = displayValue.split("*")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }

                    val result = first.toDouble() * second.toDouble()
                    display?.text = removeDotAndZero(result.toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun removeDotAndZero(result: String): String {
        var value = result
        if (result.endsWith(".0"))
            value = result.dropLast(2)
        return value
    }
}