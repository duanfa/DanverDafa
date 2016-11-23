package com.syhd.util.mt2Notifier;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 * 
 */
public abstract class RSACoder extends Coder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	public static final String PUBLIC_KEY = "RSAPublicKey";
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	
	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> generateKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, String> keyMap = new HashMap<String, String>(2);
		
		keyMap.put(PUBLIC_KEY, encryptBASE64(publicKey.getEncoded()));
		keyMap.put(PRIVATE_KEY, encryptBASE64(privateKey.getEncoded()));
		
		return keyMap;
	}
	
	public static void main(String[] args) throws Exception {
		//签名生成
		String content="appId=APP00001003&cashFee=100&corpId=CM00001002&orderCreateTime=20160505210954&orderExpireTime=20160506113033&outTradeNo=462482569494-91326-72525-69286&totalFee=100&xiaoyTradeNo=xy1620160506146250543363221259";
		String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOf7LbZ7Mko3ckTB6/ptXRQDq45i4WeqvodGD94VlfCpWZUDk3DgcSoGBDrxe4NplbvWac04b9Pchyv/odV0feV0QglVz/6JbjIecrAlclcuF+ZOeww4g0G4qRpKcsSoOWoVR7SAU+MOv9aLhqzTPuGK+CGQWYakIFo1fswJ4rXXAgMBAAECgYEAhHzClcZ0aUiTXUt3bzXIg+a4swAsBZ3RZMaRx1+Cm8jMXbuGGGBGoR6Aif1iciH6HyYMlOUXkOMQ3AMKNTNrtmq2Ht0Jv3uauTsP2bVD+5x921XZaTpLF42qiwFDdwN04enTJlid/4AMj+Rsc4usRgbDCxKcE1qPY17jOy7XKhkCQQD2+yTiUM3BfRr8lxt3wXEn+KrVn5Q5z3jVqjIoU9CSKxmyucOKDGXNNqA+DeW5/uhuA1SxG61lPJPe3s+pRG5DAkEA8HPPoVovRh4aXm3HdjsNgzqjzHytjthSr6SqyIMK4hUW66d1bS1CMFH7jOgpPUvAwuKGpS3YGx7NFlJUD5EC3QJASwCNfOTI9x9E2LwSrVVjRZ4wUts5Ki0lJs2embyKNDk+fpYHGZ8WMzGJjA6wWsFcWDxOtdIP4BR7W00Shvau/QJBALQxheLcK9s3CfnD+RtQK9MxKbk/oe0Pjf+UvmufUJOWzGNzThuwNA70EThKb0VBNMaXbeHxVicU0QquTdKQkH0CQG/VwLy00QjqwLv6oqZ+i6XpsSoCTlwe25Yp/pjsUrpq5+DnZ9mkw2s2WUi2sdwOpUogctQ5XlBbdjOLpoLhVjM=";
		String sign = sign(content.getBytes(), privateKey);
		String xiaoySign = URLEncoder.encode(sign.replace("\n", ""), "UTF-8");
		System.out.println("签名内容：" + content);
		System.out.println("小y签名："+ xiaoySign);
		
		//签名验证
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDn+y22ezJKN3JEwev6bV0UA6uOYuFnqr6HRg/eFZXwqVmVA5Nw4HEqBgQ68XuDaZW71mnNOG/T3Icr/6HVdH3ldEIJVc/+iW4yHnKwJXJXLhfmTnsMOINBuKkaSnLEqDlqFUe0gFPjDr/Wi4as0z7hivghkFmGpCBaNX7MCeK11wIDAQAB";
		boolean bverify = verify(content.getBytes(), publicKey, URLDecoder.decode(xiaoySign, "UTF-8"));
		System.out.println("验证结果："+bverify);
		
		
		//生成新的公私钥
		/*Map<String, String> keyMap = generateKey();
		for(Map.Entry<String, String> entry:keyMap.entrySet()){    
		     System.out.println(entry.getKey()+"--->\n"+entry.getValue());    
		}*/
		
		
	}
}
