package hi.cosmonaut.currency.ui.activity.main

import androidx.lifecycle.ViewModel
import hi.cosmonaut.currency.ui.fragment.home.HomeViewModel
import hi.cosmonaut.currency.ui.fragment.host.HostViewModel
import java.lang.Exception

class MainViewModel : ViewModel() {

    private lateinit var homeViewModel: HomeViewModel

    fun setHomeViewModel(viewModel: HomeViewModel) {
        homeViewModel = viewModel
    }

    fun getHomeViewModel(): HomeViewModel {
        if (::homeViewModel.isInitialized) {
            return homeViewModel
        } else {
            throw Exception("You should initialize HomeViewModel first!")
        }
    }

}