package com.example.androidfingerprint

import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfingerprint.biometric.BiometricCallback
import kotlinx.android.synthetic.main.activity_main.*
import com.example.androidfingerprint.biometric.BiometricManager


/**
 * Created by tho nguyen on 2019-05-10.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var cancellationSignal: CancellationSignal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_authenticate.setOnClickListener {

            BiometricManager.BiometricBuilder(this@MainActivity)
                    .setTitle("Add a title")
                    .setSubtitle("Add a subtitle")
                    .setDescription("Add a description")
                    .setNegativeButtonText("Add a cancel button")
                    .build()
                    .authenticate(object : BiometricCallback {
                        override fun onSdkVersionNotSupported() {
                            notifyUser("onSdkVersionNotSupported")
                        }

                        override fun onBiometricAuthenticationNotSupported() {
                            notifyUser("onBiometricAuthenticationNotSupported")
                        }

                        override fun onBiometricAuthenticationNotAvailable() {
                            notifyUser("onBiometricAuthenticationNotAvailable")
                        }

                        override fun onBiometricAuthenticationPermissionNotGranted() {
                            notifyUser("onBiometricAuthenticationPermissionNotGranted")
                        }

                        override fun onBiometricAuthenticationInternalError(error: String?) {
                            notifyUser("onBiometricAuthenticationInternalError")
                        }

                        override fun onAuthenticationFailed() {
                            notifyUser("onAuthenticationFailed")
                        }

                        override fun onAuthenticationCancelled() {
                            notifyUser("onAuthenticationCancelled")
                        }

                        override fun onAuthenticationSuccessful() {
                            notifyUser("onAuthenticationSuccessful")
                        }

                        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                            notifyUser("onAuthenticationHelp")
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            notifyUser("onAuthenticationError")
                        }
                    })
//            if (checkBiometricSupport()) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    authenticateUser()
//                }
//            }
        }

    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}