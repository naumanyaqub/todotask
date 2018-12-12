package com.carters.appdev.todotask.retail.framework.model


import com.carters.appdev.retail.framework.App
import com.google.gson.GsonBuilder
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.reflect.KClass

/**
 * ListView provides data encapsulation, serialization to files,
 * and API URL calls. It is VERY important.
 */

open class TodoListView()
{
    companion object {
        const val BASE_URL_DEFAULT :String = "jsonplaceholder.typicode.com"
        const val API_LEVEL = "/todos"

    }

    /**
     * Gets the object(s) at the specified URL.
     * Note: We append https:// here. Don't include that in the URLString
     * @return the results of the post.
     * @see Result
     */
    fun urlGet(urlString:String, listener: ResultListener)
    {
        val url = URL(String.format("https://%s", urlString))
        with(url.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"


            println("\nSending 'GET' request to URL : $url")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                println(response.toString())

//                var objs = Gson().fromJson(response.toString(), TypeToken <ArrayList<todostruct::class.java>)


//               listener.result("GET", Result(true, 0, objs))
//
            }
        }
    }

    /**
     * Read checks the local file system for the particular file.
     * This type of read is for singleton files. For instance,
     * It will check the file system /Item.json and return the
     * contents of the JSON file as an object of the given type.
     */
    fun read(cName: String, kClass: KClass<out TodoListView>): TodoListView? {

        val filesDir = App.appContext!!.applicationInfo.dataDir
        try {

            val builder = GsonBuilder()
            val gson = builder.create()

            var url = "/"
            url += cName
            url += ".json"
            val file = File(filesDir, url)
            return if (file.exists())
            {
                val bufferedReader = BufferedReader(
                    FileReader(file))
                gson.fromJson(bufferedReader, kClass.java) as TodoListView

            }
            else
            {
                null
            }

        } catch (e: Exception) {
            return null
        }

    }
    /**
     * Read checks the local file system for the particular file.
     * This type of read is for non singleton files.
     * contents of the JSON file as an object of the given type.
     */
    fun read(cName: String, kClass: KClass<out TodoListView>, id: String): TodoListView? {

        val filesDir =  App.appContext!!.applicationInfo.dataDir
        try {

            val builder = GsonBuilder()
            val gson = builder.create()

            var url = "/"
            url += cName
            url += "/"
            var file = File(filesDir, url)
            if (!file.exists())
            {
                file.mkdir()
            }
            url = String.format("%s%s.json", url, id)

            file = File(filesDir, url)
            return if (file.exists())
            {
                val bufferedReader = BufferedReader(
                    FileReader(file))

                gson.fromJson(bufferedReader, kClass.java) as TodoListView

            }
            else
            {
                null
            }

        } catch (e: Exception) {
            return null
        }

    }

    /**
     * ReadAll checks the local file system for the particular file.
     * This type of readAll is for non singleton files. For instance,
     * Let's say you have a bunch of Item objects.
     * It will check the file system /Item/, and return the
     * contents of every JSON file in this folder as an object of the given type.
     */
    fun readAll(cName: String, kClass: KClass<out TodoListView>): ArrayList<TodoListView>? {

        val out = ArrayList<TodoListView>()
        val filesDir = App.appContext!!.applicationInfo.dataDir
        try {

            val builder = GsonBuilder()
            val gson = builder.create()

            var url = "/"
            url += cName
            url += "/"
            val file = File(filesDir, url)
            if (!file.exists())
            {
                file.mkdir()
            }

            file.listFiles().forEach {

                val bufferedReader = BufferedReader(
                    FileReader(it))
                out.add(gson.fromJson(bufferedReader, kClass.java) as TodoListView)

            }

            return out


        } catch (e: Exception) {
            return out
        }

    }

    /**
     * DropAll checks the local file system for the particular file types.
     * This type of DropAll is for non singleton files. For instance,
     * Let's say you have a bunch of Item objects.
     * It will check the file system /Item/, and delete any files in this folder.
     */
    fun dropAll(cName: String) {

        val filesDir = App.appContext!!.applicationInfo.dataDir
        try {

            var url = "/"
            url += cName
            url += "/"
            val file = File(filesDir, url)
            if (!file.exists())
            {
                file.mkdir()
            }

            file.listFiles().forEach {
                it.delete()
            }

        } catch (e: Exception)
        {
        }

    }







/**
 * Drop removes the file with the given id.
 */
fun drop(cName: String): Boolean
{

    val filesDir = App.appContext!!.applicationInfo.dataDir
    try {
        var url = "/"
        url += cName
        url += "/"
        var file = File(filesDir, url)
        if (!file.exists())
        {
            file.mkdir()
        }


        file = File(filesDir, url)
        return file.delete()

    } catch (e: Exception)
    {
        return false
    }

}

/**
 * Write writes the contents of the object as a file with the given id.
 */
fun write(cName: String, isSingleton: Boolean): Boolean {
    val filesDir = App.appContext!!.applicationInfo.dataDir
    try {

        val builder = GsonBuilder()
        val gson = builder.create()

        var url = "/"
        var file: File

       

            url += cName
            url += ".json"
            file = File(filesDir, url)

        


        val writer = FileWriter(file)
        val toJson = gson.toJson(this)
        writer.write(toJson)
        writer.close()
        return true

    } catch (e: Exception) {
        return false
    }

}
}


