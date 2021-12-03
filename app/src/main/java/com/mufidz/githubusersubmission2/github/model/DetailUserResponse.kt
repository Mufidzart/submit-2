package com.mufidz.githubusersubmission2.github.model

data class DetailUserResponse(
    val login: String,
    val name: String,
    val avatar_url: String,
    val company: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int
)
