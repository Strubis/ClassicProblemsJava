package chapter01;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;

public class fibonacci {
    
    private static int last, next;
    
    /**
     * Map.of() está disponível a partir do Java 9, ele retorna um map imutável.
     * Isto cria um map com 0->0, 1->1, que são nossos casos base.
     * */
    static Map<Integer, Integer> memo = new HashMap<>( Map.of(0, 0, 1, 1) );

    public static void main(String[] args) {
        // StackOverFlow
        //fib1(5);

        // Recursão melhorada sem gerar erro
        //fib2(5);

        // Recursão com melhoria no tempo de execução
        //fib3(40);

        // Utilizando um laço
        //fib4(10);
        
        // Utiliza��o da stream
        //Fib5 fib5 = new Fib5();
        //fib5.stream().limit(41).forEachOrdered(System.out::println);
    }

    /**
     * Recursão infinita gera um erro de StackOverFlow.
     * */
    private static int fib1(int n){
        return fib1(n - 1) + fib1(n - 2);
    }

    /**
     * Recursão com caso base para parada.
     * */
    private static int fib2(int n){
        if( n < 2 ) { return n; }

        return fib2(n - 1) + fib2(n - 2);
    }

    /**
     * Recursão com melhoria utilizando o conceito de memorização
     * dos valores já calculados.
     * Esse método utiliza um map para guardar os valores calculados,
     * o que nos faz economizar um tempo de execução muito importante,
     * para valores relativamente altos.
     * */
    private static int fib3(int n){
        if( !memo.containsKey(n) ){
            // Memorização do valor
            memo.put(n, fib3(n - 1) + fib3(n - 2) );
        }

        return memo.get(n);
    }

    /**
     * Utilizando iteração para resolver o problema, onde os valores
     * são trocados e o limite para o for é n - 1 vezes.
     * */
    private static int fib4(int n){
        last = 0; next = 1; // fib(0) e fib(1)

        for (int i = 0; i < n; i++) {
            int oldLast = last;
            last = next;
            next += oldLast;
        }

        return last;
    }
    
    /**
     * Utilizando stream para melhorar ainda mais o tempo de execu��o.
     * Necess�rio estar numa nova classe isolada para ter o funcionamento.
     */
    public static class Fib5{
        private int last = 0, next = 1; // fib(0) e fib(1)
        
        public IntStream stream(){
            return IntStream.generate( () -> {
                int oldLast = last;
                last = next;
                next += oldLast;
                return oldLast;
            } );
        }
    }
}
