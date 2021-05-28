const mParentContainerListed = document.getElementById("cont-myItems");
const mParentContainerDelisted = document.getElementById("cont-myDelisted");
const mParentContainerSold = document.getElementById("cont-mySold");

function createItemDiv(jsonObject, parentContainer) {
  var parentDiv = document.createElement("DIV");
  parentDiv.className = "item";

  var imgChild = document.createElement("IMG");
  imgChild.src = "https://via.placeholder.com/200";

  var itemNameChild = document.createElement("P");
  var itemPriceChild = document.createElement("P");
  var itemDateChild = document.createElement("P");

  itemNameChild.appendChild(document.createTextNode(jsonObject.itemName));
  itemPriceChild.appendChild(
    document.createTextNode("£" + jsonObject.itemPrice)
  );
  itemDateChild.appendChild(
    document.createTextNode(jsonObject.itemCreatedDate.split("T")[0])
  );

  parentDiv.appendChild(imgChild);
  parentDiv.appendChild(itemNameChild);
  parentDiv.appendChild(itemPriceChild);
  parentDiv.appendChild(itemDateChild);

  if (parentContainer == mParentContainerDelisted) {
    getOrderStatus(jsonObject).then(data => {
      var orderStatusChild = document.createElement("P");
      orderStatusChild.className = data.orderStatus;
      orderStatusChild.appendChild(document.createTextNode(data.orderStatus));
      parentDiv.appendChild(orderStatusChild);
      return parentDiv;
    });
  }

  return parentDiv;
}

function augmentDate(data, parentContainer) {
  if (data._embedded != null) {
    var jsonObjectArray = data._embedded.itemList;

    for (i = 0; i < jsonObjectArray.length; i++) {
      var rootDiv = createItemDiv(jsonObjectArray[i], parentContainer);

      parentContainer.appendChild(rootDiv);
    }
  }
}

getDataWithToken("/items/me=LISTED").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerListed);
});

getDataWithToken("/items/me=DELISTED").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerDelisted);
});

getDataWithToken("/items/me=SOLD").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerSold);
});
