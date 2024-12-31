import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorDeMoedas {

    private static final String CHAVE_API = "a8b429b500669ffab7710cd9";
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/" + CHAVE_API + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n====================================");
            System.out.println("      🌎 Conversor de Moedas 🌎");
            System.out.println("====================================");
            System.out.println("Escolha uma moeda para converter para Real (BRL):");
            System.out.println("1 - Dólar (USD)");
            System.out.println("2 - Euro (EUR)");
            System.out.println("3 - Peso Argentino (ARS)");
            System.out.println("4 - Peso Mexicano (MXN)");
            System.out.println("5 - Iene Japonês (JPY)");
            System.out.println("6 - Yuan Chinês (CNY)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> converterMoeda(scanner, "USD");
                case 2 -> converterMoeda(scanner, "EUR");
                case 3 -> converterMoeda(scanner, "ARS");
                case 4 -> converterMoeda(scanner, "MXN");
                case 5 -> converterMoeda(scanner, "JPY");
                case 6 -> converterMoeda(scanner, "CNY");
                case 0 -> {
                    executando = false;
                    exibirMensagemDeSaida();
                }
                default -> System.out.println("Opção inválida. Tente novamente!");
            }
        }

        scanner.close();
    }

    private static void converterMoeda(Scanner scanner, String codigoMoeda) {
        try {
            double taxaDeCambio = obterTaxaDeCambio(codigoMoeda);
            System.out.print("Digite o valor em " + codigoMoeda + ": ");
            double valor = scanner.nextDouble();
            double valorConvertido = valor * taxaDeCambio;
            System.out.printf("Valor em BRL: R$ %.2f%n", valorConvertido);
        } catch (IOException e) {
            System.out.println("Erro ao buscar taxa de câmbio: " + e.getMessage());
        }
    }

    private static double obterTaxaDeCambio(String codigoMoeda) throws IOException {
        URL url = new URL(URL_BASE + codigoMoeda);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        Scanner scanner = new Scanner(conexao.getInputStream());
        StringBuilder resposta = new StringBuilder();
        while (scanner.hasNext()) {
            resposta.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonResposta = new JSONObject(resposta.toString());
        return jsonResposta.getJSONObject("conversion_rates").getDouble("BRL");
    }

    public static void exibirMensagemDeSaida() {
        System.out.println("\n" + "=".repeat(22) + " 🐱✨ Programa encerrado! ✨🐱 " + "=".repeat(20));
        System.out.println("Desenvolvido por: Jeisa Boaventura");
        System.out.println("GitHub: https://github.com/Caaarolb");
        System.out.println("LinkedIn: https://www.linkedin.com/in/-caroline-boaventura/");
        System.out.println("=" + "=".repeat(68) + "\n");
    }
}
