package fr.messager.popmes.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import fr.messager.popmes.common.Constants.FIELD_GUID
import fr.messager.popmes.common.Constants.TABLE_CONTACTS
import fr.messager.popmes.data.database.entities.ContactsEntity
import fr.messager.popmes.domain.model.contact.Contact
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContactDao: BaseDao<Contact, ContactsEntity>(
    tableName = TABLE_CONTACTS,
) {
    @Query("select * from $TABLE_CONTACTS")
    abstract fun findContacts(): Flow<List<ContactsEntity>>

    @Query("delete from $TABLE_CONTACTS where $FIELD_GUID = :guid")
    abstract suspend fun deleteFromGuid(guid: String)

    @Query("select * from $TABLE_CONTACTS where $FIELD_GUID = :guid")
    abstract suspend fun findContactEntityFromGuid(guid: String): ContactsEntity?
}