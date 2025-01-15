package Service.com.Saving.Account.enums;

public enum CoaCode {

    KASTABUNGAN("2", "2");

    private final String key;
    private final String value;

    CoaCode(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
