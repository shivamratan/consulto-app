package com.ratanapps.consultoapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ratanapps.consultoapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class ConsultoDb : RoomDatabase() {
    // DAOs will be added here
}