package newfarmstudio.ru.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import newfarmstudio.ru.R

class ActivityHome : AppCompatActivity() {

    private lateinit var drawer: Drawer
    private lateinit var toolbar: Toolbar

    private var channelNames: Array<String>? = null
    private var channelId: Array<String>? = null
    private var videoTypes: Array<String>? = null

    private var selectedDrawerItem: Int? = null

    private lateinit var layoutList: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        layoutList = findViewById(R.id.fragment_container)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        channelNames = resources.getStringArray(R.array.channel_names)
        channelId = resources.getStringArray(R.array.channel_id)
        videoTypes = resources.getStringArray(R.array.video_types)

        val primaryDrawerItems: ArrayList<IDrawerItem<*>> = arrayListOf()

        for (i in 0 until channelId!!.size - 1) {
            val name = channelNames!![i]
            primaryDrawerItems.add(PrimaryDrawerItem()
                .withName(name)
                .withIdentifier(i)
                .withSelectable(true))
        }

        val accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .build()

        drawer = DrawerBuilder(this)
            .withActivity(this)
            .withAccountHeader(accountHeader)
            .withDisplayBelowStatusBar(true)
            .withActionBarDrawerToggleAnimated(true)
            .withSavedInstance(savedInstanceState)
            .withDrawerItems(primaryDrawerItems)
            .addStickyDrawerItems(
                SecondaryDrawerItem()
                    .withName(getString(R.string.about))
                    .withIdentifier(channelId!!.size - 1)
                    .withSelectable(false)
            )
            .withOnDrawerNavigationListener {
                return@withOnDrawerNavigationListener false
            }
            .withSavedInstance(savedInstanceState)
            .withShowDrawerOnFirstLaunch(true)
            .build()
    }
}
