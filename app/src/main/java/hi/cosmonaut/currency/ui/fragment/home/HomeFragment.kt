package hi.cosmonaut.currency.ui.fragment.home

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import hi.cosmonaut.base.ui.fragment.BaseFragment
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.databinding.FragmentHomeBinding
import hi.cosmonaut.currency.ui.activity.main.MainViewModel
import hi.cosmonaut.currency.ui.fragment.host.HostViewModel

class HomeFragment:BaseFragment<FragmentHomeBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var navHomeHost: View
    private lateinit var navControllerHome: NavController
    private lateinit var navControllerHost: NavController
    private lateinit var navHomeGraph: NavGraph

    private lateinit var messageObserver: Observer<String>
    private lateinit var messageIdObserver: Observer<Int>
    private lateinit var navigationObserver: Observer<NavDirections>

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onInit() {
        initViews()
        initComponents()
    }

    override fun onStart() {
        super.onStart()
        startObserve()
    }


    private fun startObserve() {
        viewModel.messageData.observe(this, messageObserver)
        viewModel.messageIdData.observe(this, messageIdObserver)
        viewModel.navigationData.observe(this, navigationObserver)
    }

    private fun initViews() {
        bottomNavigationView = binding.fragmentHomeBnNavMenu

    }

    private fun initComponents() {
        initViewModelComponents()
        initNavComponents()
        initObservers()
    }

    private fun initObservers() {
        messageObserver = Observer {
            it?.let{ message -> showMessage(message)}
        }

        messageIdObserver = Observer {
            it?.let{ messageId -> showMessage(messageId)}
        }

        navigationObserver = Observer {
            it?.let{ action ->
                navigate(action)
            }
        }
    }

    private fun initNavComponents() {
        navControllerHost = findNavController()

        navHomeHost = binding.root.findViewById(R.id.fragment_home_nav_host)

        navControllerHome = Navigation.findNavController(navHomeHost)
        navHomeGraph = navControllerHome.navInflater.inflate(R.navigation.graph_bottom)

        bottomNavigationView.setupWithNavController(navControllerHome)
    }

    private fun initViewModelComponents() {
        mainViewModel.setHomeViewModel(viewModel)
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    companion object{
        private val TAG: String = HomeFragment::class.java.simpleName
    }
}