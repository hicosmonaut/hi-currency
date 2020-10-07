package hi.cosmonaut.currency.ui.fragment.currency.single

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import hi.cosmonaut.base.ui.fragment.BaseFragment
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.databinding.FragmentCurrencyBinding

class SingleCurrencyFragment: BaseFragment<FragmentCurrencyBinding>() {

    private val viewModel by viewModels<SingleCurrencyViewModel>()

    private lateinit var etDataUSD: EditText
    private lateinit var etDataEUR: EditText
    private lateinit var bButtonConvert: Button

    private lateinit var messageObserver: Observer<String>
    private lateinit var messageIdObserver: Observer<Int>
    private lateinit var navigationObserver: Observer<NavDirections>
    private lateinit var conversionObserver: Observer<String>

    private lateinit var navController: NavController

    override fun getLayoutId(): Int = R.layout.fragment_currency

    override fun onInit() {
        initViews()
        initComponents()
        initObservers()
    }

    private fun initObservers() {
        conversionObserver = Observer {
            it?.let{ result -> setData(result)}
        }

        messageObserver = Observer {
            it?.let{ message -> showMessage(message)}
        }

        messageIdObserver = Observer {
            it?.let{ messageId -> showMessage(messageId)}
        }

        navigationObserver = Observer {
            it?.let{ action -> navigate(action)}
        }
    }

    override fun onStart() {
        super.onStart()
        startObserve()
    }

    private fun startObserve() {
        viewModel.messageData.observe(this, messageObserver)
        viewModel.messageIdData.observe(this, messageIdObserver)
        viewModel.navigationData.observe(this, navigationObserver)
        viewModel.conversionData.observe(this, conversionObserver)
    }

    private fun setData(result: String) {
        etDataEUR.setText(result, TextView.BufferType.EDITABLE)
    }

    private fun navigate(action: NavDirections) = navController.navigate(action)

    private fun initComponents() {
        initBackPressedDispatcher()
        initNavComponents()
    }

    private fun initViews() {
        etDataUSD = binding.fragmentCurrencyEtDataUsd
        etDataEUR = binding.fragmentCurrencyEtDataEur
        bButtonConvert = binding.fragmentCurrencyButtonConvert

        bButtonConvert.setOnClickListener {
            handleConvertClick()
        }
    }

    private fun handleConvertClick() {
        viewModel.convert(etDataUSD.text.toString())
    }

    private fun initNavComponents() {
        navController = findNavController()
    }

    private fun initBackPressedDispatcher(){
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { handleBackClick() }
            }
        )
    }

    private fun handleBackClick() = viewModel.navigateToHome()

    companion object{
        private val TAG: String = SingleCurrencyFragment::class.java.simpleName
    }
}