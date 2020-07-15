package com.patternmatch.ecs.textapi.service;

import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsComprehendClient {

    private AmazonComprehend comprehendClient = AmazonComprehendClientBuilder.standard().build();

    public String detectLanguage(String text) {
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);

        return detectDominantLanguageResult.getLanguages().get(0).getLanguageCode();
    }

    public String detectSentiment(String text, String language) {
        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text).withLanguageCode(language);
        DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);

        return detectSentimentResult.getSentiment();
    }
}