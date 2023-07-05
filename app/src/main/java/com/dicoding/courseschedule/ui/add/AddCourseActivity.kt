package com.dicoding.courseschedule.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private var startTime: String = ""
    private var endTime: String = ""
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
        addCourseViewModel.saved.observe(this,{
            if (it.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                val edCourseName = findViewById<TextInputEditText>(R.id.add_ed_course_name)
                val spinnerDay = findViewById<Spinner>(R.id.spinner_day)
                val edCourseLecturer = findViewById<TextInputEditText>(R.id.add_ed_lecture)
                val edNote = findViewById<TextInputEditText>(R.id.add_ed_note)

                var courseName = edCourseName.text.toString()
                var courseLecturer = edCourseLecturer.text.toString()
                var note = edNote.text.toString()

                if (courseName.isEmpty() && courseLecturer.isEmpty() && note.isEmpty() && spinnerDay.selectedItemPosition == Spinner.INVALID_POSITION && startTime == "" && endTime == ""){
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                }else if(courseName.isEmpty()){
                    edCourseName.setError("Course name can't be empty")
                }else if(courseLecturer.isEmpty()){
                    edCourseLecturer.setError("Course name can't be empty")
                }else if(note.isEmpty()){
                    edNote.setError("Course name can't be empty")
                }else if(spinnerDay.selectedItemPosition == Spinner.INVALID_POSITION){
                    Toast.makeText(this, "Please select the Day", Toast.LENGTH_SHORT).show()
                }else if(startTime == ""){
                    Toast.makeText(this, "Please select the Start Time", Toast.LENGTH_SHORT).show()
                }else if(endTime == ""){
                    Toast.makeText(this, "Please select the End Time", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            "startPicker" -> {
                findViewById<TextView>(R.id.add_tv_start_time).text = timeFormat.format(calendar.time)
                startTime = timeFormat.format(calendar.time)
            }
            "endPicker" -> {
                findViewById<TextView>(R.id.add_tv_end_time).text = timeFormat.format(calendar.time)
                endTime = timeFormat.format(calendar.time)
            }
        }
    }

    fun startTimePick(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "startPicker")
    }

    fun endTimePick(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "endPicker")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.add_ed_course_name).text.toString()
                val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val courseLecturer = findViewById<TextInputEditText>(R.id.add_ed_lecture).text.toString()
                val note = findViewById<TextInputEditText>(R.id.add_ed_note).text.toString()
                addCourseViewModel.insertCourse(courseName, day, startTime, endTime, courseLecturer, note)
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

}