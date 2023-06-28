package hr.dice.damir_stipancic.weatherapp.ui.daily_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.dice.damir_stipancic.weatherapp.databinding.DailyWeatherPagerItemBinding

class DailyWeatherPagerAdapter(
    private val clickListener: (position: Int) -> Unit
) :
    ListAdapter<DailyWeatherItem, DailyWeatherPagerAdapter.DailyWeatherViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        return DailyWeatherViewHolder(
            DailyWeatherPagerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
        holder.bind(
            day = getItem(position),
        )
    }

    class DailyWeatherViewHolder(
        private var binding: DailyWeatherPagerItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: DailyWeatherItem) {
            binding.day = day
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DailyWeatherItem>() {
        override fun areItemsTheSame(
            oldItem: DailyWeatherItem,
            newItem: DailyWeatherItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DailyWeatherItem,
            newItem: DailyWeatherItem
        ): Boolean {
            return oldItem.date == newItem.date
        }
    }
}
