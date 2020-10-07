package hi.cosmonaut.currency.ui.fragment.home

import android.util.Log
import androidx.navigation.NavDirections
import hi.cosmonaut.base.ui.viewmodel.feature.FeatureViewModel

class HomeViewModel: FeatureViewModel() {


    fun navigateToCurrency(action: NavDirections) {
        navigate(action)
    }

    companion object{
        private val TAG: String = HomeViewModel::class.java.simpleName
    }
}