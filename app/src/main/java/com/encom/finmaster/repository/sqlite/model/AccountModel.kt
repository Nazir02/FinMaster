package com.encom.finmaster.repository.sqlite.model

class AccountModel(
    var id: Int,
    var icon: String,
    var currency: String,
    var name: String,
    var card_holder_name: String?,
    var card_number: String?,
    var balance: Float,
    var card_type_code: String,
    var image: String,
    var is_dark: Boolean,
    var is_default: Boolean
)