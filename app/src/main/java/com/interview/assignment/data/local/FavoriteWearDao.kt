package com.interview.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.interview.assignment.data.wrappers.FavoriteWear

@Dao
interface FavoriteWearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavorite(favoriteWear: FavoriteWear)

    @Query("delete from favoritewear where topWearIndex = :topWearIndex and bottomWearIndex = :bottomWearIndex")
    fun deleteFavorite(topWearIndex: Int, bottomWearIndex: Int): Int

    @Query("select * from favoritewear where topWearIndex = :topWearIndex and bottomWearIndex = :bottomWearIndex LIMIT 1")
    fun checkFavorite(topWearIndex: Int, bottomWearIndex: Int): FavoriteWear

    @Query("select * from favoritewear")
    fun allFavs(): LiveData<List<FavoriteWear>>
}
