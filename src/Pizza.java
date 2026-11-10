/** 
 * MIT License
 *
 * Copyright(c) 2026 João Caram <caram@pucminas.br>
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

import java.security.InvalidParameterException;

/**
 * Pizza: com preço base fixo e preço final definido pela quantidade de ingredientes.
 */
public class Pizza implements IProduto{
    private static final double PRECO_BASE;
	private static final int MAXIMO_INGREDIENTES;
	private static final double VALOR_INGREDIENTE;

    private static int pizzasVendidas;
    
    private  EBorda borda;
    private int quantidadeIngredientes;

    /**
     * Inicializador static
     */
    static{
        PRECO_BASE = 29;
	    MAXIMO_INGREDIENTES = 8;
	    VALOR_INGREDIENTE = 5;
        pizzasVendidas = 0;
    }

    public static int getPizzasVendidas(){
        return pizzasVendidas;
    }

    private void init(int adicionais) {
		 adicionarIngredientes(adicionais);
         borda = EBorda.TRADICIONAL;
         pizzasVendidas++;
    }

    /**
     * Cria uma pizza sem adicionais.
     */
	public Pizza() {
		init(0);
	}

    /**
     * Cria uma pizza com os adicionais especificados. Se o valor for inválido,
     * a pizza fica sem adicionais
     * @param adicionais Adicionais para a pizza (>=0 e <=8)
     */
	public Pizza(int adicionais) {
		init(adicionais);
	}

    /**
     * Retorna o preço a se pagar por uma pizza. (base + valor dos adicionais)
     * @return Double positivo com o valor da pizza.
     */
    @Override 
	public double valorFinal() {
		return PRECO_BASE + valorAdicionais() + borda.getValor();
	}

    
	private double valorAdicionais() {
		return quantidadeIngredientes * VALOR_INGREDIENTE;
	}

    /**
     * Gera um cupom descritivo, contendo quantidade de ingredientes, preço base, valor
     * dos adicionais e valor final.
     * @return String com as informações descritas.
     */
    @Override 
	public String toString() {
		String cupom = "";
        
        cupom += String.format("Pizza com %d ingredientes e borda %s\n",      quantidadeIngredientes, borda.getNome());

        cupom += String.format("\tPreço base: R$ %.2f\n", PRECO_BASE);
        cupom += String.format("\tBorda: R$ %.2f\n", borda.getValor());
        cupom += String.format("\tAdicionais: R$ %.2f\n", valorAdicionais());
        cupom += String.format("VALOR A PAGAR: R$ %.2f", valorFinal());

        return cupom;
	}

    @Override 
    public  int hashCode(){
        return this.toString().hashCode();
    }

    /**
     * Verifica se a quantidade de ingredientes passada pode
     * ser adicionada na pizza, retornando true/false 
     * confore o resultado.
     * @param quantidade Ingredientes a serem adicionados
     * @return TRUE se a quantidade for >=0 e o total <=8, FALSE para casos contrários.
     */
	private void verificarIngredientes(int quantidade) {
        if(quantidade < 0){
            throw new InvalidParameterException("Ingredientes não podem ser negativos");
        }
        if(quantidade + quantidadeIngredientes > MAXIMO_INGREDIENTES){
            throw new MaximoDeIngredientesException(MAXIMO_INGREDIENTES-quantidadeIngredientes, MAXIMO_INGREDIENTES);
        }
    }

    /**
     * Tenta adicionar a quantidade de ingredientes na pizza.
     * @param quantidade Inteiro não negativo. Valores negativos causam
     * exceção de parâmetro inválido. 
     * @throws InvalidParameterException em caso de quantidade negativa
     * @throws IllegalStateException caso o máximo de ingredientes seja ultrapassado.
     * @return A quantatidade de adicionais na pizza após a execução do método.
     */
	public int adicionarIngredientes(int quantidade) {
		verificarIngredientes(quantidade);
        quantidadeIngredientes += quantidade;
        return quantidadeIngredientes;
	}

    /**
     * Adiciona uma borda à pízza. Se já houver uma borda, será substituida.
     * Em caso de borda nula, será adicionada uma borda tradicional.
     * @param borda Borda a ser adicionada
     * @return Preco da pizza com a borda atual.
     */
    public double adicionarBorda(EBorda borda){
        if(borda == null)
            borda = EBorda.TRADICIONAL;
        this.borda = borda;
        return valorFinal();
    }
}