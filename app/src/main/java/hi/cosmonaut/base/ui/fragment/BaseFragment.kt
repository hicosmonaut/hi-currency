package hi.cosmonaut.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<BINDING : ViewDataBinding> : Fragment() {

    protected lateinit var binding: BINDING

    //-----------------------------------BASE FRAGMENT LIFECYCLE------------------------------------
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        onInit()
    }

    protected fun showMessage(message: String){
        requireActivity().let{ activity ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun showMessage(messageId: Int){
        requireActivity().let{ activity ->
            Toast.makeText(activity, messageId, Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun onInit()

}