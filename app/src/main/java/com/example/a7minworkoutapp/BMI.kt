package com.example.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMI : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView :String = METRIC_UNITS_VIEW

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarBmi)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarBmi.setNavigationOnClickListener {
            onBackPressed()
        }
        metricView()

        binding.radio.setOnCheckedChangeListener { _, checkedId : Int ->
            if(checkedId == R.id.rbMetric){
                metricView()
            }else{
                usView()
            }
        }

        binding.btnCalculate.setOnClickListener {
            calculateUnits()
        }
    }

    private fun metricView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding.metricHeight.visibility = View.VISIBLE
        binding.usFeet.visibility = View.GONE
        binding.usInch.visibility = View.GONE

        binding.height.text?.clear()
        binding.weight.text?.clear()
        binding.llBmiResult.visibility = View.INVISIBLE
    }

    private fun usView(){
        currentVisibleView = US_UNITS_VIEW
        binding.metricHeight.visibility = View.INVISIBLE
        binding.usFeet.visibility = View.VISIBLE
        binding.usInch.visibility = View.VISIBLE

        binding.weight.hint = "WEIGHT (in pounds)"

        binding.feet.text?.clear()
        binding.inch.text?.clear()
        binding.llBmiResult.visibility = View.INVISIBLE
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnit()){
                val heightValue : Float = binding.height.text.toString().toFloat()/100

                val weightValue : Float = binding.weight.text.toString().toFloat()

                val bmi = weightValue / (heightValue*heightValue)

                displayBmiResult(bmi)
            }else{
                Toast.makeText(this@BMI,
                    "Please enter valid values", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            if(validateUSUnit()){
                val weightValue : Float = binding.weight.text.toString().toFloat()

                val feetValue : Float = binding.feet.text.toString().toFloat()
                val inchValue : Float = binding.inch.text.toString().toFloat()

                val heightValue = inchValue + feetValue*12

                val bmi = 703 * (weightValue / (heightValue*heightValue))

                displayBmiResult(bmi)
            }else{
                Toast.makeText(this@BMI,
                    "Please enter valid values", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun displayBmiResult(bmi:Float){
        var bmiLabel :String = binding.tvBmiType.text.toString()
        var bmiDescription : String =binding.tvBmiDesc.text.toString()

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout more!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese class | (Moderately Obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout more!"
        }
        else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese class || (Severely Obese)"
            bmiDescription = "OMG! You are in a dangerous position! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.llBmiResult.visibility = View.VISIBLE
        binding.tvBmiValue.text = bmiValue
        binding.tvBmiType.text = bmiLabel
        binding.tvBmiDesc.text = bmiDescription
    }

    private fun validateMetricUnit():Boolean{
        var isValid = true
        if(binding.weight.text.toString().isEmpty()){
            isValid = false
        }else if(binding.height.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnit():Boolean{
        var isValid = true
        when {
            binding.weight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.feet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.inch.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }
}