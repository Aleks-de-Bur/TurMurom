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
import com.example.turmurom.database.models.ElectedMarks
import com.example.turmurom.database.models.UserElected
import com.example.turmurom.databinding.MarkListItemBinding

class ElectedMarksAdapter(val catalogListener: CatalogListener) :
    ListAdapter<ElectedMarks, ElectedMarksAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MarkListItemBinding.bind(view)
        fun bind(item: ElectedMarks, catalogListener: CatalogListener) = with(binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description

            binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elected_35"))

            //tvCategory.text = "${item.category[0]}${item.category[1]}${item.category[2]}"
            tvCategory.text = "${catalogListener.getCategory(item.categoryId)[0]}" +
                    "${catalogListener.getCategory(item.categoryId)[1]}" +
                    "${catalogListener.getCategory(item.categoryId)[2]}"

            if(item.photo.isEmpty())
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/main_photo"))
            else
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + item.photo))

            itemView.setOnClickListener {
                catalogListener.onClick(item)
            }
            binding.btnInfo.setOnClickListener {
                catalogListener.onClick(item)
            }
            binding.btnSchedule.setOnClickListener {
                catalogListener.onClickSchedule(item)
            }
            binding.ibElect.setOnClickListener {
                itemView.visibility = View.INVISIBLE
                catalogListener.onClickElect(item.id)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<ElectedMarks>() {
        override fun areItemsTheSame(oldItem: ElectedMarks, newItem: ElectedMarks): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ElectedMarks, newItem: ElectedMarks): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mark_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), catalogListener)
    }

    interface CatalogListener {
        fun onClick(electedMark: ElectedMarks) {
            //Navigation.createNavigateOnClickListener(R.id.catalogItemFragment, null)
        }

        fun onClickSchedule(electedMark: ElectedMarks) {

        }

        fun onClickElect(markId: Int) {

        }

        fun getCategory(id: Int) : String {
            return "Категория"
        }
    }
}