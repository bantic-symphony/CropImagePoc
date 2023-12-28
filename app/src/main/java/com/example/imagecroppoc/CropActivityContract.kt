package com.example.imagecroppoc

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri

class CropActivityContract: ActivityResultContract<Intent, Uri?>() {
    override fun createIntent(context: Context, input: Intent): Intent = input

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        Log.w("BOJAN", "CropActivityContract.parseResult resultCode: $resultCode")
        if (resultCode == Activity.RESULT_OK){
            intent?.getStringExtra("uri_key")?.let {
                Log.w("BOJAN", "CropActivityContract.parseResult: $it")
                return it.toUri()
            }
        }
        return Uri.EMPTY
    }
}