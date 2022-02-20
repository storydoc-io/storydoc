package io.storydoc.server.infra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectToAngular {

    @RequestMapping({ "/fe/**"})
    public String redirect() {
        return "forward:/index.html";
    }

}
