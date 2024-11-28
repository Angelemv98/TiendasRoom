package com.angelemv.android.tiendaroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.angelemv.android.tiendaroom.databinding.ActivityMainBinding
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mbinding: ActivityMainBinding
    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mbinding.btnSave.setOnClickListener {
            val storeEntity = StoreEntity(
                name = mbinding.etName.text.toString().trim()
            )
            Thread {
                StoreApplication.database.storeDao().addStore(storeEntity)
            }.start()
            mAdapter.add(storeEntity)
            mbinding.etName.text = null
        }
        setUpRecyclerView()
    }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread {
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.delete(queue.take())
    }


    override fun onClick(storeEntity: StoreEntity) {

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread {
            StoreApplication.database.storeDao().updateStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.update(queue.take())
    }

    private fun setUpRecyclerView() {
        mAdapter = StoreAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 2)
        mbinding.rv.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
        getStores()
    }

    private fun getStores() {
        val queue = LinkedBlockingQueue<MutableList<StoreEntity>>()
        Thread {
            val stores = StoreApplication.database.storeDao().getAllStores()
            queue.add(stores)
        }.start()
        mAdapter.setStores(queue.take())
    }
}
