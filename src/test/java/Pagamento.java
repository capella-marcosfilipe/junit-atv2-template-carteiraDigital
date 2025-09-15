import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

public class Pagamento {
    
    @ParameterizedTest
    @CsvSource({
        "100.0, 30.0, true",
        "50.0, 80.0, false",
        "10.0, 10.0, true"
    })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {
        DigitalWallet wallet = new DigitalWallet("teste", inicial);
        wallet.verify();
        wallet.unlock();

        assumeTrue(wallet.isVerified() && !wallet.isLocked());

        boolean resultado = wallet.pay(valor);

        assertEquals(esperado, resultado);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -10.0})
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet wallet = new DigitalWallet("teste", 100.0);
        wallet.verify();
        wallet.unlock();

        assertThrows(IllegalArgumentException.class, () -> wallet.pay(valor));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        // Caso 1: não verificada
        DigitalWallet naoVerificada = new DigitalWallet("teste", 50.0);
        assertThrows(IllegalStateException.class, () -> naoVerificada.pay(10));

        // Caso 2: bloqueada
        DigitalWallet bloqueada = new DigitalWallet("teste", 50.0);
        bloqueada.verify();
        bloqueada.lock();
        assertThrows(IllegalStateException.class, () -> bloqueada.pay(10));
    }
}
