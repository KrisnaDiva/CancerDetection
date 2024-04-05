package com.dicoding.asclepius.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

object ViewUtils {
    fun observeToastMessage(context: Context, liveData: LiveData<String>) {
        liveData.observe(context as LifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun observeLoading(progressBar: ProgressBar, liveData: LiveData<Boolean>) {
        liveData.observe(progressBar.context as LifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}