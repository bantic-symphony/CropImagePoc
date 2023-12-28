package com.example.imagecroppoc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.canhub.cropper.CropImageActivity
import com.example.imagecroppoc.databinding.CustomCropLayoutBinding

class CustomCropActivity : CropImageActivity() {

    private lateinit var binding: CustomCropLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomCropLayoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.save.setOnClickListener {
            cropImage()
        }
        setCropImageView(binding.cropImageView)
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    override fun onPickImageResult(resultUri: Uri?) {
        super.onPickImageResult(resultUri)

        if (resultUri != null) {
            binding.cropImageView.setImageUriAsync(resultUri)
        }
    }

    override fun getResultIntent(uri: Uri?, error: java.lang.Exception?, sampleSize: Int): Intent {
        val result = super.getResultIntent(uri, error, sampleSize)
        // Adding some more information.
        Log.w("BOJAN", "result is this image uri: $uri")
        return result.putExtra("EXTRA_KEY", "Extra data")
    }

    override fun setResult(uri: Uri?, error: Exception?, sampleSize: Int) {
        Log.d("BOJAN", "Output uri: $uri")
        binding.cropImageView.setImageUriAsync(uri)



        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra("uri_key", uri.toString())
            }
        )
        finish()
    }

    override fun setResultCancel() {
        Log.d("AIC-Sample", "User this override to change behaviour when cancel")
        super.setResultCancel()
    }
}