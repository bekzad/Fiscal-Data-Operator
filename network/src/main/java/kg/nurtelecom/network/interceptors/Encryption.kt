package kg.nurtelecom.network.interceptors

/**
 * Data Encryption:
 * This class encrypts/decrypts the data by using AES/CBC/PKCS5Padding algorithm
 * We use special key (derived inside the function)
 * and an Initialization Vector from AppRefs to encrypt/decrypt the data
 *
 * Key Hashing:
 * The key for encryption is derived by using PBKDF2WithHmacSHA256 hashing algorithm
 * To derive the key we use: masterKey saved in Apprefs, and salt we get from the server as secure_key
 * KeySpecs: Iterations: 65536 times, KeyLength: 256bits
 * The key itself is never stored anywhere only derived every time we call the encrypt function
 * */

import android.util.Base64
import kg.nurtelecom.storage.sharedpref.AppPreferences
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class Encryption (private val appPrefs: AppPreferences) {

    fun encrypt(dataToEncrypt: String): String {

        // Initialize variables
        val iv = Keys().iv
        val masterKey = Keys().masterKey
        val salt = appPrefs.secureKey.toByteArray(Charsets.UTF_8)
        val dataToEncryptBytes = dataToEncrypt.toByteArray(Charsets.UTF_8)

        // Here we are creating a key
        val pbKeySpec = PBEKeySpec(masterKey, salt, 65536, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        // Wrapping an Initialization vector into IvParameterSpec
        val ivSpec = IvParameterSpec(iv)

        // Encrypting the data
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(dataToEncryptBytes)

        // Convert Encrypted bytes to Base64 String
        val encryptedBase64String = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

        return encryptedBase64String
    }

    fun decrypt(dataToDecrypt: String): String {
        val decryptedBytes: ByteArray

        // Getting the key values
        val iv = Keys().iv
        val masterKey = Keys().masterKey
        val salt = appPrefs.secureKey.toByteArray(Charsets.UTF_8)
        val dataToDecryptBytes = Base64.decode(dataToDecrypt, Base64.NO_WRAP)

        // Generating the key from password
        val pbeKeySpec = PBEKeySpec(masterKey, salt, 65536, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = secretKeyFactory.generateSecret(pbeKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        // Decrypt the data
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        decryptedBytes = cipher.doFinal(dataToDecryptBytes)

        // Convert decrypted bytes into Base64 String
        val decryptedString = String(decryptedBytes, Charsets.UTF_8)

        return decryptedString
    }
}