package com.example.movieapp.data.mappers

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.dto.CastDTO
import com.example.movieapp.domain.model.Cast

fun CastDTO.toCast(): Cast {
   return Cast(
       adult = adult ?: false,
       castId = castId ?: -1,
       character = character ?: "",
       creditId = creditId ?: "",
       gender = gender ?: 0,
       id = id ?: -1,
       knownForDepartment = knownForDepartment ?: "",
       name = name ?: "",
       order = order ?: -1,
       originalName = originalName ?: "",
       popularity = popularity ?: 0.0,
       profilePath = (BuildConfig.BASE_IMAGE_URL + profilePath)
   )
}