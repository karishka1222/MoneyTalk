package com.example.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Panel2Black : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var add: Button
    lateinit var first_lin: RelativeLayout
    lateinit var lin: LinearLayout

    lateinit var input_sum: TextView

    val auth = Firebase.auth
    lateinit var summa: EditText
    val db = Firebase.firestore
    var dohod: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel2_black)

        input_sum = findViewById(R.id.name)
        first_lin = findViewById(R.id.first)
        lin = findViewById(R.id.lin)

        back = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivityBlack::class.java)
            startActivity(intent)
        }
        DataBase()
    }


    fun DataBase(){
        summa = findViewById(R.id.edt_sum)
        add = findViewById(R.id.btn_add)
        add.setOnClickListener {
            db.collection("money").document("dohod").get().addOnSuccessListener {
                if (it != null){
                    dohod = it.data?.get("${auth.currentUser!!.displayName}").toString().toInt()
                    val traty = mapOf(
                        auth.currentUser!!.displayName to (summa.text.toString().toInt()+dohod)
                    )
                    val doc = db.collection("money").document("dohod")
                    doc.set(
                        traty
                    )
                }
            }
            val intent = Intent(this, MainActivityBlack::class.java)
            startActivity(intent)
        }
    }

}