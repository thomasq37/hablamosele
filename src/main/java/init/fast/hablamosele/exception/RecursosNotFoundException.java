package init.fast.hablamosele.exception;

public class RecursosNotFoundException extends RuntimeException {
    public RecursosNotFoundException(Long id) {
        super("Recursos introuvable avec id=" + id);
    }
}
