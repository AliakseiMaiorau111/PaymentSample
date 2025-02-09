# PaymentSample
Just simple payment sample app with 2 screens

The app modules:

app 		- The main executable model of the app, the entry point

core:common     - Empty module, but can be used later, e.g. for configuring Coroutine Dispathers, etc

core:data       - Data layer, contains Payment repository

core:paymentapi - Api layer, contains network implementation logic (retrofit, etc)

feature:pinpad  - The PinPad app, contains implementation of the 2 screens + Unit tests
