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
import com.example.turmurom.database.models.ExcursionWithPhoto
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.ExcursionListItemBinding

class ExcursionAdapter(val excursionListener: ExcursionListener) :
    ListAdapter<ExcursionWithPhoto, ExcursionAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ExcursionListItemBinding.bind(view)
        fun bind(excursion: ExcursionWithPhoto, excursionListener: ExcursionListener) =
            with(binding) {
                tvTitle.text = excursion.excursion.name
                tvDuration.text = excursion.excursion.duration
                tvPrice.text = excursion.excursion.price

                if(excursion.excursionPhotos.isEmpty())
                    imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/wow_photo"))
                else
                    imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + excursion.excursionPhotos.get(0).pathPhoto))

                //Слушатель нажатия
                itemView.setOnClickListener {
                    excursionListener.onClick(excursion.excursion)
                }
            }
    }

    class Comparator : DiffUtil.ItemCallback<ExcursionWithPhoto>() {
        override fun areItemsTheSame(oldItem: ExcursionWithPhoto, newItem: ExcursionWithPhoto): Boolean {
            return oldItem.excursion.id == newItem.excursion.id
        }

        override fun areContentsTheSame(oldItem: ExcursionWithPhoto, newItem: ExcursionWithPhoto): Boolean {
            return oldItem.excursion.id == newItem.excursion.id
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