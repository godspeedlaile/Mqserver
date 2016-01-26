package upsmart.zhsen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ServerProducer {

	/**
	 * @param args
	 */
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {
		// TODO Auto-generated method stub

		/*使用工厂类建立Connection和Channel，并且设置参数*/
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.88.132");
		factory.setPort(5672);
		factory.setUsername("mq");
		factory.setPassword("mq");
//		factory.setVirtualHost("/");

//		factory.setUri("amqp://mq:mq@192.168.88.132:5672/virtualHost");
		Connection connection = factory.newConnection();
//		factory.setRequestedHeartbeat(0);
		Channel channel = connection.createChannel();
		
		/*创建消息队列，用于发送消息*/
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		FileReader fr = new FileReader("/home/upsmart/zhsen/transdata/data");
		BufferedReader br = new BufferedReader(fr);
		String message = null;
		while ((message = br.readLine()) != null){
			Thread.sleep(1000);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		}
		br.close();
		fr.close();
		
		channel.close();
		connection.close();
	}

}
