package com.ru.questiondiary.service;

import com.ru.questiondiary.repo.QuestionRepository;
import com.ru.questiondiary.web.dto.QuestionDto;
import com.ru.questiondiary.web.entity.Question;
import com.ru.questiondiary.web.entity.User;
import com.ru.questiondiary.web.entity.Vote;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final QuestionRepository questionRepository;

    @Override
    public QuestionDto findRecommendations(User user, List<Vote> votes) {
        List<String> stringVotes = new ArrayList<>();
        for (Vote vote: votes) {
            stringVotes.add(vote.toString());
        }
        String text = String.join("\n", stringVotes);
        File dataFile = new File("data.csv");
        try {
            FileWriter fileWriter = new FileWriter(dataFile);
            fileWriter.write(text);
            fileWriter.close();
            DataModel dataModel = new FileDataModel(dataFile);
            CityBlockSimilarity similarity = new CityBlockSimilarity(dataModel);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1,similarity, dataModel);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(user.getId(), 1);
            if (!recommendations.isEmpty()) {
                RecommendedItem item = recommendations.get(0);
                Optional<Question> question = questionRepository.findById(item.getItemID());
                if (question.isEmpty()) return null;
                return QuestionDto.from(question.get(), null, null);
            }
        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
