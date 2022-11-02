package com.example.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class History : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarHistory)
        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDate(historyDao)
    }

    private fun getAllCompletedDate(historyDao:Dao){
        lifecycleScope.launch {
            historyDao.fetchALlDates().collect { completeDates->
                if(completeDates.isNotEmpty()){
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.text.visibility = View.VISIBLE
                    binding.text1.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@History)

                    val dates = ArrayList<String>()
                    for(date in completeDates){
                        dates.add(date.date)
                    }
                    val adapter = HistoryAdapter(dates)

                    binding.rvHistory.adapter = adapter
                }else{
                    binding.rvHistory.visibility = View.GONE
                    binding.text.visibility = View.GONE
                    binding.text1.visibility = View.VISIBLE
                }

            }
        }
    }
}