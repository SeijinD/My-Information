package eu.seijindemon.myinformation.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.seijindemon.myinformation.data.local.db.AppDb

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return AppDb.getInstance(context)
    }

}