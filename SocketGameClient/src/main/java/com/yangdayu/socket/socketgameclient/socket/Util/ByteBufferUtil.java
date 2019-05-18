package com.yangdayu.socket.socketgameclient.socket.Util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ByteBufferUtil {

    public static String toString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            //用这个的话，只能输出来一次结果，第二次显示为空
// charBuffer = decoder.decode(buffer);
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }

    }

    public static ByteBuffer toByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }



    public static ByteBuffer toByteBuffer(byte[] msg){
        ByteBuffer buf = ByteBuffer.allocate(msg.length);
        buf.clear();
        buf.put(msg);
        buf.flip();
        return buf;
    }

}
