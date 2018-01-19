package org.mjjaen.functionalprogramming.predicados;

import java.util.function.Predicate;

import org.mjjaen.functionalprogramming.Apple;

public class AppleHeavyPesoPredicate implements Predicate<Apple> {
	public boolean test(Apple t) {
		return t.getPeso().compareTo(150) > 0;
	}
}