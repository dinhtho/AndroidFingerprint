package com.example.androidfingerprint.biometric;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.os.Build;


@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricCallbackV28 extends BiometricPrompt.AuthenticationCallback {

    private BiometricCallback biometricCallback;
    public BiometricCallbackV28(BiometricCallback biometricCallback) {
        this.biometricCallback = biometricCallback;
    }


    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        biometricCallback.onAuthenticationSuccessful();
    }


    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        biometricCallback.onAuthenticationHelp(helpCode, helpString);
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        biometricCallback.onAuthenticationError(errorCode, errString);
    }


    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        biometricCallback.onAuthenticationFailed();
    }

    @SuppressLint({"MissingPermission"})
    public static class BiometricUtils {


        public static boolean isBiometricPromptEnabled() {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P);
        }


        /*
         * Condition I: Check if the android version in device is greater than
         * Marshmallow, since fingerprint authentication is only supported
         * from Android 6.0.
         * Note: If your project's minSdkversion is 23 or higher,
         * then you won't need to perform this check.
         *
         * */
        public static boolean isSdkVersionSupported() {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
        }



        /*
         * Condition II: Check if the device has fingerprint sensors.
         * Note: If you marked android.hardware.fingerprint as something that
         * your app requires (android:required="true"), then you don't need
         * to perform this check.
         *
         * */
        public static boolean isHardwareSupported(Context context) {
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
            return fingerprintManager.isHardwareDetected();
        }



        /*
         * Condition III: Fingerprint authentication can be matched with a
         * registered fingerprint of the user. So we need to perform this check
         * in order to enable fingerprint authentication
         *
         * */
        public static boolean isFingerprintAvailable(Context context) {
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
            return fingerprintManager.hasEnrolledFingerprints();
        }



        /*
         * Condition IV: Check if the permission has been added to
         * the app. This permission will be granted as soon as the user
         * installs the app on their device.
         *
         * */
        public static boolean isPermissionGranted(Context context) {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                    PackageManager.PERMISSION_GRANTED;
        }
    }
}
