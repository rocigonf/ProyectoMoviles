package com.moguishio.moguishio.ui.views.dialogs

import android.content.Context
import com.moguishio.moguishio.R

class ToastMessage(val context: Context) {

    val DICTIONARY = mapOf(
        "Datos incorrectos" to context.getString(R.string.login_error),
        "No se ha podido registrar" to context.getString(R.string.signup_error),
        "Mail incorrecto" to context.getString(R.string.invalid_email),
        "Sin campos nulos" to context.getString(R.string.no_empty_fields),
        "Password" to context.getString(R.string.incorrect_password)
    )

    fun getStringResourceId(string: String): String? {
        if(string in DICTIONARY)
        {
            return DICTIONARY[string]
        }
        else
        {
            return ""
        }
    }
}