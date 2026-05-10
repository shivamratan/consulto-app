package com.ratanapps.auth.ui.googleauth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import jakarta.inject.Named
import javax.inject.Inject

class GoogleAuthUIProvider @Inject constructor(
    private val credentialManager: CredentialManager,
    private val getGoogleIdOption: GetGoogleIdOption,
    @param:Named("web_client_id") private val webClientId: String
)  {

    suspend fun googleSignIn(context : Context): String? {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                val googleCredential =
                    GoogleIdTokenCredential.createFrom(
                        credential.data
                    )

                return googleCredential.idToken
            }
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuthUIProvider", "Error getting credential", e)
            throw e
        } catch (e: Exception) {
            Log.e("GoogleAuthUIProvider", "Unexpected error", e)
        }

        return null
    }
}