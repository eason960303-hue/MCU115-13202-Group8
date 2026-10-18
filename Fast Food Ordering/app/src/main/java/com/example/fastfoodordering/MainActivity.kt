package com.example.fastfoodordering

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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

        val adapter = WizardPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabTitles = arrayOf(
            getString(R.string.choose_main_meal),
            getString(R.string.choose_side_dishes),
            getString(R.string.choose_drink),
            getString(R.string.confirm_order)
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        viewModel.order.observe(this) { order ->
            val main = order.mainMeal ?: "—"
            val sides = if (order.sideDishes.isNotEmpty()) order.sideDishes.joinToString(", ") else "—"
            val drink = order.drink ?: "—"
            binding.tvCurrentSelection.text = "Current Selection: Main: $main | Sides: $sides | Drink: $drink"
        }
    }

    fun onOrderSubmitted(order: Order) {
        val main = order.mainMeal ?: "—"
        val sides = order.sideDishes.joinToString(", ")
        val drink = order.drink ?: "—"
        binding.tvCurrentSelection.text = "Submitted Order:\nMain: $main\nSides: $sides\nDrink: $drink"
    }
}
