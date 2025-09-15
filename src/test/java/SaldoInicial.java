import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

class SaldoInicial {
        @ParameterizedTest
        @ValueSource(doubles={10, 20, 30})
        void deveConfigurarSaldoInicialCorreto(double balance) {
            DigitalWallet wallet = new DigitalWallet("teste", balance);
            assertEquals(balance, wallet.getBalance());
        }

        @ParameterizedTest
        @ValueSource(doubles={-10, -5, -20})
        void deveLancarExcecaoParaSaldoInicialNegativo(double balance) {
            assertThrows(IllegalArgumentException.class, ()->{
                DigitalWallet wallet = new DigitalWallet("teste", balance);
            });
        }
    }