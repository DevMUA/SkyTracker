package com.labproject.SkyTracker.kafka;

public class KafkaConstants {

    public static String KAFKA_BROKERS = "localhost:9092";
    public static Integer MESSAGE_COUNT=1000;
    public static String TOPIC_NAME="SkyTrackerController";
    public static String GROUP_ID_CONFIG="exampleGroup";
    public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    public static String OFFSET_RESET_LATEST="latest";
    public static String OFFSET_RESET_EARLIER="earliest";
    public static Integer MAX_POLL_RECORDS=1;
    public static Integer SESSION_TIMEOUT_MS = 180000;
    public static Integer REQUEST_TIMEOUT_MS_CONFIG = 181000;
    public static String ENABLE_AUTO_COMMIT_CONFIG = "false";
    public static Integer AUTO_COMMIT_INTERVAL_MS_CONFIG = 8000;
}