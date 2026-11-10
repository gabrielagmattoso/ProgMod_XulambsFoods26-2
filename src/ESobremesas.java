public enum ESobremesas{
    BRIGADEIRO(8, "Brigadeiro na colher"),
    PUDIM(10, "Pudim de leite condensado"),
    DOCEDELEITE(9, "Doce de leite no pires");

    double preco;
    String nome;

    ESobremesas(double preco, String nome){
        this.preco = preco;
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }
}