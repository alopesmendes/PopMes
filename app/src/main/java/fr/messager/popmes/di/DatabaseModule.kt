package fr.messager.popmes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.messager.popmes.data.database.PopMesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePopMesDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        PopMesDatabase::class.java,
        "pop_mes_database.db",
    ).build()
}