package com.encom.finmaster.modules.category.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.category.view.view.CategoriesFragment

import com.encom.finmaster.modules.main.history.view.HistoryFragment
import java.io.InputStream
import java.util.*

class CategoryAdapter(
    val context: Context,
    private val dataSet: List<CategoryModel>,
    private val onClickListener: View.OnClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CATEGORY_TYPE) {
            CategoryHeadViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category_head, parent, false)
            )
        } else {
            CategoryBodyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category_body, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        if (data.is_sub_category) {
            bindCategoryBody(holder as CategoryBodyViewHolder, data)
        } else {
            bindCategoryHead(holder as CategoryHeadViewHolder, data)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].is_sub_category)
            SUBCATEGORY_TYPE
        else
            CATEGORY_TYPE
    }

    private fun bindCategoryHead(holder: CategoryHeadViewHolder, date: CategoryModel) {
        holder.textViewNameCategory.text = date.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.itemView.tag = date
        holder.itemView.setOnClickListener(onClickListener)
        try {
            val inputStream: InputStream = context.assets.open("icons/" + date.icon)
            val image = Drawable.createFromStream(inputStream, null)
            holder.imageViewIconCategory.setImageDrawable(image)
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e(HistoryFragment::class.java.simpleName, it)
            }
        }
    }

    private fun bindCategoryBody(holder: CategoryBodyViewHolder, data: CategoryModel) {
        holder.textViewNameSubCategory.text = data.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.itemView.tag = data
        holder.itemView.setOnClickListener(onClickListener)
        try {
            val inputStream: InputStream = context.assets.open("icons/" + data.icon)
            val image = Drawable.createFromStream(inputStream, null)
            holder.imageViewIconSubCategory.setImageDrawable(image)
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e(CategoriesFragment::class.java.simpleName, it)
            }
        }
    }

    class CategoryBodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewNameSubCategory: TextView = itemView.findViewById(R.id.textViewNameSubCategory) as TextView
        var imageViewIconSubCategory: ImageView = itemView.findViewById(R.id.imageViewIconSubCategory) as ImageView
    }

    class CategoryHeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewNameCategory: TextView = itemView.findViewById(R.id.textViewNameCategory) as TextView
        var imageViewIconCategory: ImageView = itemView.findViewById(R.id.imageViewIconCategory)
    }

    companion object {
        const val CATEGORY_TYPE = 0
        const val SUBCATEGORY_TYPE = 1
    }
}