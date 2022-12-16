package com.example.turmurom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.MarkOfRouteItemBinding

class RouteMarksAdapter : ListAdapter<Mark, RouteMarksAdapter.Holder>(RouteMarksAdapter.Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MarkOfRouteItemBinding.bind(view)
        fun bind(item: Mark) = with(binding) {

            tvTitle.text = item.title
            tvDescription.text = item.description

            //imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" )))
        }
    }

    class Comparator : DiffUtil.ItemCallback<Mark>() {
        override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteMarksAdapter.Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mark_of_route_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: RouteMarksAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }
}