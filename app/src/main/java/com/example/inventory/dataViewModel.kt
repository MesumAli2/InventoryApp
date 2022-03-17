package com.example.inventory

import androidx.lifecycle.*
import kotlinx.coroutines.launch


private const val PRICE_PER_CUPCAKE = 2.00


class dataViewModel(private val itemmDao: ItemmDao): ViewModel() {


    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> = _flavor

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price

    val allItems: LiveData<List<Item>> = itemmDao.getItems().asLiveData()


    fun retrieveItem(id: Int): LiveData<Item> {
        return itemmDao.getItem(id).asLiveData()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()

    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    private fun updatePrice() {
        _price.value = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
    }

    private fun inserttem(item:Item){

        viewModelScope.launch {
            itemmDao.insert(item)
        }
    }


    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    private fun updateItemEntry(id: Int, itemName: String, itemPrice: String, itemCount: String): Item{
        return Item(
            id = id,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun updateoldItem(id: Int, itemName: String, itemPrice: String, itemCount: String){
        val updateold = updateItemEntry(id, itemName, itemPrice, itemCount)
        updateItem(updateold)
    }

    private fun updateItem(item:Item){
        viewModelScope.launch {
            itemmDao.update(item)
        }
    }


    fun sellitem(item:Item) {
        if (item.quantityInStock > 0) {
            val flavor = item.copy(quantityInStock = item.quantityInStock - 1, itemPrice = item.itemPrice - 2)
            updateItem(flavor)
            //updatePrice()
        }
    }

        fun increaseItem(item:Item){
            if (item.itemPrice < 24){
                val flavor = item.copy(quantityInStock = item.quantityInStock.inc(), itemPrice = item.itemPrice + 2)
                updateItem(flavor)
            }
        }

    fun checkInc(item: Item): Boolean{
        return item.itemPrice <= 23
       }

    fun checkDec(item: Item): Boolean{
        return item.quantityInStock >= 1
    }

    fun deleteItem(item:Item){
        viewModelScope.launch {
            itemmDao.delete(item)
        }
    }



    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        inserttem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun isFlavorValid(itemName: String): Boolean{
        if (itemName.isBlank()){
            return false
        }
        return true
    }

    init {
        resetValue()
    }

    fun resetValue(){
        _quantity.value = 0
        _flavor.value = ""
        _price.value = 0.0
    }
}

class dataviewModelFactory(private val itemmDao: ItemmDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(dataViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return dataViewModel(itemmDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}