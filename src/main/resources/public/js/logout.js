var mToken = "token";
var mUser = "username";

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
}

if (getCookie(mToken) != null) {
  document.cookie =
    mToken + "=" + getCookie(mToken) + ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
}

if (getCookie(mUser) != null) {
  document.cookie =
    mUser + "=" + getCookie(mUser) + ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
}

window.location.replace("/");
