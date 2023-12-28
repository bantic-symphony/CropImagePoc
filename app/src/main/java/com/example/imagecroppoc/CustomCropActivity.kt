package com.example.imagecroppoc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import com.canhub.cropper.CropImageActivity
import com.example.imagecroppoc.databinding.CustomCropLayoutBinding

class CustomCropActivity : CropImageActivity() {

    private lateinit var binding: CustomCropLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomCropLayoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.save.setOnClickListener { cropImage() }
        setCropImageView(binding.cropImageView)
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setResult(uri: Uri?, error: Exception?, sampleSize: Int) {
        binding.cropImageView.setImageUriAsync(uri)

        setResult(
            Activity.RESULT_OK,
            Intent().apply { putExtra("uri_key", uri.toString()) }
        )
        finish()
    }

    override fun showImageSourceDialog(openSource: (Source) -> Unit) {
        openSource(Source.GALLERY)
    }
}