public enum EBebida {
    AGUADACASA(0,"Agua da casa"),
    AGUACOMGAS(4, "Água com gás"),
    CHACOMGAS(6, "Chá com gás"),
    SUCOS(9, "Sucos"),
    CERVEJA(15,"Cerveja"),
    TAÇADEVINHO(15, " Taça de vinho");




    double preco;
    String nome;

    EBebida(double preco, String nome){
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