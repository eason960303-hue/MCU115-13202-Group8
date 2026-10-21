package com.example.telephonebookusingrecyleview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var tvEmptyState: TextView

    private val addContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getSerializableExtra(AddContactActivity.EXTRA_CONTACT, Contact::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getSerializableExtra(AddContactActivity.EXTRA_CONTACT) as? Contact
            }
            if (contact != null) {
                contactAdapter.addContact(contact)
                updateEmptyState()
                Toast.makeText(this, "已新增聯絡人：${contact.name}", Toast.LENGTH_SHORT).show()
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnAddContact = findViewById<MaterialButton>(R.id.btnAddContact)
        tvEmptyState = findViewById(R.id.tvEmptyState)

        val dummyData = generateDummyData()

        contactAdapter = ContactAdapter(dummyData) { contact ->
            contactAdapter.removeContact(contact)
            updateEmptyState()
            Toast.makeText(this, "已刪除：${contact.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contactAdapter.filter(s?.toString() ?: "")
                updateEmptyState()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            addContactLauncher.launch(intent)
        }

        updateEmptyState()
    }

    private fun updateEmptyState() {
        if (contactAdapter.getDisplayedItemCount() == 0) {
            tvEmptyState.visibility = View.VISIBLE
        } else {
            tvEmptyState.visibility = View.GONE
        }
    }

    private fun generateDummyData(): MutableList<Contact> {
        return mutableListOf(
            Contact(name = "陳小明", phone = "0912-345-678", email = "xiaoming@example.com"),
            Contact(name = "林家豪", phone = "0987-654-321", email = "jiahao@example.com"),
            Contact(name = "張雅婷", phone = "0933-111-222", email = "yating@example.com")
        )
    }
}
