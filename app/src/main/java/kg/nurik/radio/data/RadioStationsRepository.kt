package kg.nurik.radio.data

import androidx.lifecycle.MutableLiveData

class RadioStationsRepository {
    var radioLiveData = MutableLiveData<RadioStations>().apply {
        value = radioList[0]
    }//будет проигрываться нулевая позиция в списке


    fun nextStation(): RadioStations? {
        val index = radioList.indexOf(radioLiveData.value) //current station
        if (index < radioList.size - 1)
            radioLiveData.postValue(radioList[index + 1])
        return radioLiveData.value
    }

    fun backStation(): RadioStations? {
        val pos = radioList.indexOf(radioLiveData.value) //current station
        if (pos > 0)
            radioLiveData.postValue(radioList[pos - 1])
        return radioLiveData.value
    }

      companion object {
        val radioList = Resources.generate()
    }
}