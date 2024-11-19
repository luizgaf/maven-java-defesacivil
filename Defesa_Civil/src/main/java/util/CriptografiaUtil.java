package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaUtil {

    public static String criptografarSenha(String senha) {
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hash = messageDigest.digest(senha.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }
    public static boolean verificarSenha(String senhaFornecida, String senhaCriptografadaArmazenada) {

        String senhaCriptografada = criptografarSenha(senhaFornecida);


        return senhaCriptografada.equals(senhaCriptografadaArmazenada);
    }
}

