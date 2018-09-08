Checkout Component 3.0
Business requirements:
1. Design and implement market checkout component with readable API that calculates the
total price of a number of carts.
2. Checkout mechanism can scan carts and return actual price (is stateful).
3. Our goods are priced individually.
4. Some carts are multi-priced: buy N of them, and they’ll cost you Y cents.
Item Price Unit Special Price
A 40 3 for 70
B 10 2 for 15
C 30 4 for 60
D 25 2 for 40
5. Client receives receipt containing list of all products with corresponding prices after
payment.
6. Some carts are cheaper when bought together - buy item X with item Y and save Z
cents.
Technical requirements:
1. The output of this task should be a project with buildable production ready service, that
can be executed independently of the source code.
2. Project should include all tests, including unit, integration and acceptance tests.
3. The service should expose REST api, without any UI.
4. You can use gradle, maven or any other building tool.
5. It ought to be written in Java 8.
6. If requested, please use Spring or any other framework, otherwise, the choice is yours.

-------------------------------------------------------------------------------------------



