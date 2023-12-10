package sg.nus.edu.iss.apiworkshop17.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.apiworkshop17.service.ConverterService;

@RestController
@RequestMapping("/conversion")
public class ConverterController {

    @Autowired
    private ConverterService convSvc;
    
    // @PostMapping
    // public String getConversion(@RequestBody MultiValueMap<String, String> form, Model m) {
    //     String fromCurr = form.getFirst("from");
    //     String toCurr = form.getFirst("to");
    //     String amount = form.getFirst("amount");
    //     Float convertedAmount = convSvc.convert(fromCurr, toCurr, amount);
    //     m.addAttribute("convertedamount", convertedAmount);
    //     return "conversion";
    // }
}
