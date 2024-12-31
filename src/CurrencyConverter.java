import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    private static final String API_KEY = "a8b429b500669ffab7710cd9";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
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

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> convertCurrency(scanner, "USD");
                case 2 -> convertCurrency(scanner, "EUR");
                case 3 -> convertCurrency(scanner, "ARS");
                case 4 -> convertCurrency(scanner, "MXN");
                case 5 -> convertCurrency(scanner, "JPY");
                case 6 -> convertCurrency(scanner, "CNY");
                case 0 -> {
                    running = false;
                    showExitMessage();
                }
                default -> System.out.println("Opção inválida. Tente novamente!");
            }
        }

        scanner.close();
    }

    private static void convertCurrency(Scanner scanner, String currencyCode) {
        try {
            double exchangeRate = getExchangeRate(currencyCode);
            System.out.print("Digite o valor em " + currencyCode + ": ");
            double amount = scanner.nextDouble();
            double convertedAmount = amount * exchangeRate;
            System.out.printf("Valor em BRL: R$ %.2f%n", convertedAmount);
        } catch (IOException e) {
            System.out.println("Erro ao buscar taxa de câmbio: " + e.getMessage());
        }
    }

    private static double getExchangeRate(String currencyCode) throws IOException {
        URL url = new URL(BASE_URL + currencyCode);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONObject("conversion_rates").getDouble("BRL");
    }

    public static void showExitMessage() {
        System.out.println("\n" + "=".repeat(22) + " 🐱✨ Programa encerrado! ✨🐱 " + "=".repeat(20));
        System.out.println("Desenvolvido por: Jeisa Boaventura");
        System.out.println("GitHub: https://github.com/Caaarolb");
        System.out.println("LinkedIn: https://www.linkedin.com/in/-caroline-boaventura/");
        System.out.println("=" + "=".repeat(68) + "\n");
    }
}

