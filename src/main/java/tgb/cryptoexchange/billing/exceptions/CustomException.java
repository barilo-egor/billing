package tgb.cryptoexchange.billing.exceptions;

import tgb.cryptoexchange.billing.enums.ErrorCode;

public interface CustomException {

    ErrorCode getErrorCode();

    String getField();

    String getDescription();

}
