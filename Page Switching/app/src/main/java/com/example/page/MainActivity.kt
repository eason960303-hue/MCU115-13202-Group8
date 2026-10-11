package com.example.page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var etInput: EditText

    // 接收第二頁回傳資料的 Launcher
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val replyText = result.data?.getStringExtra("reply_key")
            if (!replyText.isNullOrEmpty()) {
                tvResult.text = replyText
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etInput = findViewById(R.id.etInput)
        tvResult = findViewById(R.id.tvResult)
        val btnSwitch = findViewById<Button>(R.id.btnSwitch)

        // 點擊按鈕後帶字串跳轉到 SecActivity
        btnSwitch.setOnClickListener {
            val textToSend = etInput.text.toString()
            val intent = Intent(this, SecActivity::class.java).apply {
                putExtra("input_key", textToSend)
            }
            startForResult.launch(intent)
        }
    }
}