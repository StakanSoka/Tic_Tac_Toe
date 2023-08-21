package exception;

public class NoSuchCellStatementException extends Exception {

    public NoSuchCellStatementException() {
        super("There is no such cell statement");
    }
}
