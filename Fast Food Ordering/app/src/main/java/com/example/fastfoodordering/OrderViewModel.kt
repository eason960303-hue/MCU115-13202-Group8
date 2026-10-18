package com.example.fastfoodordering

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _order = MutableLiveData(Order())
    val order: LiveData<Order> = _order

    fun setMainMeal(meal: String?) {
        val currentOrder = _order.value ?: Order()
        currentOrder.mainMeal = meal
        _order.value = currentOrder
    }

    fun addSideDish(dish: String) {
        val currentOrder = _order.value ?: Order()
        if (!currentOrder.sideDishes.contains(dish)) {
            currentOrder.sideDishes.add(dish)
            _order.value = currentOrder
        }
    }

    fun removeSideDish(dish: String) {
        val currentOrder = _order.value ?: Order()
        if (currentOrder.sideDishes.contains(dish)) {
            currentOrder.sideDishes.remove(dish)
            _order.value = currentOrder
        }
    }

    fun setDrink(drink: String?) {
        val currentOrder = _order.value ?: Order()
        currentOrder.drink = drink
        _order.value = currentOrder
    }

    fun resetOrder() {
        _order.value = Order()
    }
}
