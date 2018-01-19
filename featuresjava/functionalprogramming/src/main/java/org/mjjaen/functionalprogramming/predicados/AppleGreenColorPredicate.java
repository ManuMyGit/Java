package org.mjjaen.functionalprogramming.predicados;

import java.util.function.Predicate;

import org.mjjaen.functionalprogramming.Apple;

public class AppleGreenColorPredicate implements Predicate<Apple> {
	public boolean test(Apple t) {
		return t.getColor().equals("green");
	}
}