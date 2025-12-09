package com.example.blogreader.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlogDao {

    @Query("SELECT * FROM blogs WHERE page = :page ORDER BY id")
    suspend fun getBlogsByPage(page: Int): List<BlogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<BlogEntity>)

    @Query("DELETE FROM blogs")
    suspend fun clearAll()
}
