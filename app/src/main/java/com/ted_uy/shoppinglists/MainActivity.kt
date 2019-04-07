package com.ted_uy.shoppinglists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ted_uy.shoppinglists.Adaptor.ShoppingAdaptor
import com.ted_uy.shoppinglists.DBHandler.DBHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        lateinit var  dbHandler : DBHandler

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHandler = DBHandler(this, null,null, 1)
        bindButton()
        viewList()
    }

    fun bindButton() {

        addButton.setOnClickListener {
             if(newTextView.text.isEmpty()){
                Toast.makeText(this, "Enter List Name", Toast.LENGTH_SHORT).show()
                newTextView.requestFocus()
            } else {
                val listNames = ShoppingModel()
                listNames.listName = newTextView.text.toString()
                MainActivity.dbHandler.addList(this,listNames)
                newTextView.setText("")
                newTextView.requestFocus()
            }
            viewList()
        }
    }

    private fun viewList() {
       val shoppingListName = dbHandler.getListNames(this)
        val adapter = ShoppingAdaptor(this, shoppingListName)
             listRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
            listRecyclerView.adapter = adapter
    }

    override fun onResume() {
        viewList()
        super.onResume()
    }
}
