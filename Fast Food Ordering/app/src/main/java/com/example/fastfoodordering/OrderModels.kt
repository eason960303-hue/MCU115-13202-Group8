package com.example.fastfoodordering

data class Order(
    var mainMeal: String? = null,
    val sideDishes: MutableList<String> = mutableListOf(),
    var drink: String? = null
) {
    fun isComplete(): Boolean {
        return !mainMeal.isNullOrEmpty() && sideDishes.isNotEmpty() && !drink.isNullOrEmpty()
    }
}
