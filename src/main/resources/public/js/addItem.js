function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
}

function getCheckedBoxes(chkboxName) {
  var checkboxes = document.getElementsByClassName(chkboxName);
  var checkboxesChecked = [];
  // loop over them all
  for (var i = 0; i < checkboxes.length; i++) {
    // And stick the checked ones onto an array...
    if (checkboxes[i].checked) {
      checkboxesChecked.push(checkboxes[i].value);
    }
  }
  // Return the array if it is non-empty, or null
  return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}

// Example POST method implementation:
async function postDataWithToken(url = "", data = {}) {
  // Default options are marked with *
  const response = await fetch(url, {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json; utf-8",
      Authorization: "Bearer " + getCookie("token")
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data) // body data type must match "Content-Type" header
  });
  return response.json(); // parses JSON response into native JavaScript objects
}

document.getElementById("submitBtn").addEventListener("click", submit);

function submit() {
  var categoryArray = getCheckedBoxes("categoryCheckbox");
  var itemName = document.getElementById("itemName").value;
  var itemCategory = categoryArray;
  var itemPrice = document.getElementById("itemPrice").value;

  if (itemName != null && itemCategory != null && itemPrice != null) {
    var data = {
      itemName: itemName,
      itemCategory: itemCategory,
      itemPrice: itemPrice
    };
    postDataWithToken("/items/add", data).then(data => {
      console.log(data); // JSON data parsed by `data.json()` call

      window.location.replace("/post_listing.html");
    });
  }
}
