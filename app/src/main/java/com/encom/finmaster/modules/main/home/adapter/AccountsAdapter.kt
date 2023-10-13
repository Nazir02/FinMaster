package com.encom.finmaster.modules.main.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.home.model.AddAccountModel
import com.encom.finmaster.modules.main.home.utils.ICardAdapter
import com.encom.finmaster.repository.sqlite.model.AccountModel
import java.util.ArrayList

class AccountsAdapter(val context: Context, val myDataSet: ArrayList<Any>, val onClickListener: View.OnClickListener) : PagerAdapter(), ICardAdapter {
    var layoutInflater: LayoutInflater
    var mViews: ArrayList<View?> = ArrayList()

    init {
        layoutInflater = LayoutInflater.from(context)
        for (item in myDataSet) {
            mViews.add(null)
        }
    }

    override fun getCount(): Int {
        return myDataSet.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (myDataSet[position] is AccountModel) {
            val accountsModel = myDataSet.get(position) as AccountModel
            if (accountsModel.card_type_code != "wallet") {
                val view = layoutInflater.inflate(R.layout.item_card, container, false)
                bindCard(view, accountsModel)
                container.addView(view)
                mViews[position] = view
                return view
            } else {
                val view = layoutInflater.inflate(R.layout.item_wallet, container, false)
                bindWallet(view, accountsModel)
                container.addView(view)
                view.setOnClickListener(onClickListener)
                mViews[position] = view
                return view
            }
        } else {
            val view = layoutInflater.inflate(R.layout.item_add_card_or_wallet, container, false)
            mViews[position] = view
            container.addView(view)
            view.setOnClickListener(onClickListener)
            view.setTag(myDataSet.get(position))
            return view
        }
    }

    private fun bindWallet(view: View, accountModel: AccountModel): View {
        try {
            val id = context.resources.getIdentifier(accountModel.image, "drawable", context.packageName)
            view.apply {
                findViewById<TextView>(R.id.textViewWalletBalance).text = accountModel.balance.toString() + " " + accountModel.currency
                findViewById<TextView>(R.id.textViewWalletName).text = accountModel.name
                findViewById<ImageView>(R.id.imageViewWalletBg).setImageResource(id)
                setOnClickListener(onClickListener)
                setTag(accountModel)
                val icon = Drawable.createFromStream(context.assets.open("icons/" + accountModel.icon), null)
                findViewById<ImageView>(R.id.imageViewWalletLogo).setImageDrawable(icon)
            }
        }catch (ex:Exception){

        }
        return view
    }

    private fun bindCard(view: View, accountModel: AccountModel): View {
        val icon = Drawable.createFromStream(context.assets.open("icons/" + accountModel.icon), null)
        val id = context.resources.getIdentifier(accountModel.image, "drawable", context.packageName)
        view.apply {
            findViewById<TextView>(R.id.textViewCardHolder).text = accountModel.card_holder_name
            findViewById<TextView>(R.id.textViewCardNumbers).text = accountModel.card_number
            findViewById<TextView>(R.id.textViewCardBalance).text = accountModel.balance.toString() + " " + accountModel.currency
            findViewById<TextView>(R.id.textViewCardName).text = accountModel.name
            findViewById<ImageView>(R.id.imageViewCardLogo).setImageDrawable(icon)
            findViewById<ImageView>(R.id.imageViewCardBg).setImageResource(id)
            setOnClickListener(onClickListener)
            setTag(accountModel)
        }
        return view
    }

    // From ICardAdapter
    override fun getViewAt(position: Int): View? {
        return mViews[position]
    }

    override fun getCardsCount(): Int {
        return getCount()
    }
}