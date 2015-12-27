package com.ocean.session.redis;

import java.io.IOException;

import com.ocean.session.api.SessionManager;
import com.ocean.session.core.SessionFilter;

/**
 * Redis Session Filter
 */
public class RedisSessionFilter extends SessionFilter {

    /**
     * subclass create session manager
     * @return session manager
     */
    @Override
    protected SessionManager createSessionManager() throws IOException{
        return new RedisSessionManager();
    }
}
