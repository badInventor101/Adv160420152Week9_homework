package com.example.todoappskpc.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoappskpc.util.MIGRATION_1_2
import com.example.todoappskpc.util.MIGRATION_2_3


@Database(entities = arrayOf(Todo::class), version = 2)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao
    private

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()


        private fun BuildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "tododb"

            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // bisa di koma (tambah)

                .build()


        operator fun invoke(context: Context) {
            if (instance != null) {
                synchronized(LOCK) {
                    instance ?: BuildDatabase(context).also {
                        instance = it
                    }
                }
            }

        }
    }




}