package com.ted_uy.shoppinglists.Adaptor

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ted_uy.shoppinglists.ListItem
import com.ted_uy.shoppinglists.MainActivity
import com.ted_uy.shoppinglists.R
import com.ted_uy.shoppinglists.ShoppingModel
import kotlinx.android.synthetic.main.list_row.view.*
import kotlinx.android.synthetic.main.list_update.view.*


class ShoppingAdaptor(context: Context, val listName: ArrayList<ShoppingModel>) : RecyclerView.Adapter<ShoppingAdaptor.ViewHolder>() {

    val context = context



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtListName: TextView = itemView.listNameTextView
        val deleteButton: ImageButton = itemView.deleteButton
        val updateButton: ImageButton = itemView.updateButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listName.size
    }

    override fun onBindViewHolder(holder: ShoppingAdaptor.ViewHolder, position: Int) {
        val list = listName[position]
        holder.txtListName.text = list.listName

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListItem::class.java)
            intent.putExtra("ItemID", list.listID)
            context.startActivity(intent)

        }



        holder.deleteButton.setOnClickListener {
            MainActivity.dbHandler.deleteList(list.listID)
            listName.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,listName.size)
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()

        }

        holder.updateButton.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.list_update, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Update List Name")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.dialogUpdateBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //get text from EditTexts of custom layout
                val name = mDialogView.dialogNameEt.text.toString()

                //set the input text in TextView
                MainActivity.dbHandler.updateList(list.listID.toString(), name)

                listName[position].listName = name
                notifyDataSetChanged()

            }

            //cancel button click of custom layout
            mDialogView.dialogCancelBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }

        }

    }

}