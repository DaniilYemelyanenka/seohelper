package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.entity.LinkVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkVisitsRepository extends JpaRepository<LinkVisits,Long> {
}
