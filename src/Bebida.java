public class Beebida implements IProduto{
    private EBebida tipo;

    public Beebida(EBebida tipo){
        if(tipo == null){
            throw new IllegalStateException("Bebina é nula");
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