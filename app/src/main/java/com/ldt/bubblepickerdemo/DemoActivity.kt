package com.ldt.bubblepickerdemo

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.ldt.bubblepicker.BubblePickerListener
import com.ldt.bubblepicker.adapter.BubblePickerAdapter
import com.ldt.bubblepicker.model.BubbleGradient
import com.ldt.bubblepicker.model.PickerItem
import com.ldt.bubblepicker.rendering.Item
import kotlinx.android.synthetic.main.activity_demo.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by irinagalata on 1/19/17.
 */
class DemoActivity : AppCompatActivity() {

    private val boldTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_BOLD) }
    private val mediumTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_MEDIUM) }
    private val regularTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_REGULAR) }

    companion object {
        private const val ROBOTO_BOLD = "roboto_bold.ttf"
        private const val ROBOTO_MEDIUM = "roboto_medium.ttf"
        private const val ROBOTO_REGULAR = "roboto_regular.ttf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        titleTextView.typeface = mediumTypeface
        subtitleTextView.typeface = boldTypeface
        hintTextView.typeface = regularTypeface
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            subtitleTextView.letterSpacing = 0.06f
            hintTextView.letterSpacing = 0.05f
        }

        val titles = resources.getStringArray(R.array.countries)
        Log.d("DemoActivity","Size = "+titles.size)

        val colors = resources.obtainTypedArray(R.array.colors)
        val images = resources.obtainTypedArray(R.array.images)
        picker.bubbleSize =  9
        Item.bitmapSize = 128f
        Item.textSizeRatio = 40f/280
        picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]

                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    typeface = mediumTypeface

                    textColor = ContextCompat.getColor(this@DemoActivity, android.R.color.white)
                    try {
                        backgroundImage = ContextCompat.getDrawable(this@DemoActivity, images.getResourceId(position % titles.size, 0))
                    } catch (ignored: Exception) {}
                }
            }
        }

        colors.recycle()
        images.recycle()

        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                //toast("${item.title} selected")
            }

            override fun onBubbleDeselected(item: PickerItem) {
                //toast("${item.title} deselected")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}