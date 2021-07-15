package com.example.knucseapp.ui.mypage

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.knucseapp.utils.startSignInActivity

class MypageViewModel : ViewModel() {

    fun logout(view: View) {
        view.context.startSignInActivity()
    }
}