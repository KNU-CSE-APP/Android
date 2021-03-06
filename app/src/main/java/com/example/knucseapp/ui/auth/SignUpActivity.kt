package com.example.knucseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivitySignUpBinding
import com.example.knucseapp.ui.auth.AuthListener
import com.example.knucseapp.ui.auth.AuthViewModel
import com.example.knucseapp.ui.auth.AuthViewModelFactory
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.ui.util.*

class SignUpActivity : AppCompatActivity(), AuthListener {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel : AuthViewModel
    lateinit var viewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        val connection = NetworkConnection(applicationContext)
        connection.observe(this){ isConnected ->
            if (isConnected) NetworkStatus.status = true
            else NetworkStatus.status = false
        }
        initViewModel()
        setToolbar()
        textWatcher()
    }

    private fun initViewModel(){
        viewModelFactory = AuthViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(AuthViewModel::class.java)
        viewModel.authSignUpListener = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.getResponse.observe(this){
            viewModel.setSignInLoadingFalse()
            toast(it.response)
            binding.btnEmailConfirm.isEnabled = true
            binding.emailverifyText.isEnabled=true
            binding.emailverifyText.isFocusable=true
        }

        viewModel.verifyPostResponse.observe(this){
            viewModel.setSignInLoadingFalse()
            if(it.success){
                toast("?????? ??????! \n????????? ?????? ????????? ??????????????????.")
                viewModel.signUpResponseCode = it.response
                setButtonState()
            }
            else{ toast(it.error.message) }
        }

        viewModel.signUpResponse.observe(this) {
            viewModel.setSignInLoadingFalse()
            if (it.success) {
                toast("?????? ?????? ??????! \n????????? ????????? ?????????????????????.")
                finish()
            } else {
                toast(it.error.message)
            }
        }

        viewModel.signUpLoading.observe(this) {
            if(it) binding.signupProgressBar.show()
            else binding.signupProgressBar.hide()
        }
    }

    private fun setButtonState(){
        binding.emailText.isEnabled = false
        binding.emailverifyText.isEnabled = false
        binding.btnEmailVerify.isEnabled = false
        binding.btnEmailConfirm.isEnabled = false
        binding.btnSignup.isEnabled = true
        binding.pwText.requestFocus()
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

    private fun textWatcher() {

        val sentence1 = "???????????? ?????? ?????????."
        binding.apply {
            //?????????
            emailText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.emailText.text!!.isEmpty()){
                        binding.emailLayout.error = sentence1
                    }
                    else{
                        binding.emailLayout.error = null
                    }
                }
            })

            //????????????
            pwText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.pwText.text!!.isEmpty()){
                        binding.pwLayout.error = sentence1
                    }
                    else{
                        binding.pwLayout.error = null
                    }
                }
            })

            //?????? ?????????
            ckpwText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.ckpwText.text!!.isEmpty()){
                        binding.ckpwLayout.error = sentence1
                    }
                    else if(!(binding.ckpwText.text.toString()!!.equals(binding.pwText.text.toString()))) {
                        binding.ckpwLayout.error = "???????????? ????????????."
                    }
                    else{
                        binding.ckpwLayout.error = null
                    }
                }
            })

            //??????
            stdIdText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.stdIdText.text!!.isEmpty()){
                        binding.stdIdLayout.error = sentence1
                    }
                    else{
                        binding.stdIdLayout.error = null
                    }
                }
            })

            //??????
            nameText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.nameText.text!!.isEmpty()){
                        binding.nameLayout.error = sentence1
                    }
                    else{
                        binding.nameLayout.error = null
                    }
                }
            })

            //?????????
            nicknameText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(binding.nicknameText.text!!.isEmpty()){
                        binding.nicknameLayout.error = sentence1
                    }
                    else{
                        binding.nicknameLayout.error = null
                    }
                }
            })
        }
    }

    override fun onStarted() {}
    override fun onSuccess() {}
    override fun onFailure(message: String, type : Int) {
        when(type){
            1 -> binding.pwText.requestFocus()
            2 -> binding.ckpwText.requestFocus()
            3 -> binding.nameText.requestFocus()
            4 -> binding.nicknameText.requestFocus()
            5 -> binding.stdIdText.requestFocus()
            6 -> binding.majorRadioGroup.requestFocus()
            7 -> binding.genderRadioGroup.requestFocus()
        }
        toast(message)
    }
}