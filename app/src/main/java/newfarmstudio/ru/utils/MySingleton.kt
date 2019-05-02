package newfarmstudio.ru.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class MySingleton private constructor(context: Context) {

    init {
        MySingleton.context = context
        MySingleton.requestQueue = getRequestQueue()
    }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.applicationContext)
        }
        return requestQueue!!
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        getRequestQueue().add(request)
    }

    fun getImageLoader() : ImageLoader {
        return imageLoader
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        private lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        private var instance : MySingleton? = null

        private var requestQueue : RequestQueue? = null

        private val imageLoader : ImageLoader = ImageLoader(requestQueue, object : ImageLoader.ImageCache {

            private val cache: LruCache<String, Bitmap> = LruCache(20)

            override fun getBitmap(url: String?): Bitmap {
                return cache.get(url)
            }
            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                cache.put(url, bitmap)
            }
        })

        @Synchronized
        fun getInstance(context: Context) : MySingleton {
            if (instance == null) {
                instance = MySingleton(context)
            }
            return instance!!
        }
    }
}