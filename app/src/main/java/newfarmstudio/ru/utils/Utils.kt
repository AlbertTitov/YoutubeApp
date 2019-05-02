package newfarmstudio.ru.utils

import android.app.Activity
import android.content.Context
import com.github.mrengineer13.snackbar.SnackBar
import newfarmstudio.ru.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {

        const val API_YOUTUBE = "https://www.googleapis.com/youtube/v3/"

        const val FUNCTION_SEARCH_YOUTUBE = "search?"
        const val FUNCTION_VIDEO_YOUTUBE = "videos?"
        const val FUNCTION_PLAYLIST_ITEMS_YOUTUBE = "playlistItems?"

        const val PARAM_KEY_YOUTUBE = "key="
        const val PARAM_CHANNEL_ID_YOUTUBE = "channelId="
        const val PARAM_PLAYLIST_ID_YOUTUBE = "playlistId="
        const val PARAM_VIDEO_ID_YOUTUBE = "id="
        const val PARAM_PART_YOUTUBE = "part="
        const val PARAM_PAGE_TOKEN_YOUTUBE = "pageToken="
        const val PARAM_ORDER_YOUTUBE = "order=date"
        const val PARAM_MAX_RESULT_YOUTUBE = "maxResults="
        const val PARAM_TYPE_YOUTUBE = "type=video"
        const val PARAM_FIELD_SEARCH_YOUTUBE = "fields=nextPageToken," + "pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))"
        const val PARAM_FIELD_VIDEO_YOUTUBE = "fields=pageInfo(totalResults)," + "items(contentDetails(duration))&"
        const val PARAM_FIELD_PLAYLIST_YOUTUBE = "fields=nextPageToken," + "pageInfo(totalResults),items(snippet(title,thumbnails,publishedAt,resourceId(videoId)))"
        const val PARAM_RESULT_PER_PAGE = 8
        const val ARRAY_PAGE_TOKEN = "nextPageToken"
        const val ARRAY_ITEMS = "items"
        const val OBJECT_ITEMS_ID = "id"
        const val OBJECT_ITEMS_CONTENT_DETAIL = "contentDetails"
        const val OBJECT_ITEMS_SNIPPET = "snippet"
        const val OBJECT_ITEMS_SNIPPET_THUMBNAILS = "thumbnails"
        const val OBJECT_ITEMS_SNIPPET_RESOURCEID = "resourceId"
        const val OBJECT_ITEMS_SNIPPET_THUMBNAILS_MEDIUM = "medium"
        const val KEY_VIDEO_ID = "videoId"
        const val KEY_TITLE = "title"
        const val KEY_PUBLISHEDAT = "publishedAt"
        const val KEY_URL_THUMBNAILS = "url"
        const val KEY_DURATION = "duration"
        const val ARG_TIMEOUT_MS = 4000
        const val TAG_FANDROID = "Fandroid:"
        const val TAG_CHANNEL_ID = "channel_id"
        const val TAG_VIDEO_TYPE = "video_type"

        // Method to check admob visibility
        /*fun admobVisibility(ad: AdView, isInDebugMode: Boolean): Boolean {
            if (isInDebugMode) {
                ad.setVisibility(View.VISIBLE)
                return true
            } else {
                ad.setVisibility(View.GONE)
                return false
            }
        }*/

        @JvmStatic
        fun showSnackBar(activity: Activity, message: String) {
            SnackBar.Builder(activity)
                .withMessage(message)
                .show()
        }

        @JvmStatic
        fun formatPublishedDate(activity: Activity, publishedDate: String): String? {
            var result = Date()
            val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            try {
                result = df1.parse(publishedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return getTimeAgo(result, activity)
        }

        @JvmStatic
        fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.getTime()
        }

        @JvmStatic
        fun getTimeAgo(date: Date?, ctx: Context): String? {

            if (date == null) {
                return null
            }

            val time = date.getTime()

            val curDate = currentDate()
            val now = curDate.getTime()
            if (time > now || time <= 0) {
                return null
            }

            val dim = getTimeDistanceInMinutes(time)

            var timeAgo: String? = null

            if (dim == 0) {
                timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_minute)
            } else if (dim == 1 ||
                dim == 21 ||
                dim == 31 ||
                dim == 41
            ) {
                timeAgo = dim.toString() + " " + ctx.getResources().getString(R.string.date_util_unit_minuta)
            } else if (dim == 2 || dim == 3 || dim == 4 ||
                dim == 22 || dim == 23 || dim == 24 ||
                dim == 32 || dim == 33 || dim == 34 ||
                dim == 42 || dim == 43 || dim == 44
            ) {
                timeAgo = dim.toString() + " " + ctx.getResources().getString(R.string.date_util_unit_minute)

            } else if (dim >= 5 && dim <= 20 ||
                dim >= 25 && dim <= 30 ||
                dim >= 35 && dim <= 40
            ) {
                timeAgo = dim.toString() + " " + ctx.getResources().getString(R.string.date_util_unit_minutes)
            } else if (dim >= 45 && dim <= 89) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_hour)

            } else if (dim >= 90 && dim <= 270) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        Math.round((dim / 60).toFloat()) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_hour)
            } else if (dim >= 271 && dim <= 1439) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        Math.round((dim / 60).toFloat()) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_hours)
            } else if (dim >= 1440 && dim <= 2519) {
                timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_daya)
            } else if (dim >= 2520 && dim <= 6480) {
                timeAgo = Math.round((dim / 1440).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_day)
            } else if (dim >= 6481 && dim <= 29000 || dim >= 34701 && dim <= 43200) {
                timeAgo = Math.round((dim / 1440).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_days)
            } else if (dim >= 29001 && dim <= 30500) {
                timeAgo = Math.round((dim / 1440).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_daya)
            } else if (dim >= 30501 && dim <= 34700) {
                timeAgo = Math.round((dim / 1440).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_day)
            } else if (dim >= 43201 && dim <= 86399) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_month)
            } else if (dim >= 86400 && dim <= 216000) {
                timeAgo = Math.round((dim / 43200).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_month)
            } else if (dim >= 216001 && dim <= 492480) {
                timeAgo = Math.round((dim / 43200).toFloat()).toString() + " " +
                        ctx.getResources().getString(R.string.date_util_unit_months)
            } else if (dim >= 492481 && dim <= 518400) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_year)
            } else if (dim >= 518401 && dim <= 914399) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_year)
            } else if (dim >= 914400 && dim <= 1051199) {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " +
                        ctx.getResources().getString(R.string.date_util_unit_years)
            } else {
                timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " +
                        Math.round((dim / 525600).toFloat()) + " " +
                        ctx.getResources().getString(R.string.date_util_unit_years)
            }

            return timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix)
        }

        private fun getTimeDistanceInMinutes(time: Long): Int {
            val timeDistance = currentDate().getTime() - time
            return Math.round((Math.abs(timeDistance) / 1000 / 60).toFloat())
        }

        @JvmStatic
        fun getTimeFromString(duration: String): String {
            var time = ""
            var hourexists = false
            var minutesexists = false
            var secondsexists = false
            if (duration.contains("H"))
                hourexists = true
            if (duration.contains("M"))
                minutesexists = true
            if (duration.contains("S"))
                secondsexists = true
            if (hourexists) {
                var hour: String
                hour = duration.substring(
                    duration.indexOf("T") + 1,
                    duration.indexOf("H")
                )
                if (hour.length == 1)
                    hour = "0$hour"
                time += "$hour:"
            }
            if (minutesexists) {
                var minutes: String
                if (hourexists)
                    minutes = duration.substring(
                        duration.indexOf("H") + 1,
                        duration.indexOf("M")
                    )
                else
                    minutes = duration.substring(
                        duration.indexOf("T") + 1,
                        duration.indexOf("M")
                    )
                if (minutes.length == 1)
                    minutes = "0$minutes"
                time += "$minutes:"
            } else {
                time += "00:"
            }
            if (secondsexists) {
                var seconds: String
                if (hourexists) {
                    if (minutesexists)
                        seconds = duration.substring(
                            duration.indexOf("M") + 1,
                            duration.indexOf("S")
                        )
                    else
                        seconds = duration.substring(
                            duration.indexOf("H") + 1,
                            duration.indexOf("S")
                        )
                } else if (minutesexists)
                    seconds = duration.substring(
                        duration.indexOf("M") + 1,
                        duration.indexOf("S")
                    )
                else
                    seconds = duration.substring(
                        duration.indexOf("T") + 1,
                        duration.indexOf("S")
                    )
                if (seconds.length == 1)
                    seconds = "0$seconds"
                time += seconds
            } else {
                time += "00"
            }
            return time
        }
    }
}