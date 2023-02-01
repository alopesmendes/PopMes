package fr.messager.popmes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.messager.popmes.data.database.dao.ContactDao
import fr.messager.popmes.data.repository.ContactRepositoryImpl
import fr.messager.popmes.domain.repository.ContactRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideContactRepository(
        contactDao: ContactDao,
    ): ContactRepository = ContactRepositoryImpl(
        contactDao = contactDao,
    )
}