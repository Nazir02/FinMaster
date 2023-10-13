package com.encom.finmaster.modules.accounts.listOfAccounts.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.accounts.listOfAccounts.view.AccountFragment
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.main.profile.view.ProfileFragment
import com.encom.finmaster.modules.main.settings.profile.view.adapter.ProfileAdapter
import com.encom.finmaster.repository.sqlite.model.AccountModel
import java.io.InputStream

class AccountAdapter(
    val myDataSet: ArrayList<AccountModel>, val onClickListener: AccountFragment,
    val context: Context
) : RecyclerView.Adapter<AccountAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var textViewCard: TextView
        var textViewSum: TextView
        var imageView: ImageView
        var textViewTitle: TextView

        init {
            textViewCard = view.findViewById(R.id.textViewBody)
            textViewSum = itemView.findViewById(R.id.textViewSum)
            imageView = view.findViewById(R.id.imageViewIconAccount)
            textViewTitle = view.findViewById(R.id.textViewTitleAccount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_account, null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val itemData = myDataSet[position]
            holder.textViewTitle.text = itemData.name
            holder.itemView.setOnClickListener(onClickListener)
            holder.itemView.tag = itemData
            holder.textViewSum.text = itemData.balance.toString() + " " + itemData.currency
            holder.textViewCard.text = itemData.card_number
            val inputStream: InputStream = context.assets.open("icons/" + itemData.icon)
            val image = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(image)
        } catch (ex: Exception) {

        }
    }
}