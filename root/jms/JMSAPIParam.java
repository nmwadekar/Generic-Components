package nmw.core.jms;

public class JMSAPIParam {

	private long consumerWaitDuration, producerWaitDuration;
	private String replyQueue, requestQueue;

	public long getConsumerWaitDuration() {
		return consumerWaitDuration;
	}

	public void setConsumerWaitDuration(long consumerWaitDuration) {
		this.consumerWaitDuration = consumerWaitDuration;
	}

	public String getReplyQueue() {
		return replyQueue;
	}

	public void setReplyQueue(String replyQueue) {
		this.replyQueue = replyQueue;
	}

	public long getProducerWaitDuration() {
		return producerWaitDuration;
	}

	public void setProducerWaitDuration(long producerWaitDuration) {
		this.producerWaitDuration = producerWaitDuration;
	}

	public String getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(String requestQueue) {
		this.requestQueue = requestQueue;
	}

}
