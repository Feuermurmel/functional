package ch.feuermurmel.functional;

public final class Triple<T1, T2, T3> {
	public final T1 first;
	public final T2 second;
	public final T3 third;

	Triple(T1 first, T2 second, T3 third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Triple.class)
			return false;

		return first.equals(((Triple<?, ?, ?>) obj).first)
				&& second.equals(((Triple<?, ?, ?>) obj).second)
				&& third.equals(((Triple<?, ?, ?>) obj).third);
	}

	@Override
	public int hashCode() {
		return first.hashCode()
				+ 31 * (second.hashCode()
				+ 31 * third.hashCode());
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ", " + third + ")";
	}
}
