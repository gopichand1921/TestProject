package com.trimble.cars.web_application.controller;// ...other imports

import com.trimble.cars.web_application.model.Lease;
import com.trimble.cars.web_application.service.LeaseService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
        ("/api/leases")
public class LeaseController {
    private final LeaseService leaseService;

    @Autowired
    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping("/history/export")
    public void exportLeaseHistory(HttpServletResponse response, Principal principal) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"lease_history.csv\"");
        List<Lease> leases = leaseService.getLeaseHistoryForCustomer(principal.getName());
        PrintWriter writer = response.getWriter();
        writer.println("LeaseId,Car,StartDate,EndDate,Status");
        for (Lease lease : leases) {
            writer.printf("%d,%s,%s,%s,%s\n",
                    lease.getId(),
                    lease.getCar().getModel(),
                    lease.getStartDate(),
                    lease.getEndDate(),
                    lease.getStatus());
        }
        writer.flush();
    }
}