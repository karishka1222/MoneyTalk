package com.example.kotlinlearning

import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class add_rashs: AppCompatActivity(){

    lateinit var categorii: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_rash)

        categorii = findViewById(R.id.categ)
        popupMenu()
    }

    private fun popupMenu(){

        val popupMenu = PopupMenu(applicationContext, categorii)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.main_ras -> {
                    Toast.makeText(applicationContext, "Share pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.food_ras -> {
                    Toast.makeText(applicationContext, "Flag pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.enter_ras -> {
                    Toast.makeText(applicationContext, "Message pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.move_ras -> {
                    Toast.makeText(applicationContext, "Message pressed", Toast.LENGTH_SHORT).show()
                    true
                }R.id.health_ras -> {
                    Toast.makeText(applicationContext, "Message pressed", Toast.LENGTH_SHORT).show()
                    true
                }R.id.other_ras -> {
                    Toast.makeText(applicationContext, "Message pressed", Toast.LENGTH_SHORT).show()
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
}