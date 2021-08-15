package com.example.knucseapp.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityPasswordBinding
import com.example.knucseapp.ui.auth.AuthViewModel
import com.example.knucseapp.ui.auth.AuthViewModelFactory
import com.example.knucseapp.ui.util.toast

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    lateinit var viewModel : AuthViewModel
    lateinit var viewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password)
        initViewModel()
        setToolbar()
        textWatcher()
    }

    private fun initViewModel(){
        viewModelFactory = AuthViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        // 비밀번호 찾기 이메일 인증번호 전송
        viewModel.getFindPasswordResponse.observe(this){
            if (it.success){
                toast(it.response)
                setConfirmView()
            }
            else {toast(it.error.message)}
        }

        // 인증번호 검증
        viewModel.getVerifyPasswordCode.observe(this){
            if (it.success){
                toast("인증 성공")
                viewModel.findPasswordResponseCode = it.response
                Log.d("response Code : ",it.response)
                setNewPasswordView()
                emailViewState()
            }
            else toast(it.error.message)
        }

        // 새로운 비밀번호 변경
        viewModel.changeValidatedPasswordResponse.observe(this){
            if(it.success){
                toast(it.response)
                finish()
            }
            else toast(it.error.message)
        }
    }

    private fun emailViewState(){
        binding.apply {
            emailText.isEnabled = false
            btnEmailVerify.isEnabled = false
            emailverifyText.isEnabled = false
            btnEmailConfirm.isEnabled = false
            pwText.requestFocus()
        }
    }

    private fun setConfirmView(){
        binding.apply {
            emailverifyText.isEnabled = true
            emailverifyText.isFocusable = true
            emailverifyText.requestFocus()
            btnEmailConfirm.isEnabled = true
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

    private fun setNewPasswordView() {
        AlertDialog.Builder(this)
            .setTitle("인증 완료")
            .setMessage("인증이 완료되었습니다.")
            .setPositiveButton("OK"){ _, _ ->
                binding.emailverifyNoticeTextview.text = "이메일 인증 완료"
                binding.layoutCheckPassword.visibility = VISIBLE
                binding.layoutPassword.visibility = VISIBLE
                binding.btnChangePw.visibility = VISIBLE
                emailViewState()
            }.show()
    }

    private fun textWatcher() {

        val sentence1 = "필수입력 항목 입니다."

        binding.apply {

            //비밀번호
            pwText.addTextChangedListener(object : TextWatcher {
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

            //비번 재확인
            ckpwText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
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
        }
    }
}