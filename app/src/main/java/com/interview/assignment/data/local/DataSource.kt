package com.interview.assignment.data.local

import androidx.lifecycle.LiveData
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.data.wrappers.FavoriteWear
import com.interview.assignment.data.wrappers.TopWear

interface DataSource {

    fun insertTopWear(topWear: TopWear)
    fun insertBottomWear(bottomWear: BottomWear)
    fun setFavorite(wear: FavoriteWear)
    fun removeFavorite(wear: FavoriteWear)
    fun checkFavorite(wear: FavoriteWear): FavoriteWear

    fun fetchTopWearables(): LiveData<List<TopWear>>
    fun fetchBottomWearables(): LiveData<List<BottomWear>>
}
