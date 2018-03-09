package com.siemens.mindsphere.maa.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created by wsdgc4 on 3/3/2018.
 */
public class AccessFilter extends ZuulFilter
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // Logic to determine whether should execute fliter.run()
        return true;
    }
    @Override
    public Object run() {

        // Implementation filter logic  here
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
//        //String authorization = request.getHeader("Authorization");
//        logger.info("authorization = {}", authorization);
//        // Check the authorization to determine whether reject this request.
//        // "dXNlcjpwYXNz" is the basic auth code for account user/pass
//        if("Basic dXNlcjpwYXNz".equals(authorization)){
//            logger.info("Authorization check passed.");
//        }else{
//            // Reject the request
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            context.setResponseBody("Request is rejected due to authorization failure.");
//        }
//        return null;
        String  authorization = request.getHeader("Authorization");
        if ("Basic bGVvL3Bhc3N3b3Jk".equals(authorization)){
            logger.info("Authorization check passed.");
        }
        return null;
    }
}
