package dev.ybrmst.dicodingstory.data.local.auth.dao

import androidx.room.*
import dev.ybrmst.dicodingstory.data.local.auth.entities.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
  @Query("SELECT * FROM sessions")
  fun getSessions(): Flow<List<SessionEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveSession(session: SessionEntity)

  @Query("DELETE FROM sessions")
  fun revokeSessions()
}