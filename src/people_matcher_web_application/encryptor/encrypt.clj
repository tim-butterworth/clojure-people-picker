(ns people_matcher_web_application.encryptor.encrypt)

(import java.security.InvalidKeyException)
(import java.security.NoSuchAlgorithmException)
(import javax.crypto.BadPaddingException)
(import javax.crypto.Cipher)
(import javax.crypto.IllegalBlockSizeException)
(import javax.crypto.KeyGenerator)
(import javax.crypto.NoSuchPaddingException)
(import javax.crypto.SecretKey)

;desCipher.init(Cipher.ENCRYPT_MODE, myDesKey)
;byte[] textEncrypted = desCipher.doFinal(text)
(def keygen (. KeyGenerator getInstance "DES"))
(def secretkey (. keygen generateKey))
(def cipher (. Cipher getInstance "DES/ECB/PKCS5Padding"))
(defn initialize-cipher [mode]
  (. cipher init
     mode
     secretkey))
(defn encrypttext [str]
  (let [bytes (. str getBytes)]
    (initialize-cipher (. Cipher ENCRYPT_MODE))
    ;(new String
    (. cipher doFinal bytes)))
;)
(defn decrypttext [bytes]
 ; (let [bytes (. str getBytes)]
    (initialize-cipher (. Cipher DECRYPT_MODE))
    (new String (. cipher doFinal bytes)))
;)
