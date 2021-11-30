package com.mufidz.githubusersubmission2.github.model

data class DetailUserResponse(
    val login : String,
    val name : String,
    val id : Int,
    val avatar_url : String,
    val company : String,
    val follower_url : String,
    val following_url : String,
    val followers : Int,
    val following : Int,
    val public_repos : Int
)
