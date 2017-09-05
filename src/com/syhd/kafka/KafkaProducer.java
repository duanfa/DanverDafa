package com.syhd.kafka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KafkaProducer {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		// kafka.serializer.Encoder<T>
		props.put("zk.connect", "192.168.0.250:12182,192.168.0.251:12182,192.168.0.252:12182");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("metadata.broker.list", "192.168.0.251:19093,192.168.0.250:19093,192.168.0.253:19093");
		props.put("request.required.acks", "1");
		// props.put("partitioner.class", "com.xq.SimplePartitioner");
		ProducerConfig config = new ProducerConfig(props);
		final Producer<String, String> producer = new Producer<String, String>(config);
		String ip = "192.168.0.251";

		String topic_name = "SCRMMessage-test";

//		FileReader in = new FileReader("d:\\1795839430cmtList.txt");
//		BufferedReader breader = new BufferedReader(in);
		String line = null;
		int count = 0;
		JSONObject json = JSONObject.fromObject("{\"appid\":\"wx6e40d4dfeeb109c9\",\"msgtype\":1,\"createTime\":1504509643,\"memberid\":1286699,\"id_string\":\"1bacbe68a4b87e28asdf\"}");
		long time = System.currentTimeMillis() / 1000;

		while (count < 1000) {
			count++;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic_name, ip, json.toString());
			producer.send(data);

		}

		System.out.println("uid 个数===============" + count);

		producer.close();
	}
}
