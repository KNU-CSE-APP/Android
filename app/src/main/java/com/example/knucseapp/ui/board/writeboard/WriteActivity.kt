package com.example.knucseapp.ui.board.writeboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityWriteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWriteBinding
    private lateinit var arrayAdapter : ArrayAdapter<Any?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        binding.lifecycleOwner = this
        setToolbar()
        setSpinner()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSpinner() {
        var list_of_items = arrayOf("잡담", "정보", "팀원")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, list_of_items)
        (binding.spinnerTextview as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}