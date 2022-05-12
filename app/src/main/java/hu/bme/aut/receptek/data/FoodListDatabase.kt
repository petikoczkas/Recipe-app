package hu.bme.aut.receptek.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Item::class], version = 1)
@TypeConverters(value = [Item.Category::class])
abstract class FoodListDatabase : RoomDatabase() {
    abstract fun ItemDao(): ItemDao

    companion object {
        fun getDatabase(applicationContext: Context): FoodListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                FoodListDatabase::class.java,
                "food-list"
            ).build();
        }
    }
}
