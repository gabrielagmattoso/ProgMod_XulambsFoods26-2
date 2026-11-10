public class Sobremesa implements IProduto{
    private ESobremesas tipo;

    public Sobremesa(ESobremesas tipo){
        if(tipo == null){
            throw new IllegalStateException("Sobremesa nao pode ser nula");
        }
        this.tipo = tipo;
    }

    @Override 
    public double valorFinal(){
        return tipo.getPreco();
    }

    @Override 
    public String toString(){
        return tipo.getNome();
    }

}