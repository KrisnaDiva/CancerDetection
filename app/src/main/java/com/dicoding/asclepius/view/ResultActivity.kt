package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.utils.ActionBarUtils

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        ActionBarUtils.setupActionBar(this, getString(R.string.result))
        displayImage()
        displayResult()
    }

    private fun setupBinding() {
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun displayImage() {
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            binding.resultImage.setImageURI(it)
        }
    }

    private fun displayResult() {
        val result = intent.getStringExtra(EXTRA_RESULT)
        result?.let {
            binding.resultText.text = "Result: $it"
        }
    }


    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ActionBarUtils.onOptionsItemSelected(this, item)
    }
}