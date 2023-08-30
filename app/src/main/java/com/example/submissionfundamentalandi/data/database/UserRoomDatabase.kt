package com.example.submissionfundamentalandi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionfundamentalandi.data.response.UsersGithub

@Database(entities = [UsersGithub::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userGithubDao(): UserGithubDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }

}