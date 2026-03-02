package com.ecotrack.controller;

import com.ecotrack.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PickupController {

    private final NotificationService notificationService;

    public PickupController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/pickup")
    public String schedulePickup(RedirectAttributes redirectAttributes) {
        // In a complete implementation, you would fetch the current inventory from a service here.
        String wasteInfo = "All accumulated waste items ready for collection.";
        
        notificationService.sendPickupNotification(wasteInfo);
        
        redirectAttributes.addFlashAttribute("message", "Pickup scheduled! Admin has been notified.");
        return "redirect:/user";
    }
}