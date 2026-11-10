import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoEntregaTest {
    PedidoEntrega pedido;
    Pizza pizzaVazia;

    @BeforeEach 
    void setUp(){
        pedido = new PedidoEntrega(5);
        pizzaVazia = new Pizza();
        pedido.adicionar(pizzaVazia);
    }

    @Test 
    public void calculaPrecoComEntrega(){
        //act
        double valor = pedido.precoAPagar();
        //assert
        assertEquals(34d, valor, 0.01);
    }

    @Test 
    public void relatorioContemEntrega(){
        //act
        String cupom = pedido.toString();
        //assert
        assertTrue(
            cupom.contains("ENTREGA") &&
            cupom.contains("5,00")
        );
    }

    @Test 
    public void naoUltrapassaMaximoDePizzas(){
        //arrange
        for (int i = 1; i < 8; i++) {
            pedido.adicionar(new Pizza());
        }
        //act
        int quantidade = pedido.adicionar(new Pizza());
        //assert
        assertEquals(8, quantidade);
    }
}