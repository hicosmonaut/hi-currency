package hi.cosmonaut.base.ui.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}