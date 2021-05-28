getDataWithToken("/users/me").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  var date = data.userDOB;

  document
    .getElementById("name")
    .appendChild(document.createTextNode(data.userName));
  document
    .getElementById("dob")
    .appendChild(document.createTextNode(date.split("T")[0]));
  document
    .getElementById("address")
    .appendChild(document.createTextNode(data.userAdress));
  document
    .getElementById("email")
    .appendChild(document.createTextNode(data.userEmail));
  document
    .getElementById("contact")
    .appendChild(document.createTextNode(data.userContactNumber));
});
