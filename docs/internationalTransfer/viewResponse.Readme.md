# View A Response


Pass the correlation id of missing response into a viewResponse function to get the missing response

### Usage/Examples

```java


        SDKManager.internationalTransfer.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
              
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }
        });



```

### Example Output

```json



```
