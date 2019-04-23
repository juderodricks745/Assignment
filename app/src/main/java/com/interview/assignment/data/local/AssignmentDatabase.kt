package com.interview.assignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.data.wrappers.FavoriteWear
import com.interview.assignment.data.wrappers.TopWear

@Database(entities = [TopWear::class, BottomWear::class, FavoriteWear::class], version = 1, exportSchema = false)
abstract class AssignmentDatabase : RoomDatabase() {

    internal abstract val topWearDao: TopWearDao
    internal abstract val bottomWearDao: BottomWearDao
    internal abstract val favoriteWearDao: FavoriteWearDao


    companion object {

        private var INSTANCE: AssignmentDatabase? = null

        fun instance(context: Context): AssignmentDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AssignmentDatabase::class.java, "assignment.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}
