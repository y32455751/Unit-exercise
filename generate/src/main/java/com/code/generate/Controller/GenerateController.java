package com.code.generate.Controller;

import com.code.generate.Entity.CodeEntity;
import com.code.generate.Service.FrontGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/code")
public class GenerateController {

    @Autowired
    FrontGenerateService frontGenerateService;

    @RequestMapping("/generate")
    @ResponseBody
    private String index(CodeEntity e, HttpServletResponse response){
        e.setResponse(response);
        e.setOutPath(CodeEntity.OUT_FILE_PATH);
        String path = frontGenerateService.generateFile(e);
        System.out.println(path);
        return path;
    }

}
