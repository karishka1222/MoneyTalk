package com.example.kotlinlearning

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityBlack : AppCompatActivity() {

    lateinit var pieChart: PieChart
    var main: Float = 0f
    var food: Float = 0f
    var entertainment: Float = 0f
    var moving: Float = 0f
    var health: Float = 0f
    var diff: Float = 0f

    val db = Firebase.firestore

    lateinit var ras: TextView
    lateinit var ras1: TextView
    lateinit var ras2: TextView
    lateinit var ras3: TextView
    lateinit var ras4: TextView
    lateinit var ras5: TextView
    lateinit var ras6: TextView
    lateinit var ost: TextView
    lateinit var prog: TextView
    val list: ArrayList<PieEntry> = ArrayList()
    lateinit var qr: ImageButton

    lateinit var next_btn: ImageButton
    lateinit var previous_btn: ImageButton

    lateinit var btn_settings: ImageButton
    lateinit var block_white: ImageButton
    lateinit var month: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_black)

        block_white = findViewById(R.id.block_white)
        month = findViewById(R.id.month)
        ras = findViewById(R.id.rashod)
        val auth =  Firebase.auth
        ras1 = findViewById(R.id.osn)
        ras2 = findViewById(R.id.foods)
        ras3 = findViewById(R.id.inter)
        ras4 = findViewById(R.id.trans)
        ras5 = findViewById(R.id.hel)
        ras6 = findViewById(R.id.oth)
        ost = findViewById(R.id.ost)
        prog = findViewById(R.id.prog)
        pieChart = findViewById(R.id.pie_chart)
        qr = findViewById(R.id.qr)
        previous_btn = findViewById(R.id.previous_month)
        previous_btn.setOnClickListener{
            val intent = Intent(this, PreviousMonthBlack::class.java)
            startActivity(intent)
        }
        next_btn = findViewById(R.id.next_month)
        next_btn.setOnClickListener{
            val intent = Intent(this, NextMonthBlack::class.java)
            startActivity(intent)
        }

        val doc = db.collection("money").document("obsh")
        doc.get().addOnSuccessListener {
            if (it != null){
                ras.text = it.data?.get("${auth.currentUser!!.displayName}").toString() + "р"
            }
        }
        val doc9 = db.collection("money").document("previous")
        doc9.get().addOnSuccessListener {
            if (it != null){
                prog.text = "Прогноз: "+it.data?.get("${auth.currentUser!!.displayName}").toString()+ "р"
            }
        }
        val doc7 = db.collection("money").document("dohod")
        doc7.get().addOnSuccessListener {
            if (it != null){
                ost.text = "Осталось:"+" "+it.data?.get("${auth.currentUser!!.displayName}").toString() + "р"
            }
        }
        val doc1 = db.collection("money").document("main")
        doc1.get().addOnSuccessListener {
            if (it != null){
                ras1.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                main = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(main)
            }
        }
        val doc2 = db.collection("money").document("food")
        doc2.get().addOnSuccessListener {
            if (it != null){
                ras2.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                food = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(food)
            }
        }
        val doc3 = db.collection("money").document("inter")
        doc3.get().addOnSuccessListener {
            if (it != null){
                ras3.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                entertainment = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(entertainment)
            }
        }
        val doc4 = db.collection("money").document("trans")
        doc4.get().addOnSuccessListener {
            if (it != null){
                ras4.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                moving = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(moving)
            }

        }
        val doc5 = db.collection("money").document("health")
        doc5.get().addOnSuccessListener {
            if (it != null){
                ras5.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                health = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(health)

            }
        }
        val doc6 = db.collection("money").document("other")
        doc6.get().addOnSuccessListener {
            if (it != null){
                ras6.text = it.data?.get("${auth.currentUser!!.displayName}").toString()
                diff = it.data?.get("${auth.currentUser!!.displayName}").toString().toFloat()
                createPie(diff)
            }
        }
        val btn_add1 = findViewById<ImageButton>(R.id.add)

        btn_add1.setOnClickListener{

            val intent = Intent(this, Panel2Black::class.java)
            startActivity(intent)
        }

        qr.setOnClickListener {
            val intent = Intent(this, QRCodeBlack::class.java)
            startActivity(intent)
        }

        val btn_add2 = findViewById<ImageButton>(R.id.add_rashod)

        btn_add2.setOnClickListener{

            val intent = Intent(this, Panel1Black::class.java)
            startActivity(intent)
        }

        btn_settings = findViewById(R.id.btn_setting)

        popupMenu()

    }

    fun createPie(num: Float){

        list.add(PieEntry(num))

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

    private fun popupMenu() {
        val popupMenu = PopupMenu(applicationContext, btn_settings)
        popupMenu.inflate(R.menu.settings)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.change_theme -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.sign_out -> {
                    true
                }
                else -> true
            }
        }

        btn_settings.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            }catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
            true
        }
    }

}