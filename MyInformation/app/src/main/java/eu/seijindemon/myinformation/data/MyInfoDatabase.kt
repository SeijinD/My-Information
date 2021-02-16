package eu.seijindemon.myinformation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Create Database, add class in database and version

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class MyInfoDatabase : RoomDatabase() {

    abstract fun myInfoDao(): MyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: MyInfoDatabase? = null

        fun getAppDataBase(context: Context): MyInfoDatabase? {
            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(context.applicationContext, MyInfoDatabase::class.java, "SeijinDB").build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDatabase()
        {
            INSTANCE = null
        }
    }

}