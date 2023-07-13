package com.example.budgettracker

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun transactionDao(): TransactionDao
}