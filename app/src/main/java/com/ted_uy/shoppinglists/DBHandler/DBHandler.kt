package com.ted_uy.shoppinglists.DBHandler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ted_uy.shoppinglists.ItemModel
import com.ted_uy.shoppinglists.ShoppingModel

class  DBHandler(context: Context, name : String?, factory: SQLiteDatabase.CursorFactory?, version :Int) : SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    companion object {
        private val DB_NAME = "ShoppingList.db"
        private val DB_VERSION = 1

        private val LIST_TABLE_NAME = "ShoppingList"
        private val COL_LISTID = "listid"
        private val COL_LISTNAME = "listname"


        private val ITEM_TABLE_NAME = "ItemList"
        private val COL_ITEM_LISTID = "itemListID"
        private val COL_ITEM_LISTID_CATEGORY = "listIDCategory"
        private val COL_ITEMLISTNAME = "itemListName"
        private val COL_PRICE = "price"
        private val COL_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createListTable = ("CREATE TABLE $LIST_TABLE_NAME  (" +
                "$COL_LISTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_LISTNAME TEXT UNIQUE NOT NULL)")

        val createItemListTable = ("CREATE TABLE $ITEM_TABLE_NAME  (" +
                "$COL_ITEM_LISTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_ITEM_LISTID_CATEGORY INTEGER NOT NULL," +
                "$COL_ITEMLISTNAME TEXT UNIQUE NOT NULL," +
                "$COL_QUANTITY INTEGER NOT NULL DEFAULT(1)," +
                "$COL_PRICE DOUBLE DEFAULT 0)")

        db?.execSQL(createListTable)
        db?.execSQL(createItemListTable)

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getItemListNames(context: Context, itemIDCategory: Int): ArrayList<ItemModel> {
        val query = "Select * From $ITEM_TABLE_NAME WHERE $COL_ITEM_LISTID_CATEGORY = $itemIDCategory"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val itemListName = ArrayList<ItemModel>()

        if (cursor.count == 0) {
            Log.e("MSG", "No Data Found")
        } else {
            while (cursor.moveToNext()) {
                val listName = ItemModel()
                listName.listID = cursor.getInt(cursor.getColumnIndex(COL_ITEM_LISTID))
                listName.itemListName = cursor.getString(cursor.getColumnIndex(COL_ITEMLISTNAME))
                listName.price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                listName.quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
                itemListName.add(listName)
            }
            Log.e("MSG", "${cursor.count.toString()} Data Found")
        }

        cursor.close()
        db.close()
        return itemListName
    }

    fun getListNames(context: Context) : ArrayList<ShoppingModel> {

        val query = "Select * From $LIST_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val shoppingListName = ArrayList<ShoppingModel>()

        if (cursor.count == 0) {
            Log.e("MSG", "No Data Found")
        } else {
            while (cursor.moveToNext()) {
                val listName = ShoppingModel()
                listName.listID = cursor.getInt(cursor.getColumnIndex(COL_LISTID))
                listName.listName = cursor.getString(cursor.getColumnIndex(COL_LISTNAME))
                shoppingListName.add(listName)
            }
            Log.e("MSG", "${cursor.count.toString()} Data Found")
        }

        cursor.close()
        db.close()
        return shoppingListName
    }


    fun addItemList(context: Context, itemLists: ItemModel) {
        val values = ContentValues()
        values.put(COL_ITEMLISTNAME, itemLists.itemListName)
        values.put(COL_QUANTITY, itemLists.quantity)
        values.put(COL_PRICE, itemLists.price)
        values.put(COL_ITEM_LISTID_CATEGORY, itemLists.itemListId)

        val db = this.writableDatabase

        var result = db.insert(ITEM_TABLE_NAME, null, values)

        if(result == -1.toLong()) {
            Log.e("MSG", "Failed")
        } else {

            Log.e("MSG", "Success")
        }

        db.close()

    }


    fun addList(context: Context, shoppingLists: ShoppingModel) {
        val values = ContentValues()
        values.put(COL_LISTNAME, shoppingLists.listName)

        val db = this.writableDatabase

        var result = db.insert(LIST_TABLE_NAME, null, values)
        if(result == -1.toLong()) {
            Log.e("MSG", "Failed")
        } else {

            Log.e("MSG", "Success")
        }

        db.close()

    }

    fun deleteItemList(listID: Int) {
        val db = this.writableDatabase
        db.delete(ITEM_TABLE_NAME, "$COL_ITEM_LISTID = ?", arrayOf(listID.toString()))
        db.close()
    }


    fun deleteList(listID: Int) {
        val db = this.writableDatabase
        db.delete(LIST_TABLE_NAME, "$COL_LISTID = ?", arrayOf(listID.toString()))
        db.delete(ITEM_TABLE_NAME, "$COL_ITEM_LISTID_CATEGORY = ?", arrayOf(listID.toString()))
        db.close()
    }

    fun updateItemList(id: String, listName : String, price:Double, quantity: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ITEMLISTNAME,listName)
        values.put(COL_PRICE,price)
        values.put(COL_QUANTITY,quantity)
        db.update(ITEM_TABLE_NAME, values, "$COL_ITEM_LISTID = ?", arrayOf(id))
    }

    fun updateList(id: String, listName : String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_LISTNAME,listName)
        db.update(LIST_TABLE_NAME, values, "$COL_LISTID = ?", arrayOf(id))
    }

}