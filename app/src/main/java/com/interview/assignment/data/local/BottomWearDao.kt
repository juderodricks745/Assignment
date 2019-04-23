package com.interview.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.interview.assignment.data.wrappers.BottomWear

@Dao
interface BottomWearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBottomWear(topWear: BottomWear): Long?

    @Query("select * from bottomwear")
    fun fetchBottomWears(): LiveData<List<BottomWear>>
}
