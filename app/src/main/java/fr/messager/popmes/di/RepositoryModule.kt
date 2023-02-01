package fr.messager.popmes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.messager.popmes.data.database.dao.ContactDao
import fr.messager.popmes.data.database.dao.MessageDao
import fr.messager.popmes.data.database.dao.TaskDao
import fr.messager.popmes.data.repository.ContactRepositoryImpl
import fr.messager.popmes.data.repository.MessageRepositoryImpl
import fr.messager.popmes.data.repository.TaskRepositoryImpl
import fr.messager.popmes.domain.repository.ContactRepository
import fr.messager.popmes.domain.repository.MessageRepository
import fr.messager.popmes.domain.repository.TaskRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideContactRepository(
        contactDao: ContactDao,
    ): ContactRepository = ContactRepositoryImpl(
        contactDao = contactDao,
    )

    @Provides
    fun provideMessageRepository(
        messageDao: MessageDao
    ): MessageRepository = MessageRepositoryImpl(
        messageDao = messageDao
    )

    @Provides
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository = TaskRepositoryImpl(
        taskDao = taskDao,
    )
}