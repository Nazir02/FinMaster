package com.encom.finmaster.modules.main.history.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
import com.encom.finmaster.modules.main.history.view.HistoryFragment
import com.encom.finmaster.utils.TransactionTypes
import java.io.InputStream


class HistoryAdapter(
    val context: Context,
    private val dataSet: List<Any>,
    private val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEAD_TYPE) {
            return HistoryHeadViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_history_head, parent, false)
            )
        } else {
            return HistoryBodyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet.get(position)
        if (data is HistoryModel) {
            bindHistoryBody(holder as HistoryBodyViewHolder, data)
        } else if (data is String) {
            bindHistoryHead(holder as HistoryHeadViewHolder, data)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet.get(position) is HistoryModel) return BODY_TYPE
        else return HEAD_TYPE
    }

    private fun bindHistoryHead(holder: HistoryHeadViewHolder, date: String) {
        holder.textView.text = date
    }

    @SuppressLint("SetTextI18n")
    private fun bindHistoryBody(holder: HistoryBodyViewHolder, data: HistoryModel) {
        holder.textViewTitle.text = data.category
        data.note?.let {
            holder.textViewSubTitle.text = it
            holder.textViewSubTitle.visibility = View.VISIBLE
        }
       /* if (data.isGray) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteGray))
            //We shouldn't use else for white, 'cause if we will realize dark theme, it will be white!!!
            //or we can use ColorPrimary which changes while changing theme of UI
        }*/
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
                Log.e(HistoryFragment::class.java.simpleName, it)
            }
        }
    }

    class HistoryBodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle) as TextView
        var textViewSubTitle: TextView = itemView.findViewById(R.id.textViewSubTitle) as TextView
        var textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount) as TextView
        var imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon) as ImageView
    }

    class HistoryHeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView) as TextView
    }

    companion object {
        const val HEAD_TYPE = 0
        const val BODY_TYPE = 1
    }

}