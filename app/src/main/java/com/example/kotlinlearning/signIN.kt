package com.example.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.widget.ImageButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore

class signIN : AppCompatActivity() {
    lateinit var  img: ImageView
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var auth: FirebaseAuth
    lateinit var button: ImageButton
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        img = findViewById(com.example.kotlinlearning.R.id.googleicon)
        img.setImageResource(R.drawable.googleicon)
        auth = Firebase.auth
        button = findViewById<ImageButton>(R.id.knopka)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    firebaseAuth(account.idToken!!)
                }
            }catch (e: ApiException){
                Log.d("MyLog","Api Exception")
            }
        }
        button.setOnClickListener {
            SignIn()
        }
        checkAuth()
    }

    private fun getClient(): GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this,gso)
    }

    private fun SignIn(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuth(idToken: String){

        val cridencial = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(cridencial).addOnCompleteListener {
            if(it.isSuccessful){
                val traty = mapOf(
                    "${auth.currentUser!!.displayName}" to 0
                )
                Log.d("MyLog","Successful")


                db.collection("money")
                    .document("food").set(
                        traty
                    )
                db.collection("money")
                    .document("health").set(
                        traty
                    )
                db.collection("money")
                    .document("inter").set(
                        traty
                    )
                db.collection("money")
                    .document("main").set(
                        traty
                    )
                db.collection("money")
                    .document("other").set(
                        traty
                    )
                db.collection("money")
                    .document("trans").set(
                        traty
                    )
                db.collection("money")
                    .document("dohod").set(
                        traty
                    )
                db.collection("money")
                    .document("obsh").set(
                        traty
                    )
                db.collection("money")
                    .document("prog").set(
                        traty
                    )

                checkAuth()

            }else{
                Log.d("MyLog","MOT")
            }
        }
    }

    private fun checkAuth(){
        if(auth.currentUser != null){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}