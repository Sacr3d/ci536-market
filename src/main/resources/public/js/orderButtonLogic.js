function createOrderButtonLogic(parentDiv, jsonObject) {
  var itemOrderBtnChild = document.createElement("BUTTON");
  itemOrderBtnChild.type = "submit";
  itemOrderBtnChild.setAttribute("onclick", "updateOrder(this)");

  var orderStatus = jsonObject.orderStatus;

  if (getCookie("username") != jsonObject.orderSeller) {
    switch (orderStatus) {
      case "AWATING_PAYMENT":
        itemOrderBtnChild.innerHTML = "PAY";
        itemOrderBtnChild.value = jsonObject._links.order.href + "/pay";
        parentDiv.appendChild(itemOrderBtnChild);
        break;
      case "SHIPPED":
        itemOrderBtnChild.innerHTML = "RECIVED";
        itemOrderBtnChild.value = jsonObject._links.order.href + "/recive";
        parentDiv.appendChild(itemOrderBtnChild);
        break;
      case "COMPLETED":
        itemOrderBtnChild.innerHTML = "REQUEST_REFUND";
        itemOrderBtnChild.value = jsonObject._links.order.href + "/refundReq";
        parentDiv.appendChild(itemOrderBtnChild);
        break;
    }
  }

  if (getCookie("username") == jsonObject.orderSeller) {
    switch (orderStatus) {
      case "PAID":
        itemOrderBtnChild.innerHTML = "SHIP";
        itemOrderBtnChild.value = jsonObject._links.order.href + "/ship";
        parentDiv.appendChild(itemOrderBtnChild);
        break;
      case "REQUEST_REFUND":
        itemOrderBtnChild.innerHTML = "REFUND";
        itemOrderBtnChild.value = jsonObject._links.order.href + "/refund";
        parentDiv.appendChild(itemOrderBtnChild);
        break;
    }
  }
}
