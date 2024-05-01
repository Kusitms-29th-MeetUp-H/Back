package Backend.socket.domain.chat.application.service;

@FunctionalInterface
public interface TriFunction<T, U, R> {
    R apply(T t, U u);
}