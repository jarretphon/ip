public class XenonException extends Exception {

    public XenonException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
