package ch.feuermurmel.functional;

/**
 * Contains factory methods to create different n-tuples.
 * <p/>
 * This framework only support tuples (2-tuples) and triples (3-tuples), which should be enough before creating a custom value class is unavoidable.
 */
public class Tuples {
	private Tuples() {
	}

	public static <T1, T2> Tuple<T1, T2> tuple(T1 element1, T2 element2) {
		return new Tuple<>(element1, element2);
	}

	public static <T1, T2, T3> Triple<T1, T2, T3> triple(T1 element1, T2 element2, T3 element3) {
		return new Triple<>(element1, element2, element3);
	}
}
