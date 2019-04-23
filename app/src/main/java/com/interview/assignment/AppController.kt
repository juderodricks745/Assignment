package com.interview.assignment

import android.app.Application
import com.interview.assignment.data.Injection

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        Injection.createRepo(applicationContext)
    }
}
