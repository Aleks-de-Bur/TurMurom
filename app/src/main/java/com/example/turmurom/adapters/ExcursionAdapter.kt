package com.example.turmurom.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.ExcursionListItemBinding

class ExcursionAdapter(val excursionListener: ExcursionAdapter.ExcursionListener) :
    ListAdapter<Excursion, ExcursionAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ExcursionListItemBinding.bind(view)
        fun bind(excursion: Excursion, excursionListener: ExcursionAdapter.ExcursionListener) =
            with(binding) {
                tvTitle.text = excursion.name
                tvDuration.text = excursion.duration
                tvPrice.text = excursion.price

                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + excursionListener.getPhoto(excursion)))

                //Слушатель нажатия
                itemView.setOnClickListener {
                    excursionListener.onClick(excursion)
                }
            }
    }

    class Comparator : DiffUtil.ItemCallback<Excursion>() {
        override fun areItemsTheSame(oldItem: Excursion, newItem: Excursion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Excursion, newItem: Excursion): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.excursion_list_item, parent, false)
        return ExcursionAdapter.Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), excursionListener)
    }

    interface ExcursionListener {
        fun onClick(excursion: Excursion) {
            Navigation.createNavigateOnClickListener(R.id.excursionDetailsFragment, null)
        }

        fun getPhoto(excursion: Excursion) : String {
            return "main_photo"
        }
    }
}