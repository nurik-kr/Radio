package kg.nurik.radio.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

//    val radioList = Resources.generate()
//    var radioLiveData = MutableLiveData<RadioStations>().apply {
//        value = radioList[0] }//будет проигрываться нулевая позиция в списке
    var isBound = false
//
//    fun nextStation() {
//        val index = radioList.indexOf(radioLiveData.value) //current station
//        if (index < radioList.size -1)
//            radioLiveData.postValue(radioList[index +1])
//    }
//
//    fun backStation() {
//        val pos = radioList.indexOf(radioLiveData.value) //current station
//        if (pos > 0)
//            radioLiveData.postValue(radioList[pos -1])
//    }
}