package org.study2.roomdemo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

// 뷰모델을 대신하여 Room데이터베이스와 상호작용한다
class ProductRepository(application: Application) {

    val searchResults = MutableLiveData<List<Product>>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var productDAO: ProductDAO?

    var allProducts:LiveData<List<Product>>?
    init {
        val db: ProductRoomDatabase? =
            ProductRoomDatabase.getDatabase(application)
        productDAO = db?.productDAO()
        allProducts = productDAO?.getAllProducts()
    }

    fun insertProduct(newProduct: Product) {
        coroutineScope.launch(Dispatchers.IO){
            asyncInsert(newProduct)
        }
    }

    private suspend fun asyncInsert(product: Product) {
        productDAO?.insertProduct(product)
    }
    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(name)
        }
    }

    private suspend fun asyncDelete(name: String) {
        productDAO?.deleteProduct(name)
    }

    fun findProduct(name: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }
// Deferred함수는 향후 언젠가 값을 제공한다는 약속을 나타난다.
    private suspend fun asyncFind(name: String): Deferred<List<Product>?> =
        coroutineScope.async(Dispatchers.IO){
        return@async productDAO?.findProduct(name)
    }
}