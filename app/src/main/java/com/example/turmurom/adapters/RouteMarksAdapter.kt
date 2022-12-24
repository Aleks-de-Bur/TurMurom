package com.example.turmurom.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Mark
import com.example.turmurom.database.models.MarksWithPhotos
import com.example.turmurom.databinding.MarkOfRouteItemBinding

class RouteMarksAdapter : ListAdapter<MarksWithPhotos, RouteMarksAdapter.Holder>(RouteMarksAdapter.Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MarkOfRouteItemBinding.bind(view)
        fun bind(item: MarksWithPhotos) = with(binding) {

            tvTitle.text = item.mark.title
            tvDescription.text = item.mark.description

            if(item.markPhotos.isEmpty())
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/main_photo"))
            else
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + item.markPhotos.get(0).pathPhoto))
        }
    }

    class Comparator : DiffUtil.ItemCallback<MarksWithPhotos>() {
        override fun areItemsTheSame(oldItem: MarksWithPhotos, newItem: MarksWithPhotos): Boolean {
            return oldItem.mark.id == newItem.mark.id
        }

        override fun areContentsTheSame(oldItem: MarksWithPhotos, newItem: MarksWithPhotos): Boolean {
            return oldItem.mark.id == newItem.mark.id
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