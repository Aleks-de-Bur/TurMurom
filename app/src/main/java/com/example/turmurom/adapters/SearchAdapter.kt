package com.example.turmurom.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.database.models.Mark
import kotlin.collections.ArrayList

class SearchAdapter //: RecyclerView.Adapter<SearchAdapter.DataViewHolder>(), Filterable {
{
    //var marksList: ArrayList<Mark> = ArrayList()
    //var marksListFiltered: ArrayList<Mark> = ArrayList()

    //var onItemClick: ((Mark) -> Unit)? = null

    //inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        init {
//            itemView.setOnClickListener {
//                onItemClick?.invoke(marksListFiltered[adapterPosition])
//            }
//        }

//        fun bind(result: Mark) {
//            itemView.name.text = result.id +" "+ result.author
//            Glide.with(itemView.imageView.context).load(result.downloadUrl).into(itemView.imageView)
//        }
    //}

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
//        LayoutInflater.from(parent.context).inflate(
//            R.layout.item_row_photos, parent,
//            false
//        )
//    )
//
//    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
//        holder.bind(marksListFiltered[position])
//    }
//
//    override fun getItemCount(): Int = marksListFiltered.size
//
//    fun addData(list: List<Mark>) {
//        marksList = list as ArrayList<Mark>
//        marksListFiltered = marksList
//        notifyDataSetChanged()
//    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint?.toString() ?: ""
//                if (charString.isEmpty()) marksListFiltered = marksList else {
//                    val filteredList = ArrayList<Mark>()
//                    marksList
//                        .filter {
//                            (it.id.contains(constraint!!)) or
//                                    (it.author.contains(constraint))
//
//                        }
//                        .forEach { filteredList.add(it) }
//                    marksListFiltered = filteredList
//
//                }
//                return FilterResults().apply { values = marksListFiltered }
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//
//                marksListFiltered = if (results?.values == null)
//                    ArrayList()
//                else
//                    results.values as ArrayList<Mark>
//                notifyDataSetChanged()
//            }
//        }
//    }
}