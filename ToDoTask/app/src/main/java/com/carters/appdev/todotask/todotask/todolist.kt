package com.carters.appdev.todotask.todotask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.carters.appdev.todotask.R
import com.carters.appdev.todotask.retail.framework.model.Result
import com.carters.appdev.todotask.retail.framework.model.TodoListView
import com.carters.appdev.todotask.retail.framework.model.ResultListener
import com.carters.appdev.todotask.retail.framework.model.todostruct
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class todolist : AppCompatActivity(), ResultListener {

    val todo: ArrayList<todostruct> = ArrayList()
    var rcv: RecyclerView? = null

    override fun result(requestType: String, result: Result)
    {
        print(Result.toString())
        // Loads animals into the ArrayList
        addTodo()
        val newsList : java.util.ArrayList<todostruct> = parseJsonStringToNewsList(Result.toString())


    }

    var todolist:TodoListView  =  TodoListView()
    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)
        var rcv = findViewById<RecyclerView>(R.id.rv_todo_list)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = todolistadopter(todo)


        addTodo()


        /**
         * async api call
         * and API URL calls. It is VERY important.
         */
        doAsync {
            todolist.urlGet(TodoListView.BASE_URL_DEFAULT + TodoListView.API_LEVEL, this@todolist)

        }

    }

    override fun onResume() {
        super.onResume()
    }

    private fun parseJsonStringToNewsList(jsonString: String): java.util.ArrayList<todostruct> {
        val todoList : java.util.ArrayList<todostruct> = java.util.ArrayList<todostruct>(0)
        val todoArray = JSONArray(jsonString)
        var i = 0
        var numIterations = todoArray.length()
        while(i < numIterations){
            val todoObject: JSONObject = todoArray.getJSONObject(i)
            val todo = todostruct()
            todo.todouserid = todoObject.getString("userId").toInt()
            todo.todotitle = todoObject.getString("title")
            todo.todocompletion = todoObject.getString("completed").toBoolean()
            todoList.add(todo)
            i++
        }
        return todoList
    }


    /**
     * Just for testing
     */
    fun addTodo() {

        for (i in 0..10) {
            var valueadd =  todostruct()

            valueadd.todoid = i
            valueadd.todouserid = i
            if (i%2 == 0) {
                valueadd.todocompletion = true
            } else {
                valueadd.todocompletion = false

            }
            valueadd.todotitle = "user task " + i
            todo.add(valueadd)
        }

    }


    /**
     * getting the json file, serialization to files.
     */
    private fun readJsonFromKotlinFile() :String{
        var inputString = ""
        try {
            val inputStream: InputStream = assets.open("todo.json")
            inputString = inputStream.bufferedReader().use{it.readText()}
            Log.d(TAG,inputString)
        } catch (e:Exception){
            Log.d(TAG, e.toString())
        }
        return inputString
    }
}
