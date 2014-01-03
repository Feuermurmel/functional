package ch.feuermurmel.functional;

public interface Procedure<A> {
	void call(A arg);

	Procedure<Object> empty = new Procedure<Object>() {
		@Override
		public void call(Object arg) {
		}
	};
}
