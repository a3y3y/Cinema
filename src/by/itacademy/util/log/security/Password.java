package by.itacademy.util.log.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Password {
    public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword
            , byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public static byte[] getEncryptedPassword(String password, byte[] salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 1000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(),salt,iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return f.generateSecret(spec).getEncoded();
    }

    public static byte[] generateSalt() throws NoSuchAlgorithmException{
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    public static String getStringFromByte(byte[] array) throws UnsupportedEncodingException {
        return new String(array,"ISO-8859-1");
    }

    public static byte[] getByteFromString(String string) throws UnsupportedEncodingException {
        return string.getBytes("ISO-8859-1");
    }
}
