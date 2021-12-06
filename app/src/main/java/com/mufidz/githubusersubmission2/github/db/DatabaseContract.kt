package com.mufidz.githubusersubmission2.github.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class FavoriteColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val DATE = "date"
        }
    }
}