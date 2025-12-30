package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.response.SeoAnalyseResult;
import org.springframework.stereotype.Service;

@Service
public class SeoAnalyserService {

    public SeoAnalyseResult analyseSite(SeoReportDTO seoReportDTO){

        SeoAnalyseResult seoAnalyseResult = new SeoAnalyseResult();

        seoAnalyseResult.setUrl(seoReportDTO.getUrl());
        seoAnalyseResult.setRecommendations(getRecommendations(seoReportDTO));
        seoAnalyseResult.setScore(getScore(seoReportDTO));
        return seoAnalyseResult;
    }

    private String getRecommendations(SeoReportDTO seoReportDTO){

        StringBuilder sb = new StringBuilder();

        if(seoReportDTO.getMetaDescription() == null || seoReportDTO.getMetaDescription().isEmpty()){
            sb.append("- Добавьте meta description, чтобы улучшить видимость в поисковиках.\n");
        }
        if(seoReportDTO.getMetaDescription() != null && seoReportDTO.getMetaDescription().length() < 50){
            sb.append("- meta description слишком короткий, увеличтье до 120 - 150 символов.\n");
        }
        if(seoReportDTO.getH1() == null || seoReportDTO.getH1().isEmpty()){
            sb.append("- Добавьте H1 заголовок на главной странице.\n");
        }

        if(seoReportDTO.getHasSs1() == null ||  !seoReportDTO.getHasSs1()){
            sb.append(" - Установите ssl (https) это влияет на ранжирование и доверие пользователей.");
        }

        if (seoReportDTO.getSpeedScore() != null && seoReportDTO.getSpeedScore() < 75) {
            sb.append(String.format("- Ваша скорость загрузки: %d . Оптимизируйте скорость загрузки сайта, она влияет на SEO и мобильный опыт.\n",seoReportDTO.getMobileScore()));

        }

        if (seoReportDTO.getMobileScore() != null && seoReportDTO.getMobileScore() < 75) {
            sb.append(String.format("- Ваше количество баллов за адаптивность: %d. Сделайте сайт адаптивным для мобильных устройств.\n",seoReportDTO.getSpeedScore()));
        }

        if (sb.isEmpty()) {
            sb.append("Сайт в порядке, критических проблем не обнаружено.");
        }

        return sb.toString();
    }

    private Integer getScore(SeoReportDTO seoReportDTO){

        int score = 0;

        if (seoReportDTO.getMetaDescription() != null && !seoReportDTO.getMetaDescription().isEmpty()) score += 2;
        if (seoReportDTO.getH1() != null && !seoReportDTO.getH1().isEmpty()) score += 2;
        if (seoReportDTO.getHasSs1() != null && seoReportDTO.getHasSs1()) score += 2;
        if(seoReportDTO.getMobileScore()<100 && seoReportDTO.getMobileScore() > 75) score += 2;
        if (seoReportDTO.getSpeedScore()<100 && seoReportDTO.getSpeedScore() > 75) score +=2;

        return score;
    }
}
