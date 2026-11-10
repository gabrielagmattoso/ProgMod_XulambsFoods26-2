public class Sobremesa implements IProduto {
    private ESobremesas tipo;

    public Sobremesa(ESobremesas tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Sobremesa não pode ser nula");
        }
        this.tipo = tipo;
    }

    @Override
    public double valorFinal() {
        return tipo.getPreco();
    }

    @Override
    public String toString() {
        return String.format("Sobremesa: %s - R$ %.2f", tipo.getNome(), valorFinal());
    }
}