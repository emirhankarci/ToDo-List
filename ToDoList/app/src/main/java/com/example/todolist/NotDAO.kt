package com.example.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.model.Not
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NotDAO {

    @Query("SELECT * FROM `Not`")
    fun getall() : Flowable<List<Not>>

    @Query("SELECT * FROM `Not` WHERE id = :id")
    fun get(id: Int) : Flowable<Not>

    @Insert
    fun insert(not: Not) : Completable

    @Delete
    fun delete(not: Not) : Completable
}