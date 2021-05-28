var mPrivateHeaders = [
  createLinkNode(document.createTextNode("Home")),
  createLinkNode(document.createTextNode("Browse")),
  createLinkNode(document.createTextNode("Orders")),
  createLinkNode(document.createTextNode("Post Listing")),
  createLinkNode(document.createTextNode("Profile")),
  createLinkNode(document.createTextNode("Logout"))
];

var mPublicHeaders = [
  createLinkNode(document.createTextNode("Home")),
  createLinkNode(document.createTextNode("Browse")),
  createLinkNode(document.createTextNode("Login"))
];

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
}

// create <header>
var mHeaderNode = document.createElement("HEADER");
mHeaderNode.id = "myHeader";
document.body.appendChild(mHeaderNode);

// create <h1>
var mH1Node = document.createElement("h1");
var mTextNode = document.createTextNode("Marketplace");
mH1Node.appendChild(mTextNode);

// create <nav> and <nav> items
var mNavNode = document.createElement("UL");
mNavNode.id = "nav";

document.getElementById("myHeader").appendChild(mH1Node);
document.getElementById("myHeader").appendChild(mNavNode);

if (getCookie("token") == null) {
  mPublicHeaders.forEach(element => {
    var liNode = document.createElement("LI");
    liNode.appendChild(element);
    mNavNode.appendChild(liNode);
  });
} else {
  mPrivateHeaders.forEach(element => {
    var liNode = document.createElement("LI");
    liNode.appendChild(element);
    mNavNode.appendChild(liNode);
  });
}

function createLinkNode(node) {
  let text = node.textContent;

  var aNode = document.createElement("A");
  aNode.class = "link";

  switch (text) {
    case "Home":
      aNode.href = "/";
      break;
    case "Browse":
      aNode.href = "/browse.html";
      break;
    case "Orders":
      aNode.href = "/orders.html";
      break;
    case "Post Listing":
      aNode.href = "/post_listing.html";
      break;
    case "Login":
      aNode.href = "/login.html";
      break;
    case "Profile":
      aNode.href = "/profile.html";
      break;
    case "Logout":
      aNode.href = "/logout.html";
      break;
    default:
      break;
  }

  aNode.appendChild(node);
  return aNode;
}
