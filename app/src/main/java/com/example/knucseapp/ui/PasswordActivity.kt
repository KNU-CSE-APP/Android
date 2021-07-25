package com.example.knucseapp.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityPasswordBinding
import com.example.knucseapp.databinding.ActivitySignInBinding
import com.example.knucseapp.ui.reservation.seat.ReservationConfirmActivity

class PasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password)

        setToolbar()
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
        //TODO: 확인 완료되면 띄울 수 있도록 추구 구현
        binding.btnEmailConfirm.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("인증 완료")
                .setMessage("인증이 완료되었습니다.")
                .setPositiveButton(
                    android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        binding.emailverifyNoticeTextview.text = "이메일 인증 완료"
                        binding.layoutCheckPassword.visibility = VISIBLE
                        binding.layoutPassword.visibility = VISIBLE
                        binding.btnChangePw.visibility = VISIBLE
                    })
                .show()
        }

        binding.btnChangePw.setOnClickListener {
            Toast.makeText(this, "변경된 아이디로 로그인해주세요", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}