package com.ecotrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Controller
public class WasteManagementApplication {

    private final Map<String, Double> inventory = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(WasteManagementApplication.class, args);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("inventory", inventory);
        return "index";
    }

    @PostMapping("/add")
    public String addWaste(@RequestParam("waste_type") String wasteType,
                           @RequestParam("weight") Double weight,
                           RedirectAttributes redirectAttributes) {
        if (weight == null || weight <= 0) {
            redirectAttributes.addFlashAttribute("error", "Weight must be a positive number.");
        } else {
            inventory.merge(wasteType, weight, Double::sum);
            redirectAttributes.addFlashAttribute("success", "Successfully added " + weight + "kg of " + wasteType + ".");
        }
        return "redirect:/";
    }

    @PostMapping("/pickup")
    public String pickup(RedirectAttributes redirectAttributes) {
        if (inventory.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Inventory is empty. Nothing to pick up.");
        } else {
            inventory.clear();
            redirectAttributes.addFlashAttribute("success", "Pickup scheduled successfully! Inventory cleared.");
        }
        return "redirect:/";
    }
}