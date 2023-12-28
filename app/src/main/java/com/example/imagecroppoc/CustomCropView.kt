package com.example.imagecroppoc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageView
import com.example.imagecroppoc.databinding.CustomCropLayoutBinding

class CustomCropView : CropImageActivity() {

    private lateinit var binding: CustomCropLayoutBinding


    companion object {
        fun start(activity: Activity) {
            ActivityCompat.startActivity(
                activity,
                Intent(activity, CustomCropView::class.java),
                null,
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomCropLayoutBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        binding.save.setOnClickListener { cropImage() }
        binding.flip.setOnClickListener { onRotateClick() }


        setCropImageView(binding.cropImageView)
    }

    override fun setResult(uri: Uri?, error: Exception?, sampleSize: Int) {
        val result = CropImage.ActivityResult(
            originalUri = binding.cropImageView.imageUri,
            uriContent = uri,
            error = error,
            cropPoints = binding.cropImageView.cropPoints,
            cropRect = binding.cropImageView.cropRect,
            rotation = binding.cropImageView.rotatedDegrees,
            wholeImageRect = binding.cropImageView.wholeImageRect,
            sampleSize = sampleSize,
        )

        binding.cropImageView.setImageUriAsync(result.uriContent)
    }

    override fun onSetImageUriComplete(
        view: CropImageView,
        uri: Uri,
        error: Exception?,
    ) {
        super.onSetImageUriComplete(view, uri, error)
    }


    private fun onRotateClick() {
        binding.cropImageView.rotateImage(90)
    }
}