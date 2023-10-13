package com.encom.finmaster.modules.transaction.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.encom.finmaster.R

class ButtomShitDialogFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.bottom_shit_dialog, container, false)

        return view
    }


}