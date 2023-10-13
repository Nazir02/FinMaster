package com.encom.finmaster.modules.main.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.history.model.HistoryModel
import com.encom.finmaster.modules.main.home.view.HomeHistoryFragment
import com.encom.finmaster.utils.TransactionTypes
import java.io.InputStream

class HomeHistoryAdapter(
    val context: Context,
    private val dataSet: List<HistoryModel>,
    private val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryBodyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val data = dataSet.get(position)
        bindHistoryBody(holder as HistoryBodyViewHolder, data)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun bindHistoryBody(holder: HistoryBodyViewHolder, data: HistoryModel) {
        holder.textViewTitle.text = data.category
        holder.textViewSubTitle.text = data.note
        holder.textViewDate.text = data.date
        if (data.isGray) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteGray))
            //We shouldn't use else for white, 'cause if we will realize dark theme, it will be white!!!
            //or we can use ColorPrimary which changes while changing theme of UI
        }
        when (data.type) {
            TransactionTypes.RASKHOD, TransactionTypes.CREDIT -> {
                holder.textViewAmount.text = "- " + data.amount + " " + data.currency
                holder.textViewAmount.setTextColor(context.resources.getColor(R.color.colorExpend))
            }

            TransactionTypes.DOKHOD, TransactionTypes.DOLG -> {
                holder.textViewAmount.text = "+ " + data.amount + " " + data.currency
                holder.textViewAmount.setTextColor(context.resources.getColor(R.color.colorIncome))
            }
        }
        try {
            val inputStream: InputStream = context.getAssets().open("icons/" + data.icon)
            val image = Drawable.createFromStream(inputStream, null)
            holder.imageViewIcon.setImageDrawable(image)
            holder.itemView.tag = data
            holder.itemView.setOnClickListener(onClickListener)
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e(HomeHistoryFragment::class.java.simpleName, it)
            }
        }
    }

    class HistoryBodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewSubTitle: TextView = itemView.findViewById(R.id.textViewSubTitle)
        var textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        var textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        var imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)
    }
}

