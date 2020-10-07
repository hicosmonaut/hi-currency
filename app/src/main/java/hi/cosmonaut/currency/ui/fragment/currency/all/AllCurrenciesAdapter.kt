package hi.cosmonaut.currency.ui.fragment.currency.all

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.databinding.ItemCurrencyBinding

class AllCurrenciesAdapter(private val viewModel: AllCurrenciesViewModel): RecyclerView.Adapter<AllCurrenciesAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var currencies: MutableList<Currency> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemCurrencyBinding.inflate(inflater)

        binding.root.apply {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            this.layoutParams = layoutParams
        }

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currencies.let{
            holder.bind(position)
        }
    }

    fun refresh(passedList: List<Currency>) {
        this.currencies = passedList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCurrencyBinding): RecyclerView.ViewHolder(binding.root) {

        private lateinit var clRoot: ConstraintLayout
        private lateinit var tvDataCurrency: TextView
        private lateinit var ivButtonFavorite: ImageView

        fun bind(position: Int)= with(binding){
            initViews(this)
            initData(position)
        }

        private fun initData(position: Int) {
            val currency = currencies[position]
            tvDataCurrency.text = context.getString(R.string.template_data_currency, currency.name, currency.value)
            val isFavorite = currency.isFavorite
            binding.isFavorite = isFavorite
            binding.notifyChange()
        }

        private fun initViews(binding: ItemCurrencyBinding) {
            tvDataCurrency = binding.itemCurrencyTvCurrencyData
            ivButtonFavorite = binding.itemCurrencyIvButtonFavorites
            clRoot = binding.itemCurrencyClRoot

            clRoot.setOnClickListener {
                handleRootClick()
            }

            ivButtonFavorite.setOnClickListener {
                handleFavoriteClick()
            }
        }

        private fun handleRootClick() {
            viewModel.navigateToSelectedCurrency()
        }

        private fun handleFavoriteClick() {

            val currency = currencies[adapterPosition]
            val isFavorite = currency.isFavorite ?: false

            currency.isFavorite = !isFavorite

            viewModel.performItemAction(context, currency)


            binding.isFavorite = currency.isFavorite

            notifyItemChanged(adapterPosition)
            binding.notifyChange()
        }
    }

    companion object{
        private val TAG: String = AllCurrenciesAdapter::class.java.simpleName
    }

}