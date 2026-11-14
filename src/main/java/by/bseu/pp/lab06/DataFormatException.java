package by.bseu.pp.lab06;

/** Ошибка содержимого файла, а не доступа к файлу. */
public class DataFormatException extends IllegalArgumentException {
    public DataFormatException(String message) { super(message); }
    public DataFormatException(String message, Throwable cause) { super(message, cause); }
}
