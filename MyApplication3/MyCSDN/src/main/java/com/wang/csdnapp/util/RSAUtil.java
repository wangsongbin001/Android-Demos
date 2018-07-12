package com.wang.csdnapp.util;


import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class RSAUtil {
    /**
     * 定义加密方式
     */
    private static final String KEY_RSA = "RSA";
    /**
     * 定义签名算法
     */
    private static final String KEY_RSA_SIGNATURE = "MD5withRSA";
    /**
     * 定义公钥算法
     */
    private static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    /**
     * 定义私钥算法
     */
    private static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

    private static Map<String, Object> init(){
        Map<String, Object> map = null;
        //初始化RSA。生成公钥，与私钥
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublickey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            //将密钥封装成map
            map = new HashMap<String, Object>();
            map.put(KEY_RSA_PUBLICKEY, rsaPublickey);
            map.put(KEY_RSA_PRIVATEKEY, rsaPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     *
     * @param map
     * @return
     */
    private static String getPublicKey(Map<String, Object> map){
        String str = "";
        try {
            RSAPublicKey key = (RSAPublicKey) map.get(KEY_RSA_PUBLICKEY);
            str = encryptByBase64(key.getEncoded());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return str;
    }

    private static String getPrivateKey(Map map){
        String str = "";
        try{
            RSAPrivateKey key = (RSAPrivateKey) map.get(KEY_RSA_PRIVATEKEY);
            str = encryptByBase64(key.getEncoded());
        }catch(Exception e){
            // TODO: handle exception
        }
        return str;
    }

    /**
     * 利用公钥加密
     * @param str
     * @param key
     * @return
     */
    private static String encryptByPublicKey(String str, Key key){
        String result = "";
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key.getEncoded());
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(spec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return encryptByBase64(cipher.doFinal(str.getBytes()));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    private static String decryptByPrivateKey(String str, Key key){
        String resut = "";
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key.getEncoded());
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(spec);
            Cipher cipher = Cipher.getInstance(KEY_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(decryptByBase64(str)));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return resut;
    }

    /**
     * base64编码
     * @param data
     * @return
     */
    private static String encryptByBase64(byte[] data){
        return new String(Base64.encode(data, Base64.DEFAULT));
    }

    /**
     * base64解码
     * @param str
     * @return
     */
    private static byte[] decryptByBase64(String str){
        return Base64.decode(str.getBytes(), Base64.DEFAULT);
    }

    /**
     * 私钥加密
     * @param data
     * @param key
     * @return
     */
    private static String encryptByPrivateKey(String data, Key key){
        String str = "";
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key.getEncoded());
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(spec);
            Cipher cipher = Cipher.getInstance(KEY_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            str = encryptByBase64(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return str;
    }

    private static String decryptByPublicKey(String data, Key key){
        String result = "";
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key.getEncoded());
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(spec);
            Cipher cipher = Cipher.getInstance(KEY_RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = new String(cipher.doFinal(decryptByBase64(data)));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    public static void main(String[] args) {
        String privateKey = "";
        String publicKey = "";
        //生成公私钥
        Map<String, Object> map = init();
        //获取公钥字符串
        publicKey = getPublicKey(map);
        //获取私钥字符串
        privateKey = getPrivateKey(map);
        System.out.println("公钥：\n\r" + publicKey);
        System.out.println("私钥：\n\r" + privateKey);
        System.out.println("公钥加密----------私钥解密");
        String world = "你好，世界";
        RSAPublicKey puKey = (RSAPublicKey) map.get(KEY_RSA_PUBLICKEY);
        String encrypted = encryptByPublicKey(world, puKey);

        RSAPrivateKey prKey = (RSAPrivateKey) map.get(KEY_RSA_PRIVATEKEY);
        System.out.println("加密前：" + world + "\n" + "公钥加密后：" + encrypted);
        System.out.println("私钥解密后：" + decryptByPrivateKey(encrypted, prKey));

        System.out.println("私钥加密----------公钥解密");
        world = "hello world";
        encrypted = encryptByPrivateKey(world, prKey);
        System.out.println("加密前：" + world + "\n" + "私钥加密后：" + encrypted);
        System.out.println("公钥解密后：" + decryptByPublicKey(encrypted, puKey));
    }

}

