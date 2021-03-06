package com.example.knucseapp.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.MypageFragmentBinding
import com.example.knucseapp.ui.SignInActivity
import com.example.knucseapp.ui.mypage.menu.*
import com.example.knucseapp.ui.util.MyApplication
import com.example.knucseapp.ui.util.NetworkConnection
import com.example.knucseapp.ui.util.NetworkStatus
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    lateinit var binding: MypageFragmentBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = MypageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MypageViewModel::class.java)
        binding.viewmodel = viewModel
        val connection = NetworkConnection(MyApplication.instance.context())
        connection.observe(viewLifecycleOwner) { isConnected ->
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
        return binding.root
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        setButton()
        setViewModel()
    }

    fun setViewModel() {
        viewModel.getLogoutResponse.observe(viewLifecycleOwner) {
            if(it.success) {
                Toast.makeText(activity, it.response, Toast.LENGTH_SHORT).show()
                MyApplication.prefs.clear()
                val intent = Intent(binding.root.context, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                binding.mypageProgressBar.hide()
                binding.root.context?.startActivity(intent)
            }
        }
    }
    
    fun setButton() {
        binding.apply {
            btnMypageMypage.setOnClickListener {
                val intent = Intent(it.context, UserInfoEditActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageChangePassword.setOnClickListener {
                val intent = Intent(it.context, PasswordEditActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageDeleteMember.setOnClickListener {
                val intent = Intent(it.context, DeleteMemberActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageReservationHistory.setOnClickListener {
                val intent = Intent(it.context, ReservationHistoryActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageWriteHistory.setOnClickListener {
                val intent = Intent(it.context, WriteHistoryActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageCommentHistory.setOnClickListener {
                val intent = Intent(it.context, CommentHistoryActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageSetting.setOnClickListener {
                val intent = Intent(it.context, SettingActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageLogout.setOnClickListener {
                showLogoutDialog()
            }
        }
    }

    fun showLogoutDialog(){
        MaterialAlertDialogBuilder(binding.root.context)
                .setTitle("????????????")
                .setMessage("???????????????????????????????")
                .setPositiveButton("??????") { _, _ ->
                    if(NetworkStatus.status){
                        binding.mypageProgressBar.show()
                        viewModel.logout()
                    }
                    else
                        Toast.makeText(activity, "???????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("??????") { _, _ -> // ????????? ?????? ??????
                }
                .show()
    }
}