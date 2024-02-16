package com.mohamed.tasks.comics.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ComicsDao {
    @Query("SELECT * FROM comics")
     fun getAllComics(): List<LocalComics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(list: List<LocalComics>?)

    @Query("SELECT * FROM comics WHERE textObjects=:caption")
     fun getSearchComics(caption: String): LocalComics?

}