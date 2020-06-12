package com.herlavroc.proyectofinal.encrypt

import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

public class Encriptador {
    companion object{
        val pswdIterations = 10
        val keySize = 128
        val plainText = "sampleText"
        val AESSalt = "exampleSalt"
        val secretKeyInstance = "PBKDF2WithHmacSHA1"
        val cypherInstance = "AES/CBC/PKCS5Padding"
        val initializationVector = "8119745113154120"
        public fun GenerarEncriptado(textToEncrypt: String): String
        {




            val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
            val cipher = Cipher.getInstance(cypherInstance)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(initializationVector.toByteArray()))
            return cipher.doFinal(textToEncrypt.toByteArray()).toString()
        }
        fun Desencriptar(textToDecrypt: String):String
        {
            val encryted_bytes = textToDecrypt.toByteArray()
            val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
            val cipher = Cipher.getInstance(cypherInstance)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(initializationVector.toByteArray()))
            val decrypted = cipher.doFinal(encryted_bytes)
            return decrypted.toString()
        }
        private fun getRaw(plainText:String, salt:String):ByteArray {
            try
            {
                val factory = SecretKeyFactory.getInstance(secretKeyInstance)
                val spec = PBEKeySpec(plainText.toCharArray(), salt.toByteArray(), pswdIterations, keySize)
                return factory.generateSecret(spec).encoded
            }
            catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            }
            catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ByteArray(0)
        }
    }
}