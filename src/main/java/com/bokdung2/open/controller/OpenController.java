package com.bokdung2.open.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.open.service.OpenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/api/open")
@CrossOrigin
public class OpenController {
    final OpenService openService;

    @GetMapping("")
    @ResponseBody
    public ResponseCustom<?> getWhetherServiceIsOpen(){
        boolean isOpen = openService.isServiceOpen();
        return ResponseCustom.OK(isOpen);
    }
}
