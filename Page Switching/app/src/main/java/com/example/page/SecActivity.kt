package com.example.page

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val tvReceived = findViewById<TextView>(R.id.tvReceived)
        val etReply = findViewById<EditText>(R.id.etReply)
        val btnReturn = findViewById<Button>(R.id.btnReturn)

        // 接收第一頁傳過來的文字
        val receivedText = intent.getStringExtra("input_key")
        if (!receivedText.isNullOrEmpty()) {
            tvReceived.text = receivedText
        }

        // 點擊按鈕後回傳資料並結束 Activity
        btnReturn.setOnClickListener {
            val replyText = etReply.text.toString()
            val resultIntent = Intent().apply {
                putExtra("reply_key", replyText)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}

