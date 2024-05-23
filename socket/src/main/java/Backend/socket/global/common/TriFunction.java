package Backend.socket.global.common;

@FunctionalInterface
public interface TriFunction<T, U, R> {
    R apply(T t, U u);
}