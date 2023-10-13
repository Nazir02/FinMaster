package com.encom.finmaster.modules.main.settings.profile.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.main.profile.view.ProfileFragment
import java.io.InputStream


class ProfileAdapter(
    val myDataSet: ArrayList<ProfileModel>, val onClickListener: ProfileFragment,
    val context: Context
) : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var textViewTitle: TextView
        init {
            imageView = view.findViewById(R.id.imageViewIconProfile)
            textViewTitle = view.findViewById(R.id.textViewTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemData = myDataSet[position]
        holder.textViewTitle.text = itemData.name
        holder.itemView.setOnClickListener(onClickListener)
        holder.itemView.tag = itemData
        holder.imageView.setImageResource(itemData.icon)
    }
}