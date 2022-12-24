package com.example.turmurom.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class ExcursionWithPhoto (
    @Embedded
    val excursion: Excursion,
    @Relation(
        parentColumn = "id",
        entityColumn = "ExcursionId"
    )
    val excursionPhotos: List<ExcursionPhoto>
)