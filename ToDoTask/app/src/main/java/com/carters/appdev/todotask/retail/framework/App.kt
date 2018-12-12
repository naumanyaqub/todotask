package com.carters.appdev.retail.framework

import android.app.Application
import android.content.*


/**
 * Our main app. This must be configured in Manifest.xml.
 * @see Application
 * @see LifecycleObserver
 */
class App : Application()
{

    companion object {

        var appContext: Context? = null

    }
    /**
     * Basic android stuff.
     * On start, we create our idle timeout if needed.
     * Also, setup Mint.
     */
    override fun onCreate() {
        super.onCreate()

        App.appContext = applicationContext
    }

}