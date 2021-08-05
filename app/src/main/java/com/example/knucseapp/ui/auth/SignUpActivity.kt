package com.example.knucseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivitySignUpBinding
import com.example.knucseapp.ui.auth.AuthListener
import com.example.knucseapp.ui.auth.AuthViewModel
import com.example.knucseapp.ui.auth.AuthViewModelFactory
import com.example.knucseapp.ui.data.VerifyEmailDTO
import com.example.knucseapp.ui.repository.AuthRepository
import com.example.knucseapp.ui.request.user.ApiRequestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity(), AuthListener {


    private lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel : AuthViewModel
    lateinit var viewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        initViewModel()
        setToolbar()
        textWatcher()
        setButton()
    }

    private fun initViewModel(){
        viewModelFactory = AuthViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.getResponse.observe(this){
            Toast.makeText(this,it.response,Toast.LENGTH_SHORT).show()
            binding.btnEmailConfirm.isEnabled = true
            binding.emailverifyText.isEnabled=true
            binding.emailverifyText.isFocusable=true
        }

        viewModel.verifyPostResponse.observe(this){
            if(it.success){
                Toast.makeText(this,"인증 성공!",Toast.LENGTH_SHORT).show()
                binding.btnSignup.isEnabled = true
            }
            else{ Toast.makeText(this,it.error.message,Toast.LENGTH_SHORT).show() }
        }

        viewModel.signUpResponse.observe(this) {
            if (it.success) {
                Toast.makeText(this, "회원 가입 완료!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
            }
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

    private fun setButton() {
        binding.btnSignup.setOnClickListener {
            //TODO: 입력 칸 확인
            Toast.makeText(this, "가입한 정보로 로그인 해 주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun textWatcher() {

        val sentence1 = "필수입력 항목 입니다."

        binding.apply {
            //이메일
            emailText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.emailText.text!!.isEmpty()){
                        binding.emailLayout.error = sentence1
                    }
                    else{
                        binding.emailLayout.error = null
                    }
                }

            })

            //비밀번호
            pwText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.pwText.text!!.isEmpty()){
                        binding.pwLayout.error = sentence1
                    }
                    else{
                        binding.pwLayout.error = null
                    }
                }
            })

            //비번 재확인
            ckpwText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.ckpwText.text!!.isEmpty()){
                        binding.ckpwLayout.error = sentence1
                    }
                    else if(!(binding.ckpwText.text.toString()!!.equals(binding.pwText.text.toString()))) {
                        binding.ckpwLayout.error = "일치하지 않습니다."
                    }
                    else{
                        binding.ckpwLayout.error = null
                    }
                }

            })

            //학번
            stdIdText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.stdIdText.text!!.isEmpty()){
                        binding.stdIdLayout.error = sentence1
                    }
                    else{
                        binding.stdIdLayout.error = null
                    }
                }

            })

            //이름
            nameText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.nameText.text!!.isEmpty()){
                        binding.nameLayout.error = sentence1
                    }
                    else{
                        binding.nameLayout.error = null
                    }
                }
            })

            //닉네임
            nicknameText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

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

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {

    }
}