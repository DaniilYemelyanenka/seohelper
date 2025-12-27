package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.entity.LinkVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkVisitsRepository extends JpaRepository<LinkVisits,Long> {

    long countByTrackedLinkId(Long trackedLinkId);


    @Query("""
            select lv.country, count(lv)
            from LinkVisits lv
            where lv.trackedLink.id =:id
            group by lv.country
            """)
    List<Object[]> countByCountry(@Param("id") Long trackedLinkId);

    @Query("""
            select lv.browser, count(lv)
            from LinkVisits lv
            where lv.trackedLink.id =:id
            group by lv.browser
            """)
    List<Object[]> countByBrowser(@Param("id") Long trackedLinkId);

    @Query("""
            select lv.device, count(lv)
            from LinkVisits lv
            where lv.trackedLink.id =:id
            group by lv.device
            """)
    List<Object[]> countByDevice(@Param("id") Long trackedLinkId);
}
