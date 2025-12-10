package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.analyticsDTO.DashboardStatsDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.RevenueDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.TopProductDTO;

import java.util.List;

public interface AnalyticsService {
    DashboardStatsDTO getDashboardStats();
    List<MonthlySalesDTO> getMonthlySales();
    RevenueDTO getRevenue();
    List<TopProductDTO> getTopProducts();
}
