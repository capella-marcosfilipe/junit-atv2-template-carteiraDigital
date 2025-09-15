import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import com.example.DigitalWallet;

class Deposito {

    private DigitalWallet wallet;

    @BeforeEach
    void createWallet() {
        wallet = new DigitalWallet("test", 0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.0, 30.0})
    void deveDepositarValoresValidos(double amount) {
        double saldoAntes = wallet.getBalance();
        wallet.deposit(amount);
        double saldoEsperado = saldoAntes + amount;

        assertEquals(saldoEsperado, wallet.getBalance(),
            "O saldo deve ser atualizado corretamente após depósito");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, 0, -999.99})
    void deveLancarExcecaoParaDepositoInvalido(double amount) {
        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(amount));
    }
}
