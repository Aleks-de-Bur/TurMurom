package com.example.turmurom.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.MarkListItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogAdapter(val catalogListener: CatalogListener) :
    ListAdapter<Mark, CatalogAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MarkListItemBinding.bind(view)
        fun bind(item: Mark, catalogListener: CatalogListener) = with(binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description

            //tvCategory.text = "${item.category[0]}${item.category[1]}${item.category[2]}"
            tvCategory.text = "${catalogListener.getCategory(item.categoryId)[0]}" +
                    "${catalogListener.getCategory(item.categoryId)[1]}" +
                    "${catalogListener.getCategory(item.categoryId)[2]}"

            //imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + catalogListener.getPhoto(item)))

            itemView.setOnClickListener {
                catalogListener.onClick(item)
            }
            binding.btnInfo.setOnClickListener {
                catalogListener.onClick(item)
            }
            binding.btnSchedule.setOnClickListener {
                catalogListener.onClickSchedule(item)
            }
//            binding.btnGo.setOnClickListener {
//                catalogListener.onClickSearch()
//            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mark_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), catalogListener)
    }

    interface CatalogListener {
        fun onClick(mark: Mark) {
            Navigation.createNavigateOnClickListener(R.id.catalogItemFragment, null)
        }

        fun onClickSchedule(mark: Mark) {

        }
        fun onClickSearch() {

        }

        fun getPhoto(mark: Mark) : String {
            return "1"
        }

        fun getCategory(id: Int) : String {
            return "Категория"
        }
    }
}