package dev.ybrmst.dicodingstory.data.local.auth.entities

import androidx.room.*

@Entity(
  tableName = "sessions",
  indices = [
    Index(value = ["user_id"], unique = true),
    Index(value = ["name"])
  ]
)
data class SessionEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "session_id")
  val sessionId: Int = 0,

  @ColumnInfo(name = "user_id") val userId: String,

  @ColumnInfo val name: String,
)