package ch.feuermurmel.functional;

public interface TernaryFunction<R, A1, A2, A3> {
	R apply(A1 firstArg, A2 secondArg, A3 thirdArg);
}
