package com.example.imagecroppoc

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.imagecroppoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val registry = this.activityResultRegistry
    private lateinit var binding: ActivityMainBinding
    private lateinit var getContent: ActivityResultLauncher<String>

    private val getCroppedImage = registerForActivityResult(ActivityResultContracts.GetContent(), registry,  ActivityResultCallback<Uri?> {uri ->

        val fileInputStream = uri?.let { applicationContext.contentResolver.openInputStream(it) }
        val fileInputStream2 = uri?.let { applicationContext.contentResolver.openInputStream(it) }
        Log.w("CROPPED_IMAGE", "original size in bytes: ${fileInputStream2?.available()}")
        Log.w("CROPPED_IMAGE", "new size in bytes: ${fileInputStream?.available()}")
        binding.imageView.setImageURI(uri)
    }
    )

    private val startCustomCropActivityLauncher = registerForActivityResult(CropActivityContract()){
        Log.w("BOJAN", "registerForActivityResult(CropActivityContract(): image uri:$it")
        binding.imageView.setImageURI(it)
    }

//    }) { uri: Uri? ->
//        val fileInputStream = uri?.let { applicationContext.contentResolver.openInputStream(it) }
//        val fileInputStream2 = uri?.let { applicationContext.contentResolver.openInputStream(it) }
//        Log.w("CROPPED_IMAGE", "original size in bytes: ${fileInputStream2?.available()}")
//        Log.w("CROPPED_IMAGE", "new size in bytes: ${fileInputStream?.available()}")
//        binding.imageView.setImageURI(uri)
//    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            val fileInputStream = uriContent?.let { applicationContext.contentResolver.openInputStream(it) }
            val fileInputStream2 = result.originalUri?.let { applicationContext.contentResolver.openInputStream(it) }
            Log.w("CROPPED_IMAGE", "original size in bytes: ${fileInputStream2?.available()}")
            Log.w("CROPPED_IMAGE", "new size in bytes: ${fileInputStream?.available()}")
            binding.imageView.setImageURI(uriContent)
        } else {
            // An error occurred.
            Log.e("cropImage.registerForActivityResult", "${result.error?.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {
            startCrop(null)
        }

        getContent = registry.register("key", binding.root.findViewTreeLifecycleOwner()!!, ActivityResultContracts.GetContent()) { uri ->
            // Handle the returned Uri
            binding.imageView.setImageURI(uri)
        }

        binding.button.setOnClickListener {
//            CustomCropActivity.start(this)
//            getCroppedImage.launch("image/*")
//            startActivity(Intent(this, CustomCropActivity::class.java))
            startCustomCropActivityLauncher.launch(
                Intent(this, CustomCropActivity::class.java)
            )
        }
    }

        private fun startCrop(image: Uri?) {
        cropImage.launch(
            CropImageContractOptions(
                image,
                CropImageOptions(
                    imageSourceIncludeCamera = false,
                    imageSourceIncludeGallery = true,
                    cropMenuCropButtonTitle = "Save",
                    multiTouchEnabled = false,
                    cropShape = CropImageView.CropShape.OVAL,
                    toolbarTitleColor = getColor(R.color.Azure),
                    toolbarColor = getColor(R.color.BlanchedAlmond),
                    toolbarBackButtonColor = getColor(R.color.BlueViolet),
                    activityBackgroundColor = getColor(R.color.PaleGreen),
                    activityMenuTextColor = getColor(R.color.CadetBlue),
                    activityMenuIconColor = getColor(R.color.DarkSlateGray),
                    outputCompressQuality = 20,
                    outputCompressFormat = Bitmap.CompressFormat.JPEG,
                    fixAspectRatio = true,
                )
            )
        )
    }
}