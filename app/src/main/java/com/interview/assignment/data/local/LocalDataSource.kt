package com.interview.assignment.data.local

import androidx.lifecycle.LiveData
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.data.wrappers.FavoriteWear
import com.interview.assignment.data.wrappers.TopWear

class LocalDataSource private constructor(private val database: AssignmentDatabase) : DataSource {

    companion object {
        private var source: LocalDataSource? = null

        fun instance(database: AssignmentDatabase): LocalDataSource {
            if (source == null) {
                source = LocalDataSource(database)
            }
            return source!!
        }
    }

    override fun insertTopWear(topWear: TopWear) {
        database.topWearDao.insertTopWear(topWear)
    }

    override fun insertBottomWear(bottomWear: BottomWear) {
        database.bottomWearDao.insertBottomWear(bottomWear)
    }

    override fun fetchTopWearables(): LiveData<List<TopWear>> {
        return database.topWearDao.fetchTopWears()
    }

    override fun fetchBottomWearables(): LiveData<List<BottomWear>> {
        return database.bottomWearDao.fetchBottomWears()
    }

    override fun setFavorite(wear: FavoriteWear) {
        database.favoriteWearDao.setFavorite(wear)
    }

    override fun removeFavorite(wear: FavoriteWear) {
        database.favoriteWearDao.deleteFavorite(wear.topWearIndex, wear.bottomWearIndex)
    }

    override fun checkFavorite(wear: FavoriteWear): FavoriteWear {
        return database.favoriteWearDao.checkFavorite(wear.topWearIndex, wear.bottomWearIndex)
    }

    fun fetchAllFavs(): LiveData<List<FavoriteWear>> {
        return database.favoriteWearDao.allFavs()
    }
}
