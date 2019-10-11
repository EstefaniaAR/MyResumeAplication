package com.example.estefaniaar.myresumeaplication

import android.app.Activity
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class CustomAdapter(private val context: Activity, private val expList: ArrayList<Experience>):BaseAdapter()
{
    private class ViewHolder(row: View?) {
        var name: TextView? = null
        var position: TextView? = null
        var date: TextView? = null
        var task:TextView?=null
        init {
            this.name = row?.findViewById<TextView>(R.id.enter)
            this.position = row?.findViewById<TextView>(R.id.position_exp)
            this.date = row?.findViewById<TextView>(R.id.date)
            this.task = row?.findViewById<TextView>(R.id.task)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        val view: View
        val viewHolder: CustomAdapter.ViewHolder
        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custon_adapter, null)
            viewHolder = CustomAdapter.ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as CustomAdapter.ViewHolder
        }

        viewHolder.date?.text= expList[position].date
        viewHolder.position?.text= expList[position].position
        viewHolder.name?.text= expList[position].enterprise
        viewHolder.task?.text=expList[position].task

        notifyDataSetChanged();
        return view as View
    }

    override fun getItem(position: Int): Any {
        return expList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return expList.size
    }


}