# PIN SELECT SDK

This sdk is meant to enable banks to change or create the customers card pins through a secure interface.

## Process Flow

This is the basic flow for all the functions
![img.png](img.png)

## Adding it to a project
```groovy
implementation 'com.github.interswitch-kenya-limited:pinonmobile:0.1.0'
```

Ensure that the jitpack io repository is part of your project by adding it to the root build.gradle in the allprojects => repositories section

### Configuring
Edit you app manifest file to add the following configuration metadata inside the application tag, the values given here are for test and you will need to change them once you are ready to go live.

```xml
 <meta-data
            android:name="interswitch-kenya-limited.pin_on_mobile.base_url"
            android:value="https://testids.interswitch.co.ke/identity/api/v1/"/>
```
Finally ensure data binding is enabled in the root gradle file of your projects by adding the following config in its android section

```groovy
dataBinding {
    enabled = true
}
```

## Usage examples
```java
// create an instance of the institution class

Institution institution = new Institution(
                                   clientId,
                                   clientSecret,
                                   institutionId,
                                   rsaPubKeyString,
                                   rsaPrivateKeyString,
                                   keyId);
                                   
// create an instance of the clients account

Account account = new Account(isDebit, cardSerialNumber);

/*
 * cardSerialNumber is a String value.
 *
 * isDebit is a boolean value(true/false) passed to show 
 * whether the serial number provided is for a debit card or not.
 */

// The instance of an activity that will be active until the process is completed

PinOnMobile pinOnMobile = PinOnMobile.getInstance(MainActivity.this,institution,account);

// Call the desired method

pinOnMobile.changePin(
                        response -> {
                            progressIndicator.setVisibility(View.GONE);

                            Snackbar.make(view, "Pin Changed Successfully", Snackbar.LENGTH_LONG)
                                    .show();
                        },
                        error -> {
                            progressIndicator.setVisibility(View.GONE);
                            
                            Snackbar.make(view, "ERROR::"+error.getMessage(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                );

```

## Source code

Visit [the github repository](https://github.com/interswitch-kenya-limited/pin_on_mobile/tree/main/pinonmobile) to get the source code and releases of this project if you want to try a manual integration process that does not make use of gradle.
