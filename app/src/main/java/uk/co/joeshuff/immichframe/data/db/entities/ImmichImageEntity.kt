package uk.co.joeshuff.immichframe.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImmichImageEntity(
    @PrimaryKey val id: String,
)