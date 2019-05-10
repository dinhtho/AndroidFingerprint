package com.example.androidfingerprint

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by tho nguyen on 2019-05-10.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var cancellationSignal: CancellationSignal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_authenticate.setOnClickListener {
            if (checkBiometricSupport()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    authenticateUser()
                }
            }
        }

    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun checkBiometricSupport(): Boolean {

        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        val packageManager = this.packageManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("Lock screen security not enabled in Settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {

            notifyUser("Fingerprint authentication permission not enabled")
            return false
        }

        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getAuthenticationCallback(): BiometricPrompt.AuthenticationCallback {

        return object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,
                                               errString: CharSequence) {
                notifyUser("Authentication error: $errString")
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationHelp(helpCode: Int,
                                              helpString: CharSequence) {
                super.onAuthenticationHelp(helpCode, helpString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                notifyUser("Authentication Succeeded")
                super.onAuthenticationSucceeded(result)
            }
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal.setOnCancelListener { notifyUser("Cancelled via signal") }
        return cancellationSignal
    }


    @RequiresApi(Build.VERSION_CODES.P)
    public fun authenticateUser() {
        val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Biometric Demo")
                .setSubtitle("Authentication is required to continue")
                .setDescription("This app uses biometric authentication to protect your data.")
                .setNegativeButton("Cancel", this.mainExecutor,
                        DialogInterface.OnClickListener { _, _ -> notifyUser("Authentication cancelled"); })
                .build()

        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor,
                getAuthenticationCallback())
    }

}