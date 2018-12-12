package com.carters.appdev.todotask.todotask

import android.support.v7.widget.RecyclerView;
import android.util.Log
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.carters.appdev.todotask.R
import com.carters.appdev.todotask.retail.framework.model.inflate
import com.carters.appdev.todotask.retail.framework.model.todostruct


class todolistadopter(private val todolisttemp: ArrayList<todostruct>): RecyclerView.Adapter<todolistadopter.NewsHolder>() {




    override fun getItemCount(): Int {
        return todolisttemp.size
    }

    override fun onBindViewHolder(holder: todolistadopter.NewsHolder, position: Int) {
        val item = todolisttemp[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todolistadopter.NewsHolder{
        val inflatedView = parent.inflate(R.layout.todo_list_item)
        return NewsHolder(inflatedView)
    }


    class NewsHolder(v:View) : RecyclerView.ViewHolder(v) , View.OnClickListener {

        private var titleView: TextView? = null
        private var shortView: TextView? = null
        private  var count: TextView? = null
        private var view : View = v
        private var listtodotemp : todostruct? = null

        init {

            titleView = v.findViewById(R.id.textViewTitle)
            shortView = v.findViewById(R.id.textViewShortDesc)
            count = v.findViewById(R.id.textViewRating)
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")


        }

        fun bind(temptodo: todostruct) {

            titleView!!.text = temptodo.todotitle
            shortView!!.text = "" + temptodo.todouserid
            count!!.text = "" + temptodo.todocompletion


        }
    }
}