package com.example.turmurom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Schedule
import com.example.turmurom.databinding.ScheduleListItemBinding

class ScheduleAdapter : ListAdapter<Schedule, ScheduleAdapter.Holder>(ScheduleAdapter.Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ScheduleListItemBinding.bind(view)
        fun bind(item: Schedule) = with(binding) {
            when (item.day) {
                1 -> tvDay.text = "Понедельник"
                2 -> tvDay.text = "Вторник"
                3 -> tvDay.text = "Среда"
                4 -> tvDay.text = "Четверг"
                5 -> tvDay.text = "Пятница"
                6 -> tvDay.text = "Суббота"
                7 -> tvDay.text = "Воскресенье"
                else -> tvDay.text = "Неопознанный день"
            }
            tvSch.text = item.start + " - " + item.end
        }
    }

    class Comparator : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }
}