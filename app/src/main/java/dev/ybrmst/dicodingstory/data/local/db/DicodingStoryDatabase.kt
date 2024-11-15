package dev.ybrmst.dicodingstory.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ybrmst.dicodingstory.data.local.auth.dao.SessionDao
import dev.ybrmst.dicodingstory.data.local.auth.entities.SessionEntity

@Database(
  entities = [SessionEntity::class],
  version = 1,
  exportSchema = false
)
abstract class DicodingStoryDatabase : RoomDatabase() {
  abstract fun sessionDao(): SessionDao
}