package com.ted_uy.shoppinglists.Adaptor

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ted_uy.shoppinglists.ItemModel
import com.ted_uy.shoppinglists.MainActivity
import com.ted_uy.shoppinglists.R
import kotlinx.android.synthetic.main.item_update.view.*
import kotlinx.android.synthetic.main.list_item_row.view.*


class ListItemAdaptor(context: Context, val itemList: ArrayList<ItemModel>) : RecyclerView.Adapter<ListItemAdaptor.ViewHolder>() {


    val context = context


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameView : TextView = itemView.nameTextView
        val itemQuantityView : TextView = itemView.quantityTextView
        val itemPriceView: TextView = itemView.priceTextView
        val itemDeleteButton : ImageButton = itemView.deleteItemButton
        val itemUpdateButton : ImageButton = itemView.updateItemButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row,parent,false)
        return ListItemAdaptor.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = itemList[position]

        Log.e("MSG", "${item.price.toString()}")
        holder.itemNameView.text = item.itemListName
        holder.itemPriceView.text = item.price.toString()
        holder.itemQuantityView.text = item.quantity.toString()


        holder.itemDeleteButton.setOnClickListener {
            MainActivity.dbHandler.deleteItemList(item.listID)
            itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,itemList.size)
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()

        }

        holder.itemUpdateButton.setOnClickListener {

            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.item_update, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Update Item")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.dialogUpdateItemBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //get text from EditTexts of custom layout
                val name = mDialogView.dialogNameItem.text.toString()
                val quantity = mDialogView.dialogQuantityItem.text.toString().toInt()
                val price = mDialogView.dialogPriceItem.text.toString().toDouble()

                //set the input text in TextView
                MainActivity.dbHandler.updateItemList(item.listID.toString(), name, price, quantity)

                itemList[position].itemListName = name
                itemList[position].price = price.toDouble()
                itemList[position].quantity = quantity.toInt()

                notifyDataSetChanged()

            }

            //cancel button click of custom layout
            mDialogView.dialogCancelItemBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }

        }
    }





}
