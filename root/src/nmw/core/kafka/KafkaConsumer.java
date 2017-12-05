package nmw.kafka;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogLevelFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaConsumer implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(KafkaConsumerAPI.class);
	private List<String> eventMsg = new ArrayList<String>();
	private boolean isConsumerRunning = true;
	private static final long DEFAULT_POLLING_INTERVAL = 3000l;
	private KafkaConsumer<String, String> kafkaConsumer;
	private long SLEEP_DURATION;

	private String APP_NODE_PATH;
	private String APPLICATION_NAME;
	private String SEVERITY_NODE_PATH;
	private String BUSINESS_FIELD_KEY;
	private long POLLING_INTERVAL;

	public KafkaConsumerAPI(KafkaConsumerAPIParam input) {

		initialize(input);
	}

	private void initialize(KafkaConsumerAPIParam input) {

		Properties props = input.getProperties();

		kafkaConsumer = new KafkaConsumer<>(props);

		List<String> topics = Arrays.asList(input.getTopics().toString().split(","));

		kafkaConsumer.subscribe(topics);

		SLEEP_DURATION = Long.parseLong(input.getSleepDuration());

		APP_NODE_PATH = input.getApplicationNodePath();
		APPLICATION_NAME = input.getApplicationName();

		SEVERITY_NODE_PATH = input.getSeverityNodePath();
		BUSINESS_FIELD_KEY = input.getBusinessFieldNodeName();

		POLLING_INTERVAL = input.getPollingInterval();

		if (POLLING_INTERVAL == 0) {
			POLLING_INTERVAL = DEFAULT_POLLING_INTERVAL;
		}
	}

	public void startConsumer() {

		Thread t = new Thread(this, "KafkaConsumerAPIThread");
		t.start();
	}

	@Override
	public void run() {

		try {

			String eventApp = null;

			while (isConsumerRunning) {

				ConsumerRecords<String, String> records = kafkaConsumer.poll(POLLING_INTERVAL);

				for (ConsumerRecord<String, String> record : records) {

					eventApp = null;
					try {
						eventApp = JSONUtils.fetchNodeFromJSON(record.value(), APP_NODE_PATH);
					} catch (Exception e) {
						LOGGER.error(e);
					}

					if (APPLICATION_NAME.equals(eventApp)) {

						synchronized (eventMsg) {
							eventMsg.add(record.value());
						}
					}
				}

				kafkaConsumer.commitSync();
			}

		} catch (CommitFailedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			kafkaConsumer.close();
		}
	}

	public void stopConsumer() {
		isConsumerRunning = false;
	}

	private void clearList() {

		synchronized (eventMsg) {
			eventMsg.clear();
		}
	}

	public void clearMessage() {

		CommonUtils.addDelay(SLEEP_DURATION);

		clearList();
	}

	public List<String> getMessages(List<LogLevel> level) {

		try {

			CommonUtils.addDelay(SLEEP_DURATION);

			List<String> copy = null;

			String eventSeverity = null;

			synchronized (eventMsg) {

				if (eventMsg != null && eventMsg.size() > 0) {

					copy = new ArrayList<String>();

					for (String event : eventMsg) {

						eventSeverity = JSONUtils.fetchNodeFromJSON(event, SEVERITY_NODE_PATH);

						if (eventSeverity != null && level.contains(LogLevel.valueOf(eventSeverity.trim())))
							copy.add(event);
					}

					clearList();
				}
			}

			return (copy == null || copy.size() == 0 ? null : copy);

		} catch (IOException | LogLevelFormatException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
