package com.carbon.education.dto

import java.sql.Timestamp


interface ThreadInfo {
    fun getThreadId(): Int
    fun getTitle(): String
    fun getDescription(): String
    fun getAuthorName(): String
    fun getAuthorId(): Int
    fun getPostCount(): Int
    fun getCreatedAt(): Timestamp
}
