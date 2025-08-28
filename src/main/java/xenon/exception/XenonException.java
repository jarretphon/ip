package xenon.exception;

/**
 * Represents an exception specific to the Xenon application.
 * This exception is used to encapsulate errors
 * that occur during the execution of the application.
 */
public class XenonException extends Exception {

    public XenonException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
