package com.example.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Panel1Activity : AppCompatActivity() {

    lateinit var categorii: ImageButton
    lateinit var back: ImageView
    lateinit var text_categ: TextView
    lateinit var categ_text: TextView
    var title = ""
    val db = Firebase.firestore
    lateinit var add: Button
    lateinit var summa: EditText
    val auth = Firebase.auth
    lateinit var first_lin: RelativeLayout
    lateinit var second_lin: LinearLayout
    lateinit var third_lin: LinearLayout
    lateinit var input_summ: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel1)

        text_categ = findViewById(R.id.choosen_categ)
        categ_text = findViewById(R.id.categ_text)
        first_lin = findViewById(R.id.hi)
        second_lin = findViewById(R.id.second)
        third_lin = findViewById(R.id.third)
        input_summ = findViewById(R.id.input_summ)

        categorii = findViewById(R.id.categ)

        back = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        popupMenu()
    }
    private fun popupMenu(){

        val popupMenu = PopupMenu(applicationContext, categorii)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.main_ras -> {
                    text_categ.setText("Основные расходы")
                    title = "main"
                    DataBase(title)
                    true
                }
                R.id.food_ras -> {
                    text_categ.setText("Еда и рестораны")
                    title = "food"
                    DataBase(title)
                    true
                }
                R.id.enter_ras -> {
                    text_categ.setText("Развлечения")
                    title = "inter"
                    DataBase(title)
                    true
                }
                R.id.move_ras -> {
                    text_categ.setText("Транспорт")
                    title = "trans"
                    DataBase(title)
                    true
                }
                R.id.health_ras -> {
                    text_categ.setText("Здоровье")
                    title = "health"
                    DataBase(title)
                    true
                }
                R.id.other_ras -> {
                    text_categ.setText("Прочие расходы")
                    title = "other"
                    DataBase(title)
                    true
                }
                else -> true
            }
        }

        categorii.setOnClickListener {
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
    fun DataBase(title: String){
        summa = findViewById(R.id.summa)
        add = findViewById(R.id.btn_add)
        add.setOnClickListener {

            val doc = db.collection("money").document(title)
            doc.get().addOnSuccessListener {
                if (it != null){
                    val traty = mapOf(
                        auth.currentUser!!.displayName to (summa.text.toString().toInt()+it.data?.get("${auth.currentUser!!.displayName}").toString().toInt())
                    )
                    db.collection("money").document(title).set(
                        traty
                    )
                }
            }

            val doc1 = db.collection("money").document("obsh")
            doc1.get().addOnSuccessListener {
                if (it != null){
                    val rash = mapOf(
                        auth.currentUser!!.displayName to (summa.text.toString().toInt()+it.data?.get("${auth.currentUser!!.displayName}").toString().toInt())
                    )
                    db.collection("money").document("obsh").set(
                        rash
                    )
                }
            }
            val doc2 = db.collection("money").document("dohod")
            doc2.get().addOnSuccessListener {
                if (it != null){
                    val rash = mapOf(
                        auth.currentUser!!.displayName to (it.data?.get("${auth.currentUser!!.displayName}").toString().toInt()-summa.text.toString().toInt())
                    )
                    db.collection("money").document("dohod").set(
                        rash
                    )
                }
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}