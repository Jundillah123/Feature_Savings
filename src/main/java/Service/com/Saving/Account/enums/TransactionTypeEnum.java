package Service.com.Saving.Account.enums;

public enum TransactionTypeEnum {
    TF("TF");



    private final String description;

    TransactionTypeEnum(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
