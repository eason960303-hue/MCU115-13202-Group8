package com.example.fastfoodordering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fastfoodordering.databinding.FragmentDrinkBinding

class DrinkFragment : Fragment() {

    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.order.observe(viewLifecycleOwner) { order ->
            when (order.drink) {
                "Cola" -> if (binding.rgDrink.checkedRadioButtonId != R.id.rb_cola) binding.rbCola.isChecked = true
                "Iced Tea" -> if (binding.rgDrink.checkedRadioButtonId != R.id.rb_iced_tea) binding.rbIcedTea.isChecked = true
                "Water" -> if (binding.rgDrink.checkedRadioButtonId != R.id.rb_water) binding.rbWater.isChecked = true
                else -> if (order.drink.isNullOrEmpty()) binding.rgDrink.clearCheck()
            }
        }

        binding.rgDrink.setOnCheckedChangeListener { _, checkedId ->
            val selectedDrink = when (checkedId) {
                R.id.rb_cola -> "Cola"
                R.id.rb_iced_tea -> "Iced Tea"
                R.id.rb_water -> "Water"
                else -> null
            }
            if (viewModel.order.value?.drink != selectedDrink) {
                viewModel.setDrink(selectedDrink)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
