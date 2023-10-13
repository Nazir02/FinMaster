package com.encom.finmaster.modules.accounts.accountBottomSheet.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.repository.sqlite.model.AccountModel
import java.io.InputStream

class AccountBottomSheetDialogAdapter(
    private val context: Context,
    private val myDataset: ArrayList<AccountModel>,
    private val onClickListener: View.OnClickListener,
) : RecyclerView.Adapter<AccountBottomSheetDialogAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_select_account, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: AccountModel = myDataset.get(position)
        holder.textViewTitle.text = data.name
        data.card_number?.let {
            if (it.length > 4) holder.textViewBody.text =
                "*" + getLastNsimbolsFromText(4, it)
        }

        holder.textViewSum.text = data.balance.toString() + " " + data.currency
        holder.itemView.setOnClickListener(onClickListener)
        holder.itemView.tag = myDataset.get(position)
        try {

            val inputStream: InputStream = context.assets.open("icons/" + data.icon)
            val image = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(image)
        } catch (ex: Exception){

        }
    }

    private fun getLastNsimbolsFromText(n: Int, text: String): String {
        return text.substring(text.length - n, text.length)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewBody: TextView
        val textViewSum: TextView
        val imageView: ImageView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle) as TextView
            textViewBody = itemView.findViewById(R.id.textViewBody) as TextView
            textViewSum = itemView.findViewById(R.id.textViewSum) as TextView
            imageView = itemView.findViewById(R.id.imageView) as ImageView
        }
    }
}