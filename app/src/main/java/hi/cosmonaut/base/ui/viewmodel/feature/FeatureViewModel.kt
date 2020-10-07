package hi.cosmonaut.base.ui.viewmodel.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import hi.cosmonaut.base.ui.viewmodel.BaseViewModel
import hi.cosmonaut.base.ui.viewmodel.contract.ViewModelContract

open class FeatureViewModel:
    BaseViewModel(),
    ViewModelContract.INavigation,
    ViewModelContract.IMessage
{
    private val _navigationData = MutableLiveData<NavDirections>()
    private val _messageData: MutableLiveData<String> = MutableLiveData<String>()
    private val _messageIdData: MutableLiveData<Int> = MutableLiveData<Int>()

    override val navigationData: LiveData<NavDirections> get() = _navigationData
    override val messageData: LiveData<String> get() = _messageData
    override val messageIdData: LiveData<Int> get() = _messageIdData

    override fun navigate(action: NavDirections) = _navigationData.postValue(action)
    override fun sendMessage(message: String) = _messageData.postValue(message)
    override fun sendMessage(messageId: Int) = _messageIdData.postValue(messageId)
}