package com.encom.finmaster.modules.main.history.model


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