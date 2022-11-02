package com.example.a7minworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkoutapp.databinding.ActivityExerciseBinding
import com.example.a7minworkoutapp.databinding.CustomLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 1
    private var maxProgress = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration :Long = 1

    private var exerciseList : ArrayList<Exercise>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBar.setNavigationOnClickListener {
            customDialogBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this,this)

        setRestView()
        setUpExerciseStatusAdapter()

    }

    private fun setUpExerciseStatusAdapter(){
        binding.rvExercise.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvExercise.adapter = exerciseAdapter
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressBar.progress = maxProgress - restProgress
                binding.tvTimer.text = (maxProgress - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setExerciseView()
            }

        }.start()
    }

    private fun setRestView(){

        /*try {
            val soundURI = Uri.parse(
                "android.resource://com.example.a7minworkoutapp/"
                        + R.raw.song)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

         */

        binding.flProgress.visibility = View.VISIBLE
        binding.flExerciseProgress.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseTitle.visibility = View.INVISIBLE
        binding.ivExercise.visibility = View.INVISIBLE
        binding.tvUpcoming.visibility = View.VISIBLE
        binding.tvUpcomingExercise.visibility = View.VISIBLE
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding.tvUpcomingExercise.text = exerciseList!![currentExercisePosition +1].getName()
        setRestProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding.progressExerciseBar.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.progressExerciseBar.progress = 10 - exerciseProgress
                binding.tvExerciseTimer.text = (10 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition < exerciseList?.size!! -1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity, Finish::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }
    fun setExerciseView(){
        binding.flProgress.visibility = View.INVISIBLE
        binding.flExerciseProgress.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.GONE
        binding.tvExerciseTitle.visibility = View.VISIBLE
        binding.ivExercise.visibility = View.VISIBLE
        binding.tvUpcoming.visibility = View.INVISIBLE
        binding.tvUpcomingExercise.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        val exercise: Exercise = exerciseList!![currentExercisePosition]

        speakOut(exercise.getName())
        binding.ivExercise.setImageResource(exercise.getImage())
        binding.tvExerciseTitle.text = exercise.getName()

        setExerciseProgressBar()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language is not supported")
            }else{
                Log.e("TTS", "initialization failed")
            }
        }
    }

    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun customDialogBackButton(){
        val dialog = Dialog(this)
        val dialogBinding = CustomLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener{
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        customDialogBackButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
    }

}