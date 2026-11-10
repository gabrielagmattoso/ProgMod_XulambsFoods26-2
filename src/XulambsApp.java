import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class XulambsApp {
    private List<Pedido> listaPedidos;
    private Scanner teclado;  


    String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return teclado.nextLine();
    }

    void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void pausa() {
        lerTexto("Digite <ENTER> para continuar");
        limparTela();
    }

    int lerNumero(String mensagem) {
        int retorno;
        try {
            retorno = Integer.parseInt(lerTexto(mensagem));
        } catch (NumberFormatException excecao) {
            System.out.println(excecao.getMessage());
            retorno = -1;
        }
        return retorno;
    }


    void config() {
        listaPedidos = new LinkedList<>();
        teclado = new Scanner(System.in);
    }

    void cabecalho() {
        limparTela();
        System.out.println("XULAMBS FOODS - v0.6");
        System.out.println("=====================");
        System.out.println("Pizzas vendidas hoje: " +
                Pizza.getPizzasVendidas());
    }

    int exibirMenu() {
        cabecalho();
        System.out.println("1 - Abrir pedido");
        System.out.println("2 - Alterar pedido");
        System.out.println("3 - Relatório de um pedido");
        System.out.println("4 - Encerrar pedido");
        System.out.println("0 - Sair");
        return lerNumero("Digite sua opção: ");
    }



    Pizza comprarPizza() {
        cabecalho();
        int adicionais;
        Pizza novaPizza = new Pizza();

        EBorda borda = escolherBorda();
        novaPizza.adicionarBorda(borda);

        adicionais = lerNumero("Quantos ingredientes? ");
        try {
            novaPizza.adicionarIngredientes(adicionais);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            pausa();
            return comprarPizza();
        } catch (MaximoDeIngredientesException maisDoQueOMaximo) {
            System.out.println(maisDoQueOMaximo.getMessage());
            String completar = lerTexto("Deseja completar a pizza com o máximo possível(1) ou cancelar(2)? ");
            if (completar.toLowerCase().equals("1"))
                maisDoQueOMaximo.completarPizza(novaPizza);
            else
                novaPizza = null;
        }

        exibirRelatorio(novaPizza);
        return novaPizza;
    }

    EBorda escolherBorda() {
        EBorda[] bordas = EBorda.values();
        EBorda bordaEscolhida = EBorda.TRADICIONAL;
        int i = 1;
        System.out.println("Escolha sua borda: ");
        for (EBorda eBorda : bordas) {
            System.out.println(String.format("%d - Borda %s", i, eBorda.getNome()));
            i++;
        }
        int escolha = lerNumero("Digite sua opção: ");
        try {
            bordaEscolhida = bordas[escolha - 1];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Valor inválido. Escolha novamente (obrigatório para comprar uma pizza.");
            return escolherBorda();
        }

        return bordaEscolhida;
    }


    Bebida escolherBebida() {
        EBebida[] bebidas = EBebida.values();

        System.out.println("Escolha sua bebida: ");
        for (int i = 0; i < bebidas.length; i++) {
            System.out.println(String.format("%d - %s (R$ %.2f)",
                    i + 1, bebidas[i].getNome(), bebidas[i].getPreco()));
        }

        int escolha = lerNumero("Digite sua opção: ");

        if (escolha < 1 || escolha > bebidas.length) {
            System.out.println("Valor inválido. Escolha novamente.");
            return escolherBebida();
        }
        return new Bebida(bebidas[escolha - 1]);
    }

    Sobremesa escolherSobremesa() {
        ESobremesas[] sobremesas = ESobremesas.values();

        System.out.println("Escolha sua sobremesa: ");
        for (int i = 0; i < sobremesas.length; i++) {
            System.out.println(String.format("%d - %s (R$ %.2f)",
                    i + 1, sobremesas[i].getNome(), sobremesas[i].getPreco()));
        }

        int escolha = lerNumero("Digite sua opção: ");

        if (escolha < 1 || escolha > sobremesas.length) {
            System.out.println("Valor inválido. Escolha novamente.");
            return escolherSobremesa();
        }
        return new Sobremesa(sobremesas[escolha - 1]);
    }

    IProduto escolherProduto() {
        cabecalho();
        System.out.println("Escolha o produto: ");
        System.out.println("1 - Pizza");
        System.out.println("2 - Bebida");
        System.out.println("3 - Sobremesa");
        System.out.println("0 - Nenhum (finalizar)");
        int escolha = lerNumero("Digite sua opção: ");

        return switch (escolha) {
            case 1 -> comprarPizza();
            case 2 -> escolherBebida();
            case 3 -> escolherSobremesa();
            default -> null;
        };
    }

    void adicionarAoPedido(Pedido pedido, IProduto produto) {
        try {
            pedido.adicionar(produto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }



    void armazenarPedido(Pedido pedido) {
        if (pedido != null)
            listaPedidos.add(pedido);
    }

    PedidoEntrega criarPedidoEntrega() {
        String dist = lerTexto("Qual a distância da entrega? ");
        double distancia = Double.parseDouble(dist);
        return new PedidoEntrega(distancia);
    }

    Pedido escolherTipoPedido() {
        cabecalho();
        System.out.println("Escolha o tipo do pedido: ");
        System.out.println("1 - Local ");
        System.out.println("2 - Para entrega");
        int escolha = lerNumero("Digite sua opção: ");
        return switch (escolha) {
            case 1 -> new PedidoLocal();
            case 2 -> criarPedidoEntrega();
            default -> null;
        };
    }

    void abrirPedido() {
        Pedido novoPedido = escolherTipoPedido();
        if (novoPedido == null) {
            System.out.println("Tipo de pedido inválido.");
            return;
        }

        String continuar;
        do {
            IProduto produto = escolherProduto();
            adicionarAoPedido(novoPedido, produto);
            continuar = lerTexto("Mais algum item? (s/n)");
        } while (continuar.toLowerCase().equals("s"));

        exibirRelatorio(novoPedido);
        armazenarPedido(novoPedido);
    }

    void alterarPedido() {
        Pedido buscado = (Pedido) localizar();
        if (buscado != null) {
            adicionarAoPedido(buscado, escolherProduto());
            exibirRelatorio(buscado);
        }
    }

    void relatorioPedido() {
        Pedido buscado = (Pedido) localizar();
        if (buscado != null) {
            exibirRelatorio(buscado);
        }
    }

    void encerrarPedido() {
        Pedido buscado = (Pedido) localizar();
        if (buscado != null) {
            buscado.fecharPedido();
            exibirRelatorio(buscado);
        }
    }

    Object localizar() {
        cabecalho();
        System.out.println("LOCALIZAÇÃO DE PEDIDOS\n");
        int codigo = lerNumero("Código do pedido: ");
        Object localizado = null;
        for (int i = 0; i < listaPedidos.size() && localizado == null; i++) {
            Object candidato = listaPedidos.get(i);
            if (candidato.hashCode() == codigo) {
                localizado = candidato;
            }
        }
        return localizado;
    }

    void exibirRelatorio(Object objeto) {
        cabecalho();
        System.out.println();
        String mensagem = "";
        if (objeto != null)
            mensagem = objeto.toString();

        System.out.println(mensagem);
    }

  

    void executar() {
        int opcao;
        config();
        do {
            opcao = exibirMenu();
            switch (opcao) {
                case 1 -> abrirPedido();
                case 2 -> alterarPedido();
                case 3 -> relatorioPedido();
                case 4 -> encerrarPedido();
                case 0 -> System.out.println("Encerrando!");
                default -> System.out.println("Opção inválida");
            }
            pausa();
        } while (opcao != 0);
    }

   
    public static void main(String[] args) {
        new XulambsApp().executar();
    }
}