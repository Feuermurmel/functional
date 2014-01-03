package ch.feuermurmel.functional;

public final class Tuple<T1, T2> {
	public final T1 first;
	public final T2 second;

	Tuple(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Tuple.class)
			return false;

		return first.equals(((Tuple<?, ?>) obj).first)
				&& second.equals(((Tuple<?, ?>) obj).second);
	}

	@Override
	public int hashCode() {
		return first.hashCode()
				+ 31 * second.hashCode();
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}
