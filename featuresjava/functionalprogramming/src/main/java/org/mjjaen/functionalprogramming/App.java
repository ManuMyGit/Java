package org.mjjaen.functionalprogramming;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class App {
    public static void main( String[] args ) {
    	exampleMetodoReferenceClass();
    	exampleMetodoReferenceInstance();
    	exampleMetodoReferenceType();
    	exampleMetodoReferenceEmptyConstructor();
    	exampleMetodoReferenceNotEmptyConstructor();
    	exampleLambda();
    	exampleStream();
    	exampleOptional();
    	examplePredicado();
    	exampleFunctions();
    	exampleComparators();
    }
    
    private static Person[] initializeArrayPerson() {
    	Person[] personArray = new Person[5];
    	Person p1 = new Person("Person 1", LocalDate.of(1981, 7, 24));
    	Person p2 = new Person("Person 2", LocalDate.of(1982, 1, 18));
    	Person p3 = new Person("Person 3", LocalDate.of(1979, 7, 5));
    	Person p4 = new Person("Person 4", LocalDate.of(1984, 11, 1));
    	Person p5 = new Person("Person 5", LocalDate.of(1982, 1, 2));
    	personArray[0] = p1;
    	personArray[1] = p2;
    	personArray[2] = p3;
    	personArray[3] = p4;
    	personArray[4] = p5;
    	return personArray;
    }
    
    private static void printArray(Object[] array) {
    	for(int i = 0; i < array.length; i ++) {
    		System.out.println(array[i]);
    	}
    }
    
    private static void exampleMetodoReferenceClass() {
    	System.out.println("\nMethod exampleMetodoReferenceClass:\n");
    	Person[] personArray = initializeArrayPerson();
    	System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using lambdas
        Arrays.sort(personArray, (a, b) -> Person.compareByAge(a, b));
        System.out.println("Array after ordering:");
        printArray(personArray);
        personArray = initializeArrayPerson();
        System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using reference methods
        Arrays.sort(personArray, Person::compareByAge);
        System.out.println("Array after ordering:");
        printArray(personArray);
    }
    
    private static void exampleMetodoReferenceInstance() {
    	System.out.println("\nMethod exampleMetodoReferenceInstance:\n");
    	Comparator<Person> comparator = Person.comparator;
    	Person[] personArray = initializeArrayPerson();
    	System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using lambdas
        Arrays.sort(personArray, (a, b) -> comparator.compare(a, b));
        System.out.println("Array after ordering:");
        printArray(personArray);
        personArray = initializeArrayPerson();
        System.out.println("Array before ordering:");
    	printArray(personArray);
        //Order an array using reference methods
        Arrays.sort(personArray, comparator::compare);
        System.out.println("Array after ordering:");
        printArray(personArray);
    }
    
    private static void exampleMetodoReferenceType() {
    	System.out.println("\nMethod exampleMetodoReferenceType:\n");
    	String[] strings = {"asdf", "asdfas2", "adsfh23", "xvzcsd", "234adsf", "jsdf34"};
    	System.out.println("Array before ordering:");
    	printArray(strings);
        //Order an array using lambdas
        Arrays.sort(strings, (a, b) -> a.compareToIgnoreCase(b));
        System.out.println("Array after ordering:");
    	printArray(strings);
        String[] anotherStrings = {"asdf", "asdfas2", "adsfh23", "xvzcsd", "234adsf", "jsdf34"};
        System.out.println("Array before ordering:");
        printArray(anotherStrings);
        //Order an array using reference methods
        Arrays.sort(anotherStrings, String::compareToIgnoreCase);
        System.out.println("Array after ordering:");
    	printArray(anotherStrings);
    }
    
    private static void exampleMetodoReferenceEmptyConstructor() {
    	System.out.println("\nMethod exampleMetodoReferenceEmptyConstructor:\n");
    	//Create a person using lambdas
        Supplier<Person> s1 = () -> new Person();
        Person p1 = s1.get();
        System.out.println(p1);
        //Create a persons using reference methods
        Supplier<Person> s2 = Person::new;
        Person p2 = s2.get();
        System.out.println(p2);
    }
    
    private static void exampleMetodoReferenceNotEmptyConstructor() {
    	System.out.println("\nMethod exampleMetodoReferenceNotEmptyConstructor:\n");
    	//Create a person using lambdas
        BiFunction<String, LocalDate, Person> f1 = (a, b) -> new Person(a, b);
        Person p1 = f1.apply("Person 1", LocalDate.of(1981, 7, 24));
    	//Person p2 = new Person("Person 2", LocalDate.of(1982, 1, 18));
        System.out.println(p1);
        //Create a persons using reference methods
        BiFunction<String, LocalDate, Person> f2 = Person::new;
        Person p2 = f2.apply("Person 1", LocalDate.of(1981, 7, 24));
        System.out.println(p2);
    }
    
    private static void exampleLambda() {
    	System.out.println("\nMethod exampleLambda:\n");
    	System.out.println("Initial array:");
    	List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 1, 5);
    	for(Integer numero : list) {
    		System.out.print(numero + " ");
    	}
    	System.out.println("");
    	System.out.println("First element");
    	list.stream().findFirst().ifPresent(System.out::println);
    	System.out.println("Filter an element");
    	list.stream().filter(elemento -> elemento == 5).forEach(System.out::println);
    	System.out.println("Distinct elements");
    	list.stream().distinct().forEach(a -> System.out.print(a + " "));
    	System.out.println("Duplicate value of the elements");
    	list.stream().map(s -> s * 2).forEach(a -> System.out.print(a + " "));
    	System.out.println("\nRemove duplicate elements and then duplicate values");
    	list.stream().distinct().map(s -> s * 2).forEach(a -> System.out.print(a + " "));
    }
    
    private static void exampleStream() {
    	System.out.println("\nMethod exampleStream:\n");
    	List<String> lista = new Vector<String>();
    	lista.add("Taller");
    	lista.add("Taller Lambdas y Stream API");
    	long contador = lista.stream()	//List<String> --> Stream<String>
    			.map(s -> s.split(""))	//Stream<String> --> Stream<Stream<String> -> Two elements
    			.distinct()				//Stream<Stream<String>> --> Stream<Stream<String>>
    			.count();				//Stream<Stream<String>> --> long
    	System.out.println("Counter");
    	System.out.println(contador);
    	contador = lista.stream()			//List<String> --> Stream<String>
    			.map(s -> s.split(""))		//Stream<String> --> Stream<Stream<String> -> Two elements
    			.flatMap(Arrays::stream)	//Stream<Stream<String>> --> Stream<String> -> One flatted element
    			.distinct()					//Stream<String> --> Stream<String> 
    			.count();					//Stream<String> --> long
    	System.out.println("Counter again");
    	System.out.println(contador);
    }
    
    private static void exampleOptional() {
    	Optional<Person> optional = Optional.ofNullable(null);
    	System.out.println("optional is not null but the object inside the optional is null. You can use optional without throwing NullPointerException");
    	System.out.println(optional);
    	System.out.println(optional.orElse(null));
    	System.out.println("You can check if the object inside the optional is null");
    	if(optional.isPresent())
    		System.out.println("The object inside the optional is not null");
    	else
    		System.out.println("The object inside the optional is null");
    	optional = Optional.of(new Person());
    	if(optional.isPresent())
    		System.out.println("The object inside the optional is not null");
    	else
    		System.out.println("The object inside the optional is null");
    }
    
    //Predicates include and, or y negate methods
    private static void examplePredicado() {
    	System.out.println("\nMethod examplePredicado:\n");
    	
    	List<String> lista = new Vector<String>();
    	lista.add("Asunto");
    	lista.add("Andúril");
    	lista.add("Ar");
    	lista.add("Barba");
    	lista.add("Bar");
    	lista.add("Turgon");
    	
    	//Unique predicates
    	Predicate<String> p1 = s -> s.length() > 3;
    	Predicate<String> p2 = s -> s.charAt(0) == 'A';
    	Predicate<String> p3 = s -> s.compareToIgnoreCase("Asunto") == 0;
    	
    	//Predicates composition, fromt left to right
    	Predicate<String> p4 = p1.and(p2).or(p3).negate();
    	
    	//Use of predicates
    	List<String> resultado = lista.stream().filter(p1).collect(Collectors.toList());
    	System.out.println("Apply p1 predicate");
    	resultado.stream().forEach(p -> System.out.println(p));
    	resultado = lista.stream().filter(p2).collect(Collectors.toList());
    	System.out.println("Apply p2 predicate");
    	resultado = lista.stream().filter(p3).collect(Collectors.toList());
    	System.out.println("Apply p3 predicate");
    	resultado = lista.stream().filter(p4).collect(Collectors.toList());
    	System.out.println("Apply p4 predicate");
    }
    
    //Functions include aply, andThen and compose methods
    private static void exampleFunctions() {
    	System.out.println("\nMethod exampleFunctions:\n");
    	
    	//Unique functions
    	Function<String, Integer> f1 = String::length;
    	Function<Integer, Integer> f2 = i -> i * 2;
    	
    	//Functions composition
    	Function<String, Integer> f3 = f1.andThen(f2);
    	System.out.println(f3.apply("example"));
    	
    	//Simple bifunctions
    	BiFunction<String, String, Integer> bf1 = (a, b) -> a.length() + b.length();
    	
    	//Composition of bifunction with function
    	BiFunction<String, String, Integer> bf2 = bf1.andThen(f2);
    	System.out.println(bf2.apply("example1", "example2"));
    }
    
    //Comparatos include compare, reserved, thenComparing (overloaded), thenComparingDouble, thenComparingInt, thenComparingLong methods
    private static void exampleComparators() {
    	System.out.println("\nMethod exampleComparators:\n");
    	
    	//Unique comparators
    	Comparator<String> c1 = (a, b) -> a.length() - b.length();
    	Comparator<String> c2 = String::compareTo;
    	
    	//Comparator composition. First c1 is used and then, if equals, c2 is used.
    	Comparator<String> c3 = c1.thenComparing(c2);
    	System.out.println(c3.compare("srint1", "string11"));
    }
}