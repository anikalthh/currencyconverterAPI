package sg.nus.edu.iss.apiworkshop17.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.edu.iss.apiworkshop17.service.ConverterService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ConverterService convSvc;

    @GetMapping
    public String getConverter(Model m) {
        List<String> allCurrencies = convSvc.getAllCurrencies();
        m.addAttribute("allCurrencies", allCurrencies);
        return "index";
    }

    @PostMapping("/conversion")
    public String getConversion(@RequestBody MultiValueMap<String, String> form, Model m) {
        String fromCurr = form.getFirst("from");
        String toCurr = form.getFirst("to");
        String amount = form.getFirst("amount");
        Float convertedAmount = convSvc.convert(fromCurr, toCurr, amount);
        m.addAttribute("convertedamount", convertedAmount.toString());
        m.addAttribute("amount", amount);
        m.addAttribute("from", fromCurr+": ");
        m.addAttribute("to", toCurr+": ");
        return "conversion";
    }
}
