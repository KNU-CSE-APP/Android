package com.example.knucseapp.ui.mypage.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityDeleteMemberBinding
import com.example.knucseapp.ui.SignInActivity
import com.example.knucseapp.ui.mypage.MypageViewModel
import com.example.knucseapp.ui.mypage.MypageViewModelFactory
import com.example.knucseapp.ui.util.MyApplication
import com.example.knucseapp.ui.util.NetworkConnection
import com.example.knucseapp.ui.util.NetworkStatus
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteMemberActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDeleteMemberBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_member)
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
        binding.btnDeleteMember.setOnClickListener { showDialog() }
        initViewModel()
        setToolbar()
    }

    private fun initViewModel(){
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(MypageViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.getDeleteMemberResponse.observe(this){
            viewModel.setDataLoadingFalse()
            if (it.success){
                toast(it.response)
                MyApplication.prefs.clear()
                val intent = Intent(binding.root.context, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                binding.root.context?.startActivity(intent)
            }
            else{
                toast(it.error.message)
            }
        }

        viewModel.dataLoading.observe(this) {
            if(it) {
                binding.deleteMemberProgressBar.show()
            }
            else {
                binding.deleteMemberProgressBar.hide()
            }
        }
    }

    private fun showDialog(){
        MaterialAlertDialogBuilder(binding.root.context)
            .setTitle("?????? ??????")
            .setMessage("????????? ?????? ???????????????????")
            .setPositiveButton("??????") { _, _ ->
                if(NetworkStatus.status)
                    viewModel.deleteMember()
                else
                    toast("???????????? ????????? ????????? ?????????.")
            }
            .setNegativeButton("??????") { _, _ -> // ????????? ?????? ??????
            }
            .show()
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