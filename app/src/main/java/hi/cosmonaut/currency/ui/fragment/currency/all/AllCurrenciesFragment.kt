package hi.cosmonaut.currency.ui.fragment.currency.all

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.base.ui.fragment.BaseFragment
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.databinding.FragmentAllCurrenciesBinding
import hi.cosmonaut.currency.ui.activity.main.MainViewModel
import hi.cosmonaut.currency.ui.fragment.currency.my.MyCurrenciesFragment
import hi.cosmonaut.currency.ui.fragment.home.HomeViewModel

class AllCurrenciesFragment: BaseFragment<FragmentAllCurrenciesBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<AllCurrenciesViewModel>()

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var rvAllCurrencies: RecyclerView

    private lateinit var adapter: AllCurrenciesAdapter

    private lateinit var navController: NavController

    private lateinit var adapterObserver: Observer<List<Currency>>
    private lateinit var messageObserver: Observer<String>
    private lateinit var messageIdObserver: Observer<Int>
    private lateinit var navigationObserver: Observer<NavDirections>
    private lateinit var navigationHomeObserver: Observer<NavDirections>

    override fun getLayoutId(): Int = R.layout.fragment_all_currencies

    override fun onInit() {
        initViews()
        initComponents()
    }

    override fun onStart() {
        super.onStart()
        startObserve()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData(requireContext())
    }

    private fun initViews() {
        rvAllCurrencies = binding.fragmentAllCurrenciesRvCurrencies
    }

    private fun initComponents() {
        initViewModelComponents()
        initNavComponents()
        initAdapter()
        initObservers()
    }

    private fun initNavComponents() {
        navController = findNavController()
    }

    private fun initViewModelComponents() {
        homeViewModel = mainViewModel.getHomeViewModel()
    }

    private fun startObserve() {
        viewModel.adapterData.observe(this, adapterObserver)
        viewModel.messageData.observe(this, messageObserver)
        viewModel.messageIdData.observe(this, messageIdObserver)
        viewModel.navigationData.observe(this, navigationObserver)
        viewModel.navigationHomeData.observe(this, navigationHomeObserver)
    }

    private fun initObservers() {
        adapterObserver = Observer {
            it?.let{ data -> refreshData(data)}
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

        navigationHomeObserver = Observer {
            it?.let{ action -> navigateHome(action)}
        }
    }

    private fun navigateHome(action: NavDirections) {
        homeViewModel.navigate(action)
    }

    private fun refreshData(data: List<Currency>) = getAdapter().refresh(data)
    private fun navigate(action: NavDirections) = navController.navigate(action)

    private fun initAdapter() {
        adapter = AllCurrenciesAdapter(viewModel)
        rvAllCurrencies.adapter = adapter
    }

    private fun getAdapter() = (rvAllCurrencies.adapter as AllCurrenciesAdapter)

    companion object{
        private val TAG: String = AllCurrenciesFragment::class.java.simpleName
    }
}