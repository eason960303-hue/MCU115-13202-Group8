package com.example.fastfoodordering

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fastfoodordering.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ViewPager2 and TabLayout
        val adapter = WizardPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabTitles = arrayOf(
            getString(R.string.tab_main_meal),
            getString(R.string.tab_side_dishes),
            getString(R.string.tab_drink),
            getString(R.string.tab_confirm)
        )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // Three "Choose ..." button click handlers
        binding.btnChooseMain.setOnClickListener {
            binding.viewPager.currentItem = 0
        }

        binding.btnChooseSides.setOnClickListener {
            binding.viewPager.currentItem = 1
        }

        binding.btnChooseDrink.setOnClickListener {
            binding.viewPager.currentItem = 2
        }

        // Live observation for Current Selection status display
        viewModel.order.observe(this) { order ->
            val main = order.mainMeal ?: "-"
            val sides = if (order.sideDishes.isNotEmpty()) order.sideDishes.joinToString(", ") else "-"
            val drink = order.drink ?: "-"
            binding.tvCurrentSelection.text = "Main: $main\nSides: $sides\nDrink: $drink"
        }

        // Bottom "Order" button validation and submit dialog
        binding.btnOrder.setOnClickListener {
            val order = viewModel.order.value
            if (order?.isComplete() != true) {
                Toast.makeText(
                    this,
                    getString(R.string.please_select_all),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.submit_order))
                .setMessage(
                    "Main: ${order.mainMeal}\n" +
                    "Sides: ${order.sideDishes.joinToString(", ")}\n" +
                    "Drink: ${order.drink}\n\n" +
                    "Submit this order?"
                )
                .setPositiveButton(getString(R.string.submit)) { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.order_submitted), Toast.LENGTH_SHORT).show()
                    onOrderSubmitted(order)
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
    }

    fun onOrderSubmitted(order: Order) {
        val main = order.mainMeal ?: "-"
        val sides = order.sideDishes.joinToString(", ")
        val drink = order.drink ?: "-"
        binding.tvCurrentSelection.text = "[Submitted Order]\nMain: $main\nSides: $sides\nDrink: $drink"
    }
}
