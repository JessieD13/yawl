/*
 * Copyright (c) 2004-2012 The YAWL Foundation. All rights reserved.
 * The YAWL Foundation is a collaboration of individuals and
 * organisations who are committed to improving workflow technology.
 *
 * This file is part of YAWL. YAWL is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * YAWL is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with YAWL. If not, see <http://www.gnu.org/licenses/>.
 */

package org.yawlfoundation.yawl.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Enforces UTF-8 encoding for all servlet requests and responses.
 * Sourced from stackoverflow.com based on code on the Tomcat wiki
 * Modified by: Michael Adams
 * Creation Date: 12/12/2008
 */

public class CharsetFilter implements Filter {

    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) encoding="UTF-8";
    }

    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
    //         throws IOException, ServletException {

    //     // Respect the client-specified character encoding
    //     // (see HTTP specification section 3.4.1)
    //     if (null == request.getCharacterEncoding()) {
    //         request.setCharacterEncoding(encoding);
    //     }

    //     response.setCharacterEncoding("UTF-8");

    //     next.doFilter(request, response);
    // }

     public void doFilter(ServletRequest req, ServletResponse res,
                  FilterChain chain) throws IOException, ServletException {
            // 将请求和响应强制转换成Http形式
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            // 处理响应乱码
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");


            chain.doFilter(request, response);
      }

    public void destroy() {}
}
