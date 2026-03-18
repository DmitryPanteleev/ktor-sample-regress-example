package com.example

import kotlinx.serialization.Serializable

@Serializable
data class BaseRsDto<out T>(
    val data: T?
)