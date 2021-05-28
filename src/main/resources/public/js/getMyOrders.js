const mParentContainerBuying = document.getElementById("cont-myBuying");
const mParentContainerSelling = document.getElementById("cont-mySelling");

function createOrderStatus(parentDiv, jsonObject) {
  var orderStatusChild = document.createElement("P");

  var orderStatus = document.createElement("DIV");
  orderStatus.appendChild(
    orderStatusChild.appendChild(
      document.createTextNode(jsonObject.orderStatus)
    )
  );
  orderStatus.className = "orderItem odd";

  parentDiv.appendChild(orderStatus);

  createOrderButtonLogic(parentDiv,jsonObject);
}

function createOrderDiv(jsonObject) {
  var parentDiv = document.createElement("DIV");
  parentDiv.className = "line";

  var orderIdChild = document.createElement("P");
  var orderItemChild = document.createElement("P");
  var orderDateChild = document.createElement("P");
  var orderPriceChild = document.createElement("P");

  var orderId = document.createElement("DIV");
  orderId.className = "orderItem odd";

  orderId.appendChild(
    orderIdChild.appendChild(document.createTextNode(jsonObject.id))
  );

  var orderItem = document.createElement("DIV");
  orderItem.className = "orderItem";

  orderItem.appendChild(
    orderItemChild.appendChild(document.createTextNode(jsonObject.orderItem))
  );

  var orderDate = document.createElement("DIV");
  orderDate.className = "orderItem odd";

  orderDate.appendChild(
    orderDateChild.appendChild(
      document.createTextNode(jsonObject.orderDate.split("T")[0])
    )
  );

  var orderPrice = document.createElement("DIV");
  orderPrice.className = "orderItem";

  orderPrice.appendChild(
    orderPriceChild.appendChild(
      document.createTextNode("£" + jsonObject.orderPrice)
    )
  );

  parentDiv.appendChild(orderId);
  parentDiv.appendChild(orderItem);
  parentDiv.appendChild(orderDate);
  parentDiv.appendChild(orderPrice);

  createOrderStatus(parentDiv, jsonObject);

  return parentDiv;
}

function augmentDate(data, parentContainer) {
  if (data._embedded != null) {
    var jsonObjectArray = data._embedded.orderList;

    for (i = 0; i < jsonObjectArray.length; i++) {
      var rootDiv = createOrderDiv(jsonObjectArray[i]);

      parentContainer.appendChild(rootDiv);
    }
  }
}

getDataWithToken("/orders/me/buying").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerBuying);
});

getDataWithToken("/orders/me/selling").then(data => {
  console.log(data); // JSON data parsed by `data.json()` call

  augmentDate(data, mParentContainerSelling);
});
