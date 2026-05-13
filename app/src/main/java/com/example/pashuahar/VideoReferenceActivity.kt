package com.example.pashuahar

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VideoReferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            setBackgroundColor(android.graphics.Color.parseColor("#F5F5F5"))
        }

        val header = TextView(this).apply {
            // FIXED: Using getString() for translation
            text = getString(R.string.video_tutorials_title)
            textSize = 24f
            setTextColor(android.graphics.Color.parseColor("#2E7D32"))
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 50)
        }
        root.addView(header)

        // List of videos: Titles are now translated using resource IDs
        addVideoRow(root, getString(R.string.tip_hygiene), R.raw.vedio1)
        addVideoRow(root, getString(R.string.tip_mastitis), R.raw.video)
        addVideoRow(root, getString(R.string.tip_deworming), R.raw.video2)
        addVideoRow(root, getString(R.string.tip_hoof), R.raw.video3)

        setContentView(root)
    }

    private fun addVideoRow(layout: LinearLayout, title: String, resId: Int) {
        val btn = Button(this).apply {
            // FIXED: Using watch_prefix string resource
            text = "${getString(R.string.watch_prefix)} $title"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 20) }
            transformationMethod = null
        }

        btn.setOnClickListener { playVideo(resId) }
        layout.addView(btn)
    }

    private fun playVideo(resId: Int) {
        val dialog = android.app.Dialog(this)
        val videoView = VideoView(this)
        val videoPath = "android.resource://$packageName/$resId"

        videoView.setVideoURI(Uri.parse(videoPath))
        val mc = MediaController(this)
        mc.setAnchorView(videoView)
        videoView.setMediaController(mc)

        dialog.setContentView(videoView)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            800
        )
        videoView.start()
        dialog.show()
    }
}