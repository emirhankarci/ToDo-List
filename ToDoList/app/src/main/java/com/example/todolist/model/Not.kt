package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Not(

    @ColumnInfo(name = "yazi")
    val yazi: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
