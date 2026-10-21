package com.example.telephonebookusingrecyleview

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_contact)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val btnConfirmAdd = findViewById<MaterialButton>(R.id.btnConfirmAdd)

        btnBack.setOnClickListener {
            finish()
        }

        btnConfirmAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                Toast.makeText(this, "請輸入電話", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newContact = Contact(name = name, phone = phone, email = email)
            val resultIntent = Intent().apply {
                putExtra(EXTRA_CONTACT, newContact)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_CONTACT = "extra_contact"
    }
}
