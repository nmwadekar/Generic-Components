package nmw.se.concurrent;

import java.util.Date;
import java.util.Stack;

public class ProducerConsumerClient {

	private static int SIZE = 3;

	public static void main(String[] args) throws InterruptedException {

		ProducerConsumerClient client = new ProducerConsumerClient();

		final Processor processor = client.new Processor();

		Runnable p = () -> {

			System.out.println("PRODUCER INITIATING...");

			try {
				processor.produce();
			} catch (Exception e) {
				e.printStackTrace();
			}

		};

		Runnable c = () -> {

			System.out.println("CONSUMER INITIATING...");

			try {
				processor.consume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Thread producer = new Thread(p);
		Thread consumer = new Thread(c);

		consumer.start();
		producer.start();
	}

	private class Processor {

		Stack<String> elements = new Stack<String>();

		public void produce() throws InterruptedException {

			while (true) {

				synchronized (this) {

					if (elements.size() < SIZE) {

						String s = new Date().toString();

						elements.push(s);

						System.out.println("PRODUCED - " + s);

						notify();

					} else {

						wait();
					}
				}

				Thread.sleep(2000);

			}
		}

		public void consume() throws InterruptedException {

			while (true) {

				synchronized (this) {

					if (elements.size() > 0) {

						System.out.println("CONSUMED - " + elements.pop());

						notify();

					} else {

						wait();
					}
				}

				Thread.sleep(2000);
			}
		}

	}
}
