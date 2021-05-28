async function getOrderStatus(jsonObject) {
  var itemId = jsonObject.id;

  const orderJSON = await getDataWithToken(
    "/orders/orderStatusOf=" + itemId
  ).then(data => {
    if (data.status == 404) {
      alert("Order for item " + jsonObject.itemName + " could not be found");
    }
  });

  return orderJSON;
}

function updateOrder(element) {
  var objectAdress = element.value;

  postWithToken(objectAdress).then(data => {
    console.log(data);

    var status = data.status;

    switch (status) {
      case 406:
        alert("Cannot buy your own item");
        break;
      case 404:
        alert("Item no longer avalible");
        window.location.reload();
        break;
      default:
        window.location.reload();
        break;
    }
  });
}
