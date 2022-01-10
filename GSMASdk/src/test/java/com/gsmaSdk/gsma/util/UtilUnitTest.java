package com.gsmaSdk.gsma.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.internal.bind.JsonTreeReader;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilUnitTest {


//    @Test
//    public void base64Encode_String_Success(){
//
//        String key="59vthmq3f6i15v6jmcjskfkmh:ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb";
//
//        String expectedResult="59vthmq3f6i15v6jmcjskfkmh:ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb";
//
//        String actualResult=Utils.base64EncodeString(key);
//        assertEquals(actualResult,expectedResult);
//
//
//    }


    @Test
    public void hashMapFrom_Object_Success() {

        TransactionFilter transactionFilter = new TransactionFilter();

        transactionFilter.setOffset(0);
        transactionFilter.setLimit(5);
        transactionFilter.setFromDateTime("02-1-2018-06:07:59");
        transactionFilter.setToDateTime("05-1-2018-06:07:59");
        transactionFilter.setTransactionStatus("pending");
        transactionFilter.setTransactionType("merchantpay");


        HashMap<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("offset", "0");
        expectedHashMap.put("limit", "5");
        expectedHashMap.put("fromDateTime", "02-1-2018-06:07:59");
        expectedHashMap.put("toDateTime", "05-1-2018-06:07:59");
        expectedHashMap.put("transactionStatus", "pending");
        expectedHashMap.put("transactionType", "merchantpay");

        HashMap<String, String> actualHashMap = Utils.getHashMapFromObject(transactionFilter);

        assertEquals(expectedHashMap, actualHashMap);


    }

    @Test
    public void hashMapFrom_Object_Failure() {

        TransactionFilter transactionFilter = new TransactionFilter();

        transactionFilter.setOffset(0);
        transactionFilter.setLimit(5);

        HashMap<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("offset", "0");
        expectedHashMap.put("limit", "5");
        expectedHashMap.put("fromDateTime", "02-1-2018-06:07:59");
        expectedHashMap.put("toDateTime", "05-1-2018-06:07:59");
        expectedHashMap.put("transactionStatus", "pending");
        expectedHashMap.put("transactionType", "merchantpay");

        HashMap<String, String> actualHashMap = Utils.getHashMapFromObject(transactionFilter);

        assertNotEquals(expectedHashMap, actualHashMap);


    }
}