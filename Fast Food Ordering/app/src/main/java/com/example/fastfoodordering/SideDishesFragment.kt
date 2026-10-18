package com.example.fastfoodordering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fastfoodordering.databinding.FragmentSideDishesBinding

class SideDishesFragment : Fragment() {

    private var _binding: FragmentSideDishesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.order.observe(viewLifecycleOwner) { order ->
            if (binding.cbFries.isChecked != order.sideDishes.contains("Fries")) {
                binding.cbFries.isChecked = order.sideDishes.contains("Fries")
            }
            if (binding.cbSalad.isChecked != order.sideDishes.contains("Salad")) {
                binding.cbSalad.isChecked = order.sideDishes.contains("Salad")
            }
            if (binding.cbCornCup.isChecked != order.sideDishes.contains("Corn Cup")) {
                binding.cbCornCup.isChecked = order.sideDishes.contains("Corn Cup")
            }
        }

        val checkListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val dish = when (buttonView.id) {
                R.id.cb_fries -> "Fries"
                R.id.cb_salad -> "Salad"
                R.id.cb_corn_cup -> "Corn Cup"
                else -> null
            }
            dish?.let {
                if (isChecked) {
                    viewModel.addSideDish(it)
                } else {
                    viewModel.removeSideDish(it)
                }
            }
        }

        binding.cbFries.setOnCheckedChangeListener(checkListener)
        binding.cbSalad.setOnCheckedChangeListener(checkListener)
        binding.cbCornCup.setOnCheckedChangeListener(checkListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
