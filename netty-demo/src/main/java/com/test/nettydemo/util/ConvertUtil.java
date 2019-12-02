package com.test.nettydemo.util;

public class ConvertUtil {
    public static void main(String[] args) {
                  //0000000 000AABBCCDD011D58BCCE8A50F7EE3A100D193EA0014A00D27D009C400F7
        String str="AA750000381F83A4B61201235B4C10990000000000000000000000000000000000382D";
        byte[] bytes = ConvertUtil.hexStrToByteArray(str);
        System.out.println(bytes.length);
        String s = ConvertUtil.byteArrayToHexStr(bytes);
        System.out.println(s);

    }
    /**
     * 十六进制String转byte[]
     * @param str
     * @return
     */
    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * byte[]转十六进制String
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
