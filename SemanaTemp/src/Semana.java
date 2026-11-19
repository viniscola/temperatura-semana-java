import java.util.Scanner;

public class Semana {

    // Nomes dos dias, na ordem em que serão pedidos
    static final String[] DIAS = {"seg", "ter", "qua", "qui", "sex", "sáb", "dom"};

    // Faixa aceita: fora disso, provavelmente é erro de digitação
    static final double MENOR_ACEITA = -10.0;
    static final double MAIOR_ACEITA = 45.0;

    // largura do gráfico no console (em caracteres)
    static final int LARGURA_GRAFICO = 40;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        double[] maximas = new double[7];
        double[] minimas = new double[7];
        int recusados = 0; // quantos valores precisei pedir de novo

        System.out.println("Temperatura da semana (em °C). Pode usar vírgula ou ponto.");
        System.out.println();

        // leitura dos dados
        for (int i = 0; i < 7; i++) {
            System.out.println("== " + DIAS[i] + " ==");

            // máxima
            Double max = null;
            while (max == null) {
                max = lerNumero(entrada, "  máxima: ");
                if (max == null) recusados++;
            }

            // mínima: precisa ser menor ou igual à máxima
            Double min = null;
            while (min == null) {
                min = lerNumero(entrada, "  mínima: ");
                if (min == null) {
                    recusados++;
                } else if (min > max) {
                    System.out.println("  A mínima não pode ser maior que a máxima (" + formatar(max) + "). Digite de novo.");
                    min = null;
                    recusados++;
                }
            }

            maximas[i] = max;
            minimas[i] = min;
        }

        // cálculos
        double somaMax = 0;
        double somaMin = 0;
        int diaMaisQuente = 0;     // índice do dia com a maior máxima
        int diaMaisFrio = 0;       // índice do dia com a menor mínima
        int diaMaiorAmplitude = 0; // índice do dia com maior diferença entre máxima e mínima

        for (int i = 0; i < 7; i++) {
            somaMax += maximas[i];
            somaMin += minimas[i];

            if (maximas[i] > maximas[diaMaisQuente]) diaMaisQuente = i;
            if (minimas[i] < minimas[diaMaisFrio]) diaMaisFrio = i;
            if (amplitude(maximas, minimas, i) > amplitude(maximas, minimas, diaMaiorAmplitude)) diaMaiorAmplitude = i;
        }

        double mediaMax = somaMax / 7;
        double mediaMin = somaMin / 7;

        // dias acima da média das máximas
        int diasAcimaDaMedia = 0;
        for (int i = 0; i < 7; i++) {
            if (maximas[i] > mediaMax) diasAcimaDaMedia++;
        }

        // começo x fim da semana: média das máximas dos 3 primeiros e dos 3 últimos dias
        double inicio = (maximas[0] + maximas[1] + maximas[2]) / 3;
        double fim = (maximas[4] + maximas[5] + maximas[6]) / 3;

        // gráfico
        // a escala vai da menor mínima até a maior máxima da semana
        double escalaMin = minimas[diaMaisFrio];
        double escalaMax = maximas[diaMaisQuente];

        System.out.println();
        System.out.println("Faixa de temperatura de cada dia (da mínima à máxima)");
        System.out.println();
        for (int i = 0; i < 7; i++) {
            String barra = desenharFaixa(minimas[i], maximas[i], escalaMin, escalaMax);
            System.out.printf("%s  %6s  %s  %-6s%n", DIAS[i], formatar(minimas[i]), barra, formatar(maximas[i]));
        }
        System.out.printf("%s  %6s  %s  %-6s%n", "   ", "", "^" + " ".repeat(LARGURA_GRAFICO - 2) + "^", "");
        System.out.printf("%s  %6s  %-" + (LARGURA_GRAFICO / 2) + "s%" + (LARGURA_GRAFICO / 2) + "s%n",
                "   ", "", formatar(escalaMin) + " °C", formatar(escalaMax) + " °C");

        // resumo
        System.out.println();
        System.out.println("Resumo");
        System.out.println("  Média das máximas:    " + formatar(mediaMax) + " °C");
        System.out.println("  Média das mínimas:    " + formatar(mediaMin) + " °C");
        System.out.println("  Dia mais quente:      " + DIAS[diaMaisQuente] + " (" + formatar(maximas[diaMaisQuente]) + " °C)");
        System.out.println("  Dia mais frio:        " + DIAS[diaMaisFrio] + " (" + formatar(minimas[diaMaisFrio]) + " °C)");
        System.out.println("  Maior amplitude:      " + DIAS[diaMaiorAmplitude] + " ("
                + formatar(amplitude(maximas, minimas, diaMaiorAmplitude)) + " °C entre a mínima e a máxima)");
        System.out.println("  Dias acima da média:  " + diasAcimaDaMedia + " de 7");

        // tendência da semana, comparando o começo com o fim
        double diferenca = fim - inicio;
        String tendencia;
        if (diferenca >= 1) {
            tendencia = "esquentou " + formatar(diferenca) + " °C";
        } else if (diferenca <= -1) {
            tendencia = "esfriou " + formatar(-diferenca) + " °C";
        } else {
            tendencia = "ficou estável (variação menor que 1 °C)";
        }
        System.out.println("  Começo x fim:         a máxima média " + tendencia);
        System.out.println("                        (seg a qua x sex a dom)");

        // qualidade dos dados: quantas entradas foram recusadas
        System.out.println();
        System.out.println("Dados: 14 valores aceitos, " + recusados + " recusado(s) na digitação.");

        entrada.close();
    }

    /*
     * lê um número digitado. Aceita vírgula ou ponto.
     * devolve null (e explica o motivo) se o valor não servir.
     */
    static Double lerNumero(Scanner entrada, String pergunta) {
        System.out.print(pergunta);
        String texto = entrada.nextLine().trim().replace(",", ".");

        if (texto.isEmpty()) {
            System.out.println("  Nada foi digitado. Tente de novo.");
            return null;
        }

        double valor;
        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            System.out.println("  \"" + texto + "\" não é um número. Exemplo válido: 23,5");
            return null;
        }

        if (valor < MENOR_ACEITA || valor > MAIOR_ACEITA) {
            System.out.println("  " + formatar(valor) + " °C está fora da faixa aceita ("
                    + formatar(MENOR_ACEITA) + " a " + formatar(MAIOR_ACEITA) + "). Confira e digite de novo.");
            return null;
        }

        return valor;
    }

    // diferença entre a máxima e a mínima de um dia
    static double amplitude(double[] maximas, double[] minimas, int dia) {
        return maximas[dia] - minimas[dia];
    }

    /*
     * desenha a faixa de um dia numa linha de LARGURA_GRAFICO caracteres:
     * pontos fora da faixa e '■' entre a mínima e a máxima.
     */
    static String desenharFaixa(double min, double max, double escalaMin, double escalaMax) {
        double tamanhoEscala = escalaMax - escalaMin;
        if (tamanhoEscala == 0) tamanhoEscala = 1; // evita divisão por zero se todos os valores forem iguais

        int inicio = (int) Math.round((min - escalaMin) / tamanhoEscala * (LARGURA_GRAFICO - 1));
        int fim = (int) Math.round((max - escalaMin) / tamanhoEscala * (LARGURA_GRAFICO - 1));

        String linha = "";
        for (int c = 0; c < LARGURA_GRAFICO; c++) {
            if (c >= inicio && c <= fim) {
                linha += "■";
            } else {
                linha += "·";
            }
        }
        return linha;
    }

    // formata com uma casa decimal e vírgula (padrão brasileiro)
    static String formatar(double valor) {
        return String.format("%.1f", valor).replace(".", ",");
    }
}
