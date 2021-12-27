package eu.seijindemon.myinformation.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.seijindemon.myinformation.data.local.converter.Converter
import eu.seijindemon.myinformation.data.local.dao.AppDao
import eu.seijindemon.myinformation.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDb: RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {

        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDb::class.java,
                        "my_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}