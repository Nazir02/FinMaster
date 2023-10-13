package com.encom.finmaster.modules.category.model

import java.io.Serializable

/**
 * Created by ABDUAHAD FAIZULLOEV on 17,октябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class CategoryModel(
    var id: Int,
    var id_parent: Int,
    var icon: String,
    var name: String,
    var transaction_type: Int,
    var is_sub_category: Boolean):Serializable