const mParentContainer = document.getElementById("cont-trend");

function augmentData(data) {
  if (data._embedded != null) {
    var jsonObjectArray = data._embedded.itemList;

    for (i = 0; i < jsonObjectArray.length; i++) {
      var rootDiv = createItemDiv(jsonObjectArray[i]);

      mParentContainer.appendChild(rootDiv);
    }
  }
}

function changeCategory(element) {
  mParentContainer.innerHTML = "";

  var id = element.id;

  getData("/items/category=" + id).then(data => {
    console.log(data); // JSON data parsed by `data.json()` call

    augmentData(data);
  });
}

getData("/items/trending").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentData(data);
});
