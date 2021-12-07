package com.mufidz.githubusersubmission2.github.menu

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.github.db.DatabaseContract
import com.mufidz.githubusersubmission2.github.db.FavoriteHelper
import com.mufidz.githubusersubmission2.github.model.Favorite
import com.mufidz.githubusersubmission2.github.ui.GithubUserAdapter
import com.mufidz.githubusersubmission2.github.ui.favorite.FavoriteAdapter

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.OnItemClickCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        val favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val cursor = favoriteHelper.queryAll()
        if (cursor.count == 0) {
            Log.d("DEBUG", "NO DATA FOUND")
        } else {
            val usernameColumn = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.USERNAME)
            val dateColumn = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.DATE)
            val idColumn = cursor.getColumnIndex(DatabaseContract.FavoriteColumns._ID);
            val adapter = FavoriteAdapter(this)
            val favorites = ArrayList<Favorite>()

            cursor.moveToFirst()

            do {
                val username = cursor.getString(usernameColumn)
                val date = cursor.getString(dateColumn)
                val id = cursor.getInt(idColumn)

                favorites.add(Favorite(id, username, date))
            } while (cursor.moveToNext())

            adapter.listFavorites = favorites
            val recyclerView = findViewById<RecyclerView>(R.id.rv_user_favorite)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        favoriteHelper.close()
    }

    override fun onItemClicked(selectedFavorite: Favorite?, position: Int) {
        TODO("Not yet implemented")
    }
}