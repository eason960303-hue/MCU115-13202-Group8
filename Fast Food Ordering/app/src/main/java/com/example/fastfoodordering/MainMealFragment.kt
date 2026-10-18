package com.example.fastfoodordering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fastfoodordering.databinding.FragmentMainMealBinding

class MainMealFragment : Fragment() {

    private var _binding: FragmentMainMealBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.order.observe(viewLifecycleOwner) { order ->
            when (order.mainMeal) {
                "Burger" -> if (binding.rgMainMeal.checkedRadioButtonId != R.id.rb_burger) binding.rbBurger.isChecked = true
                "Fried Chicken" -> if (binding.rgMainMeal.checkedRadioButtonId != R.id.rb_fried_chicken) binding.rbFriedChicken.isChecked = true
                "Chicken Nuggets" -> if (binding.rgMainMeal.checkedRadioButtonId != R.id.rb_chicken_nuggets) binding.rbChickenNuggets.isChecked = true
                else -> if (order.mainMeal.isNullOrEmpty()) binding.rgMainMeal.clearCheck()
            }
        }

        binding.rgMainMeal.setOnCheckedChangeListener { _, checkedId ->
            val selectedMeal = when (checkedId) {
                R.id.rb_burger -> "Burger"
                R.id.rb_fried_chicken -> "Fried Chicken"
                R.id.rb_chicken_nuggets -> "Chicken Nuggets"
                else -> null
            }
            if (viewModel.order.value?.mainMeal != selectedMeal) {
                viewModel.setMainMeal(selectedMeal)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
