package com.bluecloud.sqstest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Service
public class SqsPollingService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.highPriorityQueueUrl}")
    private String highPriorityQueueUrl;

    @Value("${aws.sqs.normalPriorityQueueUrl}")
    private String normalPriorityQueueUrl;

    public SqsPollingService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }


    @Scheduled(fixedRate = 1000)
    public void pollHighPriorityQueue() {
        pollQueue(highPriorityQueueUrl, "HIGH PRIORITY");
    }


    @Scheduled(fixedRate = 10000)
    public void pollNormalPriorityQueue() {
        pollQueue(normalPriorityQueueUrl, "NORMAL PRIORITY");
    }

    private void pollQueue(String queueUrl, String queueType) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(2)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        if (!messages.isEmpty()) {
            System.out.println("====== [" + queueType + " QUEUE] Received Messages ======");

            for (Message message : messages) {
                System.out.println("Message ID: " + message.messageId());
                System.out.println("Body: " + message.body());
                System.out.println("------------------------------------");

                deleteMessage(queueUrl, message.receiptHandle());
            }
        }
    }

    private void deleteMessage(String queueUrl, String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }
}

