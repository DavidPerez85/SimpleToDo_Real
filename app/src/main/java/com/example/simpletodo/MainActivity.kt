package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //Remove item from the list

                listOfTasks.removeAt(position)

                //notify adapter that data set has changed

                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        //findViewById<Button>(R.id.button).setOnClickListener{
            //Code will be executed when user clicks on button
//            Log.i("David", "User clicked on button")

//        }
        loadItems()

        //Look up recycleView in layout
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerViewer)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)


        //SETUP BUTTTON FOR USER INPUT AND ADD TO LIST

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            //grab text user has put in addtasfield
            val userInputtedTask = inputTextField.text.toString()


            //add string to list of tasks : listofTasks

            listOfTasks.add(userInputtedTask)

            //notify adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //reset text field/leave it blank
            inputTextField.setText("")

            saveItems()



        }
    }

    //save data that user has inputted
    //saving data from writing/reading from a file

    //create a method to get file we need

    fun getDataFile() : File {
        //Every line will be a task in our list
        return File(filesDir, "data.txt")
    }

    //method to load items by reading every line from file

    fun loadItems() {
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    //method to save items by writing them in a file

    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }

}