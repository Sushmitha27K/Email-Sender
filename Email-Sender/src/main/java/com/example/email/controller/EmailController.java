package com.example.email.controller;


import com.example.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String showForm(Model model) {
        return "email_form";
    }

    @PostMapping("/send")
    public String scheduleEmail(@RequestParam String dateTime,
                                @RequestParam String subject,
                                @RequestParam String message) {
        emailService.scheduleEmail(dateTime, subject, message);
return "redirect:/success";
    }
    
    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}