package com.eko.cabaca.util

import android.os.Build
import android.text.Html
import android.view.View

const val HEADER = "x-dreamfactory-api-key:25e0bf00ab2fa7f03a9fa57035139e47ccb28c20658f6de907b8011347e369fb"

private const val IMAGE_URL = "https://cabaca.id:8443/api/v2/files/"
private const val IMAGE_API_KEY = "api_key=32ded42cfffb77dee86a29f43d36a3641849d4b5904aade9a79e9aa6cd5b5948"

fun String.fixImageUrl(): String = "$IMAGE_URL$this&$IMAGE_API_KEY"

fun View.visibleWhen(show: Boolean){
    if (show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@Suppress("DEPRECATION")
fun String.fromHtml(): String{
    return if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}