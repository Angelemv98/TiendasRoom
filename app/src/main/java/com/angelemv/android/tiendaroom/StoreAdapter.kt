package com.angelemv.android.tiendaroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelemv.android.tiendaroom.databinding.ItemStoreCardBinding

class StoreAdapter(
    private var stores: MutableList<StoreEntity>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreCardBinding.bind(view)
        fun setListener(storeEntity: StoreEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(storeEntity) }
                setOnClickListener { listener.onFavoriteStore(storeEntity) }
                setOnLongClickListener {
                    listener.onDeleteStore(storeEntity)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store_card, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores.get(position)
        with(holder) {
            setListener(store)
            binding.tvName.text = store.name
            binding.cbFavorite.isChecked = store.isFavorite
        }
    }

    fun add(storeEntity: StoreEntity) {
        stores.add(storeEntity)
        notifyDataSetChanged()
    }

    fun setStores(stores: MutableList<StoreEntity>) {
        this.stores = stores
        notifyDataSetChanged()
    }

    fun update(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)
        if (index != -1) {
            stores.set(index, storeEntity)
            notifyItemChanged(index)
        }
    }
    fun delete(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)
        if (index != -1) {
            stores.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}