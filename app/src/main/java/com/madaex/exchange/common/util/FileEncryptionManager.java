package com.madaex.exchange.common.util;

import static com.madaex.exchange.common.util.RSAProvider.encryptPrivateKey;
import static com.madaex.exchange.common.util.RSAProvider.encryptPublicKey;


/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2017/2/22 21:13 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description: file encryption manager <br>
 * notice： <br>
 * <li>1. first step: set public and private key. 如果输入的秘钥（公钥和私钥）都是经过Base64.encode处理的，Base64Utils.encode(key.getEncoded())，如果是自己生成的需要先行处理</li>
 * <li>2. encrypt and decrypt file by method </li>
 */

public class FileEncryptionManager {
    private static FileEncryptionManager INSTANCE;
    private String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGSUOlQvXa+BVJjMJNp88NBuLC\n" +
            "+Z2DD+73sUgPSOM9evpEJSSeZNw4fKjgzmnFfGN/a1cosZOGIVKA0q205g0Dey6A\n" +
            "rERBhsYUjp0e3wHvqdgOFUjqszBv1wKcO8i7WsUzeblWUmBQ1kPqSRlareW4PZhi\n" +
            "qPcK44BeUif9s/6bpQIDAQAB";
    private String privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL6fC/MZVNY83agE\n" +
            "b+iJgMsREOGk+xPbAwgmDZxN66xn1Ubtp3RXXcb64xVfet0XnBl6u5iM6SC9qmsR\n" +
            "ALcQa87NknBxLbr3BZIm9m29pQPOJmE3/+JU+U1qhJbbFA/b1PYQMU0dKCnYejcN\n" +
            "EwzbPrQ1yNUC/D2XgQdYDl8Dx623AgMBAAECgYEApZNFL+4K8OSBrJXeFuCJTNoQ\n" +
            "Dc/ef4q3gXZ8UA6m7WRrr2ZzE+1neZUndsMu16jQYJlpJcMQJO5afxGPy3OqD7/9\n" +
            "INfELuNRUUAq33SpsttwbcNSuXsHk59kBjQRrL8u50d9ZuYQbAOWFp1Tk8iXwzp9\n" +
            "xsYdv0GIF1KVxWrA32ECQQD290Z+lQKrzoiUXkoVYSPM0lRWTa/xOE0oeo1NuP+m\n" +
            "N9xdxVkAMbBDV4DFUT9lCPRUJPe0KnqM2hS9h5YRESwZAkEAxZghDGVnyiFZmIMK\n" +
            "bkryUjTvKDnTIkfmrJ8LgOPw/KfIiDomvAk8gNzDhbUwFOeL3gB/lJyL4kMuUAg8\n" +
            "zJXiTwJBAM15Aj/CvkwvM9ctc7nB7kU1IVIEpMOvMhLhk7r8lka25zNlRB0zOhFt\n" +
            "Ngf1ByeKga2YbDaLuRaDQwpFSoe8n9ECQGHxP2hql2C82TFcuv3ijmyrKmSRDROv\n" +
            "+ipUh5oy4lAKRr8nu+120pO5Wf1by2KBR3YaXyLl2fykej9XZfOu08cCQQDTxBe/\n" +
            "sgZaDHsUsVWi+L4lS6gH1dCEKe90Ts5XIB+qqAaZqxXk50VhLLzkIMtBS5HeMdMr\n" +
            "8LTHGuLJbZf9YA8d";

    private FileEncryptionManager() {
    }

    public static FileEncryptionManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FileEncryptionManager();
        }
        return INSTANCE;
    }


    /**
     * sign data by private key
     * @param data the data by encrypted
     * @return
     * @throws Exception
     * @see #verifyByPublicKey(byte[], String)
     */
    public String signByPrivateKey(byte[] data) throws Exception {
        if (privateKey == null || privateKey.isEmpty()){
            throw new IllegalArgumentException("PrivateKey is empty, you should invoke setRSAKey or generateKey");
        }
        return RSAProvider.sign(data, privateKey);
    }

    /**
     * verify data by sign
     * @param data the data by encrypted
     * @param sign
     * @return
     * @throws Exception
     * @see #signByPrivateKey(byte[])
     */
    public boolean verifyByPublicKey(byte[] data, String sign) throws Exception {
        if (publicKey == null || publicKey.isEmpty()){
            throw new IllegalArgumentException("PublicKey is empty, you should invoke setRSAKey or generateKey");
        }
        return RSAProvider.verify(data, publicKey, sign);
    }

    /**
     * encrypt by public key
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encryptByPublicKey(byte[] data) throws Exception {
        if (publicKey == null || publicKey.isEmpty()){
            throw new IllegalArgumentException("PublicKey is empty, you should invoke setRSAKey or generateKey");
        }
        return encryptPublicKey(data, publicKey);
    }

    /**
     * decrypt by private key
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] decryptByPrivateKey(byte[] data) throws Exception {
        if (privateKey == null || privateKey.isEmpty()){
            throw new IllegalArgumentException("PrivateKey is empty, you should invoke setRSAKey or generateKey");
        }
        return RSAProvider.decryptPrivateKey(data, privateKey);
    }

    /**
     * encrypt by private key
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encryptByPrivateKey(byte[] data) throws Exception {
        if (privateKey == null || privateKey.isEmpty()){
            throw new IllegalArgumentException("PrivateKey is empty, you should invoke setRSAKey or generateKey");
        }
        return encryptPrivateKey(data, privateKey);
    }

    /**
     * decrypt by public key
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] decryptByPublicKey(byte[] data) throws Exception {
        if (publicKey == null || publicKey.isEmpty()){
            throw new IllegalArgumentException("PublicKey is empty, you should invoke setRSAKey or generateKey");
        }
        return RSAProvider.decryptPublicKey(data, publicKey);
    }

}
