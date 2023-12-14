package cz.cvut.fit.tjv.czcClient.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class IndexController {
    @GetMapping()
    public String index(){
        return "index";
    }
}
