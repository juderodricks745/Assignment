package com.interview.assignment.ui.dash

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.interview.assignment.data.Injection
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.data.wrappers.FavoriteWear
import com.interview.assignment.data.wrappers.TopWear
import com.interview.assignment.utils.zip2
import java.io.File

class DashViewModel(application: Application) : AndroidViewModel(application) {

    var favorite = ObservableBoolean(false)
    private var topWear: LiveData<List<TopWear>>? = null
    private var bottomWear: LiveData<List<BottomWear>>? = null

    private val files by lazy { Injection.repo()?.files() }
    private val local by lazy { Injection.repo()?.local() }

    init {
        topWear = Injection.repo()?.local()?.fetchTopWearables()
        bottomWear = Injection.repo()?.local()?.fetchBottomWearables()
    }

    fun getTopWear(): LiveData<List<TopWear>>? {
        return topWear
    }

    fun getBottomWear(): LiveData<List<BottomWear>>? {
        return bottomWear
    }

    fun insertTopWear(topWear: TopWear) {
        local?.insertTopWear(topWear)
    }

    fun insertBottomWear(bottomWear: BottomWear) {
        local?.insertBottomWear(bottomWear)
    }

    fun getImageSaveLocation(): File? {
        return files?.createImageCaptureFile()
    }

    fun checkFavorite(topIndex: Int, bottomIndex: Int) {
        val favWear = FavoriteWear().apply { topWearIndex = topIndex; bottomWearIndex = bottomIndex; id = (topIndex + bottomIndex).hashCode() }
        val favWearCheck = local?.checkFavorite(favWear)
        if (favWearCheck != null) {
            favorite.set(true)
        } else {
            favorite.set(false)
        }
    }

    fun favOperation(topIndex: Int, bottomIndex: Int) {
        val favWear = FavoriteWear().apply { topWearIndex = topIndex; bottomWearIndex = bottomIndex; id = (topIndex + bottomIndex).hashCode() }
        if (local?.checkFavorite(favWear) != null) {
            local?.removeFavorite(favWear)
            favorite.set(false)
        } else {
            local?.setFavorite(favWear)
            favorite.set(true)
        }
    }

    fun favObserver(): LiveData<Boolean> {
        return zip2(topWear!!, bottomWear!!) { topWears: List<TopWear>, bottomWears: List<BottomWear> -> topWears.isNotEmpty() && bottomWears.isNotEmpty() }
    }

    fun refreshObserver(): LiveData<Boolean> {
        return zip2(topWear!!, bottomWear!!) { topWears: List<TopWear>, bottomWears: List<BottomWear> -> topWears.size >= 2 || bottomWears.size >= 2 }
    }
}
