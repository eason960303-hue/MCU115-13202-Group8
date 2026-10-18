package com.example.fastfoodordering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fastfoodordering.databinding.FragmentConfirmBinding

class ConfirmFragment : Fragment() {

    private var _binding: FragmentConfirmBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.order.observe(viewLifecycleOwner) { order ->
            val main = order.mainMeal ?: "—"
            val sides = if (order.sideDishes.isNotEmpty()) order.sideDishes.joinToString(", ") else "—"
            val drink = order.drink ?: "—"
            binding.tvSummary.text = "Main: $main\nSides: $sides\nDrink: $drink"
        }

        binding.btnConfirm.setOnClickListener {
            val order = viewModel.order.value
            if (order?.isComplete() != true) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_select_all),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.submit_order))
                .setMessage(
                    "Main: ${order.mainMeal}\n" +
                    "Sides: ${order.sideDishes.joinToString(", ")}\n" +
                    "Drink: ${order.drink}\n\n" +
                    "Submit this order?"
                )
                .setPositiveButton(getString(R.string.submit)) { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(requireContext(), getString(R.string.order_submitted), Toast.LENGTH_SHORT).show()
                    (activity as? MainActivity)?.onOrderSubmitted(order)
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
