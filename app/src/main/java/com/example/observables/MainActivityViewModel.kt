package com.example.observables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    // live data is obsolete
    private val _livedata = MutableLiveData("Hello World")
    fun livedata(): LiveData<String> = _livedata

    //in stateflow we have powerful flow operators
    //can map the results easily, filter them
    //easier testable than livedata because use coroutines
    //testing capabilities of coroutines are great, no delay
    //use it when the project not a composed project
    //called hot flow and keep emitting values even if there are no collectors, While “cold flows,” which only emit values when there is an active collector.
    //emit value again when rotate the mobile
    private val _stateflow = MutableStateFlow("Hello World")
    fun stateflow(): StateFlow<String> = _stateflow.asStateFlow()

    //one time event
    private val _sharedflow = MutableSharedFlow<String>()
    fun sharedflow(): SharedFlow<String> = _sharedflow.asSharedFlow()


    fun triggerLiveData() {
        _livedata.value = "LiveData"
    }

    fun triggerStateFlow() {
        _stateflow.value = "StateFlow"
    }

    //for emit multiple values over a period of time
    fun triggerFlow(): Flow<Int> {
        return flow {
            repeat(60) {
                emit(it + 1)
                delay(1000L)
            }
        }
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedflow.emit("SharedFlow")
        }
    }

}