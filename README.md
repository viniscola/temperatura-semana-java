# Temperatura da semana

Programa em Java que recebe a temperatura máxima e a mínima de cada dia de uma semana e mostra, no console, a faixa de cada dia e um resumo da semana.

Fiz este projeto no 1º semestre de Análise e Desenvolvimento de Sistemas na FIAP. Usei só o que vi até agora: variáveis, `if`, laços, vetores, métodos e `Scanner`. Não há bibliotecas externas.

## O que o programa faz

Pede a máxima e a mínima de segunda a domingo e confere cada valor antes de aceitar. Aceita vírgula ou ponto como separador decimal. Recusa texto, campo vazio, temperatura fora da faixa de -10 °C a 45 °C e mínima maior que a máxima. Quando recusa, diz o motivo e pede o valor de novo.

Com os 14 valores aceitos, ele calcula:

- a média das máximas e a das mínimas;
- o dia mais quente e o dia mais frio;
- o dia com a maior amplitude (diferença entre máxima e mínima);
- quantos dias ficaram acima da média das máximas;
- se a semana esquentou ou esfriou, comparando a máxima média de segunda a quarta com a de sexta a domingo.

No fim, informa quantos valores foram recusados na digitação.

## Exemplo de saída

Os valores abaixo são de teste, não de uma semana real.

```
Faixa de temperatura de cada dia (da mínima à máxima)

seg    16,0  ·······■■■■■■■■■■■■■■■■■■■■■■■■■········  27,5
ter    17,5  ··········■■■■■■■■■■■■■■■■■■■■■■■■■■····  29,0
qua    18,0  ···········■■■■■■■■■■■■■■■■■■■■■■■■■■■■■  31,0
qui    19,0  ·············■■■■■■■■■■■■■■■■■■■■■■■■■··  30,2
sex    15,0  ····■■■■■■■■■■■■■■■■■■■■■■■■■···········  26,0
sáb    13,0  ■■■■■■■■■■■■■■■■■■■■■■■■■···············  24,0
dom    14,5  ···■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■······  28,0
             ^                                      ^
             13,0 °C                          31,0 °C

Resumo
  Média das máximas:    28,0 °C
  Média das mínimas:    16,1 °C
  Dia mais quente:      qua (31,0 °C)
  Dia mais frio:        sáb (13,0 °C)
  Maior amplitude:      dom (13,5 °C entre a mínima e a máxima)
  Dias acima da média:  4 de 7
  Começo x fim:         a máxima média esfriou 3,2 °C
                        (seg a qua x sex a dom)

Dados: 14 valores aceitos, 0 recusado(s) na digitação.
```

Todos os dias usam a mesma escala, da menor mínima à maior máxima da semana. Por isso dá para comparar as faixas só de olhar.

## Como rodar

É preciso ter o JDK instalado (versão 11 ou mais nova).

```
javac Semana.java
java Semana
```

Se os acentos aparecerem trocados no terminal do Windows, rode `chcp 65001` antes.

No IntelliJ: crie um projeto Java, coloque o `Semana.java` dentro da pasta `src` e clique na seta verde ao lado do `main`.

## Como o código está organizado

Tudo fica em `Semana.java`, dividido em quatro partes dentro do `main`: leitura, cálculos, gráfico e resumo. Três métodos auxiliares fazem o trabalho repetido:

- `lerNumero` lê e valida um valor digitado;
- `desenharFaixa` monta a linha do gráfico de um dia;
- `formatar` mostra o número com uma casa decimal e vírgula.

As constantes no topo da classe controlam a faixa de temperatura aceita e a largura do gráfico.

## Autor

Vinicius Jaime Scola

- GitHub: [github.com/viniscola](https://github.com/viniscola)
- LinkedIn: [vinicius-jaime-scola](https://www.linkedin.com/in/vinicius-jaime-scola-0193a03a8)
