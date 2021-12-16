
# Check API availability

`Here, viewServiceAvailability() creates a GET request to /heartbeat`

> `This endpoint returns the current status of the API.`


### Usage/Examples

```java

   SDKManager.internationalTransfer.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });



```

### Example Output

```json
200
{
  "serviceStatus": "available"
}
```
