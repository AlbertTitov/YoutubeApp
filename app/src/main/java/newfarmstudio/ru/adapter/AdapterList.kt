package newfarmstudio.ru.adapter

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.toolbox.ImageLoader
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper
import newfarmstudio.ru.R
import newfarmstudio.ru.utils.MySingleton
import newfarmstudio.ru.utils.Utils

class AdapterList(context: Context, list: ArrayList<HashMap<String, String>>) :
    UltimateViewAdapter<RecyclerView.ViewHolder>() {

    private var DATA: ArrayList<HashMap<String, String>> = list
    private var imageLoader: ImageLoader = MySingleton.getInstance(context).getImageLoader()
    private var interpolator: Interpolator = LinearInterpolator()
    private var lastPosition = 5
    private val ANIMATION_DURATION: Long = 300

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateHeaderId(position: Int): Long {
        return 0
    }

    override fun getAdapterItemCount(): Int {
        return DATA.size
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_video_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position < itemCount &&
            (if (customHeaderView != null) { position <= DATA.size }
            else { position < DATA.size }) && (customHeaderView == null || position > 0)) {

            val item: HashMap<String, String> = DATA[if (customHeaderView != null) { position - 1 } else { position }]

            //Set data to the view
            (holder as ViewHolder).txtTitle.text = item[Utils.KEY_TITLE]
            holder.txtDuration.text = item[Utils.KEY_DURATION]
            holder.txtPublished.text = item[Utils.KEY_PUBLISHEDAT]

            //Set image to imageView
            imageLoader.get(item[Utils.KEY_URL_THUMBNAILS],
                ImageLoader.getImageListener(holder.imgThumbnail, R.mipmap.empty_photo, R.mipmap.empty_photo))
        }

        var isFirstOnly: Boolean = true

        if (!isFirstOnly || position > lastPosition) {

            val animations = getAdapterAnimations(holder.itemView, AdapterAnimationType.SlideInLeft)
            for (animator: Animator in animations) {
                animator.setDuration(ANIMATION_DURATION).start()
                animator.interpolator = interpolator
            }
            lastPosition = position
        }
        else {
            ViewHelper.clear(holder.itemView)
        }
    }

    override fun getViewHolder(view: View?): RecyclerView.ViewHolder {
        return UltimateRecyclerviewViewHolder(view)
    }

    class ViewHolder(view: View) : UltimateRecyclerviewViewHolder(view) {

        var txtTitle: TextView = view.findViewById(R.id.txtTitle)
        var txtPublished: TextView = view.findViewById(R.id.txtPublishedAt)
        var txtDuration: TextView = view.findViewById(R.id.txtDuration)

        var imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
    }
}