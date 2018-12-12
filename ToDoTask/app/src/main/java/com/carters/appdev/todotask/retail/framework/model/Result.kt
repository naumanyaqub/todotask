package com.carters.appdev.todotask.retail.framework.model;

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Anything that needs an API result must implement this interface.
 */
interface ResultListener
{
    fun result(requestType: String, result: Result)
}


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}

/**
 * Result encapsulates the data we need from an API call.
 * @param ok
 * @param error
 * @param obj
 */
data class Result(val ok: Boolean, val error: Int, val obj: ArrayList<TodoListView>?)
{
    companion object
    {

        val OK = 0
        val ERROR_REQUIRED = 1
        val ERROR_NOT_FOUND = 2
        val ERROR_NO_STORE = 3
        val ERROR_NO_NETWORK = 4
        val ERROR_GENERAL = 5
        val ERROR_NOT_LOGGED_IN = 6
        val ERROR_SOME_ERRORS = 7

    }

}

