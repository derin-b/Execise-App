package com.example.a7minworkoutapp

object Constants {
    fun defaultExerciseList() : ArrayList<Exercise>{
        val exerciseList = ArrayList<Exercise>()

        val jumpingJacks = Exercise(
            1,
            "Jumping Jacks",
            R.drawable.jump,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(jumpingJacks)
        val squats = Exercise(
            2,
            "Squats",
            R.drawable.squat,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(squats)
        val lunges = Exercise(
            3,
            "Lunges",
            R.drawable.lunge,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(lunges)
        val mountainClimber = Exercise(
            4,
            "Mountain Climber",
            R.drawable.mountain,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(mountainClimber)
        val plank = Exercise(
            5,
            "Planks",
            R.drawable.plank,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(plank)
        val highKneesRunning = Exercise(
            6,
            "High Knees Running",
            R.drawable.high,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(highKneesRunning)
        val pushUp = Exercise(
            7,
            "Push Up",
            R.drawable.push,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(pushUp)
        val abdominalCrunch = Exercise(
            8,
            "Abdominal Crunch",
            R.drawable.abdominal,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(abdominalCrunch)
        val tricepsDips = Exercise(
            9,
            "Triceps Dip On Chair",
            R.drawable.tricep,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(tricepsDips)
        val sidePlank = Exercise(
            10,
            "Side Plank",
            R.drawable.side,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(sidePlank)
        val wallSit = Exercise(
            11,
            "Wall Sit",
            R.drawable.sit,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(wallSit)

        return exerciseList
    }
}