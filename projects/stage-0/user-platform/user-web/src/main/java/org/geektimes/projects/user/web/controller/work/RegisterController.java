package org.geektimes.projects.user.web.controller.work;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.commons.lang.CharSetUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.work.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.geektimes.projects.user.service.work.impl.UserServiceImpl.convert2String;
import static org.geektimes.projects.user.service.work.impl.UserServiceImpl.mapToObject;

/**
 * @program: user-platform
 * @description: 用户注册控制器
 * @author: magic_json
 * @create: 2021-02-28 12:26
 **/
@Path("/user")
public class RegisterController implements PageController {

    private static Logger logger = Logger.getLogger(RegisterController.class.getName());

    private UserServiceImpl userService = new UserServiceImpl();

    @GET
    @Path("/regView")
    public String registerView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "register-form.jsp";
    }

    @POST
    @Path("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map params = request.getParameterMap();
        User user = null;
       if(Objects.nonNull(params)){
           user = (User) mapToObject(params,User.class);
       }
       boolean status = userService.register(user);
       String registerStatus = "成功";
       if(!status){
           registerStatus = "失败";
       }
        HttpSession session = request.getSession();
        session.setAttribute("registerStatus", "注册" + registerStatus);
        return "login-form.jsp";
    }

    @GET
    @Path("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login-form.jsp";
    }


}

