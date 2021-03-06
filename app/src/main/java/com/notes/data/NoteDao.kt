package com.notes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY modifiedAt DESC")
    fun getAll(): Flow<List<NoteDbo>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getItemById(id: Long): NoteDbo

    @Insert
    fun insertAll(vararg notes: NoteDbo)

    @Delete
    fun deleteAll(vararg notes: NoteDbo)

    @Update
    fun updateAll(vararg notes: NoteDbo)
}
