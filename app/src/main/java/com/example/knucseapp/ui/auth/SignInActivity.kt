package com.example.knucseapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivitySignInBinding
import com.example.knucseapp.ui.auth.*
import com.example.knucseapp.ui.util.BaseActivity
import com.example.knucseapp.ui.util.MyApplication
import com.example.knucseapp.ui.util.toast

class SignInActivity : BaseActivity(),AuthListener {

    private lateinit var binding: ActivitySignInBinding
    lateinit var viewModel : AuthViewModel
    lateinit var viewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        initViewModel()

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnFindPW.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("networkStatus","onResume")
        viewModel.getSharedPreference()
    }

    private fun initViewModel(){
        viewModelFactory = AuthViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(AuthViewModel::class.java)
        viewModel.authSignInListener = this
        viewModel.isSelected.set(false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.signInResponse.observe(this){
            if(it.success){
                toast(it.response.userId.toString() + "nickname : " + it.response.nickname)
                MyApplication.prefs.setUserNickname(it.response.nickname)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                toast(it.error.message)
                viewModel.removeEditText()
            }
        }
    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String, type: Int) {
        when(type){
            0 -> {
                binding.emailText.requestFocus()
            }
            1 -> {
                binding.pwText.requestFocus()
            }
        }
        toast(message)
    }
}