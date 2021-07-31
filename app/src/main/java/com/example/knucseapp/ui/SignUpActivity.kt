package com.example.knucseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivitySignInBinding
import com.example.knucseapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        setToolbar()
        textWatcher()
        setButton()
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
}