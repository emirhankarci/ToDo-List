package com.example.todolist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.model.Not

@Database(entities = [Not::class], version = 1)
abstract class NotDatabase : RoomDatabase() {
    abstract fun notDao(): NotDAO
}
