public class Bebida implements IProduto {
    private EBebida tipo;
 
    public Bebida(EBebida tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Bebida não pode ser nula");
        }
        this.tipo = tipo;
    }
 
    @Override
    public double valorFinal() {
        return tipo.getPreco();
    }
 
    @Override
    public String toString() {
        return String.format("Bebida: %s - R$ %.2f", tipo.getNome(), valorFinal());
    }
}
 