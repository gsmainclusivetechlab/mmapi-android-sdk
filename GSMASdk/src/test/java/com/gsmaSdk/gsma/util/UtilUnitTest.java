package com.gsmaSdk.gsma.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.internal.bind.JsonTreeReader;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.http.PUT;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilUnitTest {

    /*******************HashMap from Object*********************************/

    @Test
    public void hashMapFromObjectSuccess() {

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
    public void hashMapFromObjectFailure() {

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

    /*****************************Parse Error*********************************/


    @Test
    public void parseErrorSuccess() {

        String response = "{\n" +
                "\t\"errorCategory\": \"businessRule\",\n" +
                "\t\"errorCode\": \"genericError\",\n" +
                "\t\"errordescription\": \"string\",\n" +
                "\t\"errorDateTime\": \"2022-01-10T09:43:00.078Z\"\n" +
                "\n" +
                "}";

        ErrorObject expectedObject = new Gson().fromJson(response, ErrorObject.class);
        ErrorObject actualObject = Utils.parseError(response);
        assertEquals(expectedObject.getErrorCode(), actualObject.getErrorCode());
    }


    @Test
    public void parseErrorFailure() {

        String response = "{\n" +
                "\t\"errorCategory\": \"businessRule\",\n" +
                "\t\"errorCode\": \"genericError\",\n" +
                "\t\"errordescription\": \"string\",\n" +
                "\t\"errorDateTime\": \"2022-01-10T09:43:00.078Z\"\n" +
                "\n" +
                "}";
        ErrorObject actualObject = Utils.parseError(response);
        assertNotEquals(actualObject.getErrorCode(), "validationError");

    }

    @Test
    public void parseErrorNullStringSuccess() {
        ErrorObject actualObject = Utils.parseError(null);
        assertEquals( "Invalid Json Format",actualObject.getErrorDescription());
    }

    @Test
    public void parseErrorEmptyStringSuccess() {
        ErrorObject actualObject = Utils.parseError("");
        assertEquals( "Invalid Json Format",actualObject.getErrorDescription());
    }

    @Test
    public void parseErrorEmptyJsonSuccess() {
        ErrorObject actualObject = Utils.parseError("{}");
        assertEquals(actualObject.getErrorCode(),actualObject.getErrorCode());
    }


    /*****************************Identifiers*********************************/

    @Test
    public void identifierPathWithOneAccountIdentifierSuccess(){
        String expectedIdentifierPath="accountid/1";

        Identifier identifier=new Identifier();
        identifier.setKey("accountid");
        identifier.setValue("1");

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        identifierArrayList.add(identifier);

        String actualIdentifierPath=Utils.getIdentifiers(identifierArrayList);

        assertEquals(expectedIdentifierPath,actualIdentifierPath);



    }

    @Test
    public void identifierPathTwoAccountIdentifierSuccess(){
        String expectedIdentifierPath="accountid@2999$walletid@1";

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        Identifier identifierWallet=new Identifier();
        identifierWallet.setKey("walletid");
        identifierWallet.setValue("1");

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        identifierArrayList.add(identifierAccount);
        identifierArrayList.add(identifierWallet);

        String actualIdentifierPath=Utils.getIdentifiers(identifierArrayList);

        assertEquals(expectedIdentifierPath,actualIdentifierPath);
    }

    @Test
    public void identifierPathThreeAccountIdentifierSuccess(){
        String expectedIdentifierPath="accountid@2999$walletid@1$msidn@+44012345678";

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        Identifier identifierWallet=new Identifier();
        identifierWallet.setKey("walletid");
        identifierWallet.setValue("1");

        Identifier identifierMsidn=new Identifier();
        identifierMsidn.setKey("msidn");
        identifierMsidn.setValue("+44012345678");

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        identifierArrayList.add(identifierAccount);
        identifierArrayList.add(identifierWallet);
        identifierArrayList.add(identifierMsidn);

        String actualIdentifierPath=Utils.getIdentifiers(identifierArrayList);

       assertEquals(expectedIdentifierPath,actualIdentifierPath);

    }

    @Test
    public void identifierPathFourAccountIdentifierSuccess(){
        String expectedIdentifierPath="accountid@2999$walletid@1$msidn@+44012345678$consumerno@1";

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        Identifier identifierWallet=new Identifier();
        identifierWallet.setKey("walletid");
        identifierWallet.setValue("1");

        Identifier identifierMsidn=new Identifier();
        identifierMsidn.setKey("msidn");
        identifierMsidn.setValue("+44012345678");


        Identifier identifierConsumer=new Identifier();
        identifierConsumer.setKey("consumerno");
        identifierConsumer.setValue("1");

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        identifierArrayList.add(identifierAccount);
        identifierArrayList.add(identifierWallet);
        identifierArrayList.add(identifierMsidn);
        identifierArrayList.add(identifierConsumer);

        String actualIdentifierPath=Utils.getIdentifiers(identifierArrayList);

        assertEquals(expectedIdentifierPath,actualIdentifierPath);


    }

    @Test
    public void identifierPathEmptyAccountIdentifierSuccess(){
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        String actualIdentifierPath=Utils.getIdentifiers(identifierArrayList);
         assertEquals("",actualIdentifierPath);

    }

    /****************************Validation Error Object*********************************/

    @Test
    public void setErrorValidationNoInternetConnectivitySuccess(){

        ErrorObject actualErrorObject=Utils.setError(0);

       assertEquals(actualErrorObject.getErrorCategory(),"Service Unavailable");
       assertEquals(actualErrorObject.getErrorCode(),"GenericError");
       assertEquals(actualErrorObject.getErrorDescription(),"No Internet connectivity");

    }

    @Test
    public void setErrorValidationInvalidAccountIdentifierSuccess(){

        ErrorObject actualErrorObject=Utils.setError(1);

        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid account identifier");

    }

    @Test
    public void setErrorValidationInvalidServerCorrelationIdSuccess(){

        ErrorObject actualErrorObject=Utils.setError(2);

        Assert.assertEquals(actualErrorObject.getErrorCategory(),"validation");
        Assert.assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        Assert.assertEquals(actualErrorObject.getErrorDescription(),"Invalid server correlation id");
    }

    @Test
    public void setErrorValidationInvalidTransactionReferenceSuccess(){

        ErrorObject actualErrorObject=Utils.setError(3);

        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid transaction reference");

    }
    @Test
    public void setErrorValidationInvalidReferenceIdSuccess(){
        ErrorObject actualErrorObject=Utils.setError(4);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid reference id");

    }

    @Test
    public void setErrorValidationInvalidJSONFormatSuccess(){
        ErrorObject actualErrorObject=Utils.setError(5);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid json format");

    }

    @Test
    public void setErrorValidationInvalidCorrelationIdSuccess(){
        ErrorObject actualErrorObject=Utils.setError(6);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid correlation id");
    }


    @Test
    public void setErrorValidationInvalidTransactionTypeSuccess(){
        ErrorObject actualErrorObject=Utils.setError(7);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Transaction type");

    }

    @Test
    public void setErrorValidationInvalidIdentifierSuccess(){
        ErrorObject actualErrorObject=Utils.setError(8);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Identifier");

    }

    @Test
    public void setErrorValidationInvalidAuthCodeSuccess(){
        ErrorObject actualErrorObject=Utils.setError(9);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Authorisation Code");
    }

    @Test
    public void setErrorValidationInvalidQuotationReferenceSuccess(){
        ErrorObject actualErrorObject=Utils.setError(10);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Quotation Reference");
    }


    @Test
    public void setErrorValidationInvalidQuotationMaxSuccess(){
        ErrorObject actualErrorObject=Utils.setError(11);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid account identifier format!maximum 3 identifiers are allowed");
    }

    @Test
    public void setErrorValidationInvalidBillReferenceSuccess(){
        ErrorObject actualErrorObject=Utils.setError(12);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid bill Reference");
    }

    @Test
    public void setErrorValidationInvalidPatchObjectSuccess(){
        ErrorObject actualErrorObject=Utils.setError(13);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Patch Object");

    }

    @Test
    public void setErrorValidationInvalidIdentityIdSuccess(){
        ErrorObject actualErrorObject=Utils.setError(14);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Identity Id");

    }

    @Test
    public void setErrorValidationInvalidBatchIdSuccess(){
        ErrorObject actualErrorObject=Utils.setError(15);
        assertEquals(actualErrorObject.getErrorCategory(),"validation");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"Invalid Batch Id");

    }



    @Test
    public void setErrorValidationInvalidDefaultErrorSuccess(){
        ErrorObject actualErrorObject=Utils.setError(16);
        assertEquals(actualErrorObject.getErrorCategory(),"");
        assertEquals(actualErrorObject.getErrorCode(),"GenericError");
        assertEquals(actualErrorObject.getErrorDescription(),"");
    }

    @Test
    public void generateUUIDNotNullSuccess() {
        String uuid=Utils.generateUUID();
        assertNotNull(uuid);
    }

    @Test
    public void setCallbackUrlSuccess(){
        String callbackURl="https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io";
        String actualCallBackURl=Utils.setCallbackUrl(NotificationMethod.CALLBACK,callbackURl);
        assertEquals(callbackURl,actualCallBackURl);

    }

    @Test
    public void setNullCallBackNullUrlSuccess(){

        String actualCallBackURl=Utils.setCallbackUrl(null,null);
        assertEquals(actualCallBackURl,"");
    }

    @Test
    public void setNullCallbackUrlSuccess(){
        String callbackURl="https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io";
        String actualCallBackURl=Utils.setCallbackUrl(null,callbackURl);
        assertEquals(actualCallBackURl,"");
    }

    @Test
    public void setCallbackNullUrlSuccess(){
        String actualCallBackURl=Utils.setCallbackUrl(NotificationMethod.CALLBACK,null);
        assertEquals(actualCallBackURl,"");
    }

    @Test
    public void setPollingURLSuccess(){
        String callbackURl="https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io";

        String actualCallBackURl=Utils.setCallbackUrl(NotificationMethod.POLLING,callbackURl);

        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);

    }

    @Test
    public void setNullPollingNullUrlSuccess(){

        String actualCallBackURl=Utils.setCallbackUrl(null,null);

        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);

    }

    @Test
    public void setPollingNullUrlSuccess(){

        String actualCallBackURl=Utils.setCallbackUrl(NotificationMethod.POLLING,null);

        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);

    }
    @Test
    public void setNullPollingURLSuccess(){
        String callbackURl="https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io";

        String actualCallBackURl=Utils.setCallbackUrl(null,callbackURl);

        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);
    }

    @Test
    public void setPollingEmptyUrlSuccess(){
        String actualCallBackURl=Utils.setCallbackUrl(NotificationMethod.POLLING,"");
        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);
    }

    @Test
    public void setNullNotificationMethodEmptySuccess(){
        String actualCallBackURl=Utils.setCallbackUrl(null,"");
        assertNotNull(actualCallBackURl);
        assertEquals("",actualCallBackURl);
    }



}