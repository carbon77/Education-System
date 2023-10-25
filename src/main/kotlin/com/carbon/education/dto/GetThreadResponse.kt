package com.carbon.education.dto

import com.carbon.education.model.Post
import com.carbon.education.model.Thread

data class GetThreadResponse (
    val thread: Thread,
    val posts: List<Post>
)