package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var op: String
    private lateinit var errorAlert: AlertDialog
    private var resultFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resetValues()
        btn_all_clear.setOnClickListener { v: View? ->
            resetValues()
            resultFlag = false
        }
        btn_off.setOnClickListener { v: View? -> finish() }
        errorAlert = AlertDialog.Builder(this).setTitle("Error")
            .setPositiveButton("OK") { _, _ -> resetValues() }.create()
    }

    fun pushDigit(v: View?) {
        if (resultFlag) num1 = "0"
        val d = v?.tag.toString()
        if (op == ""){
            num1 = if (num1 == "0" && d != ".") d else num1 + d
        } else{
            num2 = if (num2 == "0") d else num2 + d
        }
        setDisplayText()
    }

    fun setOperator(v: View?) {
        op = v?.tag.toString()
        setDisplayText()
        resultFlag = false
    }

    fun resultCall(v: View?) {
        if (num2 == "") {
            errorAlert.apply {
                setMessage("Invalid expression")
                show()
            }
        } else if (op == "/" && num2 == "0") {
            errorAlert.apply {
                setMessage("Cannot divide by zero")
                show()
            }
        } else calculate()
    }

    private fun calculate() {
        var n1 = num1.toFloat()
        val n2 = num2.toFloat()
        when (op){
            "+" -> n1 += n2
            "-" -> n1 -= n2
            "*" -> n1 *= n2
            "/" -> n1 /= n2
        }
        num1 = n1.toString()
        if (num1.matches(Regex("^\\d+\\.0+$"))) num1 = num1.substring(0, num1.indexOf('.'))
        num2 = ""
        op = ""
        setDisplayText()
        resultFlag = true
    }

    private fun setDisplayText(){
        calc_display.text = num1 + op + num2
    }

    private fun resetValues(){
        num1 = "0"
        num2 = ""
        op = ""
        setDisplayText()
    }
}
