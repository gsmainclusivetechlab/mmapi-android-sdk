# Create A New Quotation

`Here, createQuotation() creates a POST request to /quotations`

> `Provided with a valid object representation, this endpoint allows for a new quotation to be created.`
### Usage/Examples

```javascript
/**
 * Set up your function to be invoked
 */
const createQuotation = async (body, polling = false) => {
  try {
    /**
     * Construct a request object and set desired parameters
     */
    const request = new mmapi.internationalTransfer.createQuotation();
    console.log('Request X-CorrelationID', request.headers['X-CorrelationID']);
    /**
     * Set the request body parameter
     */
    request.creditParty(body.creditParty);
    request.debitParty(body.debitParty);
    request.type(body.type);
    request.subtype(body.subtype);
    request.requestAmount(body.requestAmount);
    request.requestCurrency(body.requestCurrency);
    request.chosenDeliveryMethod(body.chosenDeliveryMethod);
    request.originCountry(body.originCountry);
    request.receivingCountry(body.receivingCountry);
    request.recipientKyc(body.recipientKyc);
    request.senderKyc(body.senderKyc);
    request.requestingOrganisation(body.requestingOrganisation);
    request.sendingServiceProviderCountry(body.sendingServiceProviderCountry);
    request.requestDate(body.requestDate);
    request.customData(body.customData);
    request.metadata(body.metadata);
    /**
     * Chose the polling method.
     */
    if (polling) {
      request.polling();
    }
    /**
     * Call API with your client and get a response for your call
     */
    const response = await client.execute(request);
    console.log("Response Status: ", response.status);
    console.log("Response Data: ", JSON.stringify(response.data, null, 4));
    /**
     * Return a successful response
     */
    return response;
  } catch (err) {
    /**
     * Handle any errors from the call
     */
    console.log(err);
    /**
     * Return an error response
     */
    return err;
  }
};
/**
 * Invoke the function
 */
createQuotation('<<REPLACE-WITH-REQUEST-BODY>>');
```

### Example Output - Callback

```javascript
202
{
  "serverCorrelationId": "7a20ef01-996c-4652-95ee-13766f116544",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "535",
  "pollLimit": 100
}
```

### Example Output - Polling

```javascript
202
{
  "serverCorrelationId": "eb95b1b5-79bb-4729-9d7c-67d8bd357f8e",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "804",
  "pollLimit": 100
}
