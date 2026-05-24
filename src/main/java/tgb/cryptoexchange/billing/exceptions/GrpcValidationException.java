package tgb.cryptoexchange.billing.exceptions;

import com.google.rpc.Status;
import lombok.Getter;

@Getter
public class GrpcValidationException extends RuntimeException {

    private final Status rpcStatus;

    public GrpcValidationException(Status rpcStatus) {
        super(rpcStatus.getMessage());
        this.rpcStatus = rpcStatus;
    }


}
