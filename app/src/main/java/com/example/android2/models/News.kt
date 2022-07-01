package com.example.android2.models

import androidx.room.Entity
import java.io.Serializable
@Entity
class News(
    var id:Int,
    var title: String,
    val createdAt: Long

):Serializable
