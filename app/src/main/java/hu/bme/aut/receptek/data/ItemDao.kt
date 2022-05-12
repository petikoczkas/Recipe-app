package hu.bme.aut.receptek.data

import android.content.ClipData
import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Insert
    fun insert(items: Item): Long

    @Update
    fun update(item: Item)

    @Delete
    fun deleteItem(item: Item)
}
