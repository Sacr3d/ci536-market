const mParentContainerRecent = document.getElementById("cont-recent");
const mParentContainerTrending = document.getElementById("cont-trending");

function augmentDate(data, parentContainer) {
  if (data._embedded != null) {
    var jsonObjectArray = data._embedded.itemList;

    for (i = 0; i < jsonObjectArray.length; i++) {
      var rootDiv = createItemDiv(jsonObjectArray[i]);

      parentContainer.appendChild(rootDiv);
    }
  }
}

getData("/items/recent=4").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerRecent);
});

getData("/items/trending=4").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerTrending);
});
