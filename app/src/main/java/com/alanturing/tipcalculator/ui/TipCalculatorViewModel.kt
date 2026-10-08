package com.alanturing.tipcalculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TipCalculatorViewModel(): ViewModel() {

    private val _state = MutableStateFlow(TipCaculatorScreenState())
    val state: StateFlow<TipCaculatorScreenState>
        get() = _state.asStateFlow()
    fun updateGuest(newGuest:String){
        val oldState = state.value
        _state.value = oldState.copy(
            guests = newGuest
        )
        enableCalculate()
    }
    fun updateAmount(newAmount:String){
        val oldState = state.value
        _state.value = oldState.copy(
            amount = newAmount
        )
        enableCalculate()
    }
    fun updateTipAmount(newTipAmount:Float){
        val oldState = state.value
        _state.value = oldState.copy(
            tipAmount = newTipAmount
        )
    }
    fun enableCalculate(){
        val state = _state.value
        val amount = state.amount.toDoubleOrNull()
        val guests = state.guests.toIntOrNull()
        _state.value = state.copy( isCaculateEnabled = (amount != null && guests != null) && guests > 0 && amount > 0.0)
    }

    fun calculateSplit() {
        val state = _state.value
        val amount = state.amount.toDoubleOrNull()
        val guests = state.guests.toIntOrNull()

        val totalWithTip = when (state.tipAmount) {
            1.0f -> amount!! * 1.05f
            2.0f -> amount!! * 1.1f
            3.0f -> amount!! * 1.15f
            4.0f -> amount!! * 1.2f
            else -> amount

        }
        val result = totalWithTip!! / guests!!
        _state.value = state.copy(
            result = result.toString()
        )
    }

    fun changeTip(isEnabled:Boolean) {
        val oldState = state.value
        _state.value = oldState.copy(
            tip = isEnabled
        )
    }
}

data class TipCaculatorScreenState(
    val guests:String = "0",
    val amount:String = "0.00",
    val tip:Boolean = false,
    val tipAmount:Float = 0.0F,
    val result:String = "",
    val isCaculateEnabled:Boolean = false

)