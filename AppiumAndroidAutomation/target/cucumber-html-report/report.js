$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("\u0027login.feature\u0027");
formatter.feature({
  "line": 1,
  "name": "Logging into Saleforce",
  "description": "\nIn order to work\nAs a sales person\nI want to login",
  "id": "logging-into-saleforce",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 7,
  "name": "As a user im able to login into the application",
  "description": "",
  "id": "logging-into-saleforce;as-a-user-im-able-to-login-into-the-application",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "i am on application landing page",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "i login to the application if the user is not logged in",
  "keyword": "When "
});
formatter.match({
  "location": "GroceryTest.pageTour()"
});
formatter.result({
  "duration": 7823525380,
  "status": "passed"
});
formatter.match({
  "location": "GroceryTest.login()"
});
formatter.result({
  "duration": 28551848978,
  "status": "passed"
});
});