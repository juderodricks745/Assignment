package com.interview.assignment.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import com.interview.assignment.data.wrappers.TopWear

@Dao
interface TopWearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopWear(topWear: TopWear): Long?

    @Query("select * from topwear")
    fun fetchTopWears(): LiveData<List<TopWear>>
}
