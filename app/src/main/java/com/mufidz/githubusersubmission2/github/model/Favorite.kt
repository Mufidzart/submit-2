package com.mufidz.githubusersubmission2.github.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    var id: Int = 0,
    var username: String? = null,
    var date: String? = null
) : Parcelable
