
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.metodosclases.MetodosGlobales;


public class MetodosGlobalesTest {

    MetodosGlobales metodosGlobales = new MetodosGlobales();

    @Test
    void testCheckPuertoIsDigit() {
        Assertions.assertTrue(metodosGlobales.checkPuertoIsDigit(8080), "El puerto 8080 debería ser válido");
        Assertions.assertTrue(metodosGlobales.checkPuertoIsDigit(12345), "El puerto 12345 debería ser válido");
        Assertions.assertTrue(metodosGlobales.checkPuertoIsDigit(65535), "El puerto 65535 debería ser válido");
    }

    @Test
    void testCheckPuertoIsDigitFalse() {
        Assertions.assertFalse(metodosGlobales.checkPuertoIsDigit(-1), "El puerto -1 no debería ser válido");
        Assertions.assertTrue(metodosGlobales.checkPuertoIsDigit(70000), "El puerto 70000 no debería ser válido");
    }

    @Test
    void testCheckLimitPuerto() {
        Assertions.assertTrue(metodosGlobales.checkLimitPuerto(1024), "El puerto 1024 es el límite inferior válido");
        Assertions.assertTrue(metodosGlobales.checkLimitPuerto(50000), "El puerto 50000 debería ser válido");
        Assertions.assertTrue(metodosGlobales.checkLimitPuerto(65535), "El puerto 65535 es el límite superior válido");
    }

    @Test
    void testCheckLimitPuertoFalse() {
        Assertions.assertFalse(metodosGlobales.checkLimitPuerto(1023), "El puerto 1023 está fuera del rango permitido");
        Assertions.assertFalse(metodosGlobales.checkLimitPuerto(65536), "El puerto 65536 está fuera del rango permitido");
    }
}
