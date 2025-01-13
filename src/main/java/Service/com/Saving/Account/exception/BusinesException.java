package Service.com.Saving.Account.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinesException extends Exception {

    public BusinesException(String message) {
        super(message);
    }
}
