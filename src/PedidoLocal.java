public class PedidoLocal extends Pedido {

    private static final double TAXA_SERVICO = 0.1;

    @Override
    public double precoAPagar() {
        return  valorPizzas() + valorServico();
    }

    private double valorServico() {
        return  valorItens() + TAXA_SERVICO;
    }

        @Override 
    public String toString(){
        StringBuilder cupom = new StringBuilder(cabecalho());
        cupom.append("PEDIDO LOCAL\n");
        cupom.append(detalhesPedido()+"\n");
        
        cupom.append(String.format("SERVIÇO: R$ %.2f\n", 
                            precoAPagar()));
        cupom.append(String.format("VALOR DO PEDIDO: R$ %.2f", 
                            precoAPagar()));

        return cupom.toString();
    }
    
}
