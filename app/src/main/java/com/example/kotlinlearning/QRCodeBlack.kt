package com.example.kotlinlearning

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QRCodeBlack : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    val db = Firebase.firestore
    val auth =  Firebase.auth
    lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_black)

        back = findViewById(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, MainActivityBlack::class.java)
            startActivity(intent)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)
        }else{
            startScanning()
        }

    }

    private fun startScanning(){
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this,scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val znach = it.text.toString()

                val n = znach.split("=","&",".")
                val got = (n[3])
                val doc1 = db.collection("money").document("obsh")
                doc1.get().addOnSuccessListener {
                    if (it != null){
                        val rash = mapOf(
                            auth.currentUser!!.displayName to (got.toInt()+it.data?.get("${auth.currentUser!!.displayName}").toString().toInt())
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
                            auth.currentUser!!.displayName to (it.data?.get("${auth.currentUser!!.displayName}").toString().toInt()-got.toInt())
                        )
                        db.collection("money").document("dohod").set(
                            rash
                        )
                    }
                }

                val doc3 = db.collection("money").document("main")
                doc3.get().addOnSuccessListener {
                    if (it != null){
                        val rash = mapOf(
                            auth.currentUser!!.displayName to (it.data?.get("${auth.currentUser!!.displayName}").toString().toInt()+got.toInt())
                        )
                        db.collection("money").document("main").set(
                            rash
                        )
                    }
                }

                val intent = Intent(this, MainActivityBlack::class.java)

                startActivity(intent)
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera Initialization error:${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this,"Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

}