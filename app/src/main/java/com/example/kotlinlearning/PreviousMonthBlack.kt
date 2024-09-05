package com.example.kotlinlearning

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PreviousMonthBlack : AppCompatActivity() {

    lateinit var next_btn: ImageButton

    lateinit var pieChart: PieChart
    var main: Float = 7426f
    var food: Float = 1284f
    var entertainment: Float = 1636f
    var moving: Float = 257f
    var health: Float = 1599f
    var diff: Float = 6091f

    val list: ArrayList<PieEntry> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_month_black)

        next_btn = findViewById(R.id.next_month)
        next_btn.setOnClickListener{
            val intent = Intent(this, MainActivityBlack::class.java)
            startActivity(intent)
        }

        pieChart = findViewById(R.id.pie_chart)

        list.add(PieEntry(main))
        list.add(PieEntry(food))
        list.add(PieEntry(entertainment))
        list.add(PieEntry(moving))
        list.add(PieEntry(health))
        list.add(PieEntry(diff))

        val pieDataSet = PieDataSet(list, "")
        val colours: ArrayList<Int> = arrayListOf()

        colours.add(Color.parseColor("#6AAAF3"))
        colours.add(Color.parseColor("#8D6CC6"))
        colours.add(Color.parseColor("#CD6AC9"))
        colours.add(Color.parseColor("#F26F2D"))
        colours.add(Color.parseColor("#F36E71"))
        colours.add(Color.parseColor("#FFBA1B"))

        pieDataSet.setColors(colours)

        pieDataSet.valueTextSize=0f


        pieDataSet.valueTextColor = Color.BLACK

        val pieData = PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.centerText = ""

        pieChart.description.text = ""
        pieChart.animateY(2000)

    }
}