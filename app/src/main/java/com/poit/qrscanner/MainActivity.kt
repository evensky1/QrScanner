package com.poit.qrscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {

    private lateinit var scanBtn: Button
    private lateinit var genBtn: Button
    private lateinit var urlEdit: EditText
    private lateinit var qrView: ImageView

    private val barLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            val intent = Intent(this@MainActivity, WebViewActivity::class.java)
            intent.putExtra("url", it.contents)
            intent.putExtra("currLoc", urlEdit.text.toString())
            startActivity(intent)
            this.onPause()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanBtn = findViewById(R.id.scanBtn)
        scanBtn.setOnClickListener {
            scanCode()
        }

        genBtn = findViewById(R.id.generateBtn)
        genBtn.setOnClickListener {
            genCode()
        }

        urlEdit = findViewById(R.id.urlEdit)
        urlEdit.setText(intent.getStringExtra("currLoc") ?: "")
        genCode()

        qrView = findViewById(R.id.qrImage)
    }

    private fun scanCode() {
        val scanOptions = ScanOptions()
            .setPrompt("Volume up to flash on")
            .setBeepEnabled(true)
            .setOrientationLocked(true)
            .setCaptureActivity(CodeCaptureActivity::class.java)

        barLauncher.launch(scanOptions)
    }

    private fun genCode() {
        val url = urlEdit.text.toString().trim()

        if (url.isNotBlank()) {
            val matrix = MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 1000, 1000)

            qrView.setImageBitmap(
                BarcodeEncoder().createBitmap(matrix)
            )
        }
    }
}