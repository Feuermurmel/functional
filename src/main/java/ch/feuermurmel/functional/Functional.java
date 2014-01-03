package ch.feuermurmel.functional;

import java.util.*;

import static ch.feuermurmel.functional.Tuples.*;

public class Functional {
	private Functional() {
	}

	/**
	 * Apply a function to the elements of a collection and return the results as a {@link List}.
	 *
	 * @param collection The collection from which the elements are taken.
	 * @param function The function that is applied to the elements of the collection.
	 * @param <R> Type of the elements of the resulting collection.
	 * @param <E> Type of the elements of the source collection.
	 *
	 * @return The resulting elements in a new {@link List} instance.
	 */
	public static <R, E> List<R> map(Collection<E> collection, Function<R, E> function) {
		List<R> res = new ArrayList<>(collection.size());

		for (E i : collection)
			res.add(function.apply(i));

		return res;
	}

	/**
	 * Return a list only containing the element from the specified collection for which the specified function returns {@code true}.
	 *
	 * @param collection The collection from which elements are tested with the specified function.
	 * @param function Function to use to decide whether to add an element to the resulting list.
	 * @param <E> The type of the elements of the specified collection and the result list.
	 */
	public static <E> List<E> filter(Collection<E> collection, Function<Boolean, E> function) {
		List<E> res = new ArrayList<>(collection.size());

		for (E i : collection)
			if (function.apply(i))
				res.add(i);

		return res;
	}

	public static <E> void map(Collection<E> collection, Procedure<E> procedure) {
		for (E i : collection)
			procedure.call(i);
	}

	/**
	 * Apply a function to the the values of a {@link Map} and return the result as values of a new {@link Map}.
	 *
	 * @param map The map from which the values are taken.
	 * @param function The function that is applied to the values of the map.
	 * @param <R> The type of the values of the resulting map.
	 * @param <K> The type of the values of the source map.
	 * @param <V> The type of the keys of the source and result map.
	 *
	 * @return A new {@link Map} instance with the keys of the source map and the values returned by the function.
	 */
	public static <R, K, V> Map<K, R> mapValues(Map<K, V> map, final Function<R, V> function) {
		return mapEntries(map, new BinaryFunction<R, K, V>() {
			@Override
			public R apply(K firstArg, V secondArg) {
				return function.apply(secondArg);
			}
		});
	}

	/**
	 * Apply a function to the keys and values of a {@link Map} and return the results as the values of the new map.
	 * <p/>
	 * This method does the same as {@link Functional#mapValues(Map, Function)} but also provides the keys of the map to the function.
	 */
	public static <R, K, V> Map<K, R> mapEntries(Map<K, V> map, BinaryFunction<R, K, V> function) {
		Map<K, R> res = new HashMap<>();

		for (Map.Entry<K, V> i : map.entrySet())
			res.put(i.getKey(), function.apply(i.getKey(), i.getValue()));

		return res;
	}

	public static <A1, A2> List<Tuple<A1, A2>> zip(Collection<A1> firstCollection, Collection<A2> secondCollection) {
		return zip(firstCollection, secondCollection, new BinaryFunction<Tuple<A1, A2>, A1, A2>() {
			@Override
			public Tuple<A1, A2> apply(A1 firstArg, A2 secondArg) {
				return tuple(firstArg, secondArg);
			}
		});
	}

	public static <A1, A2, A3> List<Triple<A1, A2, A3>> zip(Collection<A1> firstCollection, Collection<A2> secondCollection, Collection<A3> thirdCollection) {
		return zip(firstCollection, secondCollection, thirdCollection, new TernaryFunction<Triple<A1, A2, A3>, A1, A2, A3>() {

			@Override
			public Triple<A1, A2, A3> apply(A1 firstArg, A2 secondArg, A3 thirdArg) {
				return triple(firstArg, secondArg, thirdArg);
			}
		});
	}

	/**
	 * Apply a function to the paired elements of two collections and return the resulting elements in a new list.
	 * <p/>
	 * For each time the function is applied, one element is taken from the first collection and one from the second. The elements are paired in the order that they are returned by the collections iterators. The collections must have the same number of elements.
	 *
	 * @param firstCollection The collection from which the elements are passed as the first argument of the function.
	 * @param secondCollection The collection from which the elements are passed as the second argument of the function.
	 * @param function The function which is applied to paired elements of the collections.
	 * @param <R> The type of the elements of the resulting collection.
	 * @param <A1> The type of the element of the first source collection.
	 * @param <A2> The type of the element of the second source collection.
	 *
	 * @return Returns the results of the function as a new {@link List} instance.
	 */
	public static <R, A1, A2> List<R> zip(Collection<A1> firstCollection, Collection<A2> secondCollection, BinaryFunction<R, A1, A2> function) {
		int size = firstCollection.size();

		if (size != secondCollection.size())
			throw new IllegalArgumentException(String.format("The collections do not have the same size: %s != %s", firstCollection.size(), secondCollection.size()));

		List<R> res = new ArrayList<>(size);

		Iterator<A1> firstIterator = firstCollection.iterator();
		Iterator<A2> secondIterator = secondCollection.iterator();

		while (firstIterator.hasNext()) {
			A1 firstElement = firstIterator.next();
			A2 secondElement = secondIterator.next();

			res.add(function.apply(firstElement, secondElement));
		}

		return res;
	}

	public static <R, A1, A2, A3> List<R> zip(Collection<A1> firstCollection, Collection<A2> secondCollection, Collection<A3> thirdCollection, TernaryFunction<R, A1, A2, A3> function) {
		int size = firstCollection.size();

		if (size != secondCollection.size() || size != thirdCollection.size())
			throw new IllegalArgumentException(String.format("The collections do not have the same size: %s != %s or %s != %s", size, secondCollection.size(), size, thirdCollection.size()));

		List<R> res = new ArrayList<>(size);

		Iterator<A1> firstIterator = firstCollection.iterator();
		Iterator<A2> secondIterator = secondCollection.iterator();
		Iterator<A3> thirdIterator = thirdCollection.iterator();

		while (firstIterator.hasNext()) {
			A1 firstElement = firstIterator.next();
			A2 secondElement = secondIterator.next();
			A3 thirdElement = thirdIterator.next();

			res.add(function.apply(firstElement, secondElement, thirdElement));
		}

		return res;
	}

	/**
	 * Accumulate a result value by consecutively repeatedly applying a function to the value of a state value and consecutive element of a collection. The final value of the state variables will be returned as the result.
	 * <p/>
	 * This higher-order function is called "reduce" in some contexts.
	 *
	 * @param collection The collection from which the elements are taken.
	 * @param start The initial value of the state variable.
	 * @param function The function to apply to the value of the state variable and consecutive elements of the collection.
	 * @param <R> The type of the state variable and thus the result of the fold operation.
	 * @param <E> The type of the elements of the collection.
	 *
	 * @return Returns the value of the state variable after the last element has been processed.
	 */
	public static <R, E> R fold(Collection<E> collection, R start, BinaryFunction<R, R, E> function) {
		for (E i : collection)
			start = function.apply(start, i);

		return start;
	}

	public static <E> List<List<E>> product(final List<List<E>> choicesLists) {
		final List<List<E>> combinations = new ArrayList<>();

		final class Local {
			void process(List<E> choicesPrefix) {
				// A single List instance is used for choicesPrefix which is modified in each step but the instance will never leave the method product().

				if (choicesPrefix.size() < choicesLists.size()) {
					List<E> choices = choicesLists.get(choicesPrefix.size());
					int choicesPrefixLength = choicesPrefix.size();

					for (E i : choices) {
						choicesPrefix.add(i);

						process(choicesPrefix);

						choicesPrefix.remove(choicesPrefixLength);
					}
				} else {
					combinations.add(new ArrayList<>(choicesPrefix));
				}
			}
		}

		new Local().process(new ArrayList<E>());

		return combinations;
	}
}
