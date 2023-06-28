package hr.dice.damir_stipancic.weatherapp.ui.daily_details_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.DailyDetails
import hr.dice.damir_stipancic.weatherapp.databinding.DailyDetailsCardItemBinding
import hr.dice.damir_stipancic.weatherapp.databinding.DailyDetailsContentItemBinding
import hr.dice.damir_stipancic.weatherapp.databinding.DailyDetailsHeaderItemBinding

class DailyDetailsRecyclerAdapter :
    ListAdapter<DailyDetails, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.daily_details_header_item ->
                HeaderItemViewHolder(
                    DailyDetailsHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )

            R.layout.daily_details_card_item ->
                CardItemViewHolder(
                    DailyDetailsCardItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )

            else ->
                ContentItemViewHolder(
                    DailyDetailsContentItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DailyDetails.DailyDetailsCard ->
                (holder as CardItemViewHolder).bind(item)

            is DailyDetails.DailyDetailsHeader ->
                (holder as HeaderItemViewHolder).bind(item)

            is DailyDetails.DailyDetailsContent ->
                (holder as ContentItemViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DailyDetails.DailyDetailsCard -> R.layout.daily_details_card_item
            is DailyDetails.DailyDetailsHeader -> R.layout.daily_details_header_item
            is DailyDetails.DailyDetailsContent -> R.layout.daily_details_content_item
        }
    }

    inner class CardItemViewHolder(
        private var binding: DailyDetailsCardItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: DailyDetails.DailyDetailsCard) {
            binding.detailsCardModel = content
            binding.executePendingBindings()
        }
    }

    inner class HeaderItemViewHolder(
        private var binding: DailyDetailsHeaderItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: DailyDetails.DailyDetailsHeader) {
            binding.headerModel = content
            binding.executePendingBindings()
        }
    }

    inner class ContentItemViewHolder(
        private var binding: DailyDetailsContentItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: DailyDetails.DailyDetailsContent) {
            binding.dailyDetails = content
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DailyDetails>() {
        override fun areItemsTheSame(
            oldItem: DailyDetails,
            newItem: DailyDetails
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DailyDetails,
            newItem: DailyDetails
        ): Boolean {
            return if (oldItem is DailyDetails.DailyDetailsCard &&
                newItem is DailyDetails.DailyDetailsCard
            )
                oldItem.date == newItem.date
            else if (oldItem is DailyDetails.DailyDetailsHeader &&
                newItem is DailyDetails.DailyDetailsHeader
            )
                oldItem.titleId == newItem.titleId
            else if (oldItem is DailyDetails.DailyDetailsContent &&
                newItem is DailyDetails.DailyDetailsContent
            )
                oldItem.value == newItem.value
            else true
        }
    }
}
