package com.example.knucseapp.ui.notice

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import com.example.knucseapp.R
import com.example.knucseapp.databinding.NoticeFragmentBinding
import com.example.knucseapp.ui.MainActivity

class NoticeFragment : Fragment() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    private lateinit var viewModel: NoticeViewModel
    private lateinit var fragmentBinding : NoticeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        fragmentBinding = NoticeFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView()

        fragmentBinding.previousBtn.setOnClickListener {
            val canGoBack: Boolean = fragmentBinding.webview.canGoBack()
            if (canGoBack) {
                fragmentBinding.webview.goBack()
            }
        }


        fragmentBinding.nextBtn.setOnClickListener {
            val canGoForward: Boolean = fragmentBinding.webview.canGoForward()
            if (canGoForward) {
                fragmentBinding.webview.goForward()
            }
        }

    }

    private fun setWebView(){
        fragmentBinding.webview.apply {
            webViewClient = WebViewClientClass() // new WebViewClient()); //클릭시 새창 안뜨게

            //팝업이나 파일 업로드 등 설정해주기 위해 webView.webChromeClient를 설정
            //웹뷰에서 크롬이 실행가능&& 새창띄우기는 안됨
            //webChromeClient = WebChromeClient()

            //웹뷰에서 팝업창 호출하기 위해
            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
                    val newWebView = WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                    }

                    val dialog = Dialog(context).apply {
                        setContentView(newWebView)
                        window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
                        window!!.attributes.height = ViewGroup.LayoutParams.MATCH_PARENT
                        show()
                    }

                    newWebView.webChromeClient = object : WebChromeClient() {
                        override fun onCloseWindow(window: WebView?) {
                            dialog.dismiss()
                        }
                    }

                    (resultMsg?.obj as WebView.WebViewTransport).webView = newWebView
                    resultMsg.sendToTarget()
                    return true
                }
            }


            settings.javaScriptEnabled = true
            settings.setSupportMultipleWindows(true) // 새창띄우기 허용여부
            settings.javaScriptCanOpenWindowsAutomatically = true // 자바스크립트 새창뛰우기 (멀티뷰) 허용여부
            settings.loadWithOverviewMode = true //메타태크 허용여부
            settings.useWideViewPort = true //화면 사이즈 맞추기 허용여부
            settings.setSupportZoom(true) //화면 줌 허용여부
            settings.builtInZoomControls = true //화면 확대 축소 허용여부
            settings.textZoom = 100
            // Enable and setup web view cache
            settings.cacheMode =
                WebSettings.LOAD_NO_CACHE //브라우저 캐시 허용여부  // WebSettings.LOAD_DEFAULT
            settings.domStorageEnabled = true //로컬저장소 허용여부
            settings.displayZoomControls = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true  // api 26
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.mediaPlaybackRequiresUserGesture = false
            }

            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowUniversalAccessFromFileURLs = true
            }

            settings.allowFileAccess = true
            //settings.loadsImagesAutomatically = true

            fitsSystemWindows = true
        }


        val url = "http://cse.knu.ac.kr/06_sub/02_sub.html"
        fragmentBinding.webview.loadUrl(url)
    }

    inner class WebViewClientClass : WebViewClient() {
        //페이지 이동
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            fragmentBinding.progressbar.visibility = ProgressBar.VISIBLE
            fragmentBinding.webview.visibility = View.INVISIBLE
        }

        override fun onPageCommitVisible(view: WebView, url: String) {
            super.onPageCommitVisible(view, url)
            fragmentBinding.progressbar.visibility = ProgressBar.GONE
            fragmentBinding.webview.visibility = View.VISIBLE
        }


        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            var builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(context)
            var message = "SSL Certificate error."
            when (error.primaryError) {
                SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                SslError.SSL_EXPIRED -> message = "The certificate has expired."
                SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
            }
            message += " Do you want to continue anyway?"
            builder.setTitle("SSL Certificate Error")
            builder.setMessage(message)
            builder.setPositiveButton("continue",
                DialogInterface.OnClickListener { _, _ -> handler.proceed() })
            builder.setNegativeButton("cancel",
                DialogInterface.OnClickListener { dialog, which -> handler.cancel() })
            val dialog: android.app.AlertDialog? = builder.create()
            dialog?.show()
        }
    }

}