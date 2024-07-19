package pe.edu.unfv.emplos.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pe.edu.unfv.emplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(){
        this.cuenta = new Cuenta("Elio", new BigDecimal("1000.12345"));
        System.out.println("Inciando el metodo");
    }

    @AfterEach
    void tearDwon(){
        System.out.println("Fin del metodo");
    }

    @Test
    @Disabled
    void getNombreCuenta() {
        //cuenta.setPersona("Elio");
        String esperado = "Elio";
        String real = cuenta.getPersona();

        assertNotNull(real, "La cuenta no purede ser nula");
        assertEquals(esperado, real, "El nombre de la cuenta no es el que se esperada");
        assertEquals("Elio", real, "Nombre cuenta esperda debe ser igual a la real");
    }

    @Test
    void getSaldoCuenta() {
        cuenta = new Cuenta("Elio", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        cuenta = new Cuenta("Santiago", new BigDecimal("8900.12345"));
        Cuenta cuenta2 = new Cuenta("Santiago", new BigDecimal("8900.12345"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String expected = "Dinero insuficiente";
        assertEquals(expected, actual);
    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("2500.12345"));
        Cuenta cuenta1 = new Cuenta("Sebastian", new BigDecimal("3500.12345"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta1, cuenta, new BigDecimal(500));
        assertEquals("3000.12345", cuenta1.getSaldo().toPlainString());
        assertEquals("3000.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta = new Cuenta("Santiago", new BigDecimal("2500.12345"));
        Cuenta cuenta1 = new Cuenta("Sebastian", new BigDecimal("3500.12345"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta1);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta1, cuenta, new BigDecimal(500));
        assertAll(
                () -> {
                    assertEquals("3000.12345", cuenta1.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("3000.12345", cuenta.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    assertEquals("Banco del Estado", cuenta.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Sebastian", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Sebastian"))
                            .findFirst().get().getPersona());
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Sebastian")));
                }
        );
    }
}