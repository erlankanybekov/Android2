package com.example.android2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
class News(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title: String,
    val createdAt: Long

):Serializable{
    constructor():this(0,"",0L)
}
