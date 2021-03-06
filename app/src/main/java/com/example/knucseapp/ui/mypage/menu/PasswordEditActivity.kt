package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityPasswordEditBinding
import com.example.knucseapp.ui.mypage.MypageViewModel
import com.example.knucseapp.ui.mypage.MypageViewModelFactory
import com.example.knucseapp.ui.util.*

class PasswordEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPasswordEditBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_edit)
        initViewModel()
        setToolbar()
        textWatcher()
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.connectedLayout.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
            }
            else
            {
                binding.connectedLayout.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }
    }

    private fun initViewModel(){
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(MypageViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.getChangePasswordResponse.observe(this){
            if (it.success){
                toast(it.response)
                finish()
            }
            else {toast(it.error.message)}
        }
    }

    private fun textWatcher(){
        binding.apply{

            val sentence1 = "???????????? ?????? ?????????."
            //?????? ?????????
            newpasswordconfirmText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.newpasswordconfirmText.text!!.isEmpty()){ binding.newpasswordconfirmLayout.error = sentence1 }
                    else if(!(binding.newpasswordconfirmText.text.toString()!!.equals(binding.newpasswordText.text.toString()))) {
                        binding.newpasswordconfirmLayout.error = "???????????? ????????????."
                    }
                    else{ binding.newpasswordconfirmLayout.error = null }
                }

            })

            // ?????? ????????????
            curpasswordText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.curpasswordText.text!!.isEmpty()){
                        binding.curpasswordLayout.error = sentence1
                    }
                    else{ binding.curpasswordLayout.error = null }
                }
            })
        }
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
}