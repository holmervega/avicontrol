package Pruebas;

import org.mindrot.jbcrypt.BCrypt;

public class TestBCrypt {

    public static void main(String[] args) {
        String contraseña = "1234";
        String hash = BCrypt.hashpw(contraseña, BCrypt.gensalt());

        System.out.println("Hash generado: " + hash);
        System.out.println("¿Verificación correcta? " + BCrypt.checkpw("1234", hash));
    }
}
