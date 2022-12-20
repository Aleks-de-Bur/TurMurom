package com.example.turmurom.adapters

import android.graphics.drawable.Drawable
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
import com.example.turmurom.database.models.MarksWithPhotos
import com.example.turmurom.databinding.MarkListItemBinding

class CatalogAdapter(val catalogListener: CatalogListener) :
    ListAdapter<MarksWithPhotos, CatalogAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MarkListItemBinding.bind(view)
        fun bind(item: MarksWithPhotos, catalogListener: CatalogListener) = with(binding) {
            tvTitle.text = item.mark.title
            tvDescription.text = item.mark.description

            //catalogListener.onClickElect(binding)

            //tvCategory.text = "${item.category[0]}${item.category[1]}${item.category[2]}"
            tvCategory.text = "${catalogListener.getCategory(item.mark.categoryId)[0]}" +
                    "${catalogListener.getCategory(item.mark.categoryId)[1]}" +
                    "${catalogListener.getCategory(item.mark.categoryId)[2]}"

            if(item.mark.elected)
                binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elected_35"))
            else
                binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elect_35"))

            if(item.markPhotos.isEmpty())
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/main_photo"))
            else
                imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + item.markPhotos.get(0).pathPhoto))

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
                catalogListener.onClickElect(item, binding)
            }
//            binding.btnGo.setOnClickListener {
//                catalogListener.onClickSearch()
//            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mark_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), catalogListener)
    }

    interface CatalogListener {
        fun onClick(marksWithPhotos: MarksWithPhotos) {
            Navigation.createNavigateOnClickListener(R.id.catalogItemFragment, null)
        }

        fun onClickSchedule(marksWithPhotos: MarksWithPhotos) {

        }
        fun onClickSearch() {

        }

        fun onClickElect(marksWithPhotos: MarksWithPhotos, binding : MarkListItemBinding) {

        }

//        fun getPhoto(mark: MarksWithPhotos) : String {
//            return "1"
//        }

        fun getCategory(id: Int) : String {
            return "Категория"
        }
    }
}