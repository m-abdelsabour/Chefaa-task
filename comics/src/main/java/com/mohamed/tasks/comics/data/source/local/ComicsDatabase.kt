package com.mohamed.tasks.comics.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [LocalComics::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ComicsDatabase : RoomDatabase() {

    abstract val dao: ComicsDao
}