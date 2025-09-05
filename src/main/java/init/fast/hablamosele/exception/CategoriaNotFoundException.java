package init.fast.hablamosele.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("Categoria introuvable avec id=" + id);
    }
}
