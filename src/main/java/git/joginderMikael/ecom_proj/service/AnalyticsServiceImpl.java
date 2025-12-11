package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.analyticsDTO.DashboardStatsDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.RevenueDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.TopProductDTO;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import git.joginderMikael.ecom_proj.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final EcomUsersRepo  userRepository;
    private final OrderRepo orderRepository;
    private final ProductRepo productRepository;


    @Override
    public DashboardStatsDTO getDashboardStats() {
        long users = userRepository.count();
        long orders = orderRepository.count();
        long products = productRepository.count();
        Double revenue = orderRepository.sumTotalRevenue();
        if(revenue==0) {
            revenue = 0.0;
        }
        return new DashboardStatsDTO(users, orders, revenue, products);
    }

    @Override
    public List<MonthlySalesDTO> getMonthlySales() {
        return null;
        //return orderRepository.getMonthlySales();
    }

    @Override
    public RevenueDTO getRevenue() {
        Double total = orderRepository.sumTotalRevenue();
        Double monthly = orderRepository.sumRevenueThisMonth();
        Double daily = orderRepository.sumRevenueToday();

        return new RevenueDTO(
                total == null ? 0 : total,
                monthly == null ? 0 : monthly,
                daily == null ? 0 : daily
        );
    }

    @Override
    public List<TopProductDTO> getTopProducts() {
        return orderRepository.getTopSellingProducts();
    }
}
