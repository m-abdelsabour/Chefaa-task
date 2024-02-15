package com.mohamed.tasks.core.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.mohamed.tasks.core.R

object DialogHelper {

    fun Context.confirmMenu(
        title: String,
        positiveBtnText: String = getString(R.string.close),
        positiveBtnClickListener: (alert: AlertDialog) -> Unit = { it.dismiss() },
        cancelable: Boolean = true,
        @LayoutRes resource: Int = R.layout.dialog_menu,
    ) {
        val builder = AlertDialog.Builder(this)
        val alertDialog = builder.create()
        val view = LayoutInflater.from(this).inflate(resource, null)
        val titleTv: TextView = view.findViewById(R.id.tv_title)
        val positiveBtn: Button = view.findViewById(R.id.btn_positive)

        titleTv.text = title
        positiveBtn.text = positiveBtnText

        positiveBtn.setOnClickListener { positiveBtnClickListener.invoke(alertDialog) }
        alertDialog.setView(view)
        alertDialog.setCancelable(cancelable)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }
}
