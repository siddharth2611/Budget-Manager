package com.example.budgettracker

import android.arch.persistence.room.RoomDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)
        val labelInput = findViewById<TextView>(R.id.labelInput)
        val amountInput = findViewById<TextView>(R.id.amountInput)
        val descriptionInput = findViewById<TextView>(R.id.descriptionInput)
        val labelLayout = findViewById<TextView>(R.id.labelLayout)
        val amountLayout = findViewById<TextView>(R.id.amountLayout)
        val description = findViewById<TextView>(R.id.descriptionLayout)
        val closebtn = findViewById<ImageButton>(R.id.closeBtn)

        labelInput.addTextChangedListener {
            if(it!!.count()>0)
                labelLayout.error = null
        }

        amountInput.addTextChangedListener {
            if(it!!.count()>0)
                amountLayout.error = null
        }


        addTransactionBtn.setOnClickListener{
            val label: String = labelInput.text.toString()
            val description: String = descriptionInput.text.toString()
            val amount: Double? = amountInput.text.toString().toDoubleOrNull()


            if(label.isEmpty())
                labelLayout.error = "Please enter a valid label"

            else if(amount == null)
                amountLayout.error = "Please enter a valid amount"
            else{
                val transaction = Transaction(0,label,amount,description)
                insert(transaction)
            }

        }

        closebtn.setOnClickListener{
            finish()
        }
    }
    private fun insert(transaction: Transaction){
        val db: AppDatabase = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()

        }
    }
}