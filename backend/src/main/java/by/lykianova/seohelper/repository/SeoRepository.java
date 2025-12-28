package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.entity.SeoReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeoRepository extends JpaRepository<SeoReports,Long> {

    List<SeoReports> findAllByUserId(Long id);
}
