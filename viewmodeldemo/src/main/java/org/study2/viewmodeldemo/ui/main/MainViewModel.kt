package org.study2.viewmodeldemo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val usd_to_eu_rate = 0.74f
//    private var dollarText = ""
    var dollarValue: MutableLiveData<String> = MutableLiveData()
   // private var result: Float = 0f
     var result: MutableLiveData<Float> = MutableLiveData()

    fun convertValue() {
        dollarValue.let {
            if (!it.value.equals("")){
                result.value=it.value?.toFloat()?.times(usd_to_eu_rate)
            }else{
                result.value = 0f
            }
        }
    }


//    fun setAmount(value:String) {
//        this.dollarText = value
//        result.setValue(value.toFloat()*usd_to_eu_rate)
//    }
//
//    fun getResult():MutableLiveData<Float> {
//        return result
//    }
}