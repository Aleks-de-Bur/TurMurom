package com.example.turmurom.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Category
import com.example.turmurom.databinding.CategoryListItemBinding

class CategoryFilterAdapter(val categoryFilterListener: CategoryFilterListener) :
    ListAdapter<Category, CategoryFilterAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CategoryListItemBinding.bind(view)
        fun bind(item: Category, categoryFilterListener: CategoryFilterListener) = with(binding) {
            tvCategory.text = item.title

            categoryFilterListener.coloringCategoryItems(item, binding)

            itemView.setOnClickListener {
                categoryFilterListener.onClick(item, binding)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), categoryFilterListener)
    }

    interface CategoryFilterListener {
        @SuppressLint("ResourceAsColor")
        fun onClick(category: Category, binding: CategoryListItemBinding) {

        }

        fun coloringCategoryItems(category: Category, binding: CategoryListItemBinding){

        }
    }
}