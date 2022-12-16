package com.example.turmurom.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.turmurom.R
import com.example.turmurom.database.models.ExcursionPhoto

//class PhotoVPAdapter(private val carouselDataList: List<ExcursionPhoto>) :
class PhotoVPAdapter(private val carouselDataList: MutableList<String>) :
    RecyclerView.Adapter<PhotoVPAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val ivPhoto = holder.itemView.findViewById<ImageView>(R.id.ivPhoto)
        //val photos : List<ExcursionPhoto> = carouselDataList.
        //ivPhoto.setImageURI(Uri.parse(photos[position].pathPhoto))
        ivPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + carouselDataList[position]))
        //ivPhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + carouselDataList[position].pathPhoto))
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}