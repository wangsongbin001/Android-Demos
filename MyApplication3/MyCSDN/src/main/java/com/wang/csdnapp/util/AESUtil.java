package com.wang.csdnapp.util;

import android.content.Context;
import android.text.TextUtils;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String TAG = "AESUtil";
    //密钥算法
    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPTHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static Key key;

    public static void init(Context context){
        if(key != null){
            return;
        }
        String keyStr = PreferenceUtil.readString(context, "aeskey");
        if(!TextUtils.isEmpty(keyStr)){
            key = toKey(hex2byte(keyStr));
            LogUtil.i(TAG, "key form pre:" + keyStr);
        }else{
            byte[] keyByte = initSecretKey();
            key = toKey(keyByte);
            keyStr = byte2hex(keyByte);
            PreferenceUtil.write(context, "aeskey", keyStr);
            LogUtil.i(TAG, "key new:" + keyStr);
        }
    }
    /**
     * 初始化密钥
     * @return byte[]
     */
    private static byte[] initSecretKey(){
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化密钥生成器，确定长度，通常为128,192,256
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * @param data
     * @return
     */
    private static String showByteArray(byte[] data){
        if(data == null){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(int i= 0;i<data.length;i++){
            sb.append(data[i]);
            if(i < data.length - 1){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 转化密钥
     * @param key
     * @return
     */
    private static Key toKey(byte[] key){
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密
     * @param data 待加密数据
     * @param ky 密钥
     * @return 加密数据
     */
    private static byte[] encrypt(byte[] data, Key ky) throws Exception{
        return encrypt(data, ky, DEFAULT_CIPTHER_ALGORITHM);
    }

    /**
     * 加密算法模式
     * @param
     */
    private static byte[] encrypt(byte[] data, Key ky, String cipher_algorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipher_algorithm);
        //使用密钥初始化，设置加密模式
        cipher.init(Cipher.ENCRYPT_MODE, ky);
        //加密
        return cipher.doFinal(data);
    }

    /**
     * @param data 加密元数据
     * @return 由加密后的byte[] 转化成功十六进制字符串
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception{
        if(data == null){
            return null;
        }
        byte[] encryptData = encrypt(data.getBytes(), key);
        return byte2hex(encryptData);
    }

    /**
     * 二进制byte数组，转换成十六进制的字符串
     * @param data
     * @return
     */
    public static String byte2hex(byte[] data){
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<data.length;i++){
            String temp = Integer.toHexString(data[i] & 0XFF);
            if(temp.length() == 1){
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 将十六进制的字符串转换成二进制byte数组
     * @param
     * @return
     */
    public static byte[] hex2byte(String hexStr){
        if(hexStr == null && hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];

        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return 解密后的数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, Key key) throws Exception{
        return decrypt(data, key, DEFAULT_CIPTHER_ALGORITHM);
    }

    /**
     *
     * @param alogthm 解密模式
     */
    private static byte[] decrypt(byte[] data, Key key, String alogthm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(alogthm);
        //使用密钥初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param hexStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String hexStr) throws Exception{
        if(hexStr == null){
            return null;
        }
        byte[] decryptData = decrypt(hex2byte(hexStr), key);
        return new String(decryptData);
    }

    public static void main(String[] args) throws Exception {

        byte[] key = initSecretKey();
        System.out.println("key:" + showByteArray(key));

        Key ky = toKey(key);

        String data = "aes数据";
        System.out.println("加密前的数据:String " + data);
        System.out.println("加密前的数据:byte[] " + showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), ky);
        System.out.println("加密后的数据:byte[] " + showByteArray(encryptData));
        System.out.println("加密后的数据:String " + byte2hex(encryptData));
        System.out.println();
        String hexStr = byte2hex(encryptData);
        System.out.println("解密前的数据:String " + hexStr);
        System.out.println("解密前的数据:byte[] " + showByteArray(hex2byte(hexStr)));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, ky);
        System.out.println("解密后的数据:byte[] " + showByteArray(decryptData));
        System.out.println("解密后的数据:String " + new String(decryptData));

        String encryptStr = encrypt("hello world");
        System.out.println();
        System.out.println(encryptStr);
        System.out.println(decrypt(encryptStr));
    }
}
