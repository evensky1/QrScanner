package com.poit.qrscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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

            Toast.makeText(this, it.contents, Toast.LENGTH_LONG).show()

            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", it.contents)
            startActivity(intent)
            this.onPause()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanBtn = findViewById<Button?>(R.id.scanBtn).apply {
            setOnClickListener { scanCode() }
        }

        genBtn = findViewById<Button?>(R.id.generateBtn).apply {
            setOnClickListener { genCode() }
        }

        urlEdit = findViewById(R.id.urlEdit)
        qrView = findViewById(R.id.qrImage)
    }

    private fun scanCode() {
        val scanOptions = ScanOptions()
            .setPrompt("Volume up/down to flash on/off")
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