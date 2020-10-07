package hi.cosmonaut.currency.ui.fragment.host

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import hi.cosmonaut.base.ui.fragment.BaseFragment
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.databinding.FragmentHostBinding
import hi.cosmonaut.currency.ui.activity.main.MainActivity
import hi.cosmonaut.currency.ui.activity.main.MainViewModel

class HostFragment: BaseFragment<FragmentHostBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_host

    override fun onInit() {
        Log.d(TAG, "onInit: start")
    }

    companion object{
        private val TAG: String = HostFragment::class.java.simpleName
    }
}