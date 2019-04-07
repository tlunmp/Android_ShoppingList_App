package com.ted_uy.shoppinglists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ted_uy.shoppinglists.Adaptor.ListItemAdaptor
import com.ted_uy.shoppinglists.DBHandler.DBHandler
import kotlinx.android.synthetic.main.activity_list_item.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_row.*

class ListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        lateinit var  dbHandler : DBHandler

        val intent = Intent()
        val number = getIntent().extras!!.getInt("ItemID")


        Log.e("MSG", "${number}")

        bindButton(number)
        viewItemList(number)

    }

    fun bindButton(number: Int) {

        addItemButton.setOnClickListener {
            if(itemMainTextView.text.isEmpty()){
                Toast.makeText(this, "Enter Item Name", Toast.LENGTH_SHORT).show()
                itemMainTextView.requestFocus()
            } else if (quanityMainTextView.text.isEmpty()) {
                Toast.makeText(this, "Enter Quanity", Toast.LENGTH_SHORT).show()
                quanityMainTextView.requestFocus()
            } else if(priceMainTextView.text.isEmpty()) {
                Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show()
                priceMainTextView.requestFocus()
            } else {
                val itemNames = ItemModel()

                itemNames.itemListName = itemMainTextView.text.toString()
                itemNames.itemListId = number
                itemNames.quantity = quanityMainTextView.text.toString().toInt()
                itemNames.price = priceMainTextView.text.toString().toDouble()

                MainActivity.dbHandler.addItemList(this, itemNames)

                itemMainTextView.setText("")
                quanityMainTextView.setText("")
                priceMainTextView.setText("")
                itemMainTextView.requestFocus()

            }
           viewItemList(number)
        }
    }


    private fun viewItemList(number: Int) {
        val itemListName = MainActivity.dbHandler.getItemListNames(this,number)
        val adapter = ListItemAdaptor(this, itemListName)
        itemListRecycleView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        itemListRecycleView.adapter = adapter
    }
}
