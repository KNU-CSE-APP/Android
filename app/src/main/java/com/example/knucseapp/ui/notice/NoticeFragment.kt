package com.example.knucseapp.ui.notice

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.*
import android.view.View.*
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.databinding.NoticeFragmentBinding
import com.example.knucseapp.ui.util.*


class NoticeFragment : Fragment(){

    companion object {
        fun newInstance() = NoticeFragment()
    }

    private lateinit var viewModel: NoticeViewModel
    private lateinit var noticeFragmentBinding : NoticeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        noticeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.notice_fragment, container, false)
        return noticeFragmentBinding.root
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val canGoBack: Boolean = noticeFragmentBinding.webview.canGoBack()
            if (canGoBack) {
                noticeFragmentBinding.webview.goBack()
            }

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)
        val connection = NetworkConnection(MyApplication.instance.context())
        connection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected)
            {
                NetworkStatus.status = true
                noticeFragmentBinding.webview.visibility = VISIBLE
                setWebView()
            }
            else
            {
                noticeFragmentBinding.webview.visibility = GONE
                NetworkStatus.status = false
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
    }

    private fun setWebView(){
        noticeFragmentBinding.webview.apply {
            webViewClient = WebViewClientClass()//????????? ?????? ?????????

            //???????????? ?????? ????????? ??? ??????????????? ?????? webView.webChromeClient??? ??????
            //???????????? ????????? ????????????&& ?????????????????? ??????
            //webChromeClient = WebChromeClient()

            //???????????? ????????? ???????????? ??????
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
            settings.setSupportMultipleWindows(true) // ??????????????? ????????????
            settings.javaScriptCanOpenWindowsAutomatically = true // ?????????????????? ??????????????? (?????????) ????????????
            settings.loadWithOverviewMode = true //???????????? ????????????
            settings.useWideViewPort = true //?????? ????????? ????????? ????????????
            settings.setSupportZoom(true) //?????? ??? ????????????
            settings.builtInZoomControls = true //?????? ?????? ?????? ????????????
            settings.textZoom = 100
            // Enable and setup web view cache
            settings.cacheMode =
                WebSettings.LOAD_NO_CACHE //???????????? ?????? ????????????  // WebSettings.LOAD_DEFAULT
            settings.domStorageEnabled = true //??????????????? ????????????
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
        noticeFragmentBinding.webview.loadUrl(url)
    }

    inner class WebViewClientClass : WebViewClient() {
        //????????? ??????
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            noticeFragmentBinding.progressbar.show()
            noticeFragmentBinding.webview.visibility = View.INVISIBLE
        }

        override fun onPageCommitVisible(view: WebView, url: String) {
            super.onPageCommitVisible(view, url)
            noticeFragmentBinding.progressbar.hide()
            noticeFragmentBinding.webview.visibility = View.VISIBLE
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

