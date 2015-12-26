package com.ocean.session.sessionCluster.httpssion;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer
                extends AbstractHttpSessionApplicationInitializer { 

        public Initializer() {
                super(Config.class); 
        }
}