package com.dicoding.asclepius.utils

import android.app.Activity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

object ActionBarUtils {
    fun setupActionBar(
        activity: AppCompatActivity,
        title: String,
        displayHomeAsUpEnabled: Boolean = true
    ) {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
            this.title = title
        }
    }

    fun onOptionsItemSelected(activity: Activity, item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity.finish()
                true
            }

            else -> false
        }
    }
}