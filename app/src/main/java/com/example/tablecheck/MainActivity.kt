package com.example.tablecheck

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.tablecheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val places = mutableMapOf<Int, Int>()
        var cellScoreNumber = 0
        for (i in 1..7) {
            val sumId = "sum$i"
            val sumResourceId = resources.getIdentifier(sumId, "id", packageName)
            val sumTextView = findViewById<TextView>(sumResourceId)
            var rowSum = 0
            for (j in 1..7) {
                if (i != j) {
                    val editTextId = "row$i$j"
                    val resourceId = resources.getIdentifier(editTextId, "id", packageName)
                    val editText = findViewById<EditText>(resourceId)
                    editText.addTextChangedListener {
                        if (!listOf("0", "1", "2", "3", "4", "5").contains(editText.text.toString())) {
                            editText.setTextColor(Color.RED)
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.error_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            cellScoreNumber++
                            writeProperDigit(editText, j, i, sumTextView)
                            rowSum += editText.text.toString().toInt()
                            sumTextView.text = rowSum.toString()
                            places[i - 1] = rowSum

                            if (cellScoreNumber == 42) {
                                showPlaces(places)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showPlaces(places: MutableMap<Int, Int>) {
        val sortedPlaces =
            places.toList().sortedBy { (k, v) -> v }.reversed().toMap().keys.map { x -> x + 1 }
        val realSortedPlaces = sortedPlaces.zip(sortedPlaces.indices.map { x -> x + 1 }).toMap()
        for (k in 1..7) {
            val placeId = "place$k"
            val placeResourceId = resources.getIdentifier(placeId, "id", packageName)
            val placeTextView = findViewById<TextView>(placeResourceId)
            placeTextView.text = realSortedPlaces[k].toString()
        }
    }

    private fun writeProperDigit(editText: EditText, j: Int, i: Int, sumTextView: TextView) {
        editText.setTextColor(Color.BLACK)
        if (j != 7 && !(i == 7 && j == 6)) {
            val nextEditTextId = if (i != j + 1) "row$i${j + 1}" else "row$i${j + 2}"
            val nextResourceId = resources.getIdentifier(
                nextEditTextId, "id", packageName
            )
            val editText2 = findViewById<EditText>(nextResourceId)
            editText2.requestFocus()
        }else{
            sumTextView.visibility = View.VISIBLE
        }
        editText.clearFocus()
        editText.isEnabled = false
    }
}