package fr.messager.popmes.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import fr.messager.popmes.data.database.entities.BaseEntity
import fr.messager.popmes.domain.model.ProtoData

abstract class BaseDao<V : ProtoData, T : BaseEntity<V>>(
    val tableName: String,
) {
    /**
     * Insert data by adding or replacing same data.
     *
     * @param obj
     * @return row index
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T): Long

    /**
     * Insert multiple data by adding or replacing same data.
     *
     * @param obj
     * @return row indices
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg obj: T): LongArray

    /**
     * Delete data
     *
     * @param obj
     */
    @Delete
    abstract suspend fun delete(obj: T)

    /**
     * Delete multiple data
     *
     * @param obj
     */
    @Delete
    abstract suspend fun delete(vararg obj: T)

    /**
     * Update data
     *
     * @param obj
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: T)

    /**
     * Update multiple data
     *
     * @param obj
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(vararg obj: T)
}