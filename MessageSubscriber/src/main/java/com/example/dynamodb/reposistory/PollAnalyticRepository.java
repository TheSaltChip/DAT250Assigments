package com.example.dynamodb.reposistory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.dynamodb.model.PollAnalytic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class PollAnalyticRepository {
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public PollAnalyticRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    // Store the order item in the database
    public PollAnalytic savePollAnalytic(PollAnalytic pollAnalytic) {
        dynamoDBMapper.save(pollAnalytic);
        return pollAnalytic;
    }

    public PollAnalytic getPollAnalyticById(String pollAnalyticId) {
        return dynamoDBMapper.load(PollAnalytic.class, pollAnalyticId);
    }

    public String deletePollAnalyticById(String pollAnalyticId) {
        dynamoDBMapper.delete(dynamoDBMapper.load(PollAnalytic.class, pollAnalyticId));
        return "PollAnalytic Id : " + pollAnalyticId + " Deleted!";
    }

    public String updatePollAnalytic(String pollAnalyticId, PollAnalytic pollAnalytic) {
        dynamoDBMapper.save(pollAnalytic,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(pollAnalyticId)
                                )));
        return pollAnalyticId;
    }
}
