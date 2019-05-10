package com.example.androidfingerprint.biometric

/**
 * Created by tho nguyen on 2019-05-10.
 */
interface BiometricCallback {
    fun onSdkVersionNotSupported()

    fun onBiometricAuthenticationNotSupported()

    fun onBiometricAuthenticationNotAvailable()

    fun onBiometricAuthenticationPermissionNotGranted()

    fun onBiometricAuthenticationInternalError(error: String)


    fun onAuthenticationFailed()

    fun onAuthenticationCancelled()

    fun onAuthenticationSuccessful()

    fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence)

    fun onAuthenticationError(errorCode: Int, errString: CharSequence)
}