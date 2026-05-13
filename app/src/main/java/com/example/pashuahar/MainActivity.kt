package com.example.pashuahar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText

/**
 * MainActivity handles farmer data entry using a modern Stepper UI.
 * Steps: Breed -> Age -> Weight -> Milk Yield -> Review
 */
class MainActivity : AppCompatActivity() {

    private var currentStep = 1
    private val totalSteps = 5

    // UI components
    private lateinit var tvStepTitle: TextView
    private lateinit var tvStepCount: TextView
    private lateinit var stepProgress: LinearProgressIndicator

    private lateinit var layoutStep1: LinearLayout
    private lateinit var layoutStep2: LinearLayout
    private lateinit var layoutStep3: LinearLayout
    private lateinit var layoutStep4: LinearLayout
    private lateinit var layoutStep5: LinearLayout

    private lateinit var breedDropdown: AutoCompleteTextView
    private lateinit var ageInput: TextInputEditText
    private lateinit var weightInput: TextInputEditText
    private lateinit var milkYieldInput: TextInputEditText
    private lateinit var tvSummary: TextView

    private lateinit var btnNext: Button
    private lateinit var btnPrevious: Button
    private lateinit var generateButton: Button
    private lateinit var btnVideoReference: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle system bars for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        setupStepper()
        setupListeners()
    }

    private fun initializeViews() {

        tvStepTitle = findViewById(R.id.tvStepTitle)
        tvStepCount = findViewById(R.id.tvStepCount)
        stepProgress = findViewById(R.id.stepProgress)

        layoutStep1 = findViewById(R.id.layoutStep1)
        layoutStep2 = findViewById(R.id.layoutStep2)
        layoutStep3 = findViewById(R.id.layoutStep3)
        layoutStep4 = findViewById(R.id.layoutStep4)
        layoutStep5 = findViewById(R.id.layoutStep5)

        breedDropdown = findViewById(R.id.breedDropdown)
        ageInput = findViewById(R.id.ageInput)
        weightInput = findViewById(R.id.weightInput)
        milkYieldInput = findViewById(R.id.milkYieldInput)
        tvSummary = findViewById(R.id.tvSummary)

        btnNext = findViewById(R.id.btnNext)
        btnPrevious = findViewById(R.id.btnPrevious)
        generateButton = findViewById(R.id.generateButton)
        btnVideoReference = findViewById(R.id.btnVideoReference)

        /**
         * Setup Breed Dropdown
         */
        val breeds = arrayOf(

            "Jersey",

            "Desi",

            "Holstein Friesian",

            "Gir",

            "Sahiwal",

            "Red Sindhi",

            "Ongole",

            "Brown Swiss",

            "Murrah Buffalo",

            "HF Cross",

            "Kankrej",

            "Tharparkar",

            "Rathi",

            "Deoni",

            "Hallikar",

            "Amrit Mahal",

            "Krishna Valley",

            "Nagori",

            "Malnad Gidda",

            "Khillar",

            "Hariana",

            "Bargur",

            "Dangi",

            "Vechur"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            breeds
        )

        breedDropdown.setAdapter(adapter)
    }

    private fun setupStepper() {
        updateStepUI()
    }

    private fun setupListeners() {
        // Video Section Navigation
        btnVideoReference.setOnClickListener {
            startActivity(Intent(this, VideoReferenceActivity::class.java))
        }

        // Stepper Navigation - Next
        btnNext.setOnClickListener {
            if (validateCurrentStep()) {
                if (currentStep < totalSteps) {
                    currentStep++
                    updateStepUI()
                }
            }
        }

        // Stepper Navigation - Previous
        btnPrevious.setOnClickListener {
            if (currentStep > 1) {
                currentStep--
                updateStepUI()
            }
        }

        // Final Generate Button
        generateButton.setOnClickListener {
            performCalculation()
        }

        // Feature Buttons
        findViewById<Button>(R.id.btnVetTips).setOnClickListener {
            startActivity(Intent(this@MainActivity, VeterinaryTipsActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewHistory).setOnClickListener {
            startActivity(Intent(this@MainActivity, FeedHistoryActivity::class.java))
        }
    }

    private fun updateStepUI() {
        // Update Progress Bar
        val progress = (currentStep * 100) / totalSteps
        stepProgress.setProgress(progress, true)

        // Update Step Counter (e.g., "1 of 5")
        tvStepCount.text = getString(R.string.step_count_format, currentStep)

        // Handle Layout Visibility
        layoutStep1.visibility = if (currentStep == 1) View.VISIBLE else View.GONE
        layoutStep2.visibility = if (currentStep == 2) View.VISIBLE else View.GONE
        layoutStep3.visibility = if (currentStep == 3) View.VISIBLE else View.GONE
        layoutStep4.visibility = if (currentStep == 4) View.VISIBLE else View.GONE
        layoutStep5.visibility = if (currentStep == 5) View.VISIBLE else View.GONE

        // Update Header Title based on Step
        tvStepTitle.text = when (currentStep) {
            1 -> getString(R.string.step_1_title)
            2 -> getString(R.string.step_2_title)
            3 -> getString(R.string.step_3_title)
            4 -> getString(R.string.step_4_title)
            5 -> getString(R.string.step_5_title)
            else -> ""
        }

        // Navigation Button Visibility
        btnPrevious.visibility = if (currentStep > 1) View.VISIBLE else View.INVISIBLE

        if (currentStep == totalSteps) {
            btnNext.visibility = View.GONE
            generateButton.visibility = View.VISIBLE
            updateSummaryText()
        } else {
            btnNext.visibility = View.VISIBLE
            generateButton.visibility = View.GONE
        }

        // Apply smooth fade-in animation to the entering layout
        val currentLayout = when (currentStep) {
            1 -> layoutStep1
            2 -> layoutStep2
            3 -> layoutStep3
            4 -> layoutStep4
            else -> layoutStep5
        }
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 400
        currentLayout.startAnimation(fadeIn)
    }

    private fun validateCurrentStep(): Boolean {
        return when (currentStep) {
            1 -> {
                if (breedDropdown.text.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            2 -> {
                if (ageInput.text.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            3 -> {
                if (weightInput.text.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            4 -> {
                if (milkYieldInput.text.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            else -> true
        }
    }

    private fun updateSummaryText() {
        val summary = "Breed: ${breedDropdown.text}\n" +
                "Age: ${ageInput.text} years\n" +
                "Weight: ${weightInput.text} kg\n" +
                "Daily Yield: ${milkYieldInput.text} L"
        tvSummary.text = summary
    }

    private fun performCalculation() {
        try {
            val breed = breedDropdown.text.toString()
            val weightStr = weightInput.text.toString()
            val yieldStr = milkYieldInput.text.toString()

            // Ensure numeric fields are valid
            val weight = weightStr.toFloat()
            val yield = yieldStr.toFloat()

            /**
             * FIXED: Corrected Argument Order
             * Signature: calculateFeed(breed: String, weight: Float, milkYield: Float)
             */
            val recipe = FeedCalculator.calculateFeed(breed, weight, yield)

            val intent = Intent(this@MainActivity, ResultActivity::class.java).apply {
                putExtra("EXTRA_FEED_RECIPE", recipe)
            }
            startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
        }
    }
}
