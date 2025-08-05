package com.depinx.data.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 以太坊签名消息校验工具
 * @author Administrator
 */
@Slf4j
public class MetaMaskUtils {
    /**
     * 签名前缀
     */
    public static final String MESSAGE_PRE="Sign this message to validate that you are the owner of the account. Random string: %s";

    /**
     * 以太坊自定义的签名消息都以以下字符开头
     * 参考 eth_sign in https://github.com/ethereum/wiki/wiki/JSON-RPC
     */
    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";


    /**
     * 对签名消息，原始消息，账号地址三项信息进行认证，判断签名是否有效
     * @param signature 签名
     * @param message 签名内容
     * @param address 地址
     * @return 返回
     */
    public static boolean validate(String signature, String message, String address) {
        //参考 eth_sign in https://github.com/ethereum/wiki/wiki/JSON-RPC
        // eth_sign
        // The sign method calculates an Ethereum specific signature with:
        //    sign(keccak256("\x19Ethereum Signed Message:\n" + len(message) + message))).
        //
        // By adding a prefix to the message makes the calculated signature recognisable as an Ethereum specific signature.
        // This prevents misuse where a malicious DApp can sign arbitrary data (e.g. transaction) and use the signature to
        // impersonate the victim.
        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        Sign.SignatureData sd = new Sign.SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64));

        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature(
                    (byte) i,
                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                    msgHash);
            if (publicKey != null) {
                if (toUpper(Keys.getAddress(publicKey)).equals(toUpper(address.substring(2)))) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

    public static String toUpper(String wallet){
        if(wallet.startsWith("0x")){
            wallet=wallet.substring(2);
        }
        StringBuilder res = new StringBuilder("0x");
        byte[] sha3 =  Hash.sha3(wallet.toLowerCase().getBytes());
        String s = Hex.toHexString(sha3);
        for (int i = 0; i < wallet.length(); i++) {
            int hex=Integer.parseInt(String.valueOf(s.charAt(i)),16);
            if (hex>= 8) {
                res.append(String.valueOf(wallet.charAt(i)).toUpperCase());
            } else {
                res.append(wallet.charAt(i));
            }
        }
        return res.toString();
    }
}
