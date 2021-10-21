package com.devtau.template.utils

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS

private const val DEBOUNCE_RATE_MS = 300L

/**
 * Transforms static java function Snackbar.make() to an extension function on View
 * @param snackBarText text resource id
 * @param timeLength length of time for snackbar to be shown (ms)
 */
fun View.showSnackBar(
    @StringRes snackBarText: Int,
    timeLength: Int = LENGTH_LONG
) = Snackbar.make(this, snackBarText, timeLength).show()

fun View?.showDialog(
    logTag: String, @StringRes msgId: Int?, confirmed: Action? = null, cancelled: Action? = null
) {
    if (this?.context == null || msgId == null) return
    showDialog(logTag, context?.getString(msgId), confirmed, cancelled)
}

fun View?.showDialog(
    logTag: String, msg: String?, confirmed: Action? = null, cancelled: Action? = null
) {
    if (this?.context == null || msg == null) return
    Timber.d("%s, %s", logTag, msg)
    try {
        val builder = AlertDialog.Builder(context)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                confirmed?.run()
                dialog.dismiss()
            }
        if (cancelled != null) {
            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                cancelled.run()
                dialog.dismiss()
            }
        }

        builder.setMessage(msg).show()
    } catch (e: WindowManager.BadTokenException) {
        Timber.e("$logTag, showDialog. cannot show dialog")
        context.toast(msg)
    }
}

fun TextView.observeTextChanges(): Observable<String> = Observable.create { emitter: ObservableEmitter<String> ->
    val listener: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable) {
            if (!emitter.isDisposed) {
                emitter.onNext(editable.toString())
            }
        }
    }
    emitter.setCancellable { removeTextChangedListener(listener) }
    addTextChangedListener(listener)
}

fun SearchView.observeTextChanges(
    debounce: Long = DEBOUNCE_RATE_MS
): Observable<String> = Observable.create { emitter: ObservableEmitter<String> ->
    val listener: SearchView.OnQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            emit(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            emit(newText)
            return true
        }

        private fun emit(query: String?) {
            if (!emitter.isDisposed && !query.isNullOrEmpty()) {
                emitter.onNext(query)
            }
        }
    }
    emitter.setCancellable { setOnQueryTextListener(null) }
    setOnQueryTextListener(listener)
}.debounce(debounce, MILLISECONDS, AndroidSchedulers.mainThread())

fun Context?.toast(@StringRes msgId: Int) { this?.toast(this.getString(msgId)) }
fun Context?.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context?.toastLong(@StringRes msgId: Int) { this?.toastLong(this.getString(msgId)) }
fun Context?.toastLong(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()