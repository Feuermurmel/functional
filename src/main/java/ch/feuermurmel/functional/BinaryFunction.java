package ch.feuermurmel.functional;

public interface BinaryFunction<R, A1, A2> {
	R apply(A1 firstArg, A2 secondArg);
}
