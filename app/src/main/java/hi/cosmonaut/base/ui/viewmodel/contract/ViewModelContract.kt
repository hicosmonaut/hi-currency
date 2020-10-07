package hi.cosmonaut.base.ui.viewmodel.contract

import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections

interface ViewModelContract {

    interface INavigation{
        val navigationData: LiveData<NavDirections>

        fun navigate(action: NavDirections)
    }

    interface IMessage{
        val messageData: LiveData<String>
        val messageIdData: LiveData<Int>

        fun sendMessage(message: String)
        fun sendMessage(messageId: Int)
    }



}