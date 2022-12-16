package com.example.turmurom.adapters

import android.content.Context
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.database.models.Guide
import com.example.turmurom.databinding.GuideListItemBinding

class GuideAdapter(val guideListener: GuideAdapter.GuideListener) :
    ListAdapter<Guide, GuideAdapter.Holder>(Comparator()) {

    //Заполнение разметки
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = GuideListItemBinding.bind(view)
        fun bind(guide: Guide, guideListener: GuideAdapter.GuideListener) =
            with(binding) {
                tvFIO.text = guide.lastName + " " + guide.firstName + " " + guide.patronymic
                binding.tvTelNumber.text = guide.telNumber
                binding.tvEmail.text = guide.eMail

                binding.ivGuidePhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + guide.pathPhoto))
                //val uri: Uri = Uri.parse(imageUtil)
                //ivGuidePhoto.setImageDrawable(asse)
                //var image: Image =
                //ivGuidePhoto.setImageURI(Uri.parse("E:/Course/TurMurom/app/src/main/assets/photos/main_photo.jpg"))
                //Слушатель нажатия
                itemView.setOnClickListener {
                    guideListener.onClick(guide)
                }
            }
    }

    class Comparator : DiffUtil.ItemCallback<Guide>() {
        override fun areItemsTheSame(oldItem: Guide, newItem: Guide): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Guide, newItem: Guide): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.guide_list_item, parent, false)
        return GuideAdapter.Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), guideListener)
    }

    interface GuideListener {
        fun onClick(guide: Guide) {
            Navigation.createNavigateOnClickListener(R.id.guideDetailsFragment, null)
        }
    }
}