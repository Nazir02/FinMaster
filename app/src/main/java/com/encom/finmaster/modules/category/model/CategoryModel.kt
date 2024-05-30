package com.encom.finmaster.modules.category.model

import java.io.Serializable


class CategoryModel(
    var id: Int,
    var id_parent: Int,
    var icon: String,
    var name: String,
    var transaction_type: Int,
    var is_sub_category: Boolean):Serializable