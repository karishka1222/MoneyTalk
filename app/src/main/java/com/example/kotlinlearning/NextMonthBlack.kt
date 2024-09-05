package com.example.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NextMonthBlack : AppCompatActivity() {

    lateinit var previous_btn: ImageButton

    lateinit var pieChart: PieChart
    var main: Float = 0f
    var food: Float = 0f
    var entertainment: Float = 0f
    var moving: Float = 0f
    var health: Float = 0f
    var diff: Float = 0f

    val list: ArrayList<PieEntry> = ArrayList()

    val db = Firebase.firestore

    lateinit var prog: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_month_black)

        val auth =  Firebase.auth
        prog = findViewById(R.id.prog)
        pieChart = findViewById(R.id.pie_chart)

        previous_btn = findViewById(R.id.previous_month)
        previous_btn.setOnClickListener{
            val intent = Intent(this, MainActivityBlack::class.java)
            startActivity(intent)
        }

        val doc9 = db.collection("money").document("previous")
        doc9.get().addOnSuccessListener {
            if (it != null){
                val prog1 = it.data?.get("${auth.currentUser!!.displayName}").toString()
                val doc123 = db.collection("money").document("obsh")
                doc123.get().addOnSuccessListener {
                    if (it != null){
                        prog.text = "Прогноз: "+(it.data?.get("${auth.currentUser!!.displayName}").toString().toInt() + prog1.toInt()) / 2 + "р"
                    }
                }

            }
        }

    }
}