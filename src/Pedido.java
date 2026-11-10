/** 
* MIT License
*
* Copyright(c) 2024-26 João Caram <caram@pucminas.br>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import java.time.LocalDate;
import java.util.LinkedList;

public abstract class Pedido {
    private  static int ultimoPedido;
    private LocalDate data;
    protected LinkedList<IProduto> itens;
    private int idPedido;
    private boolean aberto;

    public Pedido(){
        ultimoPedido++;
        data = LocalDate.now();
        itens = new LinkedList<>();
        idPedido = ultimoPedido;
        aberto = true;
    }

    protected void verificarInclusao(IProduto produto){
        if(!aberto)
            throw new PedidoFechadoException(idPedido);
    }

    /**
     * Adiciona um produto (pizza, bebida ou sobremesa) ao pedido.
     * @param produto Produto a ser adicionado
     * @return Quantidade de itens do pedido após a inclusão
     * @throws PedidoFechadoException se o pedido estiver fechado
     * @throws IllegalArgumentException se o produto for nulo
     */
    public int adicionar(IProduto produto){
        if(produto == null)
            throw new IllegalArgumentException("Produto não foi criado corretamente. Não será adicionado ao pedido");

        verificarInclusao(produto);
        itens.add(produto);
        return itens.size();
    }

    public void fecharPedido(){
        aberto = false;
    }

    public int getID(){
        return  idPedido;
    }

    public abstract double precoAPagar();
    
    protected double valorItens(){
        double preco = 0d;
        for (IProduto item: itens) {
            preco += item.valorFinal();
        }
        return preco;
    }

    protected String cabecalho(){
        String estado = aberto ? "aberto" : "fechado";
        return ( String.format("Pedido nº %d - %s (%s) com %d itens\n", idPedido, data, estado, itens.size()));
    }

    protected String detalhesPedido(){
        StringBuilder detalhes = new StringBuilder();
        for (IProduto item: itens) {
            detalhes.append(String.format("----\n%s\n", 
                            item.toString()));
        }
        return detalhes.toString();
    }



    @Override 
    public boolean equals(Object obj){
        Pedido outro = (Pedido)obj;
        return (
            this.idPedido == outro.idPedido &&
            this.data.equals(outro.data)
        );
    }

    @Override 
    public int hashCode(){
       return  idPedido;
    }
}