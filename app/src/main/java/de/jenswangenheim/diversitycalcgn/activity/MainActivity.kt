package de.jenswangenheim.diversitycalcgn.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.ShareCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import de.jenswangenheim.diversitycalcgn.HolidayListAdapter
import de.jenswangenheim.diversitycalcgn.R
import de.jenswangenheim.diversitycalcgn.Request
import de.jenswangenheim.diversitycalcgn.ViewHolder
import de.jenswangenheim.diversitycalcgn.model.Holiday
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread



class MainActivity : AppCompatActivity(), ViewHolder.OnHolidayItemClickedListener {

    companion object {
        const val CAL_DATA_DOWNLOAD_URL = "http://jenswangenheim.de/diversity_calendar.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(R.layout.activity_main)
        requestDataAndBindViews()
    }

    private fun requestDataAndBindViews() {
        rvDates.layoutManager = LinearLayoutManager(this)

        doAsync {
            val items = Request(CAL_DATA_DOWNLOAD_URL).run()
            items.sortBy { it.from }
            uiThread {
                val datesList = items.map { it.from }
                rvDates.adapter = HolidayListAdapter(items, this@MainActivity)
                rvDates.scrollToPosition(Holiday.closestDate(datesList) - 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.send_mail -> {
                sendMail()
                true
            }
            R.id.imprint -> {
                showImprint()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendMail() {
        ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo("apps@jenswangenheim.de")
                .setSubject(getString(R.string.app_name))
                .setChooserTitle(getString(R.string.send_mail))
                .startChooser()
    }

    private fun showImprint() {
        AlertDialog.Builder(this)
                .setTitle(R.string.imprint)
                .setMessage(Html.fromHtml(getString(R.string.about_message)))
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    override fun onHolidayItemClicked(position: Int, holiday: Holiday, textView: TextView) {
        openDetailActivity(holiday, textView)
    }

    private fun openDetailActivity(holiday: Holiday, textView: TextView) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.HOLIDAY, holiday)
        intent.putExtra(DetailActivity.TRANSITION_NAME, ViewCompat.getTransitionName(textView))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    textView,
                    ViewCompat.getTransitionName(textView)!!)

            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }
}