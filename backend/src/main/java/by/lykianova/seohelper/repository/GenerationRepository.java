package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.entity.GeneratedContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationRepository extends JpaRepository<GeneratedContent,Long> {

    List<GeneratedContent> findAllByUserId(Long userId);
}
