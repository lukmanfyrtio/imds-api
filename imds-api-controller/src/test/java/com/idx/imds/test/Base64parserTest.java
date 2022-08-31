package com.idx.imds.test;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class Base64parserTest {

    @Test
    public void testParser() {
    	String base64 = "YWRtaW4xOjE=";
    	String hasilDecode = new String(Base64.getDecoder().decode(base64));
    	String hasilEncode = new String(Base64.getEncoder().encode("admin1:1".getBytes()));
    	
    	
    	System.out.println("HASIL:"+hasilDecode);
    	System.out.println("HASIL:"+hasilEncode);
    	
    }
}
