package ch.feuermurmel.functional;

import java.util.concurrent.Callable;

/**
 * Represents a function without any arguments.
 * <p/>
 * Almost equal in functionality to {@link Callable}, but the work method is not declared to throw any checked exceptions, unlike {@link Callable#call()}.
 *
 * @param <R> Type of the result of this function.
 */
public interface NullaryFunction<R> {
	R apply();
}
