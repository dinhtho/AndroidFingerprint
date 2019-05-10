package com.example.androidfingerprint.biometric

import android.R
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull


/**
 * Created by tho nguyen on 2019-05-10.
 */

class BiometricDialogV23 : BottomSheetDialog, View.OnClickListener {

    private var context: Context? = null

    private var btnCancel: Button? = null
    private var imgLogo: ImageView? = null
    private var itemTitle: TextView? = null
    private var itemDescription: TextView? = null
    private var itemSubtitle: TextView? = null
    private var itemStatus: TextView? = null

    private lateinit var biometricCallback: BiometricCallback

    constructor(@NonNull context: Context) : super(context, R.style.BottomSheetDialogTheme) {
        this.context = context.getApplicationContext()
        setDialogView()
    }

    constructor(@NonNull context: Context, biometricCallback: BiometricCallback) : super(context, R.style.BottomSheetDialogTheme) {
        this.context = context.getApplicationContext()
        this.biometricCallback = biometricCallback
        setDialogView()
    }

    constructor(@NonNull context: Context, theme: Int) : super(context, theme) {}

    protected constructor(@NonNull context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener) {}

    private fun setDialogView() {
        val bottomSheetView = getLayoutInflater().inflate(R.layout.view_bottom_sheet, null)
        setContentView(bottomSheetView)

        btnCancel = findViewById(R.id.btn_cancel)
        btnCancel!!.setOnClickListener(this)

        imgLogo = findViewById(R.id.img_logo)
        itemTitle = findViewById(R.id.item_title)
        itemStatus = findViewById(R.id.item_status)
        itemSubtitle = findViewById(R.id.item_subtitle)
        itemDescription = findViewById(R.id.item_description)

        updateLogo()
    }

    fun setTitle(title: String) {
        itemTitle!!.text = title
    }

    fun updateStatus(status: String) {
        itemStatus!!.text = status
    }

    fun setSubtitle(subtitle: String) {
        itemSubtitle!!.text = subtitle
    }

    fun setDescription(description: String) {
        itemDescription!!.text = description
    }

    fun setButtonText(negativeButtonText: String) {
        btnCancel!!.setText(negativeButtonText)
    }

    private fun updateLogo() {
        try {
            val drawable = getContext().getPackageManager().getApplicationIcon(context!!.getPackageName())
            imgLogo!!.setImageDrawable(drawable)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun onClick(view: View) {
        dismiss()
        biometricCallback.onAuthenticationCancelled()
    }
}
