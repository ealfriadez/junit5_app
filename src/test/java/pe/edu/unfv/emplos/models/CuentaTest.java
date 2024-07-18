package pe.edu.unfv.emplos.models;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pe.edu.unfv.emplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void getNombreCuenta() {
        Cuenta cuenta = new Cuenta("Elio", new BigDecimal("1000.12345"));
        //cuenta.setPersona("Elio");
        String esperado = "Elio";
        String real = cuenta.getPersona();

        assertEquals(esperado, real);
        assertEquals("Elio", real);
    }

    @Test
    void getSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Elio", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("8900.12345"));
        Cuenta cuenta2 = new Cuenta("Santiago", new BigDecimal("8900.12345"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, ()->{
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String expected = "Dinero insuficiente";
        assertEquals(expected, actual);
    }
}