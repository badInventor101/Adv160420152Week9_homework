package com.example.todoappskpc.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoappskpc.model.TodoDatabase

val DB_NAME = "tododb"

fun buildDB(context:Context):TodoDatabase{
    val db = Room.databaseBuilder(context, TodoDatabase::class.java, "tododb")
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)

        .build()
    return db
}


val MIGRATION_1_2 = object: Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
        database.execSQL("INSERT INTO todo(title,notes,priority) VALUES('EXAMPLE Todo', 'Example Notes', 3)")

    }



}

val MIGRATION_2_3 = object: Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
        database.execSQL("INSERT INTO todo(title,notes,priority,is_done) VALUES('EXAMPLE Todo', 'Example Notes', 3, 0)")

    }



}