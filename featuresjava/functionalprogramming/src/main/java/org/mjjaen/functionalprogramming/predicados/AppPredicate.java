package org.mjjaen.functionalprogramming.predicados;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.mjjaen.functionalprogramming.Apple;

public class AppPredicate {
	public static final String ESTRATEGIA_COLOR = "ESTRATEGIA_COLOR";
	public static final String ESTRATEGIA_PESO = "ESTRATEGIA_PESO";
	
	public static void main(String[] args) {
		Apple apple1 = new Apple("red", 60);
		Apple apple2 = new Apple("green", 80);
		Apple apple3 = new Apple("blue", 100);
		Apple apple4 = new Apple("white", 120);
		Apple apple5 = new Apple("black", 140);
		Apple apple6 = new Apple("yellow", 160);
		Apple apple7 = new Apple("grey", 180);
		Apple apple8 = new Apple("pink", 200);
		Apple apple9 = new Apple("purple", 210);
		
		List<Apple> lista = new Vector<Apple>();
		lista.add(apple1);
		lista.add(apple2);
		lista.add(apple3);
		lista.add(apple4);
		lista.add(apple5);
		lista.add(apple6);
		lista.add(apple7);
		lista.add(apple8);
		lista.add(apple9);
		
		List<Apple> resultado = filterApplesLambda(lista, getEstrategiaFiltrado(ESTRATEGIA_COLOR));
		System.out.println("Resultado por filtro de color");
		resultado.stream().forEach(p -> System.out.println(p));
		resultado = filterApplesLambda(lista, getEstrategiaFiltrado(ESTRATEGIA_PESO));
		System.out.println("Resultado por filtro de peso");
		resultado.stream().forEach(p -> System.out.println(p));
		
		//Lambdas
		resultado = filterApplesLambda(lista, getEstrategiaFiltradoLambda(ESTRATEGIA_COLOR));
		System.out.println("Resultado por filtro de color");
		resultado.stream().forEach(p -> System.out.println(p));
		resultado = filterApplesLambda(lista, getEstrategiaFiltradoLambda(ESTRATEGIA_PESO));
		System.out.println("Resultado por filtro de peso");
		resultado.stream().forEach(p -> System.out.println(p));
	}
	
	public static List<Apple> filterApples(List<Apple> lista, Predicate<Apple> predicado) {
		List<Apple> resultado = new Vector<Apple>();
		for(Apple apple : lista) {
			if(predicado.test(apple))
				resultado.add(apple);
		}
		return resultado;
	}
	
	/*
	 * And example with functional programming
	 */
	public static List<Apple> filterApplesLambda(List<Apple> lista, Predicate<Apple> predicado) {
		List<Apple> resultado = lista.stream().filter(predicado).collect(Collectors.toList());
		return resultado;
	}
	
	public static Predicate<Apple> getEstrategiaFiltrado(String estrategia) {
		if(estrategia.equals(ESTRATEGIA_COLOR))
			return new AppleGreenColorPredicate();
		else if(estrategia.equals(ESTRATEGIA_PESO))
			return new AppleHeavyPesoPredicate();
		else
			throw new RuntimeException("Estrategia de búsqueda no reconocida");
	}
	
	/*
	 * With lambdas we have a clean code and there is no need of AppleGreenColorPredicate and AppleHeavyPesoPredicate classes
	 */
	public static Predicate<Apple> getEstrategiaFiltradoLambda(String estrategia) {
		if(estrategia.equals(ESTRATEGIA_COLOR))
			return p -> p.getColor().equals("green");
		else if(estrategia.equals(ESTRATEGIA_PESO))
			return p -> p.getPeso().compareTo(150) > 0;
		else
			throw new RuntimeException("Estrategia de búsqueda no reconocida");
	}
}