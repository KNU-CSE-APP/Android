package com.example.knucseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import com.example.knucseapp.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        textWatcher()
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

        val sentence1 = "필수입력 항목 입니다."

        //이메일 입력
        binding.emailText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("SignupActivity", "call textwatcher")
                if(binding.emailText.text!!.isEmpty()){
                    binding.emailLayout.error = sentence1
                }
                else{
                    binding.emailLayout.error = null
                }
            }

        })

        //비번
        binding.pwText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("SignupActivity", "call textwatcher")
                if(binding.pwText.text!!.isEmpty()){
                    binding.pwLayout.error = sentence1
                }
                else{
                    binding.pwLayout.error = null
                }
            }

        })

        //비번 재확인
        binding.ckpwText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("SignupActivity", "call textwatcher")
                if(binding.ckpwText.text!!.isEmpty()){
                    binding.ckpwLayout.error = sentence1
                }
                else{
                    binding.ckpwLayout.error = null
                }
            }

        })


        //학번 입력
        binding.stdIdText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("SignupActivity", "call textwatcher")
                if(binding.stdIdText.text!!.isEmpty()){
                    binding.stdIdLayout.error = sentence1
                }
                else{
                    binding.stdIdLayout.error = null
                }
            }

        })

        //이름입력
        binding.nameText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("SignupActivity", "call textwatcher")
                if(binding.nameText.text!!.isEmpty()){
                    binding.nameLayout.error = sentence1
                }
                else{
                    binding.nameLayout.error = null
                }
            }

        })
    }
}