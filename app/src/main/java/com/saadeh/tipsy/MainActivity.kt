package com.saadeh.tipsy

import android.os.Bundle
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDel = findViewById<Button>(R.id.btn_Del)
        val btnAdd = findViewById<Button>(R.id.btn_Add)
        val btnCalc = findViewById<Button>(R.id.btn_Calc)
        val tvSplit = findViewById<TextView>(R.id.tv_split)
        var vpercent: Float = 0f
        val tvBill = findViewById<TextView>(R.id.tv_bill)
        val tvPerson = findViewById<TextView>(R.id.tv_person)
        val tvTip = findViewById<TextView>(R.id.tv_tip)
        val edtBill = findViewById<EditText>(R.id.edt_bill)

        btnCalc.setOnClickListener(){
            tvBill.text = (edtBill.text.toString().toFloat() / tvSplit.text.toString().toInt()).toString()
            tvTip.text = (tvBill.text.toString().toFloat() * vpercent).toString()
            tvPerson.text = (tvBill.text.toString().toFloat() + tvTip.text.toString().toFloat()).toString()
        }

        btnDel.setOnClickListener(){
            if (tvSplit.text.toString().toInt() == 1){
                Snackbar.make(
                    tvSplit,
                    "Split não pode ser menor do que 1",
                    Snackbar.LENGTH_LONG).show()
            }else {
                tvSplit.text = (tvSplit.text.toString().toInt() - 1).toString()
            }
        }

        btnAdd.setOnClickListener(){
            tvSplit.text = (tvSplit.text.toString().toInt() + 1).toString()
        }

        val toggleButtonGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)

        toggleButtonGroup.addOnButtonCheckedListener() { toggleButtonGroup, checkedID, isChecked ->
            if (isChecked){
                when(checkedID){
                    R.id.button -> vpercent = 0.10f  //showToast("10% percent")
                    R.id.button2 -> vpercent = 0.15f //showToast("15% percent")
                    R.id.button3 -> vpercent = 0.20f //showToast("20% percent")
                    R.id.button4 -> vpercent = 0.25f //showToast("25% percent")
                    R.id.button5 -> showToast("Custom tip")
                }
            }else{
                if (toggleButtonGroup.checkedButtonId == View.NO_ID){
                    showToast("No Tip Selected")
                }
            }
        }
    }

    private fun showToast(str: String){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }
}