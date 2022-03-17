package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter( val onitemClicked: (Item) -> Unit ) : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffcallBack){
    companion object  {
        val DiffcallBack = object: DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {

                return oldItem.itemName == newItem.itemName
            }

        }
    }

    class ItemViewHolder(private var binding: ItemListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item){
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.getCurrencyInstance()
                itemQuantity.text = item.quantityInStock.toString()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ItemListAdapter.ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
holder.itemView.setOnClickListener {
    onitemClicked(currentItem)
}
        holder.bind(currentItem)

    }


}