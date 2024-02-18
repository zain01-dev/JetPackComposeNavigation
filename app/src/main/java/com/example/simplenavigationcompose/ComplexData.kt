package com.example.simplenavigationcompose

import android.os.Parcelable
import androidx.navigation.NavArgs
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComplexData(val name: String, val age: Int)  : Parcelable, NavArgs {
    constructor() : this("", 0)
}
