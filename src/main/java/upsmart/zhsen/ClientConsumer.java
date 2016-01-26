package upsmart.zhsen;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ClientConsumer {

	private final static String QUEUE_NAME = "hello";
	
	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 * @throws URISyntaxException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException, ConsumerCancelledException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		// TODO Auto-generated method stub
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.88.132");
		factory.setPort(5672);
		factory.setUsername("mq");
		factory.setPassword("mq");
//		factory.setVirtualHost("/");

//		factory.setUri("amqp://mq:mq@192.168.88.132:5672/virtualHost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}

}
