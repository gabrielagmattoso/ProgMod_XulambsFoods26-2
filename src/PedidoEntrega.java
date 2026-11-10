public class PedidoEntrega extends Pedido{
    public static final int MAX_PIZZAS = 12;
    
    private ETaxaEntrega taxaEntrega;
    private double distanciaEntrega;

    public PedidoEntrega(double distancia){
        super();
        if(distancia < 0.1)
            throw new IllegalArgumentException("Distância de entrega inválida");
        
        distanciaEntrega = distancia;
        taxaEntrega = ETaxaEntrega.definirEntrega(distancia);
    }

    private long contarPizzas(){
        return itens.stream().filter(item -> item instanceof Pizza).count();
    }

    /**
     * O limite de pizzas vale só para pizzas: bebidas e sobremesas não contam.
     */
    @Override 
    protected void verificarInclusao(IProduto produto){
        super.verificarInclusao(produto);
        if(produto instanceof Pizza && contarPizzas() >= MAX_PIZZAS)
            throw new IllegalStateException("Pedido para entrega aceita no máximo " + MAX_PIZZAS + " pizzas");
    }

    @Override 
    public double precoAPagar(){
        return valorItens() + taxaEntrega.valorTaxa();
    }

    @Override 
    public String toString(){
        String linhaEntrega  =
            String.format("TAXA ENTREGA: R$ %.2f (%.1f km)", 
                            taxaEntrega.valorTaxa(), distanciaEntrega);
        String precoFinal  =
            String.format("\nVALOR DO PEDIDO: R$ %.2f ", precoAPagar());
        StringBuilder relat = new StringBuilder(cabecalho());
        relat.append("PEDIDO PARA ENTREGA\n");
        relat.append(detalhesPedido()+"\n");
        relat.append(linhaEntrega);
        relat.append(precoFinal);
        return relat.toString();
    }
}