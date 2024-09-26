package com.saadeh.tipsy

import android.os.Bundle
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.Locale

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
        val tvCust = findViewById<TextView>(R.id.tv_custom)
        var lvBill: Float = 0f
        var lvTip: Float = 0f
        var lvPerson: Float = 0f



        btnCalc.setOnClickListener(){
            if (tvCust.text.toString().toFloat() > 0f){
                vpercent = tvCust.text.toString().toFloat() / 100
            }
            lvBill = edtBill.text.toString().toFloat() / tvSplit.text.toString().toInt()
            lvTip = lvBill * vpercent
            lvPerson = lvBill + lvTip
            tvBill.text = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(lvBill)
            tvTip.text = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(lvTip)
            tvPerson.text = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(lvPerson)
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
                    R.id.button5 -> vpercent = tvCust.text.toString().toFloat()//showToast("Custom tip")
                }
            }else{
                if (toggleButtonGroup.checkedButtonId == View.NO_ID){
                    showToast("No Tip Selected")
                }
            }
        }

        showEditTextDialog()

    }

    private fun showEditTextDialog(){
        val btnCustom = findViewById<Button>(R.id.button5)
        val tvCust = findViewById<TextView>(R.id.tv_custom)
        btnCustom.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.edt_tip)


            with(builder){
                setTitle("Enter custom tip!")
                setPositiveButton("OK"){dialog, which ->
                    tvCust.text = editText.text.toString()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main", "Custom tip canceled.")
                }
                setView(dialogLayout)
                show()
            }
        }


    }

    private fun showToast(str: String){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }
}