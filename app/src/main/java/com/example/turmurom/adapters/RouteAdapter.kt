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
import com.example.turmurom.database.models.Route
import com.example.turmurom.databinding.RouteListItemBinding

//class RouteAdapter : ListAdapter<Route, RouteAdapter.ItemHolder>(ItemComparator()) {
class RouteAdapter(val routeListener: RouteListener) :
    ListAdapter<Route, RouteAdapter.Holder>(RouteAdapter.Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view){
        val binding = RouteListItemBinding.bind(view)
        fun bind(item: Route, routeListener: RouteListener) = with(binding){
            tvTitle.text = item.name
            tvDuration.text = item.duration
            imPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + item.pathPhoto))

            itemView.setOnClickListener {
                routeListener.onClick(item)
            }
            btnGo.setOnClickListener {
                routeListener.onGo(item)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Route>(){
        override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.route_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), routeListener)
    }

    interface RouteListener {
        fun onClick(route: Route) {
            Navigation.createNavigateOnClickListener(R.id.routeDetailsFragment, null)
        }

        fun onGo(route: Route) {
            //Navigation.createNavigateOnClickListener(R.id.mapMenuItem, null)
        }
    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
//        return ItemHolder.create(parent)
//    }
//
//    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
//        holder.setData(getItem(position))
//    }
//
//    class ItemHolder(view: View) : RecyclerView.ViewHolder(view){
//        val binding = RouteListItemBinding.bind(view)
//        fun setData(route: Route) = with(binding){
//            tvTitle.text = route.name
//            tvDuration.text = route.duration
//        }
//
//        companion object{
//            fun create(parent: ViewGroup): ItemHolder{
//
//                return ItemHolder(
//                    LayoutInflater.from(parent.context).
//                    inflate(R.layout.excursion_list_item, parent, false))
//            }
//        }
//    }
//
//    class ItemComparator : DiffUtil.ItemCallback<Route>(){
//        override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
//            return oldItem == newItem
//        }
//
//    }
}