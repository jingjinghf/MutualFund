package utilities;


/**
 * @author Yuheng Li
 * @version 1.0
 * @since Jan 14, 2016
 */
public enum TransactionType {
    BUY, SELL, DEPOSIT, REQUEST,UNKNOWN;
    public String toString() {
        switch (this) {
        case BUY:
            return "buy";
        case SELL:
            return "sell";
        case DEPOSIT:
            return "deposit";
        case REQUEST:
            return "request";
        case UNKNOWN:
            return "unknown"; 
        default:
            return "unkown";
        }
    }
}
