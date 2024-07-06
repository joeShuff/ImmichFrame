package uk.co.joeshuff.immichframe.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.joeshuff.immichframe.data.db.entities.ImmichImageEntity

@Database(
    entities = [ImmichImageEntity::class],
    version = 1
)
abstract class ImmichFrameDB: RoomDatabase() {

}