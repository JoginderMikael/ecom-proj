package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.analyticsDTO.DashboardStatsDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.RevenueDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.TopProductDTO;
import git.joginderMikael.ecom_proj.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    //@Autowired
    private final AnalyticsService analyticsService;

    @GetMapping("/stats")
    public DashboardStatsDTO getDashboardStats() {
        //http://localhost:8081/api/admin/analytics/stats
        return analyticsService.getDashboardStats();
    }

    @GetMapping("/monthly-sales")
    public List<MonthlySalesDTO> getDashboardStatsByMonth() {
        return analyticsService.getMonthlySales();
    }

    @GetMapping("/revenue")
    public RevenueDTO getRevenue() {
        //http://localhost:8081/api/admin/analytics/revenue
        return analyticsService.getRevenue();
    }

    @GetMapping("/top-products")
    public List<TopProductDTO> getTopProducts() {
        //http://localhost:8081/api/admin/analytics/top-products
        return analyticsService.getTopProducts();
    }
}
