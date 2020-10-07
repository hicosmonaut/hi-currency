package hi.cosmonaut.currency.ui.fragment.currency.my

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.databinding.ItemMyCurrencyBinding

class MyCurrenciesAdapter(private val viewModel: MyCurrenciesViewModel): RecyclerView.Adapter<MyCurrenciesAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var currencies: MutableList<Currency> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemMyCurrencyBinding.inflate(inflater)

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

    inner class ViewHolder(private val binding: ItemMyCurrencyBinding): RecyclerView.ViewHolder(binding.root) {

        private lateinit var clRoot: ConstraintLayout
        private lateinit var tvDataCurrency: TextView

        fun bind(position: Int)= with(binding){
            initViews(this)
            initData(position)
        }

        private fun initData(position: Int) {
            val currency = currencies[position]
            tvDataCurrency.text = context.getString(R.string.template_data_currency, currency.name, currency.value)
        }

        private fun initViews(binding: ItemMyCurrencyBinding) {
            tvDataCurrency = binding.itemMyCurrencyTvCurrencyData
            clRoot = binding.itemMyCurrencyClRoot

            clRoot.setOnClickListener {
                handleRootClick()
            }
        }

        fun getCurrency() = currencies[adapterPosition]

        private fun handleRootClick() {
            viewModel.navigateToSelectedCurrency()
        }

    }

    companion object{
        private val TAG: String = MyCurrenciesAdapter::class.java.simpleName
    }

}