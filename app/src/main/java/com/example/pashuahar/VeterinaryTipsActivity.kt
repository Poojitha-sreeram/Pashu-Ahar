package com.example.pashuahar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VeterinaryTipsActivity : AppCompatActivity() {

    private lateinit var adapter: TipsAdapter
    private lateinit var tipsList: List<Tip>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veterinary_tips)

        // Inside VeterinaryTipsActivity.kt - update the tipsList:
        tipsList = listOf(
            Tip("Clean Drinking Water", "Provide fresh clean water daily for healthy cattle.", android.R.drawable.ic_menu_gallery),
            Tip("Regular Vet Checkups", "Routine veterinary care improves cattle health.", android.R.drawable.ic_menu_info_details),
            Tip("Seasonal Care Tips", "Protect cattle during summer, monsoon, and winter.", android.R.drawable.ic_menu_my_calendar),
            Tip("Vaccination Schedule", "Vaccinate cattle regularly to prevent diseases.", android.R.drawable.ic_menu_today),
            Tip("Feed Storage", "Store feed in dry and hygienic conditions.", android.R.drawable.ic_menu_save),
            Tip("Mineral Supplements", "Minerals improve milk production and immunity.", android.R.drawable.ic_input_add),

            // --- NEW TIPS ADDED BELOW ---
            Tip("Mastitis Prevention", "Maintain strict hygiene during milking to prevent udder infections.", android.R.drawable.ic_lock_lock),
            Tip("Deworming Protocol", "Regular deworming helps in better weight gain and milk yield.", android.R.drawable.ic_menu_compass),
            Tip("Calf Care (Colostrum)", "Ensure newborn calves get colostrum within 2 hours of birth.", android.R.drawable.ic_menu_view),
            Tip("Hoof Management", "Trim and clean hooves to prevent lameness and infections.", android.R.drawable.ic_menu_edit)
        )

        val rvTips = findViewById<RecyclerView>(R.id.rvTips)

        // Use explicit type to fix "Cannot infer type" error
        adapter = TipsAdapter(tipsList) { selectedTip: Tip ->
            val intent = Intent(this, TipDetailActivity::class.java)
            intent.putExtra("TIP_TITLE", selectedTip.title)
            startActivity(intent)
        }

        rvTips.layoutManager = LinearLayoutManager(this)
        rvTips.adapter = adapter

        findViewById<FloatingActionButton>(R.id.btnBackTips).setOnClickListener {
            finish()
        }
    }
}