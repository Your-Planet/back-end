package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.Portfolio;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {
    
    private final EntityManager em;

    public Portfolio findById(Long portfolioNo) {
        return em.find(Portfolio.class, portfolioNo);
    }
    
    public Optional<Portfolio> findByMemberId(Long memberId) {
        List<Portfolio> portfolioList = em.createQuery("select p from Portfolio p where p.member.id = :memberId", Portfolio.class)
        .setParameter("memberId", memberId)
        .getResultList();
        
        return portfolioList.stream().findAny();
    }

    public Optional<Portfolio> findByEmail(String email) {
        List<Portfolio> portfolioList = em.createQuery("select p from Portfolio p where p.member.email = :email", Portfolio.class)
        .setParameter("email", email)
        .getResultList();
        
        return portfolioList.stream().findAny();
    }

    public List<Portfolio> findByCategory(Category category) {
        List<Portfolio> portfolioList = em.createQuery("select p from Portfolio p where :category in p.portfolioCategoryMapList", Portfolio.class)
        .setParameter("category", category)
        .getResultList();

        return portfolioList;
    }

    public void savePortfolio(Portfolio portfolio) {
        em.persist(portfolio);
    }
}
