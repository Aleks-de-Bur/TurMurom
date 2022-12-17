package com.example.turmurom.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class MarksWithPhotos (
        @Embedded
        val mark: Mark,
        @Relation(
                parentColumn = "id",
                entityColumn = "MarkId"
        )
        val markPhotos: List<MarkPhoto>
)
