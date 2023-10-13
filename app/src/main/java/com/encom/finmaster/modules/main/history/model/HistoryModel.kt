package com.encom.finmaster.modules.main.history.model

/**
 * Created by ABDUAHAD FAIZULLOEV on 17,октябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class HistoryModel(
    var id: Int,
    var icon: String,
    var category: String,
    var note: String? = null,
    var amount: Float,
    var currency: String,
    var date:String,
    var type: Int,
    var isGray: Boolean
) {
}