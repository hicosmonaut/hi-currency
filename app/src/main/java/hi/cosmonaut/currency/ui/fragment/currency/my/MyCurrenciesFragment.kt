package hi.cosmonaut.currency.ui.fragment.currency.my

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.base.ui.fragment.BaseFragment
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.databinding.FragmentMyCurrenciesBinding
import hi.cosmonaut.currency.ui.activity.main.MainViewModel
import hi.cosmonaut.currency.ui.fragment.home.HomeViewModel


class MyCurrenciesFragment : BaseFragment<FragmentMyCurrenciesBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<MyCurrenciesViewModel>()

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var rvMyCurrencies: RecyclerView
    private lateinit var adapter: MyCurrenciesAdapter

    private lateinit var adapterObserver: Observer<List<Currency>>
    private lateinit var messageObserver: Observer<String>
    private lateinit var messageIdObserver: Observer<Int>
    private lateinit var navigationObserver: Observer<NavDirections>
    private lateinit var navigationHomeObserver: Observer<NavDirections>

    private lateinit var navController: NavController

    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

            val holder = viewHolder as MyCurrenciesAdapter.ViewHolder

            viewModel.deleteFromFavoritesAndFetch(requireContext(), holder.getCurrency())
            getAdapter().notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_my_currencies

    override fun onInit() {
        initViews()
        initComponents()
    }

    override fun onStart() {
        super.onStart()
        startObserve()
    }

    private fun startObserve() {
        viewModel.adapterData.observe(this, adapterObserver)
        viewModel.messageData.observe(this, messageObserver)
        viewModel.messageIdData.observe(this, messageIdObserver)
        viewModel.navigationData.observe(this, navigationObserver)
        viewModel.navigationHomeData.observe(this, navigationHomeObserver)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData(requireContext())
    }

    private fun initViews() {
        rvMyCurrencies = binding.fragmentMyCurrenciesRvCurrencies
    }

    private fun initComponents() {
        initViewModelComponents()
        initNavComponents()
        initAdapter()
        initObservers()
    }

    private fun initViewModelComponents() {
        homeViewModel = mainViewModel.getHomeViewModel()
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
        homeViewModel.navigateToCurrency(action)
    }

    private fun initAdapter() {
        adapter = MyCurrenciesAdapter(viewModel)
        rvMyCurrencies.adapter = adapter

        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(rvMyCurrencies)
    }

    private fun refreshData(data: List<Currency>) = getAdapter().refresh(data)

    private fun initNavComponents() {
        navController = findNavController()
    }

    private fun navigate(action: NavDirections) = navController.navigate(action)

    private fun getAdapter() = (rvMyCurrencies.adapter as MyCurrenciesAdapter)

    companion object{
        private val TAG: String = MyCurrenciesFragment::class.java.simpleName
    }
}