# Check Api Availability

The availability of a service can be checked using this functions

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
