package org.study2.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [(Product::class)], version = 1)
abstract class ProductRoomDatabase: RoomDatabase() {

    abstract fun productDAO(): ProductDAO

//    companion object 인스턴스로서 단 하나만 생성하는 것을 허용한다는 의미라고 한다.
    companion object {

        private var INSTANCE: ProductRoomDatabase? = null

//        가시성제한자 (같은 모듈에서만 사용가능)
        @OptIn(InternalCoroutinesApi::class)
        internal fun getDatabase(context: Context): ProductRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ProductRoomDatabase::class.java){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder<ProductRoomDatabase>(
                            context.applicationContext,
                            ProductRoomDatabase::class.java,
                            "product_database").build()
                    }
                }
            }
    return INSTANCE
        }

    }
}