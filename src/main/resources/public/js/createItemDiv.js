function createItemDiv(jsonObject) {
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

  if (getCookie("token") != null) {
    var itemOrderBtnChild = document.createElement("BUTTON");
    itemOrderBtnChild.innerHTML = "ORDER";
    itemOrderBtnChild.value = jsonObject._links.useritem.href + "/order";
    itemOrderBtnChild.type = "submit";
    itemOrderBtnChild.setAttribute("onclick", "updateOrder(this)");
    parentDiv.appendChild(itemOrderBtnChild);
  }

  return parentDiv;
}
