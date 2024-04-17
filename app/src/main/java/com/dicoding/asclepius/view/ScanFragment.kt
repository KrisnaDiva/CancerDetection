package com.dicoding.asclepius.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.History
import com.dicoding.asclepius.databinding.FragmentScanBinding
import com.dicoding.asclepius.factory.HistoryViewModelFactory
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.utils.DateFormatter
import com.dicoding.asclepius.viewmodel.HistoryViewModel
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyViewModel: HistoryViewModel
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        setupViewModel()
        setupButtons()
        if (savedInstanceState != null) {
            val savedImageUri = savedInstanceState.getString("savedImageUri")
            if (savedImageUri != null) {
                currentImageUri = Uri.parse(savedImageUri)
                binding.previewImageView.setImageURI(currentImageUri)
                binding.noImageTextView.visibility = View.GONE
            }
        }
        return binding.root
    }

    private fun setupViewModel() {
        val factory = HistoryViewModelFactory.getInstance(requireActivity().application)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }

    private fun setupButtons() {
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val outputUri =
                File(requireActivity().filesDir, "${System.currentTimeMillis()}.jpg").toUri()
            cropImage.launch(uri to outputUri)
        }
    }

    private val uCropContract = object : ActivityResultContract<Pair<Uri, Uri>, Uri>() {
        override fun createIntent(context: Context, input: Pair<Uri, Uri>): Intent {
            val uCrop = UCrop.of(input.first, input.second)
            return uCrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            return if (resultCode == Activity.RESULT_OK && intent != null) {
                UCrop.getOutput(intent) ?: Uri.EMPTY
            } else {
                Uri.EMPTY
            }
        }
    }

    private val cropImage = registerForActivityResult(uCropContract) { uri ->
        if (uri != Uri.EMPTY) {
            currentImageUri = uri
            binding.previewImageView.setImageURI(uri)
            binding.noImageTextView.visibility = View.INVISIBLE
        } else {
            currentImageUri = null
            binding.previewImageView.setImageResource(R.drawable.placeholder)
            binding.noImageTextView.visibility = View.VISIBLE
        }
    }

    private fun analyzeImage(uri: Uri) {
        val imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            val result = it[0].categories[0]
                            val displayResult = "${result.label} " +
                                    NumberFormat.getPercentInstance()
                                        .format(result.score).trim()
                            val intent = Intent(requireContext(), ResultActivity::class.java)
                            intent.putExtra(ResultActivity.EXTRA_RESULT, displayResult)
                            intent.putExtra(
                                ResultActivity.EXTRA_IMAGE_URI,
                                currentImageUri.toString()
                            )
                            historyViewModel.insert(
                                History(
                                    prediction = result.label,
                                    score = NumberFormat.getPercentInstance()
                                        .format(result.score).trim(),
                                    imageUrl = currentImageUri.toString(),
                                    createdAt = DateFormatter.getCurrentDate()
                                )
                            )
                            startActivity(intent)
                        } else {
                            showToast(getString(R.string.failed))
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val imageUriToSave = currentImageUri?.toString()
        outState.putString("savedImageUri", imageUriToSave)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentImageUri = null
        _binding = null
    }
}