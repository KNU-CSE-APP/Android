package com.example.knucseapp.ui.board.search

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private val blankFragment by lazy { SearchBlankFragment() }
    private var resultFragment : SearchResultFragment? = null
    private var clickeditem = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        setStatusBarColor() //status bar 흰색으로 변경
        setFragment()
        setButton()
    }

    fun setFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, blankFragment)
        transaction.commit()
    }

    fun setButton() {
        binding.searchTextview.setOnEditorActionListener { textView, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                goSearchResult(binding.searchTextview.text.toString())
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchTextview.windowToken, 0)
                handled = true
            }
            handled
        }

        binding.searchTextview.setOnClickListener {
            goSearchBlank()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun goSearchBlank() {
        if(resultFragment!=null){
            supportFragmentManager.beginTransaction().remove(resultFragment!!).commit()
        }
        supportFragmentManager.beginTransaction().show(blankFragment).commit()
    }

    private fun goSearchResult(keyword: String) {

        var bundle = Bundle()
        bundle.putString("keyword", keyword)
        bundle.putStringArrayList("category", clickeditem)
        resultFragment = SearchResultFragment()
        resultFragment!!.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, resultFragment!!)
        transaction.commit()


    }

    fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.getWindow() as Window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    fun addClickedItem(data: String) {
        if(!(data in clickeditem)){
            clickeditem.add(data)
        }
    }

    fun deleteClickedItem(data: String)
    {
        if(data in clickeditem){
            clickeditem.remove(data)
        }
    }
}