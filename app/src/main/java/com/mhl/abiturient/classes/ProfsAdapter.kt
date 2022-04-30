package com.mhl.abiturient.classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mhl.abiturient.R

class ProfsAdapter(val fragment : Fragment, val data: ArrayList<Professions>) : RecyclerView.Adapter<ProfsAdapter.VH>() {


    private lateinit var myListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    class VH(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.professionImage)
        var text : TextView = itemView.findViewById(R.id.professionText)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_profs_item, parent, false), myListener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(fragment).load(data[position].image).transform(FitCenter(), RoundedCorners(20)).into(holder.image)
        holder.text.text = data[position].name
    }

    override fun getItemCount(): Int {
        return data.size
    }
}