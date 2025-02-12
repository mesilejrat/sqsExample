package com.bluecloud.sqstest.service;

import com.bluecloud.sqstest.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class MessageService {

    private final SqsClient sqsClient;

    private final String HIGH_PRIORITY = "HIGH";
    private final String NORMAL_PRIORITY = "NORMAL";

    @Value("${aws.sqs.highPriorityQueueUrl}")
    private String highPriorityQueueUrl;

    @Value("${aws.sqs.normalPriorityQueueUrl}")
    private String normalPriorityQueueUrl;

    public MessageService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public String sendMessageToHighPriorityQueue(MessageDto message) {
        return sendMessage(highPriorityQueueUrl, message.message(), HIGH_PRIORITY);
    }

    public String sendMessageToNormalPriorityQueue(MessageDto message) {
        return sendMessage(normalPriorityQueueUrl, message.message(), NORMAL_PRIORITY);
    }

    private String sendMessage(String queueUrl, String message, String priority) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("{\"priority\":\"" + priority + "\", \"message\":\"" + message + "\"}")
                .build();

        SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
        return response.messageId();
    }
}

