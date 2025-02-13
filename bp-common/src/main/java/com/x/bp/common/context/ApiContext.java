package com.x.bp.common.context;

import com.x.bp.common.model.LoginUserTO;
import lombok.Data;

/**
 * @author kang.li
 **/
@Data
public final class ApiContext {

    private static final ThreadLocal<ApiContext> LOCAL = ThreadLocal.withInitial(ApiContext::new);
    private LoginUserTO loginUser;
    private String currency;
    private String lange;

    public static ApiContext getContext() {
        return (ApiContext) LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    private ApiContext() {}
}
